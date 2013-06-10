/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.GrupoHistorico;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada às operações de desativação e reativação de grupos PET, é subclasse da interface DAO.
 *
 * @author Pedro Augusto
 * @version 3.0
 * @since 3.0
 */
public interface GrupoHistoricoDao extends Dao<GrupoHistorico> {

    /**
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idGrupo que terá o seu histórico recuperado do banco de dados.
     * @return Lista de todas as operações de desativação e reativação sofridas
     * pelo grupo PET com o id passado por parâmetro
     */
    ArrayList<GrupoHistorico> listarTodos(int idGrupo) throws SQLException;

}
