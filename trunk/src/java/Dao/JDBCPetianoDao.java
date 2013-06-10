/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Model.Grupo;
import Model.Petiano;
import java.sql.SQLException;
import java.sql.ResultSet;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
* Classe abstrata que faz operações no banco de dados sobre petianos.
*
* @author Pedro Augusto
* @version 3.0
* @since 3.0
*/
public abstract class JDBCPetianoDao<T extends Petiano> extends JDBCUsuarioDao<T> implements PetianoDao<T> {

    
    /**
     * Construtor da classe.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Recebe uma conexão com o banco de dados.
     */
    public JDBCPetianoDao(Connection conn) throws SQLException{
        super(conn);
    }

    
    /**
     * Salva um petiano no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @return :: Identificador para o próximo petiano.
     * @param :: Um petiano.
     */
    @Override
    public int salvar(T petiano) throws SQLException {        
        int proximoId = super.salvar(petiano);
        // Adiciona novo usuario na tabela de petianos
        String sql = "insert into petiano values(?, ?, ?, ?, ?, ?, null)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, proximoId);
        pst.setInt(2, petiano.getGrupo().getId());
        pst.setString(3, petiano.getNome());
        pst.setString(4, petiano.getTelefone());
        pst.setString(5, petiano.getCelular());
        pst.setDate(6, new java.sql.Date(petiano.getDataIngressoPet().getTime()));
        pst.executeUpdate();
        pst.close();
        return proximoId;
    }

    
    /**
     * Edita um petiano no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um petiano.
     */
    @Override
    public void editar(T petiano) throws SQLException{
        super.editar(petiano);

        // formatação de datas:
        java.sql.Date ingresso = null;
        java.sql.Date saida = null;
        if (petiano.getDataIngressoPet() != null)
            ingresso = new java.sql.Date(petiano.getDataIngressoPet().getTime());
        if (petiano.getDataSaidaPet() != null)
            saida = new java.sql.Date(petiano.getDataSaidaPet().getTime());

        // operação no banco de dados
        String sql = "update petiano set nome = ?, telefone = ?, celular = ?, dataIngressoPet = ?, "
                + "dataSaidaPet = ? where id = ?";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, petiano.getNome());
        pst.setString(2, petiano.getTelefone());
        pst.setString(3, petiano.getCelular());
        pst.setDate(4, ingresso);
        pst.setDate(5, saida);
        pst.setInt(6, petiano.getId());
        pst.executeUpdate();
        pst.close();
    }

    
    /**
     * Preenche as informações de um petiano no banco de dados.
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     * @throws :: SQLException
     * @param :: Um usuário petiano e uma consulta do banco de dados.
     */
    @Override
    protected void preencher(Usuario p, ResultSet rs) throws SQLException{
        super.preencher(p, rs);
        Grupo g = new Grupo();
        g.setId(rs.getInt("id_grupo"));
        
        Petiano petiano = (Petiano) p;
        petiano.setGrupo(g);
        petiano.setNome(rs.getString("nome"));
        petiano.setTelefone(rs.getString("telefone"));
        petiano.setCelular(rs.getString("celular"));
        petiano.setDataIngressoPet(rs.getDate("dataIngressoPet"));
        petiano.setDataSaidaPet(rs.getDate("dataSaidaPet"));
    }

}