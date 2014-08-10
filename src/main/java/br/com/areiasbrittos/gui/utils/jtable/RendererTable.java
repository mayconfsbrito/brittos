/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils.jtable;

import controle.objetos.*;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class RendererTable extends DefaultTableCellRenderer {

    private List lista;

    public RendererTable(List list) {
        this.setOpaque(true);
        this.lista = list;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        /**Verifica se as Pesagens ainda não foram concluídas e pinta a cor da fonte desta célula**/
        if (lista.get(row) instanceof Pesagem) {
            Pesagem pesagem = (Pesagem) lista.get(row);
            if (!pesagem.isConcluida()) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.black);
            }
        } else if (lista.get(row) instanceof Compra) {
            Compra compra = (Compra) lista.get(row);
            if (!compra.isConcluida()) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.black);
            }
        } else if (lista.get(row) instanceof Venda) {
            Venda venda = (Venda) lista.get(row);
            if (!venda.isConcluida()) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.black);
            }
        } else if (lista.get(row) instanceof Contaspagar) {
            Contaspagar conta = (Contaspagar) lista.get(row);
            if (!conta.isQuitada()) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.black);
            }
        }  else if (lista.get(row) instanceof Contasreceber) {
            Contasreceber conta = (Contasreceber) lista.get(row);
            if (!conta.isQuitada()) {
                this.setForeground(Color.red);
            } else {
                this.setForeground(Color.black);
            }
        }



        this.setValue(value);

        return this;
    }
}
