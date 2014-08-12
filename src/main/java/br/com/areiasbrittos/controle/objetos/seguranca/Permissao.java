package br.com.areiasbrittos.controle.objetos.seguranca;
// Generated 06/09/2011 03:48:18 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Permissao generated by hbm2java
 */
public class Permissao  implements java.io.Serializable {


     private Integer idPermissao;
     private String descricao;
     private Set permissaoUsuarios = new HashSet(0);

    public Permissao() {
    }

    public Permissao(Integer idPermissao){
        this.idPermissao = idPermissao;
    }
	
    public Permissao(String descricao) {
        this.descricao = descricao;
    }
    public Permissao(String descricao, Set permissaoUsuarios) {
       this.descricao = descricao;
       this.permissaoUsuarios = permissaoUsuarios;
    }
   
    public Integer getIdPermissao() {
        return this.idPermissao;
    }
    
    public void setIdPermissao(Integer idPermissao) {
        this.idPermissao = idPermissao;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Set getPermissaoUsuarios() {
        return this.permissaoUsuarios;
    }
    
    public void setPermissaoUsuarios(Set permissaoUsuarios) {
        this.permissaoUsuarios = permissaoUsuarios;
    }




}


