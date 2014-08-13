/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.controle.utils.ReportUtils;
import br.com.areiasbrittos.gui.relatorios.InternalFrameEstoque;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import br.com.areiasbrittos.persistencia.ConnectionFactory_brittos_bd;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ControleIFEstoque {

    private InternalFrameEstoque frame;

    public ControleIFEstoque(InternalFrameEstoque frame) {
        this.frame = frame;
    }

    /**
     * Inicializa as opções personalizadas para esta GUI*
     */
    public void inicializa() {
        frame.textMensagem.setText("Defina as opções para gerar o relatório de estoque.");

    }

    public void cancelar() {

        int opcao = JOptionPane.showOptionDialog(frame, "Tem certeza que deseja cancelar a operação?", "Cancelar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);
        if (opcao == JOptionPane.YES_OPTION) {
            frame.dispose();
        }

    }

    public void limpar(ActionEvent evt) {
        int opcao = JOptionPane.showOptionDialog(frame, "Tem certeza que deseja limpar estes campos?", "Limpar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, frame.botoesOpcoes, frame.botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {
            frame.comboProduto.setSelectedIndex(0);
        }
    }

    /**
     * Imprime o relatório
     */
    public void imprimir() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        boolean imprime = true;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/relatorios/Estoque.jasper");
            Map parametros = new HashMap();

            FileInputStream imagem = new FileInputStream(new java.io.File("logo.png"));
            parametros.put("imagem", imagem);


            /**
             * Define o parâmetro do nome do produto*
             */
            parametros.put("nomeProduto", frame.comboProduto.getSelectedItem().toString());

            /**
             * Executa o relatório*
             */
            if (imprime) {
                ReportUtils.openReport("Relatório de Estoque", inputStream, parametros, ConnectionFactory_brittos_bd.getConnection());
            }

        } catch (Exception er) {
            JOptionPane.showMessageDialog(frame, "Erro ao criar o relatório.\n" + er, "Erro", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();

        }

    }
    
}
