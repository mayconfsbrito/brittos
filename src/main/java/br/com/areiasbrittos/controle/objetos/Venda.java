package br.com.areiasbrittos.controle.objetos;
// Generated 11/01/2012 10:46:41 by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Venda generated by hbm2java
 */
public class Venda implements java.io.Serializable {

    private Integer idVenda;
    private Entidade entidade;
    private Date dataCriacao;
    private Time horaCriacao;
    private Date dataAlteracao;
    private Time horaAlteracao;
    private String placa;
    private BigDecimal valorTotal;
    private Date vencimento;
    private boolean quitada;
    private String motorista;
    private String pagamento;
    private boolean concluida;
    private Set vendaHasProdutos = new HashSet(0);

    public Venda() {
    }

    public Venda(Entidade entidade, Date dataCriacao, Time horaCriacao, Date dataAlteracao, Time horaAlteracao, String placa, BigDecimal valorTotal, Date vencimento, boolean quitada, String pagamento, boolean concluida) {
        this.entidade = entidade;
        this.dataCriacao = dataCriacao;
        this.horaCriacao = horaCriacao;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
        this.placa = placa;
        this.valorTotal = valorTotal;
        this.vencimento = vencimento;
        this.quitada = quitada;
        this.pagamento = pagamento;
        this.concluida = concluida;
    }

    public Venda(Entidade entidade, Date dataCriacao, Time horaCriacao, Date dataAlteracao, Time horaAlteracao, String placa, BigDecimal valorTotal, Date vencimento, boolean quitada, String motorista, String pagamento, boolean concluida) {
        this.entidade = entidade;
        this.dataCriacao = dataCriacao;
        this.horaCriacao = horaCriacao;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
        this.placa = placa;
        this.valorTotal = valorTotal;
        this.vencimento = vencimento;
        this.quitada = quitada;
        this.motorista = motorista;
        this.pagamento = pagamento;
        this.concluida = concluida;
    }

    public Venda(Entidade entidade, Date dataCriacao, Time horaCriacao, Date dataAlteracao, Time horaAlteracao, String placa, BigDecimal valorTotal, Date vencimento, boolean quitada, String motorista, String pagamento, boolean concluida, HashSet compraHasProdutos) {
        this.entidade = entidade;
        this.dataCriacao = dataCriacao;
        this.horaCriacao = horaCriacao;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
        this.placa = placa;
        this.valorTotal = valorTotal;
        this.vencimento = vencimento;
        this.quitada = quitada;
        this.motorista = motorista;
        this.pagamento = pagamento;
        this.concluida = concluida;
        this.vendaHasProdutos = compraHasProdutos;
    }

    @Override
    public String toString() {
        return "Venda{" + "idVenda=" + idVenda + ", entidade=" + entidade + ", dataCriacao=" + dataCriacao + ", horaCriacao=" + horaCriacao + ", dataAlteracao=" + dataAlteracao + ", horaAlteracao=" + horaAlteracao + ", placa=" + placa + ", valorTotal=" + valorTotal + ", vencimento=" + vencimento + ", quitada=" + quitada + ", motorista=" + motorista + ", pagamento=" + pagamento + ", concluida=" + concluida + ", vendaHasProdutos=" + vendaHasProdutos + '}';
    }

    public Integer getIdVenda() {
        return this.idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Entidade getEntidade() {
        return this.entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getVencimento() {
        return this.vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public boolean isQuitada() {
        return this.quitada;
    }

    public void setQuitada(boolean quitada) {
        this.quitada = quitada;
    }

    public String getMotorista() {
        return this.motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public boolean isConcluida() {
        return this.concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public Set getVendaHasProdutos() {
        return this.vendaHasProdutos;
    }

    public void setVendaHasProdutos(Set vendaHasProdutos) {
        this.vendaHasProdutos = vendaHasProdutos;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Time getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(Time horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Time getHoraAlteracao() {
        return horaAlteracao;
    }

    public void setHoraAlteracao(Time horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }
    
    
}
