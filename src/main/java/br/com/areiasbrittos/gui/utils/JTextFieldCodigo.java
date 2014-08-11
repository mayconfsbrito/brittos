/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.gui.utils;

/**
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class JTextFieldCodigo extends javax.swing.JTextField{

    public JTextFieldCodigo(int length, boolean zeroEsquerda) {
        this.setHorizontalAlignment(JTextFieldCodigo.RIGHT);
        this.setDocument(new NumeroDocumentCodigo(length, zeroEsquerda, false));
    }
    
    public JTextFieldCodigo(int length, boolean zeroEsquerda, boolean sinalNegativo) {
        this.setHorizontalAlignment(JTextFieldCodigo.RIGHT);
        this.setDocument(new NumeroDocumentCodigo(length, zeroEsquerda, sinalNegativo));
    }
    
    
}
