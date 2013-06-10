package Controller;

import Dao.Conexao;
import Dao.InstituicaoDao;
import Dao.JDBCInstituicaoDao;
import Dao.JDBCClaDao;
import Model.Instituicao;
import Model.Cla;
import converters.Criptografia;
import exceptions.PageNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Eduardo
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "InstituicaoController")
@ViewScoped
public class InstituicaoController {

    private InstituicaoDao instituicaoDao;
    private Instituicao instituicao;
    private Cla cla;
    private JDBCClaDao claDao;
    private int passo; // a inserção é feita em 2 passos
    private String id; // grava o id da instituição a ser editada

    /**
     * Define o valor do passo igual a 1 e instancia as classes
     * e instancia as classes JDBCInsticuicaoDao, JDBCClaDao,
     * Instituicao e Cla.
     *
     * @version 3.0
     * @since 3.0
     *
     */
    public InstituicaoController() throws SQLException {
        passo = 1;
        instituicaoDao = new JDBCInstituicaoDao();
        claDao = new JDBCClaDao();
        instituicao = new Instituicao();
        cla = new Cla();
        //reset();
    }

    /**
     * Salva no banco de dados a instituição carregada neste objeto (parâmetro
     * instituicao).
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página seguinte
     *
     */
    public String salvar() throws UnsupportedEncodingException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Connection conexao = Conexao.getConnection();
        conexao.setAutoCommit(false);
        try {
            instituicaoDao.setConexao(conexao);
            claDao.setConexao(conexao);

            instituicao.setId(instituicaoDao.salvar(instituicao));
            cla.setInstituicao(instituicao);
            cla.setSenha(Criptografia.randomMd5());
            claDao.salvar(cla);

            conexao.commit(); // a conexao é a msm para ambos os daos

            // envia e-mail para o CLA
            ArrayList<String> destinatarios = new ArrayList<String>();
            destinatarios.add(cla.getEmail());
            UsuarioController.boasVindas("CLA", cla.getSenha(), destinatarios);
            context.addMessage(null, new FacesMessage("A instituição e o CLA foram cadastrados com sucesso!"));
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O cadastro não foi realizado. O endereço de e-mail do CLA informado já existe no sistema.",
                        null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                        + "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            }
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.setAutoCommit(true);
            conexao.close();
        }
        return "instituicao";
    }

    /**
     * Carrega todos os dados da instituição no atributo "instituicao" de acordo com
     * seu id.
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void carregarInstituicao() throws SQLException {
        if (id != null) {
            Connection conexao = Conexao.getConnection();
            instituicaoDao.setConexao(conexao);
            claDao.setConexao(conexao);

            instituicao = instituicaoDao.recuperarPorId(Integer.parseInt(id));
            if (instituicao == null) {
                throw new PageNotFoundException();
            }

            cla = claDao.recuperarInterlocutorCla(instituicao.getId());

            conexao.close();
        }
    }

    /**
     * atualiza o banco de dados as informações contidas no atributo 
     * "instituicao".
     * Apenas os dados básicos da instituição (usados pelo CLA).
     *
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    // edita apenas os dados básicos da instituição. Usado pelo CLA.
    public void editarSimples() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        instituicaoDao.setConexao(Conexao.getConnection());
        instituicaoDao.getConexao().setAutoCommit(false);

        try {
            instituicaoDao.editar(instituicao);
            instituicaoDao.getConexao().commit();
            context.addMessage(null, new FacesMessage("Os dados da instituição foram alterados com sucesso!"));
        } catch (SQLException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                    + "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            instituicaoDao.getConexao().setAutoCommit(true);
            instituicaoDao.getConexao().close();
        }
    }

    /**
     * atualiza o banco de dados com as informações contidas no atributo
     * "instituicao".
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return página seguinte
     */
    public String editar() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Connection conexao = Conexao.getConnection();
        conexao.setAutoCommit(false);
        try {
            instituicaoDao.setConexao(conexao);
            claDao.setConexao(conexao);

            instituicaoDao.editar(instituicao);
            System.out.println(cla.getId());
            claDao.editar(cla);

            conexao.commit();
            context.addMessage(null, new FacesMessage("Os dados foram alterados com sucesso!"));
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O endereço de e-mail do CLA informado já existe no sistema.",
                        null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                        + "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            }
            Logger.getLogger(InstituicaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.setAutoCommit(true);
            conexao.close();
        }

        return "instituicao";
    }

    /**
     *
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return TreeMap com as instituições
     */
    public TreeMap<String, String> getTreeMap() throws SQLException {
        instituicaoDao.setConexao(Conexao.getConnection());

        TreeMap<String, String> mapa = new TreeMap<String, String>();
        ArrayList<Instituicao> instituicoes = instituicaoDao.listarTodos();

        instituicaoDao.getConexao().close();

        for (int i = 0; i < instituicoes.size(); i++) {
            Instituicao inst = instituicoes.get(i);
            String sigla = inst.getSigla();
            String idInst = String.valueOf(inst.getId());
            mapa.put(sigla, idInst);
        }
        return mapa;
    }

    /*public void reset() throws SQLException{
    instituicaoDao = new JDBCInstituicaoDao();
    instituicao = new Instituicao();
    cla = new Cla();
    cladao = new JDBCClaDao();
    }*/
    /**
     * @version 3.0
     * @since 3.0
     * @return instituicao instituição em questão
     */
    public Instituicao getInstituicao() {
        return instituicao;
    }

    /**
     * Define a instituição a ser utilizada por este controller.
     *
     * @version 3.0
     * @since 3.0
     * @param instituicao instituição em questão
     */
    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * 
     * @version 3.0
     * @since 3.0
     * @return cla Cla em questão
     */
    public Cla getCla() {
        return cla;
    }

    /**
     * Define o CLA a ser utilizado por este controller.
     *
     * @version 3.0
     * @since 3.0
     * @param cla CLA em questão
     */
    public void setCla(Cla cla) {
        this.cla = cla;
    }

    /**
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return Um array com as instituicoes do sistema
     */
    public ArrayList<Instituicao> getInstituicoes() throws SQLException {
        instituicaoDao.setConexao(Conexao.getConnection());
        ArrayList<Instituicao> instituicoes = instituicaoDao.listarTodos();
        instituicaoDao.getConexao().close();
        return instituicoes;
    }

    /**
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return O passo do Formulario
     */
    public int getPasso() {
        return passo;
    }

    /**
     * Incrementa a variavel passo.
     *
     * @version 3.0
     * @since 3.0
     */
    public void proximoPasso() {
        ++passo;
    }

    /**
     * Decrementa a variavel passo.
     *
     * @version 3.0
     * @since 3.0
     */
    public void passoAnterior() {
        --passo;
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return Id da instituição
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @param Id da instituição
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Faz a validação da sigla da instituição a ser cadastrada
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param context (JSF), component (JSF), value valor do campo a ser validado.
     */

    public void validarSiglaInst(FacesContext context, UIComponent component, Object value) throws SQLException {
        String sigla = (String) value;
        Connection conexao = Conexao.getConnection();
        instituicaoDao.setConexao(conexao);
        if (instituicao.getId() == 0) {
            if (instituicaoDao.recuperarPorSigla(sigla) == null) {
                conexao.close();
                return; // se não houver curso cadastrado com a mesma sigla na mesma instituição, passa pela validação com sucesso
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A instituição " + sigla + " já existe.", null));
            }
        } else {
            Instituicao i = new Instituicao();
            i = instituicaoDao.recuperarPorSigla(sigla);
            conexao.close();
            if (i == null) {
                return;
            } else if (instituicao.getId() == i.getId() || i.getId() == 0) {
                return;
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A instituição " + sigla + " já existe.", null));
            }
        }
    }

    /**
     * Faz a validação do nome da instituição a ser cadastrada
     *
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param context (JSF), component (JSF), value valor do campo a ser validado.
     */

    public void validarNomeInst(FacesContext context, UIComponent component, Object value) throws SQLException {
        String nome = (String) value;
        Connection conexao = Conexao.getConnection();
        instituicaoDao.setConexao(conexao);
        if (instituicao.getId() == 0) { // Se id for 0, operação = salvar.
            if (instituicaoDao.recuperarPorNome(nome) == null) {
                conexao.close();
                return; // se não houver curso cadastrado com a mesma sigla na mesma instituição, passa pela validação com sucesso
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A instituição " + nome + " já existe.", null));
            }
        } else {
            Instituicao i = new Instituicao();
            i = instituicaoDao.recuperarPorNome(nome);
            conexao.close();
            if (i == null) {
                return;
            } else if (instituicao.getId() == i.getId() || i.getId() == 0) {
                return;
            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um erro. A instituição " + nome + " já existe.", null));
            }
        }
    }
}
