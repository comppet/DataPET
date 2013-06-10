/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Administrador;
import Model.Ticket;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * Esta é uma classe que realiza operações no banco de dados
 * relacionada aos tickets, é subclasse de JDBCDao e implementa
 * a interface TicketDAO.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public class JDBCTicketDao extends JDBCDao<Ticket> implements TicketDao{

    /**
     * Construtor da classe que recebe uma conexão de uma classe a nível de Controller.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param Conexão com o banco de dados MySQL, recebida de uma classe a nível de Controller.
     */
    public JDBCTicketDao(Connection conn) throws SQLException{
        super(conn);
    }

    /**
     * Construtor da classe.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public JDBCTicketDao() throws SQLException{
        super(null);
    }

    /**
     * Recebe um Ticket, parâmetro "ticket", e o insere no banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param ticket ticket que será inserido no banco de dados.
     * @return o próximo id, da sequência de incremento, que terá o próximo
     * ticket a ser inserido.
     */
    @Override
    public int salvar(Ticket ticket) throws SQLException {
        Statement st = getConexao().createStatement();
        ResultSet rs = st.executeQuery("SHOW TABLE STATUS LIKE 'ticket'");
        rs.next();
        int proximoId = rs.getInt("Auto_increment");
        st.close();
        
        String sql = "insert into ticket values (null, ?, ?, ?)";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setInt(1, ticket.getUsuario().getId());
        pst.setString(2, ticket.getCodigo());
        pst.setTimestamp(3, new java.sql.Timestamp(ticket.getHorario().getTime()));
        pst.executeUpdate();
        pst.close();
        return proximoId; 
    }

    /**
     * Recebe o id de um ticket, parâmetro "id", e o remove do banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idTicket idTicket que será removido do banco de dados.
     */
    @Override
    public void excluir(int id) throws SQLException {
        Statement st = getConexao().createStatement();
        String sql = "delete from ticket where id = " + id;
        st.executeUpdate(sql);
        st.close();
    }

    /**
     * Apenas lança a exceção "UnsupportedOperationException", informando que a
     * operação de edição de um ticket do banco de dados não é suportada
     * pelo sistema.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param ticket a ser editado do banco de dados.
     */
    @Override
    public void editar(Ticket object) throws SQLException {
        throw new UnsupportedOperationException("Não é possível editar um ticket.");
    }

    /**
     * Apenas lança a exceção "UnsupportedOperationException", informando que a
     * operação de recuperar um ticket por id no banco de dados não é suportada
     * pelo sistema.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param idTicket idTicket a ser editado do banco de dados.
     * @return Ticket Ticket recuperado atraves do id passado como
     * parametro.
     */
    @Override
    public Ticket recuperarPorId(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param codigo codigo do Ticket do qual se fara a busca no banco de dados.
     * @return ticket ticket referente ao id passado como parametro.
     */
    @Override
    public Ticket recuperarPorCodigo(String codigo) throws SQLException {
        String sql = "select * from ticket, usuario "
                + "where codigo = ? and "
                + "now() - (horario + interval 1 day) < 0 and "
                + "usuario.id = ticket.id_usuario";
        PreparedStatement pst = getConexao().prepareStatement(sql);
        pst.setString(1, codigo);
        ResultSet rs = pst.executeQuery();
        Ticket t = null;
        if(rs.next()) {
            t = new Ticket();
            preencher (t, rs);
        }
        pst.close();
        return t;
    }

    /**
     * povoa um objeto "Ticket" passado como parâmetro ("t") com as informações contidas no
     * parâmetro do tipo "ResultSet" ("rs") que contém as informações do ticket recuperado do
     * banco de dados.
     *
     * @author Douglas Antunes
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param a objeto "Ticket" que será povoado com as informações de um ticket recuperada do banco de dados.
     *        rs objeto que contém as informações de um ticket recuperado do banco de dados.
     */
    private void preencher(Ticket t, ResultSet rs) throws SQLException{
        t.setId(rs.getInt("ticket.id"));
        t.setCodigo(rs.getString("codigo"));
        /* tanto faz o usuario, instanciei um administrador, pois é essa a
         * instância mais simples de Usuario.
         */
        Usuario u = new Administrador();
        u.setId(rs.getInt("id_usuario"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        t.setUsuario(u);
        t.setHorario(rs.getDate("horario"));
    }
}
