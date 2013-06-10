package Controller;

import Dao.Conexao;
import Dao.JDBCNaturezaDao;
import Dao.NaturezaDao;
import Model.Grupo;
import Model.Natureza;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Tiago Peres
 */
@ManagedBean(name="NaturezaController")
@RequestScoped
public class NaturezaController {
    private Natureza natureza;
    private NaturezaDao dao;
    private boolean editando;
    
    public NaturezaController() throws SQLException{
        dao = new JDBCNaturezaDao();
        reset();
    }

    // Toma medidas de segurança
    private boolean isSeguro() throws SQLException{
        /* Deve-se verificar se a natureza em questão pertence ao grupo do usuário
         * logado, se não for, a operação se trata de uma tentativa de hack ou uma
         * falha no sistema.
         */
        Grupo g = dao.recuperarPorId(natureza.getId()).getGrupo();
        return (LoginController.getGrupo().equals(g));
    }

    public void salvar() throws SQLException{
        natureza.setGrupo(LoginController.getGrupo());
        FacesContext context = FacesContext.getCurrentInstance();
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);

        try{
            dao.salvar(natureza);
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A natureza foi cadastrada com sucesso!"));
        }
        catch(SQLException ex){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista contacte o administrador do sistema.",
                    null));
            Logger.getLogger(NaturezaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }
        reset();
    }

    public void editar() throws SQLException{
        if (isSeguro()){
            FacesContext context = FacesContext.getCurrentInstance();
            dao.setConexao(Conexao.getConnection());
            dao.getConexao().setAutoCommit(false);
            try{
                dao.editar(natureza);
                dao.getConexao().commit();
                context.addMessage(null, new FacesMessage("O nome da natureza foi modificado com sucesso!"));
            }
            catch(SQLException ex){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
                Logger.getLogger(NaturezaController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                dao.getConexao().setAutoCommit(true);
                dao.getConexao().close();
            }
            reset();
        }
        else System.out.println("Não seguro");
    }

    public void excluir() throws SQLException{
        FacesContext context = FacesContext.getCurrentInstance();
        if (isSeguro()){
            dao.setConexao(Conexao.getConnection());
            dao.getConexao().setAutoCommit(false);
            try{
                dao.excluir(natureza.getId());
                dao.getConexao().commit();
                context.addMessage(null, new FacesMessage("A natureza foi excluída com sucesso!"));
            } catch(SQLException ex){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
                Logger.getLogger(NaturezaController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                dao.getConexao().setAutoCommit(true);
                dao.getConexao().close();
            }
            reset();
        }
        else System.out.println("Não seguro");
    }

    public void prepararEdicao() throws SQLException{
        if (natureza.getId() > 0){
            dao.setConexao(Conexao.getConnection());
            natureza = dao.recuperarPorId(natureza.getId());
            dao.getConexao().close();
            editando = true;
        }
        /* caso o id seja inválido, a operação que será feita será de inserção. */
        else reset();
    }

    private void reset(){
        natureza = new Natureza();
        editando = false;
    }
    
    /*
     * retorna o mapeamento (chave, valor) das naturezas no banco de dados
     */
    public TreeMap<String, String> getTreeMap() throws SQLException{
        dao.setConexao(Conexao.getConnection());
        
        TreeMap<String, String> mapa = new TreeMap<String, String>();
        ArrayList<Natureza> naturezas = dao.listarTodos(LoginController.getGrupo().getId());
        
        dao.getConexao().close();
        for (int i = 0; i < naturezas.size(); i++){
            Natureza n = naturezas.get(i);
            String nome = n.getNome();
            String id = String.valueOf(n.getId());
            mapa.put(nome, id);
        }

        return mapa;
    }

    public Natureza getNatureza(){
        return natureza;
    }

    public void setNatureza(Natureza natureza){
        this.natureza = natureza;
    }

    public boolean getEditando(){
        return editando;
    }

}
