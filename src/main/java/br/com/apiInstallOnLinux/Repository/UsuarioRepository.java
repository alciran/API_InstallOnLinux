/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Repository;

import br.com.apiInstallOnLinux.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alciran
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
