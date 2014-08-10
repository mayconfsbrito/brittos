/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import gui.frames.frameBackup;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 * Configura os eventos do JFrame
 * @author maycon
 */
public class WindowListenerFramePrincipal implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {
    }

    /**
     * Realiza backup do sistema quando o JFrame for fechado
     */
    @Override
    public void windowClosing(WindowEvent e) {
        new frameBackup();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
