/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Repository;

import br.com.apiInstallOnLinux.Model.Distribuicao;
import br.com.apiInstallOnLinux.Model.Instalador;
import br.com.apiInstallOnLinux.Model.Software;
import br.com.apiInstallOnLinux.Model.Status;
import br.com.apiInstallOnLinux.Model.UsuarioSistema;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alciran
 */
public interface InstaladorRepository extends JpaRepository<Instalador, Long>  {
    public List<Instalador> findByDistribuicao(Distribuicao distribuicao, Sort sort);
    public List<Instalador> findByStatus(Status status, Sort sort);
    public List<Instalador> findByUsuarioAndDistribuicao(UsuarioSistema usuario, Distribuicao distribuicao);
    public List<Instalador> findByUsuarioAndStatusOrStatus(UsuarioSistema us, Status status, Status status1);
    public Instalador findByDistribuicaoAndSoftware(Distribuicao distribuicao, Software software);    
}
