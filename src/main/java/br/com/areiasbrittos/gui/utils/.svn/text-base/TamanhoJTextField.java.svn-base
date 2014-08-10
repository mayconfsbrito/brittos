/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import javax.swing.JTextField;

/**
 * Trata o tamanho dos componentes JTextField
 * 
 * @author Maycon Fernando Silva Brito
 * @author mayconfsbrito@gmail.com
 */
public class TamanhoJTextField {

    public static void limitaTamanho(int[] tamanhos, JTextField... args) {
        for (int i = 0; i < args.length; i++) {
            args[i].setDocument(new FixedLengthDocument(tamanhos[i]));
        }
    }
}
