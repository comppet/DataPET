/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Cla;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface extendida de UsuarioDao.java do usuário Cla.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface ClaDao extends UsuarioDao<Cla> {

    ArrayList<Cla> recuperarPorInstituicao(int idInstituicao) throws SQLException;
    Cla recuperarInterlocutorCla(int idInstituicao) throws SQLException;

}
