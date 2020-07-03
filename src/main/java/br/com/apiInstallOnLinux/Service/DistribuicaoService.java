/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Service;

import br.com.apiInstallOnLinux.Model.Distribuicao;
import br.com.apiInstallOnLinux.Repository.DistribuicaoRepository;
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
public class DistribuicaoService {

    @Autowired
    private DistribuicaoRepository distribuicaoRepository;
    
    public List<Distribuicao> buscarTodos() {
        return distribuicaoRepository.findAll();
    }

    public Distribuicao buscarPorId(Long id) {
        Optional<Distribuicao> distribuicaoBuscada = distribuicaoRepository.findById(id);
        if(!distribuicaoBuscada.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        return distribuicaoBuscada.get();
    }

    public Distribuicao criar(Distribuicao distribuicao) {
        return distribuicaoRepository.save(distribuicao);
    }

    public Distribuicao atualizar(Long id, Distribuicao distribuicao) {
        Distribuicao distribuicaoAtualmenteSalva = buscarPorId(id);
        BeanUtils.copyProperties(distribuicao, distribuicaoAtualmenteSalva, "id");
        return distribuicaoRepository.save(distribuicaoAtualmenteSalva);
    }

    public void atualizarPropriedadeAtivo(Long id, boolean ativo) {
        Distribuicao distribuicaoAtualizada = buscarPorId(id);
        distribuicaoAtualizada.setAtivo(ativo);
        distribuicaoRepository.save(distribuicaoAtualizada);
    }

    public List<Distribuicao> buscarAtivos() {
        return distribuicaoRepository.findByAtivo(Boolean.TRUE);
    }
    
}
