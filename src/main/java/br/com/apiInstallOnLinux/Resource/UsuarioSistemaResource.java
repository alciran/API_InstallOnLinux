/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.UsuarioSistema;
import br.com.apiInstallOnLinux.Service.UsuarioSistemaService;
import br.com.apiInstallOnLinux.event.RecursoCriadoEvent;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/usuariosSistema")
public class UsuarioSistemaResource {
    
    @Autowired
    private UsuarioSistemaService usuarioService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    public List<UsuarioSistema> listar(){
        return usuarioService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public UsuarioSistema bucar(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<UsuarioSistema> criar(@Valid @RequestBody UsuarioSistema usuario, HttpServletResponse response){
        UsuarioSistema usuarioCriado = usuarioService.criar(usuario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSistema> atualizar(@PathVariable Long id,@Valid @RequestBody UsuarioSistema usuario ){
        UsuarioSistema usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok().body(usuarioAtualizado);
    }
    
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody boolean ativo){
        usuarioService.atualizarPropriedadeAtivo(id, ativo);
    }
    
}
