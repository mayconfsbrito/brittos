/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.gui.utils;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class MascarasJTextField {

    public MaskFormatter inserirMascara(String Mascara) {

        MaskFormatter F_Mascara = new MaskFormatter();

        try {

            F_Mascara.setMask(Mascara); //Atribui a mascara
            F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento

        } catch (Exception excecao) {

            JOptionPane.showMessageDialog(null, "Problema ao inserir máscara em campo.\n" + excecao, "Erro",
                    JOptionPane.ERROR_MESSAGE);

        }

        return F_Mascara;
    }

    /*
     * Verifica se a máscara de um telefone ou celular é válida
     * Máscara (##)####-####
     */
    public boolean validaMascaraTelefone(String str) {

        str = str.trim().replace("(", "");
        str = str.trim().replace(")", "");
        str = str.trim().replace("-", "");
        str = str.trim().replace(" ", "");

        if (str.length() == 10 || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaMascaraCep(String str) {

        str = str.trim().replace("-", "");
        str = str.trim().replace(" ", "");

        if (str.length() == 8 || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaMascaraCpf(String str) {

        str = str.trim().replace(".", "");
        str = str.trim().replace("-", "");
        str = str.trim().replace(" ", "");

        if (str.length() == 11 || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
}
