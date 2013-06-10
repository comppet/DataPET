/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Ticket;
import java.sql.SQLException;


/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada aos Tickets referentes a recuperação de senha, é uma subclasse
 * da interface DAO.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public interface TicketDao extends Dao<Ticket> {
    /**
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param codigo codigo do usuario pelo qual se fara a busca por um ticket
     * valido no banco de dados.
     * @return ticket ticket referente ao codigo passado como parametro.
     */
    Ticket recuperarPorCodigo(String codigo) throws SQLException;
}
