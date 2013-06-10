/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Anotacao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada as Anotações, é subclasse da interface DAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface AnotacaoDao extends Dao<Anotacao> {

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idAluno id do aluno do qual se fara a busca por anotações no
     * banco de dados.
     * @return anotações Array de anotações referente ao aluno de id passado
     * como parametro.
     */
    ArrayList<Anotacao> recuperarPorAluno(int id) throws SQLException;
}
