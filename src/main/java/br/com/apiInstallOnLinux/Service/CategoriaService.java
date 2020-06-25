/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.Categoria;
import br.com.apiInstallOnLinux.Repository.CategoriaRepository;
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
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> buscarTodos() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        Optional<Categoria> categoriaBuscada = categoriaRepository.findById(id);
        if(!categoriaBuscada.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return categoriaBuscada.get();
    }

    public Categoria criar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, Categoria categoria) {
        Categoria categoriaAtualmenteSalva = buscarPorId(id);
        BeanUtils.copyProperties(categoria, categoriaAtualmenteSalva, "id");
        return categoriaRepository.save(categoriaAtualmenteSalva);
    }

    public void remover(Long id) {
        Categoria categoriaExcluir = buscarPorId(id);
        categoriaRepository.delete(categoriaExcluir);
    }
    
}
