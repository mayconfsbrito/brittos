package br.com.areiasbrittos.controle.objetos;
// Generated 27/07/2012 19:47:38 by Hibernate Tools 3.2.1.GA

import java.sql.Blob;



/**
 * Conf generated by hbm2java
 */
public class Conf  implements java.io.Serializable {


     private Integer idConf;
     private String validadeTransacoes;
     private String imagem;
     private String nome;
     private String logradouro;
     private String numero;
     private String bairro;
     private String cep;
     private String cidade;
     private String telefone;
     private String cnpj;
     private boolean bkpAtivacao;
     private byte[] bkpEndereco;
     private boolean bkpAviso;

    public Conf() {
    }

    public Conf(String validadeTransacoes, String imagem, String nome, String logradouro, String numero, String bairro, 
            String cep, String cidade, String telefone, String cnpj, boolean bkpAtivacao, byte[] bkpEndereco, boolean bkpAviso) {
       this.validadeTransacoes = validadeTransacoes;
       this.imagem = imagem;
       this.nome = nome;
       this.logradouro = logradouro;
       this.numero = numero;
       this.bairro = bairro;
       this.cep = cep;
       this.cidade = cidade;
       this.telefone = telefone;
       this.cnpj = cnpj;
       this.bkpAtivacao = bkpAtivacao;
       this.bkpEndereco = bkpEndereco;
       this.bkpAviso = bkpAviso;
    }
   
    public Integer getIdConf() {
        return this.idConf;
    }
    
    public void setIdConf(Integer idConf) {
        this.idConf = idConf;
    }
    public String getValidadeTransacoes() {
        return this.validadeTransacoes;
    }
    
    public void setValidadeTransacoes(String validadeTransacoes) {
        this.validadeTransacoes = validadeTransacoes;
    }
    public String getImagem() {
        return this.imagem;
    }
    
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLogradouro() {
        return this.logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    public String getNumero() {
        return this.numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getBairro() {
        return this.bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCep() {
        return this.cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getCidade() {
        return this.cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getTelefone() {
        return this.telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCnpj() {
        return this.cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isBkpAtivacao() {
        return bkpAtivacao;
    }

    public void setBkpAtivacao(boolean bkpAtivacao) {
        this.bkpAtivacao = bkpAtivacao;
    }

    public byte[] getBkpEndereco() {
        return bkpEndereco;
    }

    public void setBkpEndereco(byte[] bkpEndereco) {
        this.bkpEndereco = bkpEndereco;
    }

    public boolean isBkpAviso() {
        return bkpAviso;
    }

    public void setBkpAviso(boolean bkpAviso) {
        this.bkpAviso = bkpAviso;
    }

    
    
}


