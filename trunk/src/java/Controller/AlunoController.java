/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.AlunoDao;
import Dao.Conexao;
import Dao.CursoDao;
import Dao.GrupoDao;
import Dao.JDBCAlunoDao;
import Dao.JDBCCursoDao;
import Dao.JDBCGrupoDao;
import Dao.JDBCNotaDao;
import Dao.NotaDao;
import Model.Aluno;
import Model.Curso;
import Model.Grupo;
import Model.Nota;
import Model.Usuario;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DateSelectEvent;

/**
 * Esta é uma classe de controle que realiza a interação entre a view,
 * os javabeans "Aluno" e "Nota" e o banco de dados.
 *
 * @author Pedro Augusto
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "AlunoController")
@ViewScoped
public class AlunoController extends PetianoController<Aluno, AlunoDao> {
    // atributos relativos a manipulação das notas

    private int semestreNota;
    private float valorNota = 0;
    private int periodoCurso = -1; // indica se o curso do aluno é semestral, anual ou desconhecido
    private ArrayList<Nota> notas; // notas no banco de dados (ou que serão adicionadas a ele)
    private ArrayList<AnoLetivo> anosSemNota; // anos que possuem periodos letivos sem nota no banco de dados
    private int iAno; // representa o índice de um elemento em anosSemNota
    ArrayList<Grafico> grafico; // grafico com as notas do aluno
    private Aluno[] alunosSelecionados; // grava os alunos selecionados do datatable

    /**
     * Construtor da classe, inicializa as váriaveis.
     *
     * @author Pedro Augusto
     * @exception SQLException
     * @version 3.0
     * @since 3.0
     */
    public AlunoController() throws SQLException {
        super();
        // REVER: PRA QUE ESSA PARAFERNALHA? VIDE TUTOR
        if (LoginController.getUsuario() != null && LoginController.getNivelUsuario() == LoginController.ALUNO) {
            setUsuario((Aluno) LoginController.getUsuario());
            prepararNotas();
        } else {
            setUsuario(new Aluno());
        }
        setDao(new JDBCAlunoDao());
    }

    /**
     * Verifica o id da instituição para certificar se o usuário tem permissão
     * para executar uma determinada ação.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return true, se é seguro. false se não é seguro
     */
    private boolean isSeguro() {
        Usuario uLogado = LoginController.getUsuario();
        Grupo gLogado = LoginController.getGrupo();
        return (uLogado.equals(getUsuario())
                || (LoginController.isTutor() && gLogado.equals(getUsuario().getGrupo())));
    }

    /**
     * Cadastra no banco de dados um aluno, com os dados presentes no atributo "usuario" da super-classe
     * "UsuarioController".
     *
     * @author Pedro Augusto
     * @version 3.0
     * @since 3.0
     */
    @Override
    public void salvar() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        getUsuario().setGrupo(LoginController.getGrupo());
        getDao().setConexao(Conexao.getConnection());
        getDao().getConexao().setAutoCommit(false);

        try {
            super.salvar();
            /*** COMMIT ***/
            getDao().getConexao().commit();
            context.addMessage(null, new FacesMessage("O aluno foi cadastrado com sucesso!"));

            // envia e-mail de boas vindas
            ArrayList<String> destinatarios = new ArrayList<String>(); //define destino para o email
            destinatarios.add(getUsuario().getEmail());
            boasVindas(getUsuario().getNome(), getUsuario().getSenha(), destinatarios);
            setUsuario(new Aluno());
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O aluno não foi cadastrado. O endereço de e-mail informado já existe no sistema.",
                        null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                        + "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            }
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getDao().getConexao().setAutoCommit(true);
            getDao().getConexao().close();
        }
        setUsuario(new Aluno());
    }

    /**
     * Modifica no banco de dados as informações do aluno presente no atributo "usuario" da super-classe
     * "UsuarioController".
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return a página a qual o usuário em sessão será redirecionado após a operação.
     */
    @Override
    public String editar() throws SQLException {
        if (!isSeguro()) {
            return "403";
        }

        FacesContext context = FacesContext.getCurrentInstance();
        String view;
        Connection conexao = Conexao.getConnection();
        conexao.setAutoCommit(false);
        try {
            getDao().setConexao(conexao);
            view = super.editar();
            // Salva as notas do aluno se houve alteração
            NotaDao ndao = new JDBCNotaDao(conexao);
            ndao.excluir(getUsuario().getId());
            for (int i = 0; i < notas.size(); i++) {
                Nota n = notas.get(i);
                if (n.getValor() > 100) {
                    n.setValor(100);
                }
                //if (n.getMarcador() == Nota.INSERT) {
                
                ndao.salvar(n);
                //} else if (n.getMarcador() == Nota.UPDATE) {
                //    ndao.editar(n);
                //}
            }

            conexao.commit();

            if (getUsuario().getDataSaidaPet() == null) {
                context.addMessage(null, new FacesMessage("As informações do aluno foram alteradas com sucesso!"));
                setUsuario(new Aluno());
            } else {
                setUsuario(new Aluno());
                context.addMessage(null, new FacesMessage("A operação de desligamento do aluno foi realizada com sucesso!!"));
            }
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O endereço de e-mail informado já existe no sistema.",
                        null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde."
                        + "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            }
            view = "";
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.setAutoCommit(true);
            conexao.close();
        }
        return view;
    }

    public void dataSelecionada(DateSelectEvent event) {
    }

    /**
     * retorna um array dos alunos do grupo PET logado.
     *
     * @author Pedro Augusto
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return array dos alunos do grupo PET logado.
     */
    public ArrayList<Aluno> getAlunos() throws SQLException {
        return getAlunosPorId(LoginController.getGrupo().getId());
    }

    /**
     * retorna o nome, email, telefone e celular de todos os alunos ativos do
     * Grupo PET passado por parametro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return array dos alunos ativos do grupo PET logado.
     */
    public ArrayList<Aluno> getAlunosPorId(int idGrupo) throws SQLException {
        getDao().setConexao(Conexao.getConnection());
        ArrayList<Aluno> alunos = getDao().listarTodos(idGrupo);
        ArrayList<Aluno> alunosAtivos = new ArrayList<Aluno>();
        Iterator<Aluno> it = alunos.iterator();
        while (it.hasNext()) {
            Aluno a = (Aluno) it.next();
            if (a.getDataSaidaPet() == null) {
                alunosAtivos.add(a);
            }
        }
        getDao().getConexao().close();
        return alunosAtivos;
    }

    /**
     * Retorna um mapa com o id e nome de todos os alunos do Grupo PET logado.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return mapa com o id e nome de todos os alunos do Grupo PET logado.
     */
    public TreeMap<String, String> getTreeMap() throws SQLException {
        return getTreeMapByGroup(LoginController.getGrupo().getId());
    }

    /**
     * Retorna um mapa com o id e nome de todos os alunos ativos do Grupo PET passado
     * por parâmetro.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @param id do grupo, o qual terá seus alunos ativos recuperados.
     * @return mapa com o id e nome de todos os alunos do Grupo PET logado.
     */
    public TreeMap<String, String> getTreeMapByGroup(int idGrupo) throws SQLException {
        TreeMap<String, String> mapa = new TreeMap<String, String>();

        getDao().setConexao(Conexao.getConnection());
        ArrayList<Aluno> alunos = getDao().listarTodos(idGrupo);

        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            if (a.getDataSaidaPet() == null) {
                String nome = a.getNome();
                String id = String.valueOf(a.getId());
                mapa.put(nome, id);
            }
        }

        getDao().getConexao().close();
        return mapa;
    }

    /**
     * Carrega um aluno do banco de dados no atributo "usuario" da super-classe "UsuarioController"
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    @Override
    public void prepararEdicao() throws SQLException {
        super.prepararEdicao();
        if (getUsuario() == null) {
            setUsuario(new Aluno());
        } else {
            prepararNotas();
        }
    }

    /**
     * Retorna um mapa com os anos onde não há notas cadastradas de um aluno do Grupo PET logado.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return mapa com os anos onde não há notas cadastradas de um aluno do Grupo PET logado.
     */
    public TreeMap<String, String> getAnoTreeMap() throws SQLException {
        TreeMap<String, String> mapaAno = new TreeMap<String, String>();
        for (int i = 0; i < anosSemNota.size(); i++) {
            mapaAno.put(String.valueOf(anosSemNota.get(i).ano), String.valueOf(i));
        }
        return mapaAno;
    }

    /**
     * Retorna um mapa com os períodos letivos do ano presente no atributo "iAno".
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return mapa com os períodos letivos do ano presente no atributo "iAno".
     */
    public TreeMap<String, String> getSemestreTreeMap() throws SQLException {
        TreeMap<String, String> mapaSemestre = new TreeMap<String, String>();
        if (!anosSemNota.isEmpty() && periodoCurso != Curso.ANUAL) {
            AnoLetivo al = anosSemNota.get(iAno);
            if (al.semestre1) {
                mapaSemestre.put("1º Semestre", "1");
            }
            if (al.semestre2) {
                mapaSemestre.put("2º Semestre", "2");
            }
            if (periodoCurso < 0) // periodoCurso < 0 significa que nao se sabe o periodo
            {
                mapaSemestre.put("Não se aplica", "0");
            }
        }
        return mapaSemestre;
    }

    public void ajustarNotas(DateSelectEvent event) {
        System.out.println("Passou no listener");
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (getUsuario().getId() > 0) {
            if (!(formato.format(event.getDate()).equals(formato.format(getUsuario().getDataIngressoInst())))) {
                anosSemNota = new ArrayList<AnoLetivo>();
                notas = new ArrayList<Nota>();
                System.out.println("Passou na verificação do listener");
                SimpleDateFormat formatoAno = new SimpleDateFormat("yyyy");
                SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
                Date dataAtual = new Date();
                int anoIngresso = Integer.parseInt(formatoAno.format(event.getDate()));
                int mesIngresso = Integer.parseInt(formatoMes.format(event.getDate()));
                int anoAtual = Integer.parseInt(formatoAno.format(dataAtual));
                int mesAtual = Integer.parseInt(formatoMes.format(dataAtual));

                int i = 0;
                for (int ano = anoAtual; ano >= anoIngresso; ano--) {
                    AnoLetivo al = new AnoLetivo(ano);
                    if (ano == anoIngresso && mesIngresso > 7) {
                        al.semestre1 = false;
                    }
                    if (ano == anoAtual && mesAtual < 7) {
                        al.semestre2 = false;
                    }

                    /* adiciona o ano letivo à lista de anos letivos com notas faltando no sistema caso
                     * realmente falte alguma nota.
                     */
                    if (al.semestre1 || al.semestre2) {
                        anosSemNota.add(al);
                    }
                }
            }
        }
    }

    /**
     * Carrega as notas do aluno selecionado pelo tutor logado no sistema, preparando as notas
     * por período letivo.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    private void prepararNotas() throws SQLException {
        // recupera as notas
        Connection conexao = Conexao.getConnection();
        NotaDao ndao = new JDBCNotaDao(conexao);
        notas = ndao.listarTodos(getUsuario().getId());

        anosSemNota = new ArrayList<AnoLetivo>();

        // descobre se o curso do aluno é anual ou semestral
        if (!notas.isEmpty()) {
            // Se em alguma das notas o semestre foi cadastrado como null é porque o curso era anual
            if (notas.get(0).getSemestre() == 0) {
                periodoCurso = Curso.ANUAL;
            } else {
                periodoCurso = Curso.SEMESTRAL;
            }
        } else {
            GrupoDao gdao = new JDBCGrupoDao(conexao);
            CursoDao cdao = new JDBCCursoDao(conexao);
            Grupo grupo = gdao.recuperarPorId(LoginController.getGrupo().getId());
            if (grupo.getCurso().getId() != 0) {
                Curso curso = cdao.recuperarPorId(grupo.getCurso().getId());
                periodoCurso = curso.getPeriodo();
            } else {
                periodoCurso = -1; // nem semestral nem anual, nao foi possivel encontrar o curso
            }
        }

        // descobre quais períodos letivos nao tem nota no sistema, mas poderia ter
        SimpleDateFormat formatoAno = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
        Date dataAtual = new Date();
        int anoIngresso = Integer.parseInt(formatoAno.format(getUsuario().getDataIngressoInst()));
        int mesIngresso = Integer.parseInt(formatoMes.format(getUsuario().getDataIngressoInst()));
        int anoAtual = Integer.parseInt(formatoAno.format(dataAtual));
        int mesAtual = Integer.parseInt(formatoMes.format(dataAtual));

        int i = 0;
        for (int ano = anoAtual; ano >= anoIngresso; ano--) {
            AnoLetivo al = new AnoLetivo(ano);

            /* varre as notas que ja estao no sistema para ver se o ano desta iteracao ja tem todas
             * suas notas cadastradas no bd.
             */
            while (i < notas.size()) {
                Nota n = notas.get(i);
                if (n.getAno() < ano) {
                    break; // as notas estão em ordem decrescente de ano
                } else if (n.getAno() == ano) {
                    if (periodoCurso == Curso.ANUAL) {
                        // ambos os semestres ja estao cadastrados no sistema
                        al.semestre1 = false;
                        al.semestre2 = false;
                    } else if (n.getSemestre() == 1) {
                        al.semestre1 = false;
                    } else {
                        al.semestre2 = false;
                    }
                }
                i++;
            }

            /* verifica a coerencia da nota que se pede. Cursos semestrais nao podem pedir a nota
             * do segundo semestre se o semestre corrente é o primeiro, muito menos pedir a nota
             * do primeiro semestre do ano em que o aluno entrou na faculdade enquanto seu ingresso
             * foi no segundo semestre.
             */

            if (ano == anoIngresso && mesIngresso > 7) {
                al.semestre1 = false;
            }
            if (ano == anoAtual && mesAtual < 7) {
                al.semestre2 = false;
            }

            /* adiciona o ano letivo à lista de anos letivos com notas faltando no sistema caso
             * realmente falte alguma nota.
             */
            if (al.semestre1 || al.semestre2) {
                anosSemNota.add(al);
            }
        }
        conexao.close();
    }

    /**
     * Adiciona a nota recém cadastrada no array de notas para que a mesma possa ser mostrada para
     * o usuário, logo depois da inserção da nova nota.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public void adicionarNota() {
        // Adiciona a nota
        Nota n = new Nota();
        AnoLetivo al = anosSemNota.get(iAno);
        n.setAno(al.ano);
        n.setSemestre(semestreNota);
        n.setAluno(getUsuario());
        n.setValor(valorNota);
        n.setMarcador(Nota.INSERT);
        notas.add(n);

        /* Atualiza o periodo do curso (semestral/anual) caso ele ainda não tenha sido descoberto
         * e atualiza também a lista de notas que ainda não foram para o sistema.
         */
        if (semestreNota == 1) {
            al.semestre1 = false;
            periodoCurso = Curso.SEMESTRAL;
        } else if (semestreNota == 2) {
            al.semestre2 = false;
            periodoCurso = Curso.SEMESTRAL;
        } else {
            // semestre = 0, ou seja, não se aplica, o curso do aluno é anual
            al.semestre1 = false;
            al.semestre2 = false;
            periodoCurso = Curso.ANUAL;
        }

        if (!al.semestre1 && !al.semestre2) {
            anosSemNota.remove(iAno);
        }

        // reinicia os valores
        valorNota = 0;
        semestreNota = 0;
        iAno = 0;
    }

    /**
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     * @return lista de notas de algum aluno.
     */
    public ArrayList<Nota> getNotas() throws SQLException {
        return notas;
    }

    /**
     * Define a nota de um período letivo no atributo "valorNota".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param valor nota de um período letivo
     */
    public void setValorNota(float valor) {
        valorNota = valor;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return a nota de um período letivo de um aluno.
     */
    public float getValorNota() {
        return valorNota;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return o ano letivo válido de um aluno selecionado.
     */
    public int getiAno() {
        return iAno;
    }

    /**
     * Define um ano letivo no atributo "iAno".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param valor nota de um período letivo.
     */
    public void setiAno(int iAno) {
        this.iAno = iAno;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return a nota de um aluno em um período letivo.
     */
    public int getSemestreNota() {
        return semestreNota;
    }

    /**
     * Define a nota de um aluno em um período letivo.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param semestreNota nota de um aluno em um período letivo.
     */
    public void setSemestreNota(int semestreNota) {
        this.semestreNota = semestreNota;
    }

    /**
     * Testa se o período letivo de um curso é anual.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return true se período letivo de um curso é anual, caso contrário retorna falso.
     */
    public boolean isAnual() {
        return periodoCurso == Curso.ANUAL;
    }

    /**
     * Define o período letivo ao qual um aluno faz parte.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param periodoCurso período letivo ao qual um aluno faz parte.
     */
    public void setPeriodoCurso(int periodoCurso) {
        this.periodoCurso = periodoCurso;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return uma lista de objetos "Grafico" que contém a nota de um aluno, o período letivo
     * e a nota média do grupo no período letivo em questão.
     */
    public ArrayList<Grafico> getGrafico() {
        if (grafico != null) {
            Collections.reverse(grafico);
            return grafico;
        } else {
            return new ArrayList<Grafico>();
        }
    }

    /**
     * Carrega do banco de dados as notas de um aluno por período letivo e ainda calcula a nota média do grupo
     * para cada período letivo, armazenando tais informações em uma lista de objetos "Grafico" que contém o
     * período letivo, a nota do aluno e a nota média do grupo correspondente ao mesmo período letivo.
     *
     * @author Tiago Peres
     * @throws SQLException
     * @version 3.0
     * @since 3.0
     */
    public void atualizarGrafico() throws SQLException {
        NotaDao ndao = new JDBCNotaDao(Conexao.getConnection());
        grafico = null;
        grafico = new ArrayList<Grafico>();
        //ArrayList<Nota> medias = new ArrayList<Nota>();
        Iterator<Nota> it = notas.iterator();
        // encontra  a média das notas do grupo para cada nota em "notas"
        while (it.hasNext()) {
            Nota nota = it.next();
            Grafico g = new Grafico();
            g.setPeriodo(nota.getPeriodo());
            g.setValor(nota.getValor());
            g.setMediaGrupo(nota.getValor());
            int nElementos = 1; // nro de notas que estão sendo colocadas na média

            ArrayList<Nota> notasGrupo = ndao.listarTodosGrupo(getUsuario().getGrupo().getId(), nota.getAno(), nota.getSemestre());
            Iterator<Nota> itGrupo = notasGrupo.iterator();
            while (itGrupo.hasNext()) {
                Nota n = itGrupo.next();
                // As notas do aluno já foram adicionadas
                if (n.getAluno().getId() != getUsuario().getId()) {
                    g.setMediaGrupo(g.getMediaGrupo() + n.getValor());
                    ++nElementos;
                }
            }

            // trucar a média em duas casas decimais
            float media = g.getMediaGrupo() / nElementos;
            media *= 100;
            media = (int) media;
            media /= 100;

            g.setMediaGrupo(media);
            grafico.add(g);
        }
        ndao.getConexao().close();
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return lista de alunos selecionados pelo usuário em sessão.
     */
    public Aluno[] getAlunosSelecionados() {
        return alunosSelecionados;
    }

    /**
     * Define a lista de alunos selecionados pelo usuário em sessão.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param alunosSelecionados lista de alunos selecionados pelo usuário em sessão.
     */
    public void setAlunosSelecionados(Aluno[] alunosSelecionados) {
        this.alunosSelecionados = alunosSelecionados;
    }

    /**
     * Cria uma lista dos e-mails dos alunos selecionados que estão no atributo "alunosSelecionados".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return lista de emails para os quais receberão uma mensagem através do DataPET.
     */
    public ArrayList<String> getEmailsSelecionados() {
        ArrayList<String> emails = new ArrayList<String>();
        for (int i = 0; i < alunosSelecionados.length; i++) {
            emails.add(alunosSelecionados[i].getEmail());
        }
        return emails;
    }

    /**
     * Esta é uma classe interna que representa um ano letivo.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    class AnoLetivo {

        int ano;
        boolean semestre1; // se verdadeiro, o semestre 1 ainda n possui nota no sistema
        boolean semestre2;

        /**
         * Construtor da classe, inicializa os atributos.
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         */
        AnoLetivo(int ano) {
            this.ano = ano;
            semestre1 = true;
            semestre2 = true;
        }
    }

    /**
     * Esta é uma classe interna que representa o gráfico de notas.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    public class Grafico {

        private String periodo;
        private float valor;
        private float mediaGrupo;

        /**
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @return período letivo ao qual pertence a nota e a média do grupo
         * que estão nos atributos "valor" e "mediaGrupo", respectivamente.
         */
        public String getPeriodo() {
            return periodo;
        }

        /**
         * Define o período letivo ao qual pertence a nota e a média do grupo
         * que estão nos atributos "valor" e "mediaGrupo", respectivamente.
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @param periodo período letivo ao qual pertence a nota e a média do grupo
         * que estão nos atributos "valor" e "mediaGrupo", respectivamente.
         */
        public void setPeriodo(String periodo) {
            this.periodo = periodo;
        }

        /**
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @return nota do período letivo em questão, que está no atributo "periodo".
         */
        public float getValor() {
            return valor;
        }

        /**
         * Define nota do período letivo em questão, que está no atributo "periodo".
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @param valor nota do período letivo em questão, que está no atributo "periodo".
         */
        public void setValor(float valor) {
            this.valor = valor;
        }

        /**
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @return nota média do grupo no período letivo em questão, que está no atributo "periodo".
         */
        public float getMediaGrupo() {
            return mediaGrupo;
        }

        /**
         * Define a nota média do grupo no período letivo em questão, que está no atributo "periodo".
         *
         * @author Tiago Peres
         * @version 3.0
         * @since 3.0
         * @param mediaGrupo nota média do grupo no período letivo em questão, que está no atributo "periodo".
         */
        public void setMediaGrupo(float mediaGrupo) {
            this.mediaGrupo = mediaGrupo;
        }
    }
}
