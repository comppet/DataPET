/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Administrador;
import java.sql.SQLException;

/**
 * Esta é uma interface extendida de UsuarioDao.java de um usário Administrador.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 * @throws :: as exceções lançadas pelo método
 * @return :: o que o métod
 * @param :: o que deve ser 
 */
public interface AdministradorDao extends UsuarioDao<Administrador> {
    Administrador recuperarAdministrador() throws SQLException;
}
