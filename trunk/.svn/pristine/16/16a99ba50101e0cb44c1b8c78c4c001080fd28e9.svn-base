/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AdministradorDao;
import Dao.ClaDao;
import Dao.Conexao;
import Dao.GrupoDao;
import Dao.InstituicaoDao;
import Dao.JDBCAdministradorDao;
import Dao.JDBCAlunoDao;
import Dao.JDBCClaDao;
import Dao.JDBCGrupoDao;
import Dao.JDBCInstituicaoDao;
import Dao.JDBCTutorDao;
import Dao.PetianoDao;
import Dao.TutorDao;
import Dao.UsuarioDao;
import Model.Cla;
import Model.Grupo;
import Model.Instituicao;
import Model.Petiano;
import Model.Tutor;
import Model.Usuario;
import converters.Criptografia;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * Esta é uma classe de controle reponsável pelo controle de login, e as
 * devidas permissões do sistema.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "LoginController")
@ViewScoped
public class LoginController {

    private String senha;
    private String senhaNova;
    private String senhaConfirmada;
    private int contadorSenha = 0; //contornando bug estranho de se chamar duas vezes seguidas método setSenha()
    private boolean senhaInvalida = false; //marca se o usuário digitou sua senha corretamente para invocar ou não msg de erro
    private boolean senhaInvalidaAdm = false;
    private TreeMap<String, String> instituicoes;
    private TreeMap<String, String> grupos;
    private TreeMap<String, String> usuarios;
    private TreeMap<String, String> clas;
    private int idTutor;
    private int idInstituicao;
    private int idGrupo;
    private int idUsuario;
    private int tmp; // solução temporária para os problemas de duplas requisições com ids
    private Date data;
    private Date dataInicioAtividades;
    private Date dataFimAtividades;

    
    // Roles
    public static final int N_ROLES = 6;
    public static final int ADMIN = 0;
    public static final int CLA = 1;
    public static final int TUTOR = 2;
    public static final int ALUNO = 3;
    public static final int VISITANTE = 4;
    public static final int CLA_MEMBRO = 5;
    public static final String[] ROLES = {"admin", "cla", "tutor", "aluno", "visitante", "claMembro"};
    

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tiago Peres
     * @throws SQLException, EmailException
     * @version 3.0
     * @since 3.0
     */
    public LoginController() throws SQLException {
        InstituicaoController ic = new InstituicaoController();
        instituicoes = ic.getTreeMap();
        data = new Date();
        dataInicioAtividades = new Date();
        
        dataFimAtividades = new Date();
    }

    /**
     * Atualiza os grupos da TreeMap
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public void atualizarGrupos() throws SQLException {
        senhaInvalida = false;
        senhaInvalidaAdm = false;
        GrupoController gc = new GrupoController();
        grupos = gc.getTreeMap(idInstituicao);
    }

    /**
     * Atualiza os usuarios da TreeMap
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public void atualizarUsuarios() throws SQLException {
        senhaInvalida = false;
        senhaInvalidaAdm = false;
        int idg = idGrupo;
        AlunoController ac = new AlunoController();
        TutorDao tdao = new JDBCTutorDao(Conexao.getConnection());
        Tutor tutor = tdao.recuperarTutorAtivo(idGrupo);
        tdao.getConexao().close();

        usuarios = new TreeMap<String, String>();

        if (tutor != null) {
            usuarios.put(tutor.getNome(), String.valueOf(tutor.getId()));
            idTutor = tutor.getId();
        }
        TreeMap<String, String> alunos = ac.getTreeMapByGroup(idg);
        usuarios.putAll(alunos);
        atualizarCla();
    }

    /**
     * Atualiza os usuarios de um Cla da TreeMap
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public void atualizarCla() throws SQLException {
        senhaInvalida = false;
        senhaInvalidaAdm = false;
        ClaController claCon = new ClaController();
        clas = claCon.tMapCla(idInstituicao);
    }

    /**
     * Gerencia o login dos usuários.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return string para o redirecionamento da pagina.
     */
    public String login() throws SQLException, UnsupportedEncodingException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        Connection conexao = Conexao.getConnection();
        GrupoDao gdao = new JDBCGrupoDao(conexao);
        InstituicaoDao idao = new JDBCInstituicaoDao(conexao);
        String pagina = null;
        if (idUsuario == 1) {
            AdministradorDao adao = new JDBCAdministradorDao(conexao);
            session.setAttribute("nivelUsuario", ADMIN);
            pagina = "administrador";

            Usuario usuario = adao.recuperarAdministrador();

            session.setAttribute("usuario", usuario);
        } /* nenhum usuário ou grupo tem id = -1, quando isso acontecer, por
         * convenção, a referência será ao CLA.
         */ else if (idGrupo == -1) {
            ClaDao claDao = new JDBCClaDao(conexao);
            //session.setAttribute("nivelUsuario", CLA);
            pagina = "cla";
            Usuario usuario = claDao.recuperarPorId(idUsuario);
            if (((Cla) usuario).getIdCla() == usuario.getId()) {
                session.setAttribute("nivelUsuario", CLA);
            } else {
                session.setAttribute("nivelUsuario", CLA_MEMBRO);
            }
            Instituicao instituicao = idao.recuperarPorId(idInstituicao);
            session.setAttribute("usuario", usuario);
            session.setAttribute("instituicao", instituicao);
        } else {
            PetianoDao pdao;
            if (idUsuario == idTutor) {
                pdao = new JDBCTutorDao(conexao);
                session.setAttribute("nivelUsuario", TUTOR);
                session.setAttribute("dataInicioAtividades", dataInicioAtividades);
                session.setAttribute("dataFimAtividades", dataFimAtividades);
                pagina = "tutor";
            } else {
                pdao = new JDBCAlunoDao(conexao);
                session.setAttribute("nivelUsuario", ALUNO);
                pagina = "aluno";
            }

            Usuario usuario = (Petiano) pdao.recuperarPorId(idUsuario);
            Grupo grupo = gdao.recuperarPorId(idGrupo);
            Instituicao instituicao = idao.recuperarPorId(idInstituicao);

            session.setAttribute("usuario", usuario);
            session.setAttribute("grupo", grupo);
            session.setAttribute("instituicao", instituicao);
        }

        /*Senha passada pelo usuário é criptografada e comparada com a senha
         * correspondente também criptografada, isto é, é realizada uma
         * comparação entre as criptografias.
         */
        String redirect = null;
        if ((Criptografia.md5(senha)).equals(((Usuario) session.getAttribute("usuario")).getSenha())) {
            StringBuilder sb = new StringBuilder();
            sb.append(pagina);
            sb.append("?faces-redirect=true");
            senhaInvalida = false;
            senhaInvalidaAdm = false;
            contadorSenha = 0; //Isso é POG! Encontrar a solução apropriada depois
            redirect = sb.toString();
        } else {
            session.setAttribute("nivelUsuario", VISITANTE);
            senha = new String();
            if ((Integer) session.getAttribute("nivelUsuario") == ADMIN) {
                senhaInvalidaAdm = true;
            } else {
                senhaInvalida = true;
            }
            contadorSenha = 0;

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Senha incorreta. Por favor, tente novamente", null));
        }

        conexao.close();
        return redirect;
    }

    /**
     * Gerencia a alteração de senha.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public void alterarSenha() throws SQLException, UnsupportedEncodingException {
        Connection conexao = Conexao.getConnection();
        conexao.setAutoCommit(false);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        try {
            if ((((Usuario) session.getAttribute("usuario")).getSenha()).equals(Criptografia.md5(senha))) {
                if (senhaNova.equals(senhaConfirmada)) {
                    ((Usuario) session.getAttribute("usuario")).setSenha(Criptografia.md5(senhaConfirmada));
                    UsuarioDao udao;
                    switch (((Integer) session.getAttribute("nivelUsuario"))) {
                        case ALUNO:
                            udao = new JDBCAlunoDao(conexao);
                            break;
                        case TUTOR:
                            udao = new JDBCTutorDao(conexao);
                            break;
                        case CLA:
                            udao = new JDBCClaDao(conexao);
                            break;
                        case CLA_MEMBRO:
                            udao = new JDBCClaDao(conexao);
                            break;
                        default:
                            udao = new JDBCAdministradorDao(conexao);
                    }
                    udao.editar((Usuario) session.getAttribute("usuario"));
                    conexao.commit();
                    conexao.setAutoCommit(true);
                    conexao.close();
                    context.addMessage(null, new FacesMessage("Senha alterada com sucesso!"));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro na confirmação de senhas" + "", null));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Senha incorreta. " + "Por favor, tente novamente", null));
            }
        } catch (SQLException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                    + "Caso o problema persista cantacte o administrador do sistema.",
                    null));
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, e);
        }
        contadorSenha = 0;
    }

    /**
     * Realiza o logout dos usuários.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return pagina de redirecionamento.
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("grupo", null);
        session.setAttribute("instituicao", null);
        session.setAttribute("usuario", null);
        session.setAttribute("nivelUsuario", VISITANTE);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        session.invalidate();
        //return "index?faces-redirect=true";
        return "index.xhtml?faces-redirect=true";
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return TreeMap de Instituições
     */
    public TreeMap<String, String> getInstituicoes() {
        return instituicoes;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return TreeMap de Grupos
     */
    public TreeMap<String, String> getGrupos() {
        return grupos;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return TreeMap de Usuários
     */
    public TreeMap<String, String> getUsuarios() {
        return usuarios;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return senha senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        if (contadorSenha == 0) {
            this.senha = senha;
            contadorSenha++;
        }
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Id Id da Instituição
     */
    public int getIdInstituicao() {
        return idInstituicao;
    }

    /**
     * @param idInstituicao the idInstituicao to set
     */
    public void setIdInstituicao(int idInstituicao) {
        this.idInstituicao = idInstituicao;
        idGrupo = 0;
        idUsuario = 0;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Id Id do grupo
     */
    public int getIdGrupo() {
        return idGrupo;
    }

    /**
     * @param idGrupo the idGrupo to set
     */
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
        idUsuario = 0;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Id Id do usuário.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Id Id Roles
     */
    public static int getRoleId(String role) {
        for (int i = 0; i < N_ROLES; i++) {
            if (ROLES[i].equals(role)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return tmp tmp
     */
    public int getTmp() {
        return tmp;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return ContadorSenha ContadorSenha
     */
    public int getContadorSenha() {
        return contadorSenha;
    }

    /**
     * @param tmp the tmp to set
     */
    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public void validaSenha() {
        senhaInvalida = false;
        validaSenhaAdm();
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return SenhaInvalida SenhaInvalida
     */
    public boolean getSenhaInvalida() {
        return senhaInvalida;
    }

    public void validaSenhaAdm() {
        senhaInvalidaAdm = false;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return SenhaInvalidaAdm SenhaInvalidaAmd
     */
    public boolean getSenhaInvalidaAdm() {
        return senhaInvalidaAdm;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return SenhaConfirmada SenhaConfirmada
     */
    public String getSenhaConfirmada() {
        return senhaConfirmada;
    }

    public void setSenhaConfirmada(String senhaConfirmada) {
        this.senhaConfirmada = senhaConfirmada;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return SenhaNova SenhaNova
     */
    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return data data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Usuario Usuario
     */
    public static Usuario getUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return (Usuario) session.getAttribute("usuario");
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Grupo Grupo
     */
    public static Grupo getGrupo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return (Grupo) session.getAttribute("grupo");
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Instituicao Instituicao
     */
    public static Instituicao getInstituicao() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return (Instituicao) session.getAttribute("instituicao");
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return NivelUsuario NivelUsuario
     */
    public static Integer getNivelUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute("nivelUsuario");
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a Tutor.
     */
    public static boolean isTutor() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == TUTOR);
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a Aluno.
     */
    public static boolean isAluno() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == ALUNO);
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a CLA.
     */
    public static boolean isCLA() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == CLA);
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a CLA_MEMBRO.
     */
    public static boolean isCLAMembro() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == CLA_MEMBRO);
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a ADMIN.
     */
    public static boolean isAdmin() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == ADMIN);
    }

    /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Boolean true se o nivelUsuario é igual a Visitante.
     */
    public static boolean isVisitante() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        return ((Integer) session.getAttribute("nivelUsuario") == VISITANTE);
    }

    /**
     * @return the clas
     */
    public TreeMap<String, String> getClas() {
        return clas;
    }

    /**
     * @param clas the clas to set
     */
    public void setClas(TreeMap<String, String> clas) {
        this.clas = clas;
    }
    
    
    public Date getDataFimAtividades() {
        return dataFimAtividades;
    }

    public void setDataFimAtividades(Date dataFimAtividades) {
        this.dataFimAtividades = dataFimAtividades;
    }

    public Date getDataInicioAtividades() {
        return dataInicioAtividades;
    }

    public void setDataInicioAtividades(Date dataInicioAtividades) {
        this.dataInicioAtividades = dataInicioAtividades;
    }
}
