/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Curso;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tiago Peres
 */
public interface CursoDao extends Dao<Curso> {

    ArrayList<Curso> listarTodos(int idInstituicao) throws SQLException;
    Curso recuperarPorNome(String nomeCurso, int idInstituicao) throws SQLException;

}
