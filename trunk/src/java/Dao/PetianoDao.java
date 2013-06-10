/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Petiano;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface extendida de UsuarioDao.java.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface PetianoDao<T extends Petiano> extends UsuarioDao<T> {
    ArrayList<T> listarTodos(int idGrupo) throws SQLException;
}
