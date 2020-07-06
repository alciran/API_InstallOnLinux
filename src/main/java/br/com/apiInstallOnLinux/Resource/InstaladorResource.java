/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Resource;

import br.com.apiInstallOnLinux.Model.Instalador;
import br.com.apiInstallOnLinux.Service.InstaladorService;
import br.com.apiInstallOnLinux.Service.exception.InstaladorJaExistenteException;
import br.com.apiInstallOnLinux.Service.exception.UsuarioComInstaladorStatusCriandoOuTestando;
import br.com.apiInstallOnLinux.event.RecursoCriadoEvent;
import br.com.apiInstallOnLinux.exceptionHandler.apiInstallOnLinuxExceptionHandler;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alciran
 */
@RestController
@RequestMapping("/instaladores")
public class InstaladorResource {
    
    @Autowired
    private InstaladorService instaladorService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @Autowired
    private MessageSource messageSource;
    
    @GetMapping
    public List<Instalador> listarAprovados(){
        return instaladorService.listarAprovados();
    }
    
    @GetMapping("/{id}")
    public Instalador buscar(@PathVariable Long id){
        return instaladorService.buscarPorId(id);
    }
    
    @GetMapping("/distribuicao/{id}")
    public List<Instalador> listarPorDistribuicao(@PathVariable Long id){
        return instaladorService.listarPorDistribuicao(id);
    }
    
    @GetMapping("/software/{id}/{id}")
    public Instalador buscarInstaladorPorSoftware(@PathVariable Long idDistribuicao, @PathVariable Long idSoftware){
        return instaladorService.buscarInstaladorPorSoftware(idDistribuicao, idSoftware);
    }
    
    @PostMapping
    public ResponseEntity<Instalador> criar(@Valid @RequestBody Instalador instalador, HttpServletResponse response){
        Instalador instaladorCriado = instaladorService.criar(instalador);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, instaladorCriado.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(instaladorCriado);
    }
    
    @ExceptionHandler({ InstaladorJaExistenteException.class })
    public ResponseEntity<Object> handleInstaladorJaExistenteException(InstaladorJaExistenteException ex){
        String mensagemUsuario = messageSource.getMessage("instalador.ja.existente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<apiInstallOnLinuxExceptionHandler.Erro> erros = Arrays.asList(new apiInstallOnLinuxExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
    
    @ExceptionHandler({ UsuarioComInstaladorStatusCriandoOuTestando.class })
    public ResponseEntity<Object> handleUsuarioComInstaladorStatusCriandoOuTestando(UsuarioComInstaladorStatusCriandoOuTestando ex){
        String mensagemUsuario = messageSource.getMessage("usuario.instalador.criando.ou.testando", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<apiInstallOnLinuxExceptionHandler.Erro> erros = Arrays.asList(new apiInstallOnLinuxExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
    
    @PutMapping("/{id}")
    private ResponseEntity<Instalador> atualizar(@PathVariable Long id, @Valid @RequestBody Instalador instalador){
        Instalador instaladorAtualizado = instaladorService.atualizar(id, instalador);
        return ResponseEntity.ok().body(instaladorAtualizado);
    }
    
    @PutMapping("/{id}/addNumDownScript")
    public void addNumDownScript(@PathVariable Long id){
        instaladorService.addNumDownScript(id);
    }
}
