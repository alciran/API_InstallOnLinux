/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.Usuario;
import br.com.apiInstallOnLinux.Service.UsuarioService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alciran
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    public List<Usuario> listar(){
        return usuarioService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public Usuario bucar(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
        Usuario usuarioCriado = usuarioService.criar(usuario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,@Valid @RequestBody Usuario usuario ){
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok().body(usuarioAtualizado);
    }
    
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody boolean ativo){
        usuarioService.atualizarPropriedadeAtivo(id, ativo);
    }
    
}
