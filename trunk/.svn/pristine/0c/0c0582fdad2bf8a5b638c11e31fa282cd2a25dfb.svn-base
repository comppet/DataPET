package Controller;

import Dao.AnotacaoDao;
import Dao.Conexao;
import Dao.JDBCAnotacaoDao;
import Model.Anotacao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.UnselectEvent;

/**
 * Esta é uma classe de controle que realiza a interação entre a view,
 * o javabean "Anotacao" e o banco de dados.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name="AnotacaoController")
@ViewScoped
public class AnotacaoController {
    private AnotacaoDao dao;
    private Anotacao anotacao, novaAnotacao;
    private int idAluno = 0;
    private ArrayList<Anotacao> anotacoes;

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public AnotacaoController() throws SQLException{
        dao = new JDBCAnotacaoDao();
        //updateAnotacoes();
        anotacao = new Anotacao();
        /*anotacao.setTexto("");*/
        novaAnotacao = new Anotacao();
        //anotacoes = new ArrayList<Anotacao>();
    }
    
    /**
     * Salva no banco de dados as anotações carregadas nestes objetos (parâmetro
     * anotacao).
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     *
     */
    public void salvar() throws SQLException{
        novaAnotacao.getAluno().setId(idAluno);
        Date data = new Date(System.currentTimeMillis());
        novaAnotacao.setData(data);
        FacesContext context = FacesContext.getCurrentInstance();
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);

        try{
            novaAnotacao.setId(dao.salvar(novaAnotacao));
            anotacoes.add(0, novaAnotacao);
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A anotação foi cadastrada com sucesso!"));
        }
        catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(AnotacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }
        setNovaAnotacao(new Anotacao());
        updateAnotacoes();
    }

    /**
     * atualiza o banco de dados com as informações contidas no atributo
     * "anotacao".
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */

    public void editar() throws SQLException{
        FacesContext context = FacesContext.getCurrentInstance();
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);

        try{
            dao.editar(anotacao);
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A anotação foi modificada com sucesso!"));
        }
        catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista contacte o administrador do sistema.",
                    null));
            Logger.getLogger(AnotacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }
    }

    /**
     * Exclui a anotação carregada neste objeto (atributo "anotacao") do banco
     * de dados.
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */

    public void excluir() throws SQLException{
        FacesContext context = FacesContext.getCurrentInstance();
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);
        try{
            dao.excluir(anotacao.getId());
            anotacoes.remove(anotacao);
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A anotação foi excluída com sucesso!"));
        }
        catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(AnotacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }
        
        reset();
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return instancia corrente de AnotacaoDao
     */
    public AnotacaoDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(AnotacaoDao dao) {
        this.dao = dao;
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return instancia corrente de Anotacao
     */
    public Anotacao getAnotacao() {
        return anotacao;
    }

    /**
     * @param anotacao the anotacao to set
     */
    public void setAnotacao(Anotacao anotacao) {
        System.out.println("----------->SetAnotacao!");
        this.anotacao = anotacao;
    }
    
    public void dummyalert(){
        FacesContext.getCurrentInstance().addMessage("Oi",null);
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return Array de anotações
     */
    public ArrayList<Anotacao> getAnotacoes(){
       return anotacoes;
    }

    public void updateAnotacoes() throws SQLException{
        dao.setConexao(Conexao.getConnection());
        anotacoes = dao.recuperarPorAluno(idAluno);
        dao.getConexao().close();
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return id do Aluno referente a anotacao corrente 
     */
    public int getIdAluno() {
        return idAluno;
    }

    /**
     * @param idAluno the idAluno to set
     */
    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
    
    /**
     * Estabelece o id do aluno na a partir do evento disparado pela seleção
     * de um aluno
     * @param ValueChangeEvent evento disparado quando o valor foi modificado
     */
    public void idAlunoMudou(ValueChangeEvent e){
        this.idAluno = (Integer) e.getNewValue();
    }

    public void reset(){
        setAnotacao(new Anotacao());
        /*anotacao.setCarater(true);
        anotacao.setTexto("");*/
        anotacao.getAluno().setId(idAluno);
        System.out.println("Anotação resetada!");
    }
    
    public void onRowUnselect(UnselectEvent e){
        this.reset();
    }

    /**
     * @return the novaAnotacao
     */
    public Anotacao getNovaAnotacao() {
        return novaAnotacao;
    }

    /**
     * @param novaAnotacao the novaAnotacao to set
     */
    public void setNovaAnotacao(Anotacao novaAnotacao) {
        this.novaAnotacao = novaAnotacao;
    }

}