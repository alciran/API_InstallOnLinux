/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.Software;
import br.com.apiInstallOnLinux.Repository.SoftwareRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author alciran
 */
@Service
public class SoftwareService {
    
    @Autowired
    private SoftwareRepository softwareRepository;

    public List<Software> buscarTodos() {
        return softwareRepository.findAll();
    }

    public Software buscarPorId(Long id) {
        Optional<Software> softwareBuscado = softwareRepository.findById(id);
        if(!softwareBuscado.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return softwareBuscado.get();
    }

    public Software criar(Software software) {
        return softwareRepository.save(software);
    }

    public Software atualizar(Long id, Software software) {
        Software softwareAtualmenteSalvo = buscarPorId(id);
        BeanUtils.copyProperties(software, softwareAtualmenteSalvo, "id");
        return softwareRepository.save(softwareAtualmenteSalvo);        
    }

    public void addNumDownScript(Long id) {
        Software softwareAtualizado = buscarPorId(id);
        softwareAtualizado.addNumDownScript();
        softwareRepository.save(softwareAtualizado);
    }
    
}
