/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utils;

import javax.swing.JComponent;


/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class EnabledGUIElements {

    public static void enabledJComponent(JComponent... args) {

        for (int i = 0; i < args.length; i++) {
            args[i].setEnabled(true);
        }
    }

    public static void disabledJComponent(JComponent... args) {

        for (int i = 0; i < args.length; i++) {
            args[i].setEnabled(false);
        }
    }
}
