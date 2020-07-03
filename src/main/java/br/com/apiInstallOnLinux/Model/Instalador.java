/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.apiInstallOnLinux.Model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author alciran
 */
@Getter
@Setter
@Entity
@Table(name = "instalador")
public class Instalador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull    
    @ManyToOne
    @JoinColumn(name = "id_distribuicao")
    private Distribuicao distribuicao;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_software")
    private Software software;
    
    private boolean nativo;
    
    @Column(name = "code_install")
    private String codeInstall;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;
    
    @Column(name = "num_down_script")
    private int numDownScript = 0;
    
    public void addNumDownScript(){
        setNumDownScript(this.getNumDownScript() + 1);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instalador other = (Instalador) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }    
       
}
