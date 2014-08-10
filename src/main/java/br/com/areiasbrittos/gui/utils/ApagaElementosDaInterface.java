/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.utils;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ApagaElementosDaInterface {

    public void apagaJTextField(JTextField... args){

        for(int i = 0; i < args.length; i++)
            args[i].setText("");

    }

    public void apagaJComboBox(JComboBox... args){

        for(int i = 0; i < args.length; i++)
            args[i].setSelectedIndex(0);

    }

    public void apagaCheckBox(JCheckBox... args){

        for(int i = 0; i < args.length; i++)
            args[i].setSelected(false);
        
    }

}
