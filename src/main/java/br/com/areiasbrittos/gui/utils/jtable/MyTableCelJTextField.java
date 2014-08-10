/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils.jtable;

import gui.utils.TextFieldMoedaReal;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class MyTableCelJTextField implements TableCellRenderer {

    JTextField editor = new JTextField();
    
    public MyTableCelJTextField(JTextField editor){
        this.editor = editor;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        if (value != null) {
            editor.setText(value.toString());
        }
        
        if(isSelected){
            editor.setBackground(Color.BLUE);
            editor.setForeground(Color.white);
        } else{
            editor.setBackground(Color.white);
            editor.setForeground(Color.black);
        }

        return editor;
    }
    
   
}
