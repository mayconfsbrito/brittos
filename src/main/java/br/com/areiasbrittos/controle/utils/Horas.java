/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.utils;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class Horas {

    /**
     * Captura a hora atual do SO
     *
     * @return
     */
    public static Time getHoraAgora() {
        return new Time(System.currentTimeMillis());
    }

    /**
     * Converte uma determinada string em time
     *
     * @param str
     * @return
     */
    public static Time getTime(String str) throws ParseException {
        if (str != null && !str.equals("")) {
            return Time.valueOf(str);
        } else {
            return null;
        }

    }
}
