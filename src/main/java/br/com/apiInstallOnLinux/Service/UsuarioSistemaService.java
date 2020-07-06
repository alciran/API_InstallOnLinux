/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.UsuarioSistema;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import br.com.apiInstallOnLinux.Repository.UsuarioSistemaRepository;

/**
 *
 * @author alciran
 */
@Service
public class UsuarioSistemaService {
    
    @Autowired
    private UsuarioSistemaRepository usuarioRepository;

    public List<UsuarioSistema> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public UsuarioSistema buscarPorId(Long id) {
        Optional<UsuarioSistema> usuarioBuscado = usuarioRepository.findById(id);
        if(!usuarioBuscado.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return usuarioBuscado.get();
    }

    public UsuarioSistema criar(UsuarioSistema usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioSistema atualizar(Long id, UsuarioSistema usuario) {
        UsuarioSistema usuarioAtualmenteSalvo = buscarPorId(id);
        BeanUtils.copyProperties(usuario, usuarioAtualmenteSalvo, "id", "dataCadastro");
        return usuarioRepository.save(usuarioAtualmenteSalvo);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo) {
        UsuarioSistema usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        usuarioRepository.save(usuario);
    }
}
