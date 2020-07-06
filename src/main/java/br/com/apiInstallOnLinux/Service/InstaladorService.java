/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.Distribuicao;
import br.com.apiInstallOnLinux.Service.exception.InstaladorJaExistenteException;
import br.com.apiInstallOnLinux.Model.Instalador;
import br.com.apiInstallOnLinux.Model.Software;
import br.com.apiInstallOnLinux.Model.Status;
import br.com.apiInstallOnLinux.Repository.InstaladorRepository;
import br.com.apiInstallOnLinux.Service.exception.UsuarioComInstaladorStatusCriandoOuTestando;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author alciran
 */
@Service
public class InstaladorService {
    
    @Autowired
    private InstaladorRepository instaladorRepository;
    
    @Autowired
    private DistribuicaoService distribuicaoService;
    
    @Autowired
    private SoftwareService softwareService;

    public List<Instalador> listarAprovados() {
        return instaladorRepository.findByStatus(Status.APROVADO, Sort.by("numDownScript", "DataCadastro").ascending());
    }
    
    public Instalador buscarPorId(Long id) {
        Optional<Instalador> instaladorBuscado = instaladorRepository.findById(id);
        if(!instaladorBuscado.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return instaladorBuscado.get();
    }

    public Instalador criar(Instalador instalador) {
        Instalador instaladorDS = instaladorRepository.findByDistribuicaoAndSoftware(
                instalador.getDistribuicao(), instalador.getSoftware());
        if(instaladorDS != null){
            throw new InstaladorJaExistenteException();
        }
        List<Instalador> instaladorUC = instaladorRepository.findByUsuarioAndStatusOrStatus(
                instalador.getUsuario(), Status.CRIANDO, Status.TESTANDO);
        if(instaladorUC != null){
            throw new UsuarioComInstaladorStatusCriandoOuTestando();
        }
        return instaladorRepository.save(instalador);
    }

    public void addNumDownScript(Long id) {
        Instalador instaladorAddNum = buscarPorId(id);
        instaladorAddNum.addNumDownScript();
        instaladorRepository.save(instaladorAddNum);
    }

    public Instalador atualizar(Long id, Instalador instalador) {
        Instalador instaladorAtualmenteSalvo = buscarPorId(id);
        BeanUtils.copyProperties(instalador, instaladorAtualmenteSalvo, "id", "dataCadastro");
        return instaladorRepository.save(instaladorAtualmenteSalvo);
    }

    public List<Instalador> listarPorDistribuicao(Long id) {        
        Distribuicao distribuicaoPesquisada = distribuicaoService.buscarPorId(id);
        return instaladorRepository.findByDistribuicao(distribuicaoPesquisada, Sort.by("numDownScript", "DataCadastro").ascending());
        
    }

    public Instalador buscarInstaladorPorSoftware(Long idDistribuicao, Long idSoftware) {
        Distribuicao distribuicaoPesquisada = distribuicaoService.buscarPorId(idDistribuicao);
        Software softwarePesquisado = softwareService.buscarPorId(idSoftware);
        return instaladorRepository.findByDistribuicaoAndSoftware(distribuicaoPesquisada, softwarePesquisado);
    }
}
