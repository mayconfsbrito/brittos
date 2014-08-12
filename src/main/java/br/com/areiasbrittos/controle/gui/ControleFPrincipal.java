/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.areiasbrittos.controle.gui;

import br.com.areiasbrittos.persistencia.dao.seguranca.DAOTransacao;
import br.com.areiasbrittos.controle.utils.Dates;

/**
 *
 * @author Maycon Fernando Silva Brito
 * @email mayconfsbrito@gmail.com
 */
public class ControleFPrincipal {

    /**
     * Verifica no arquivo "Preferências.conf" as transações que podem ser excluída
     * @return 
     */
    public boolean eliminaTransacoesVencidas() {

        DAOTransacao dao = new DAOTransacao();

        //Define o tempo máximo de armazenamento de uma transação
        int diasMaximos = 0;
        if(!ControleFPreferencias.conf.getValidadeTransacoes().equals("Nunca")){
            diasMaximos = Integer.parseInt(ControleFPreferencias.conf.getValidadeTransacoes().replaceAll(" dias", ""));
        }
        

        //Se a opção for nunca, ou seja, zero, não é realizada exclusão de transações
        if (diasMaximos != 0) {
            //Apaga as transações que tem mais dias do que o definido
            dao.excluir(Dates.subDias(new java.sql.Date(System.currentTimeMillis()), diasMaximos));
        }

        return true;
    }
}
