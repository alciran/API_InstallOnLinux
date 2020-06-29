/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.Distribuicao;
import br.com.apiInstallOnLinux.Service.DistribuicaoService;
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
@RequestMapping("/distribuicoes")
public class DistribuicaoResource {
    
    @Autowired
    private DistribuicaoService distribuicaoService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    public List<Distribuicao> listar(){
        return distribuicaoService.buscarTodos();
    }
    
    @GetMapping("/{id}")
    public Distribuicao buscarDistribuicao(@PathVariable Long id){
        return distribuicaoService.buscarPorId(id);
    }
    
    @PostMapping
    public ResponseEntity<Distribuicao> criar(@Valid @RequestBody Distribuicao distribuicao, HttpServletResponse response){
        Distribuicao distribuicaoCriada = distribuicaoService.criar(distribuicao);
         publisher.publishEvent(new RecursoCriadoEvent(this, response, distribuicaoCriada.getId()));
         return ResponseEntity.status(HttpStatus.CREATED).body(distribuicaoCriada);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Distribuicao> atualizar(@PathVariable Long id, @Valid @RequestBody Distribuicao distribuicao){
        Distribuicao distribuicaoAtualizada = distribuicaoService.atualizar(id, distribuicao);
        return ResponseEntity.ok(distribuicaoAtualizada);
    }
    
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long id, @RequestBody boolean ativo){
        distribuicaoService.atualizarPropriedadeAtivo(id, ativo);
    }
    
    @PutMapping("/{id}/addNumDownScript")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addNumDownScript(@PathVariable Long id){
        distribuicaoService.addDownScript(id);
    }
}
