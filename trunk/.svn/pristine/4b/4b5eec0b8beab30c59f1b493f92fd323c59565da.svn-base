/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Grupo;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada aos grupos PET, é subclasse da interface DAO.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public interface GrupoDao extends Dao<Grupo> {

    /**
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idInstituicao id da instituição que terá todos os seus grupos PET buscados.
     * @return Lista de todos os grupos PET da instituição passada como parâmetro.
     */
    ArrayList<Grupo> listarTodos(int idInstituicao) throws SQLException;

    /**
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param siglaGrupo sigla do grupo que está sendo buscado do banco de dados.
     *        idInstituicao id da instituição que terá o grupo PET recuperado.
     * @return grupo PET que tenha a sigla passada como parâmetro "siglaGrupo"
     * e faça parte da instituição passada por parâmetro "idInstituicao".
     */
    Grupo recuperaPorSigla(String siglaGrupo, int idInstituicao) throws SQLException;
}