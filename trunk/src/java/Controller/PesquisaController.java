package Controller;

import Dao.Conexao;
import Dao.JDBCEnsinoDao;
import Dao.JDBCExtensaoDao;
import Dao.JDBCPesquisaDao;
import Dao.PesquisaDao;
import Model.Atividade;
import Model.Ensino;
import Model.Extensao;
import Model.Natureza;
import Model.Pesquisa;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Esta é uma instancia da classe abstrata atividadeController.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name="PesquisaController")
@ViewScoped
/**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
public class PesquisaController extends AtividadeController<Pesquisa, PesquisaDao>{

    public PesquisaController() throws SQLException{
        super();
        setDao(new JDBCPesquisaDao());
        setAtividade(new Pesquisa());
        paginaAtividade = "pesquisa";
        setOutro1("ensino");
        setOutro2("extensão");
        updateResponsaveisDualList();
    }

    private Ensino pesquisaParaEnsino(){
        Ensino e = new Ensino();
        Natureza n = new Natureza();
        n.setId(1);
        e.setComentario(getAtividade().getComentario());
        e.setDataFim(getAtividade().getDataFim());
        e.setDataInicio(getAtividade().getDataInicio());
        e.setDescricao(getAtividade().getDescricao());
        e.setGrupo(getAtividade().getGrupo());
        e.setJustificativa(getAtividade().getJustificativa());
        e.setNatureza(n);
        e.setParceiros(getAtividade().getParceiros());
        e.setPublicoAlvo("");
        e.setResponsaveis(getAtividade().getResponsaveis());
        e.setResultadosAlcancados(getAtividade().getResultadosAlcancados());
        e.setResultadosEsperados(getAtividade().getResultadosEsperados());
        e.setTitulo(getAtividade().getTitulo());
        return e;
    }

    private Extensao pesquisaParaExtensao(){
        Extensao e = new Extensao();
        Natureza n = new Natureza();
        n.setId(1);
        e.setComentario(getAtividade().getComentario());
        e.setDataFim(getAtividade().getDataFim());
        e.setDataInicio(getAtividade().getDataInicio());
        e.setDescricao(getAtividade().getDescricao());
        e.setGrupo(getAtividade().getGrupo());
        e.setJustificativa(getAtividade().getJustificativa());
        e.setNatureza(n);
        e.setParceiros(getAtividade().getParceiros());
        e.setPublicoAlvo("");
        e.setResponsaveis(getAtividade().getResponsaveis());
        e.setResultadosAlcancados(getAtividade().getResultadosAlcancados());
        e.setResultadosEsperados(getAtividade().getResultadosEsperados());
        e.setTitulo(getAtividade().getTitulo());
        return e;
    }

    @Override
    public String alterarTipoAtividade() {
        Atividade atv = null;
        FacesContext context = FacesContext.getCurrentInstance();

        if (getTipoAtividade().equals("extensão")) {
            try {
                setDaoAlteracao(new JDBCExtensaoDao());
                atv = pesquisaParaExtensao();
            } catch (SQLException ex) {
                Logger.getLogger(EnsinoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                setDaoAlteracao(new JDBCEnsinoDao());
                atv = pesquisaParaEnsino();
            } catch (SQLException ex) {
                Logger.getLogger(EnsinoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            getDaoAlteracao().setConexao(Conexao.getConnection());
            getDao().setConexao(Conexao.getConnection());
            getDao().excluir(getAtividade().getId());

            getDaoAlteracao().salvar(atv);
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                    + "Caso o problema persista cantacte o administrador do sistema.",
                    null));
        } finally {
            try {
                getDaoAlteracao().getConexao().setAutoCommit(true);
                getDao().getConexao().setAutoCommit(true);
                getDaoAlteracao().getConexao().close();
                getDao().getConexao().close();
            } catch (SQLException ex) {
                Logger.getLogger(EnsinoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return "pesquisa";
    }
}
