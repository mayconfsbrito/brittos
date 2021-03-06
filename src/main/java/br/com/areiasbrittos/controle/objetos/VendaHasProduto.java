package br.com.areiasbrittos.controle.objetos;
// Generated 11/01/2012 10:46:41 by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;


/**
 * VendaHasProduto generated by hbm2java
 */
public class VendaHasProduto implements java.io.Serializable {

    private Integer idVendaHasProduto;
    private Venda venda;
    private Produto produto;
    private int tara;
    private int pesoBruto;
    private int pesoLiquido;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;

    public VendaHasProduto() {
    }

    public VendaHasProduto(Venda venda, Produto produto, int tara, int pesoBruto, int pesoLiquido, BigDecimal valorUnitario, BigDecimal valorTotal) {
        this.venda = venda;
        this.produto = produto;
        this.tara = tara;
        this.pesoBruto = pesoBruto;
        this.pesoLiquido = pesoLiquido;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public Integer getIdVendaHasProduto() {
        return this.idVendaHasProduto;
    }

    public void setIdVendaHasProduto(Integer idVendaHasProduto) {
        this.idVendaHasProduto = idVendaHasProduto;
    }

    public Venda getVenda() {
        return this.venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getTara() {
        return this.tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }

    public int getPesoBruto() {
        return this.pesoBruto;
    }

    public void setPesoBruto(int pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public int getPesoLiquido() {
        return this.pesoLiquido;
    }

    public void setPesoLiquido(int pesoLiquido) {
        this.pesoLiquido = pesoLiquido;
    }

    public BigDecimal getValorUnitario() {
        return this.valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
