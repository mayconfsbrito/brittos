/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.superclass;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class InternalFrame extends javax.swing.JInternalFrame {

    public InternalFrame() {
        super();
    }
    /*
     * Variáveis inicializadas
     */
    public String[] botoesOpcoes = {"Sim", "Não"};

    /**
     * Inicializa as variáveis e métodos necessários
     */
    protected void inicializa() {
    }

    /**
     * Define o comportamento da GUI para objetos que se econtram concluídas ou não
     */
    protected void guiAtiva(boolean concluida) {
    }

    /**
     * Limpa e reinicializa elementos desta GUI
     */
    protected void limparGUI() {
    }

    /**
     * Verifica o acesso do usuário para utilizar o frame
     */
    protected void verificaAcessos() {
    }

    /**
     * Preenche todos os componentes da GUI a partir do objeto selecionado na tabela
     */
    protected void tabelaPreencheGUI() {
    }

    /**
     * Lista todos os objetos Pesagem na tabela
     */
    protected void listarTodosElementosNaTabela() {
    }

    /**
     * Centraliza este JInternalFrame no centro da tela
     */
    public void centraliza() {
        Dimension paneSize = this.getSize();
        Dimension screenSize = this.getToolkit().getScreenSize();
        this.setLocation((screenSize.width - paneSize.width) / 3, (screenSize.height - paneSize.height) / 3);
    }

    /**
     * Evento de selecionar linha da tabela
     */
    protected void tabelaMouseClicked(java.awt.event.MouseEvent evt) {
        this.tabelaPreencheGUI();
    }

    /**
     * Evento do botão de cancelar
     */
    protected void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja cancelar a operação e sair?", "Cancelar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoesOpcoes, botoesOpcoes[0]);

        if (opcao == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    /**
     * Evento do botão Limpar
     */
    protected void buttonLimparActionPerformed(java.awt.event.ActionEvent evt) {
        limparGUI();
    }

    /**
     * Sub-classe que implementa o Listener para eventos de seleção de elementos da tabela da GUI
     */
    public class SelectionListener implements ListSelectionListener {

        private JTable table;

        public SelectionListener(JTable table) {
            this.table = table;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            try {

                if (!e.getValueIsAdjusting()) {
                    if (this.table != null
                            && this.table.getSelectedRow() != -1) {
                        //If cell selection is enabled, both row and column change events are fired*
                        if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
                            // Column selection changed
                            tabelaPreencheGUI();
                        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
                                && table.getColumnSelectionAllowed()) {
                            // Row selection changed
                            tabelaPreencheGUI();

                        }
                    }
                }

            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(table, "Erro ao selecionar elemento na tabela!", "Erro!", JOptionPane.ERROR_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
    }}
