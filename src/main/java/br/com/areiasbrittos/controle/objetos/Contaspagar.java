package br.com.areiasbrittos.controle.objetos;
// Generated 01/03/2012 13:32:49 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Contaspagar generated by hbm2java
 */
public class Contaspagar implements java.io.Serializable, java.lang.Comparable{


     private Integer idConta;
     private Compra compra;
     private String descricao;
     private String observacao;
     private Date dataVencimento;
     private Date dataPagamento;
     private Time horaPagamento;
     private BigDecimal valor;
     private boolean quitada;
     private Set caixadiarioHasContaspagars = new HashSet(0);

    public Contaspagar() {
    }

	
    public Contaspagar(String descricao, Date dataVencimento, Date dataPagamento, Time horaPagamento, BigDecimal valor, boolean quitada) {
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.horaPagamento = horaPagamento;
        this.valor = valor;
        this.quitada = quitada;
    }
    public Contaspagar(Compra compra, String descricao, String observacao, Date dataVencimento, Date dataPagamento, Time horaPagamento, BigDecimal valor, boolean quitada) {
       this.compra = compra;
       this.descricao = descricao;
       this.observacao = observacao;
       this.dataVencimento = dataVencimento;
       this.dataPagamento = dataPagamento;
       this.horaPagamento = horaPagamento;
       this.valor = valor;
       this.quitada = quitada;
    }
    public Contaspagar(Integer idConta, Compra compra, String descricao, String observacao, Date dataVencimento, Date dataPagamento, Time horaPagamento, BigDecimal valor, boolean quitada) {
        this(compra, descricao, observacao, dataVencimento, dataPagamento, horaPagamento, valor, quitada);
        this.idConta = idConta;
    }
    public Contaspagar(Compra compra, String descricao, String observacao, Date dataVencimento, Date dataPagamento, Time horaPagamento, BigDecimal valor, boolean quitada, Set caixadiarioHasContaspagars) {
       this.compra = compra;
       this.descricao = descricao;
       this.observacao = observacao;
       this.dataVencimento = dataVencimento;
       this.dataPagamento = dataPagamento;
       this.horaPagamento = horaPagamento;
       this.valor = valor;
       this.quitada = quitada;
       this.caixadiarioHasContaspagars = caixadiarioHasContaspagars;
    }

    @Override
    public String toString() {
        return "Contaspagar{" + "idConta=" + idConta + ", compra=" + compra + ", descricao=" + descricao + ", observacao=" + observacao + ", dataVencimento=" + dataVencimento + ", dataPagamento=" + dataPagamento + ", horaPagamento=" + horaPagamento + ", valor=" + valor + ", quitada=" + quitada + ", caixadiarioHasContaspagars=" + caixadiarioHasContaspagars + '}';
    }
    
    public Integer getIdConta() {
        return this.idConta;
    }
    
    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }
    public Compra getCompra() {
        return this.compra;
    }
    
    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getObservacao() {
        return this.observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Date getDataVencimento() {
        return this.dataVencimento;
    }
    
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    public Date getDataPagamento() {
        return this.dataPagamento;
    }
    
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
    public Time getHoraPagamento() {
        return this.horaPagamento;
    }
    
    public void setHoraPagamento(Time horaPagamento) {
        this.horaPagamento = horaPagamento;
    }
    public BigDecimal getValor() {
        return this.valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isQuitada() {
        return this.quitada;
    }
    
    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }
    public Set getCaixadiarioHasContaspagars() {
        return this.caixadiarioHasContaspagars;
    }
    
    public void setCaixadiarioHasContaspagars(Set caixadiarioHasContaspagars) {
        this.caixadiarioHasContaspagars = caixadiarioHasContaspagars;
    }

    @Override
    public int compareTo(Object o) {
        return this.compareTo(o);
    }
    
    //compara por hora
    public int compareTo(Contaspagar outraConta){
        
        if(this.getHoraPagamento().before(outraConta.getHoraPagamento())){
            return -1;
        } else{
            return 1;
        }
    }

}


