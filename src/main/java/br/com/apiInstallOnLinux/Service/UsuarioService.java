/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.Usuario;
import br.com.apiInstallOnLinux.Repository.UsuarioRepository;
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
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if(!usuarioBuscado.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return usuarioBuscado.get();
    }

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioAtualmenteSalvo = buscarPorId(id);
        BeanUtils.copyProperties(usuario, usuarioAtualmenteSalvo, "id", "dataCadastro");
        return usuarioRepository.save(usuarioAtualmenteSalvo);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(ativo);
        usuarioRepository.save(usuario);
    }
}
