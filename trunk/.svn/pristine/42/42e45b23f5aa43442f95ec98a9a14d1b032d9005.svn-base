package Controller;

import Dao.AlunoDao;
import Dao.AtividadeDao;
import Dao.Conexao;
import Dao.Dao;
import Dao.JDBCAlunoDao;
import Model.Aluno;
import Model.Atividade;
import Model.Grupo;
import exceptions.ForbiddenException;
import exceptions.PageNotFoundException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.model.DualListModel;


/**
 * Esta é uma classe abstrata de controle que realiza a interação entre a view,
 * o javabean abstrato "Atividade" e o banco de dados. As instâncias desta
 * classe são "EnsinoController", "PesquisaController" ou "ExtensaoController".
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public abstract class AtividadeController<Tatividade extends Atividade, Tdao extends AtividadeDao<Tatividade>> implements Serializable{
    private Tdao dao;
    private Tatividade atividade;

    /* Lista com os petianos que podem se tornar responsáveis por alguma atividade */
    private ArrayList<Aluno> possiveisResponsaveis;
    private DualListModel<Aluno> responsaveisDualList;

    /* Navegação */
    protected String paginaAtividade;

    /* Período das atividades a serem exibidas */
    private Date periodoInicio;
    private Date periodoFim;

    private String outro1;
    private String outro2;
    private String tipoAtividade;
    
    private Dao daoAlteracao;
    private ArrayList<Tatividade> outrass;
    
    private boolean inverter = false;
    
    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public AtividadeController() throws SQLException{
        //periodoInicio = SessionController.getInicioAno();
        //periodoFim = SessionController.getFimAno();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        periodoInicio = (Date) session.getAttribute("dataInicioAtividades");
        periodoFim = (Date) session.getAttribute("dataFimAtividades");
    }

    /**
     * Verifica o id em "atividade" para certificar se o usuário tem permissão
     * para executar uma determinada ação.
     *
     * @author Tiago Peres
     * @return true, se é seguro. false se não é seguro
     * @version 3.0
     * @since 3.0
     */
    private boolean isSeguro(){
        Grupo gLogado = LoginController.getGrupo();
        return (gLogado.equals(atividade.getGrupo()));
    }

    /**
     * Carrega todos os dados da atividade no atributo "atividade" de acordo com
     * seu id.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void carregarAtividade() throws SQLException {
        getDao().setConexao(Conexao.getConnection());
        if (atividade.getId() != 0)
            atividade = dao.recuperarPorId(atividade.getId());
        if (atividade == null) throw new PageNotFoundException();
        
        getDao().getConexao().close();
        updateResponsaveisDualList();
    }

    /**
     * Atualiza as listas de petianos responsáveis pela atividade (responsaveis)
     * e de petianos que ainda podem se tornar responsáveis por ela
     * (possiveisResponsaveis).
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    protected final void updateResponsaveisDualList() throws SQLException{
        // carrega os petianos que realizam a atividade
        ArrayList<Aluno> responsaveis = atividade.getResponsaveis();
        
        // carrega os petianos que podem realizar a atividade
        AlunoDao alunoDao = new JDBCAlunoDao(Conexao.getConnection());
        // Cast de generics não funciona
        ArrayList<Aluno> pRes = alunoDao.listarTodos(LoginController.getGrupo().getId());
        possiveisResponsaveis = new ArrayList<Aluno>();
        for (int i = 0; i < pRes.size(); i++){
            Aluno a = pRes.get(i);
            // Se o aluno já realiza a atividade ele não deve entrar para a lista de possíveis responsáveis
            
            if (!responsaveis.contains(a) && (a.getDataSaidaPet() == null || a.getDataSaidaPet().after(SessionController.getInicioAno())))
                possiveisResponsaveis.add(a);
        }
        
        alunoDao.getConexao().close();
        responsaveisDualList = new DualListModel<Aluno>(possiveisResponsaveis, responsaveis);
    }

    /**
     * Salva no banco de dados a atividade carregada neste objeto (parâmetro
     * atividade).
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página seguinte
     */
    public String salvar() throws SQLException{
        //atividade.setDataInicio(periodoInicio);
        //atividade.setDataFim(periodoFim);
        // pega o resultado do picklist e atribui a atividade
        atividade.setResponsaveis((ArrayList<Aluno>)responsaveisDualList.getTarget());
        atividade.setGrupo(LoginController.getGrupo());
        
        // prepara o JSF para lançar mensagens
        FacesContext context = FacesContext.getCurrentInstance();
        // diz ao JSF para guardar as mensagens aqui lançadas por mais uma requisição (devido ao faces-redirect=true)
        context.getExternalContext().getFlash().setKeepMessages(true);
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);

        try {
            // salva a atividade
            dao.salvar(atividade);
            /*** COMMIT **/
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A atividade foi cadastrada com sucesso!"));
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }

        return paginaAtividade + "?faces-redirect=true";
    }

    /**
     * atualiza o banco de dados com as informações contidas no atributo
     * "atividade".
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página seguinte
     */
    public String editar() throws SQLException{
        //atividade.setDataInicio(periodoInicio);
        //atividade.setDataFim(periodoFim);
        if (!isSeguro()) throw new ForbiddenException();

        // pega o resultado do picklist e atribui a atividade
        atividade.setResponsaveis((ArrayList<Aluno>)responsaveisDualList.getTarget());
        
        // prepara o JSF para lançar mensagens
        FacesContext context = FacesContext.getCurrentInstance();
        // diz ao JSF para guardar as mensagens aqui lançadas por mais uma requisição (devido ao faces-redirect=true)
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        // salva as alterações no banco
        dao.setConexao(Conexao.getConnection());
        dao.getConexao().setAutoCommit(false);

        try{
            dao.editar(atividade);
            dao.getConexao().commit();
            context.addMessage(null, new FacesMessage("A atividade foi modificada com sucesso!"));
        }
        catch(SQLException ex){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                    "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            dao.getConexao().setAutoCommit(true);
            dao.getConexao().close();
        }

        return paginaAtividade + "?faces-redirect=true";
    }

    /**
     * Exclui a atividade carregada neste objeto (atributo "atividade") do banco
     * de dados.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void excluir() throws SQLException{
        FacesContext context = FacesContext.getCurrentInstance();
        if (isSeguro()){
            dao.setConexao(Conexao.getConnection());
            dao.getConexao().setAutoCommit(false);

            try{
                dao.excluir(atividade.getId());
                dao.getConexao().commit();
                context.addMessage(null, new FacesMessage("A atividade foi excluída com sucesso!"));
            }
            catch(SQLException ex){
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                        "Caso o problema persista cantacte o administrador do sistema.",
                        null));
                Logger.getLogger(AtividadeController.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                dao.getConexao().setAutoCommit(true);
                dao.getConexao().close();
            }
        }
        else throw new ForbiddenException();
    }

    public void alterarData(DateSelectEvent event){
       SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

       GregorianCalendar dataInicial = new GregorianCalendar();
       GregorianCalendar dataFinal = new GregorianCalendar();
       dataInicial.setTime(periodoInicio);
       int ano = dataInicial.get(GregorianCalendar.YEAR);
       System.out.println("Data: " + form.format(periodoInicio));
       dataFinal.set(ano, GregorianCalendar.DECEMBER, 31);
       periodoFim = dataFinal.getTime();
    }

    /**
     * Retorna as atividades de pesquisa, ensino ou extensão do grupo,
     * dependendo da classe instanciada (EnsinoController, PesquisaController ou
     * ExtensaoController).
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Atividades do grupo
     */
    public ArrayList<Tatividade> getAtividades() throws SQLException{
        dao.setConexao(Conexao.getConnection());
	ArrayList<Tatividade> atividades = dao.listarTodos(LoginController.getGrupo().getId(), periodoInicio, periodoFim);
        if(inverter)
            Collections.reverse(atividades);
        dao.getConexao().close();
        return atividades;
    }
    
    /**
     * Retorna as atividades de pesquisa, ensino ou extensão do aluno logado,
     * dependendo da classe instanciada (EnsinoController, PesquisaController ou
     * ExtensaoController).
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Atividades do aluno logado
     */
    public ArrayList<Tatividade> getAtividadesDoAluno() throws SQLException{
        /* Uma consulta direto no banco talvez seria mais eficiente que isso. */
        dao.setConexao(Conexao.getConnection());
        ArrayList<Tatividade> todas = dao.listarTodos(LoginController.getGrupo().getId(), periodoInicio, periodoFim);
        ArrayList<Tatividade> doAluno = new ArrayList<Tatividade>();
        outrass = new ArrayList<Tatividade>();
        Aluno user = new Aluno();
        user.setId(LoginController.getUsuario().getId());
        for (int i = 0; i < todas.size(); i++){
            if (todas.get(i).getResponsaveis().contains(user))
                doAluno.add(todas.get(i));
            else
                outrass.add(todas.get(i));
        }
        dao.getConexao().close();
        if(inverter)
            Collections.reverse(doAluno);
        return doAluno;
    }
    
    public ArrayList<Tatividade> getAtividadesOutrass(){
        return outrass;
    }
    
    /**
     * Retorna as atividades de pesquisa, ensino ou extensão do grupo que não,
     * pertencem ao aluno logado, dependendo da classe instanciada
     * (EnsinoController, PesquisaController ou ExtensaoController).
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Atividades do grupo que não pertencem ao aluno logado
     */
    public ArrayList<Tatividade> getDemaisAtividades() throws SQLException{
        /* Pegar o restante de getAtividadesDoAluno() seria mais eficiente que isso. */
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 11111");
        ArrayList<Tatividade> todas = dao.listarTodos(LoginController.getGrupo().getId(), periodoInicio, periodoFim);
        ArrayList<Tatividade> demais = new ArrayList<Tatividade>();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 22222");
        Aluno user = new Aluno();
        user.setId(LoginController.getUsuario().getId());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 33333333");
        for (int i = 0; i < todas.size(); i++){
            if (!todas.get(i).getResponsaveis().contains(user))
                demais.add(todas.get(i));
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 4444444444");
        if(inverter)
            Collections.reverse(demais);
        
        return demais;
    }

    public void inverter(){
        if(inverter)
            inverter = false;
        else
            inverter = true;
    }
    
    public abstract String alterarTipoAtividade();
    
    /**
     * Recupera os alunos responsáveis e que podem se tornar responsáveis pela
     * atividade. Utilizado pelo Picklist do Primefaces.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Lista dupla de alunos. Na primeira lista, os alunos que podem ser
     * responsáveis pela atividade, na segunda os alunos que já são responsáveis
     * pela atividade.
     */
    public DualListModel<Aluno> getResponsaveisDualList(){
        return responsaveisDualList;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param responsaveisDualList Lista dupla de alunos. Na primeira lista, os
     * alunos que podem ser responsáveis pela atividade, na segunda os alunos
     * que já são responsáveis pela atividade.
     */
    public void setResponsaveisDualList(DualListModel<Aluno> responsaveisDualList){
        this.responsaveisDualList = responsaveisDualList;
    }

    /**
     * Define o dao a ser utilizado pelas operações de banco de dados.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param dao conjunto de instruções a serem utilizadas nas operações com o
     * banco de dados.
     */
    protected void setDao(Tdao dao) {
        this.dao = dao;
    }
    
    protected Tdao getDao(){
        return dao;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return atividade atividade em questão
     */
    public Tatividade getAtividade() {
        return atividade;
    }

    /**
     * Define a atividade a ser utilizada por este controller.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param atividade atividade em questão
     */
    public void setAtividade(Tatividade atividade) {
        this.atividade = atividade;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return periodoInicio início do período das atividades a serem exibidas
     */
    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    /**
     * Muda a data de início período das atividades a serem exibidas.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param periodoInicio inicio do periodo das atividades a serem exibidas
     */
    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return periodoFim fim do período das atividades a serem exibidas
     */
    public Date getPeriodoFim() {
        return periodoFim;
    }

    /**
     * Muda a data de fim do período das atividades a serem exibidas.
     * 
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param periodoFim fim do período das atividades a serem exibidas
     */
    public void setPeriodoFim(Date periodoFim) {
        this.periodoFim = periodoFim;
    }

    /**
     * @return the tipoAtividade
     */
    public String getTipoAtividade() {
        return tipoAtividade;
    }

    /**
     * @param tipoAtividade the tipoAtividade to set
     */
    public void setTipoAtividade(String tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    /**
     * @return the outro1
     */
    public String getOutro1() {
        return outro1;
    }

    /**
     * @param outro1 the outro1 to set
     */
    public void setOutro1(String outro1) {
        this.outro1 = outro1;
    }

    /**
     * @return the outro2
     */
    public String getOutro2() {
        return outro2;
    }

    /**
     * @param outro2 the outro2 to set
     */
    public void setOutro2(String outro2) {
        this.outro2 = outro2;
    }

    /**
     * @return the daoAlteracao
     */
    public Dao getDaoAlteracao() {
        return daoAlteracao;
    }

    /**
     * @param daoAlteracao the daoAlteracao to set
     */
    public void setDaoAlteracao(Dao daoAlteracao) {
        this.daoAlteracao = daoAlteracao;
    }

}