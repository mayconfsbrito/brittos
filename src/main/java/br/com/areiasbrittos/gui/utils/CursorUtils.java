package br.com.areiasbrittos.gui.utils;

import br.com.areiasbrittos.gui.FramePrincipal;
import java.awt.Container;
import java.awt.Cursor;

/**
 * Trata as propriedades do cursor da interface gráfica do programa
 *
 * @author Maycon
 */
public class CursorUtils {

    public static void normal() {
        FramePrincipal.desktopPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void wait_cursor() {
        FramePrincipal.desktopPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }

}
