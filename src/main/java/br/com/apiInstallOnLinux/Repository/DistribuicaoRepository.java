/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Repository;

import br.com.apiInstallOnLinux.Model.Distribuicao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alciran
 */
public interface DistribuicaoRepository extends JpaRepository<Distribuicao, Long> {
    public List<Distribuicao> findByAtivo(Boolean ativo);
}
