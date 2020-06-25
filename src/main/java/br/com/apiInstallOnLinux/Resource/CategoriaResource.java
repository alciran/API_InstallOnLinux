/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.Categoria;
import br.com.apiInstallOnLinux.Service.CategoriaService;
import br.com.apiInstallOnLinux.event.RecursoCriadoEvent;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alciran
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    public List<Categoria> listar(){
        return categoriaService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public Categoria buscarCategoria(@PathVariable Long id){
        return categoriaService.buscarPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria,  HttpServletResponse response){
        Categoria categoriaCriada = categoriaService.criar(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaCriada.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
        Categoria categoriaAtualizada = categoriaService.atualizar(id, categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        categoriaService.remover(id);
    }
}
