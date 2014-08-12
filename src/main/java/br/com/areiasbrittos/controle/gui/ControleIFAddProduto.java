/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.objetos.CompraHasProduto;
import br.com.areiasbrittos.controle.objetos.Produto;
import br.com.areiasbrittos.gui.internalFrames.InternalFrameAddProduto;
import br.com.areiasbrittos.gui.utils.ApagaElementosDaInterface;
import br.com.areiasbrittos.gui.utils.ConsertaBugsGUI;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import br.com.areiasbrittos.persistencia.dao.AbstractDAO;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ControleIFAddProduto {
    
    InternalFrameAddProduto frame;
    private int linhaSelecionada = -1;
    
    public ControleIFAddProduto(InternalFrameAddProduto frame) {
        this.frame = frame;
        linhaSelecionada = -1;
    }
    
    public void inicializa() {
        this.preencheComboBoxProduto();
        frame.centraliza();
        frame.textMensagem.setText("Aqui você adiciona novos produtos à Compra ou Venda.");
        frame.textValor.setText(0 + "");
        frame.textUnitario.setText(0 + "");
    }

    /**
     * Limpa os elementos de @frame
     */
    public void limparGUI() {
        new ApagaElementosDaInterface().apagaJTextField(frame.textCodigo, frame.textPesoBruto, frame.textPesoLiquido, frame.textTara, frame.textUnitario, frame.textValor);
        frame.comboProduto.setSelectedIndex(0);
    }

    /**
     * Cria a compra do produto registrado aqui
     * @return 
     */
    public CompraHasProduto criaProduto() {
        
        CompraHasProduto chp = new CompraHasProduto();
        
        if (!frame.textCodigo.getText().trim().isEmpty()) {
            chp.setIdCompraHasProduto(Integer.parseInt(frame.textCodigo.getText()));
        }

        /*Verifica se os campos estão preenchidos*/
        /*Verifica se o produto foi escolhido*/
        if (frame.comboProduto.getSelectedIndex() != 0) {
            /*Verifica se a tara foi preenchida*/
            if (!frame.textTara.getText().trim().isEmpty()) {
                /*Verifica se a tara não é maior que o peso bruto*/
                if (!this.verificaTaraPesoBruto()) {
                    
                    chp.setProduto((Produto) AbstractDAO.consultar("Produto", "nome='" + frame.comboProduto.getSelectedItem() + "'").get(0));
                    chp.setPesoBruto(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textPesoBruto));
                    chp.setTara(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textTara));
                    chp.setPesoLiquido(ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textPesoLiquido));
                    chp.setValorUnitario(new BigDecimal(frame.textUnitario.getText().replaceAll("\\.", "").replaceAll(",", "\\.")));
                    chp.setValorTotal(new BigDecimal(frame.textValor.getText().replaceAll("\\.", "").replaceAll(",", "\\.")));
                    
                    return chp;
                    
                } else {
                    JOptionPane.showMessageDialog(frame, "A tara não pode ser maior do que o peso bruto.", "Atenção!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                
                JOptionPane.showMessageDialog(frame, "A tara ainda não foi preenchida.", "Atenção!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "O produto ainda não foi escolhido.", "Atenção!", JOptionPane.WARNING_MESSAGE);
        }
        
        return null;
        
    }

    /**
     * Exibe um produto selecionado em @frame.tabelaProdutos
     */
    public void exibirProduto(JTable tabela) {
        
        /*Captura a linha selecionada na tabela*/
        linhaSelecionada = tabela.getSelectedRow();
        
        /*Preenche os elementos da GUI*/
        if (frame.modelo.getValueAt(linhaSelecionada, 0) != null) {
            frame.textCodigo.setText(frame.modelo.getValueAt(linhaSelecionada, 0).toString());
        }
        frame.comboProduto.setSelectedItem(frame.modelo.getValueAt(linhaSelecionada, 1));
        frame.textTara.setText(frame.modelo.getValueAt(linhaSelecionada, 2).toString());
        frame.textPesoBruto.setText(frame.modelo.getValueAt(linhaSelecionada, 3).toString());
        frame.textPesoLiquido.setText(frame.modelo.getValueAt(linhaSelecionada, 4).toString());
        frame.textUnitario.setText(frame.modelo.getValueAt(linhaSelecionada, 5).toString().replaceAll("\\.", "").replaceAll(",", "\\."));
        frame.textValor.setText(frame.modelo.getValueAt(linhaSelecionada, 6).toString().replaceAll("\\.", "").replaceAll(",", "\\."));
        
    }

    /**
     * Verifica se o valor em @textTara é maior do que @textPesoBruto, caso positivo retorna true e em caso negativo retorna falso
     */
    public boolean verificaTaraPesoBruto() {
        int tara = 0;
        int pesoBruto = 0;

        //Encontra os valores para tara e peso bruto
        if (!(frame.textTara.getText().replace(" ", "").isEmpty())) {
            tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textTara);
        }
        
        if (!(frame.textPesoBruto.getText().replace(" ", "").isEmpty())) {
            pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textPesoBruto);
        }
        
        if (tara > pesoBruto && pesoBruto != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula o peso líquido a partir da tara e do peso bruto inseridos neste frame
     */
    public void calculaPesoLiquido() {
        
        int tara = 0;
        int pesoBruto = 0;

        //Encontra os valores para tara e peso bruto
        if (!(frame.textTara.getText().replace(" ", "").isEmpty())) {
            
            tara = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textTara);
        }
        
        if (!(frame.textPesoBruto.getText().replace(" ", "").isEmpty())) {
            
            pesoBruto = ConsertaBugsGUI.recebeInteiroEmJTextFieldComMascara(frame.textPesoBruto);
        }

        //Verifica se não há possibilidade da tara ser maior do que o peso bruto
        //evitando assim que o peso líquido não seja negativo
        if ((tara > 0 && pesoBruto == 0) || (tara == 0 && pesoBruto == 0) || this.verificaTaraPesoBruto()) {
            frame.textPesoLiquido.setText("0");
            
        } else if (!this.verificaTaraPesoBruto()) {
            frame.textPesoLiquido.setText(Integer.toString(pesoBruto - tara));
            
        }

        this.calculaValorTotal();
    }

    /**
     * Insere o valor do preço unitário a partir da escolha do produto em @comboboxProduto
     */
    public void calculaPrecoUnitario() {
        
        if (frame.comboProduto.getSelectedIndex() > 0) {
            
            BigDecimal preco = new BigDecimal(BigInteger.ONE);
            Produto pro = (Produto) AbstractDAO.consultar("Produto", "nome='" + frame.comboProduto.getSelectedItem() + "'").get(0);
            
            /*Verifica se o preço é a vista ou a prazo*/
            if(frame.comboPagamento.getSelectedIndex() == 0){
                /*Preço a vista*/
                preco = pro.getPrecoAVista();
            } else{
                /*Preço a prazo*/
                preco = pro.getPrecoAPrazo();
            }
            
            frame.textUnitario.setText(preco.toString().replaceAll("\\.", "").replaceAll(",", "\\."));
            
            this.calculaValorTotal();
        }
    }

    /**
     * Calcula o valor total da compra deste produto
     * @return 
     */
    public void calculaValorTotal()     {
        
        if (!frame.textUnitario.getText().isEmpty()
                && !frame.textPesoLiquido.getText().replace(" ", "").isEmpty()
                && !frame.textPesoLiquido.getText().equals("      ")) {
            
            BigDecimal valor = new BigDecimal(frame.textUnitario.getText().replaceAll("\\.", "").replaceAll(",", "\\."));
            double liquido = Double.parseDouble(frame.textPesoLiquido.getText().trim());
            
            valor = valor.multiply(new BigDecimal(liquido / 1000));
            frame.textValor.setText(valor.setScale(2, BigDecimal.ROUND_HALF_UP).toString().replaceAll("\\.", "").replaceAll(",", "\\."));
            
        } 
        
    }

    /**
     * Preenche o combobox a partir de uma determinada tabela do bd
     */
    public void preencheComboBoxProduto() {
        
        frame.comboProduto.setModel(AbstractDAO.inicializaComboBox("Selecione...", "Produto where ativo=true"));
    }

    /**
     * Inserir a compra/venda do produto na tabela de produtos
     */
    public void inserirAlterarLinha() {

        /*Captura o objeto*/
        CompraHasProduto chp = this.criaProduto();
        
        if (chp != null) {

            /*Verifica se o produto será inserido na tabela ou se simplesmente aterará uma linha*/
            if (frame.textCodigo.getText().trim().isEmpty() && linhaSelecionada == -1) {
                
                /*Insere o produto na tabela de produtos*/
                frame.modelo.addRow(new Object[]{chp.getIdCompraHasProduto(), chp.getProduto().getNome(), 
                    chp.getTara(), chp.getPesoBruto(), chp.getPesoLiquido(), chp.getValorUnitario(), chp.getValorTotal()});
                frame.modelo.setValueAt(chp.getIdCompraHasProduto(), 0, 0);
                
            } else {
                
                /*Altera o produto na linha selecionada*/
                frame.modelo.setValueAt(chp.getIdCompraHasProduto(), linhaSelecionada, 0);
                frame.modelo.setValueAt(chp.getProduto().getNome(), linhaSelecionada, 1);
                frame.modelo.setValueAt(chp.getTara(), linhaSelecionada, 2);
                frame.modelo.setValueAt(chp.getPesoBruto(), linhaSelecionada, 3);
                frame.modelo.setValueAt(chp.getPesoLiquido(), linhaSelecionada, 4);
                frame.modelo.setValueAt(chp.getValorUnitario(), linhaSelecionada, 5);
                frame.modelo.setValueAt(chp.getValorTotal(), linhaSelecionada, 6);
                
            }
            
            
            frame.dispose();
        }
        
    }
}
