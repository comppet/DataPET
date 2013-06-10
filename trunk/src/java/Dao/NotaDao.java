/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Nota;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface extendida de Dao de uma Nota.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface NotaDao extends Dao<Nota> {
    ArrayList<Nota> listarTodos(int idAluno) throws SQLException;
    ArrayList<Nota> listarTodosGrupo (int idGrupo, int ano, int semestre) throws SQLException;
}
