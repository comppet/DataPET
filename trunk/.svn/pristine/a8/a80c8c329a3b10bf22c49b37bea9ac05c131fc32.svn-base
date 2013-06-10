/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Usuario;
import java.sql.SQLException;

/**
 * Esta é uma interface extendida de Dao.java
 *
 * @author Pedro Augusto
 * @version 3.0
 * @since 3.0
 */
public interface UsuarioDao<T extends Usuario> extends Dao<T> {
    T recuperarPorEmail(String email) throws SQLException;
    boolean existeEmail(String email) throws SQLException;
}