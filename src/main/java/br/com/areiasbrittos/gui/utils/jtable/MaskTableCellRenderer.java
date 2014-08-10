/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils.jtable;

import java.awt.Component;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class MaskTableCellRenderer implements TableCellRenderer {

    private JFormattedTextField text = null;

    public MaskTableCellRenderer(MaskFormatter mask) {
        text = new JFormattedTextField();
        text.setBorder(null);
        mask.install(text);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            text.setText(value.toString());
        } else {
            text.setText("");
        }

        return text;
    }
}
