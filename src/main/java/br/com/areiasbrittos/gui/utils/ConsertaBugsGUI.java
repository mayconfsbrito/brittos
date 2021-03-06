/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.gui.utils;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ConsertaBugsGUI {

    //Conserta o bug que omite as casas decimais ao escrever em um TextFieldMoedaReal,
    //permitindo escrever na mesma diretamente a partir deste método
    public static void escreveEmTextFieldMoedaReal(BigDecimal valor, JTextField text) {

        String str = valor.toString().replaceAll("\\.", "");
        String strAux = valor.toString();

        text.setText(str);

        if (!text.getText().replaceAll(",", "\\.").equals(strAux)) {
            str = str.concat("0");
        }

        text.setText(str);

    }

    //Conserta o bug que cria espaços em JTextFields com máscaras
    //Retorna o real valor inteiro da JTextField sem os espaços da máscara
    public static int recebeInteiroEmJTextFieldComMascara(JTextField textField) {

        try {
            String str = textField.getText().replaceAll(" ", "");
            textField.setText(str);

            if (str.isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(str);
            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel ler o valor de " + textField.toString() + ".\n" + er);
            return 0;
        }
    }

    //Retorna formatada a data que está no BD
    public static String getDataFormatadaNoBd(Date data, String Formato) {

        if (data != null) {
            SimpleDateFormat df = new SimpleDateFormat(Formato);
            return df.format(data);
        }
        return "";
    }
}
