/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Natureza;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada as naturezas, é subclasse da interface DAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface NaturezaDao extends Dao<Natureza> {
    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo id do grupo PET.
     * @return Lista de todas as naturezas do grupo PET passado como parâmetro.
     */
    ArrayList<Natureza> listarTodos(int idGrupo) throws SQLException;
}
