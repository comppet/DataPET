package Dao;

import Model.Instituicao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Esta é uma interface com a assinatura dos métodos que realizam operações no banco de dados
 * relacionada as Instituições, é subclasse da interface DAO.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public interface InstituicaoDao extends Dao<Instituicao> {

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Lista de todas as Instituições do sistema.
     */
    ArrayList<Instituicao> listarTodos() throws SQLException;

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param siglaInstituição sigla da instituição que está sendo buscada
     * no banco de dados.
     * @return instituição que tenha a sigla passada como parâmetro "siglaInstituição"
     */
    Instituicao recuperarPorSigla(String sigla) throws SQLException;

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param nomeInstituição nome da instituição que está sendo buscada no
     * banco de dados.
     * @return instituição que tenha o nome passado como parâmetro "nomeInstituição"
     */
    Instituicao recuperarPorNome(String nome) throws SQLException;
    
}