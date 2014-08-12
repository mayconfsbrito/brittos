/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class Dates {

    /**
     * Retorna a data do dia atual
     *
     * @return
     */
    public static Date getDataHoje() {

        Date data = new Date(System.currentTimeMillis());

        return data;
    }

    /**
     * Converte uma determinada string em Date
     *
     * @return
     */
    public static Date getDate(String str) throws ParseException {
        if (str != null && !str.equals("") && !str.equals("  /  /    ")) {
            return new Date(new SimpleDateFormat("dd/MM/yyyy").parse(str).getTime());
        } else {
            return null;
        }
    }

    /**
     * Soma dias em uma data
     *
     * @param hoje - data a ser somada
     * @param dias - dias a serem incrementados
     * @return -
     */
    public static Date addDias(Date hoje, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoje);
        calendar.add(Calendar.DATE, dias);

        java.util.Date dataUtil = calendar.getTime();
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }

    /**
     * Subtrai dias em uma data
     *
     * @param hoje - data a ser somada
     * @param dias - dias a serem incrementados
     * @return -
     */
    public static Date subDias(Date hoje, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoje);
        calendar.add(Calendar.DATE, -dias);

        java.util.Date dataUtil = calendar.getTime();
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }

    /**
     * Adiciona uma determinada quantidade de anos a data
     *
     * @param dateStart
     * @return
     */
    public static Date addYears(Date dateStart, int years) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStart);
        cal.add(Calendar.YEAR, years);	//Adding 1 year to current date

        java.util.Date dataUtil = cal.getTime();
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }

    /**
     * Subtrai uma determinada quantidade de anos a data
     *
     * @param dateStart
     * @return
     */
    public static Date subYears(Date dateStart, int years) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStart);
        cal.add(Calendar.YEAR, -years);	

        java.util.Date dataUtil = cal.getTime();
        java.sql.Date dataSql = new java.sql.Date(dataUtil.getTime());

        return dataSql;
    }
    
    /**
     * Compara duas datas e retorna qual é maior ou 0 se forem iguais
     *
     * @param date1
     * @param date2
     * @return - 0 se forem iguais - 1 se @date1 form maior - 2 se @date2 for maior
     */
    public static int comparaDatas(Date date1, Date date2) {

        if (date1.equals(date2)) {
            return 0;

        } else if (date1.after(date2)) {
            return 1;

        } else {
            return 2;
        }
    }

    /**
     * Verifica se o número de dígitos do ano foram apenas 2 e passa para 2000 anos
     */
    public static Date verificaDigitosAno(Date data) {

        //Verifica se o ano não tem quatro dígitos
        if (data.getTime() <= Dates.subYears(Dates.getDataHoje(), 1000).getTime()) {
            //Soma dois mil anos ao ano do vencimento
            data = Dates.addYears(data, 2000);
        }
        
        return data;
    }
}
