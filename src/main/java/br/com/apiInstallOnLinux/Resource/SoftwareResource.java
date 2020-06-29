/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.Software;
import br.com.apiInstallOnLinux.Service.SoftwareService;
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
@RequestMapping("/softwares")
public class SoftwareResource {
    
    @Autowired
    private SoftwareService softwareService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    public List<Software> listar(){
        return softwareService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public Software buscarSoftware(@PathVariable Long id){
        return softwareService.buscarPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<Software> criar(@Valid @RequestBody Software software, HttpServletResponse response){
        Software softwareCriado = softwareService.criar(software);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, softwareCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(softwareCriado);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Software> atualizar(@PathVariable Long id, @Valid @RequestBody Software software){
        Software softwareAtualizado = softwareService.atualizar(id, software);
        return ResponseEntity.ok().body(softwareAtualizado);
    }
    
    @PutMapping("/{id}/addNumDownScript")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNumDownScript(@PathVariable Long id){
        softwareService.addNumDownScript(id);
    }
        
}
