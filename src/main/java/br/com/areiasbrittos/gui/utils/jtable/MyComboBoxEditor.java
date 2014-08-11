/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.gui.utils.jtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class MyComboBoxEditor extends DefaultCellEditor {

        public MyComboBoxEditor(String[] items) {
            super(new JComboBox(items));
        }
    }
