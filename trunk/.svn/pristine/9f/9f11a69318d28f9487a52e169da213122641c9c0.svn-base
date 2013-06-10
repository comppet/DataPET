/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Dao.AlunoDao;
import Dao.ClaDao;
import Dao.JDBCAlunoDao;
import Dao.JDBCClaDao;
import Dao.Conexao;
import Dao.CursoDao;
import Dao.EnsinoDao;
import Dao.ExtensaoDao;
import Dao.JDBCCursoDao;
import Dao.JDBCEnsinoDao;
import Dao.JDBCExtensaoDao;
import Dao.JDBCNotaDao;
import Dao.JDBCPesquisaDao;
import Dao.NotaDao;
import Dao.PesquisaDao;
import Model.Aluno;
import Model.Cla;
import Model.Curso;
import Model.Ensino;
import Model.Extensao;
import Model.Grupo;
import Model.Pesquisa;
import Model.Tutor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Element;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.primefaces.model.StreamedContent;
import java.io.InputStream;
import java.util.ArrayList;
import org.primefaces.model.DefaultStreamedContent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.RequestScoped;

 /**
 * Essa classe é um javabean que é chamado na view Relatório
 *
 * @author Tassyo Tchesco
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "RelatorioGrupoController")
@RequestScoped
public class RelatorioGrupoController extends RelatorioAtividadesController {
    private List<String> componentesRelatorio;
    
    private ArrayList<Pesquisa> pesquisa;
    private ArrayList<Ensino> ensino;
    private ArrayList<Extensao> extensao;
    private Grupo grupo;
    private Cla cla;
    private ArrayList<Aluno> petianosBolsistas;
    private ArrayList<Aluno> petianosNaoBolsistas;
    protected static final SimpleDateFormat formatoPtBr = new SimpleDateFormat("dd/MM/yyyy");
    protected static final SimpleDateFormat formatoArquivo = new SimpleDateFormat("yyyy-MM-dd");
    
     /**
     * Construtor padrão da classe. Inicializa o vetor que contém os componentes do relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */
    public RelatorioGrupoController(){
        super();
        componentesRelatorio = new ArrayList<String>();
        componentesRelatorio.add("instituicao");
        componentesRelatorio.add("grupo");
        componentesRelatorio.add("ensino");
        componentesRelatorio.add("pesquisa");
        componentesRelatorio.add("extensao");
        
    }
    
    
     /**
     * Cria o documento RTF do relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @return documento RTF
     */
    @Override
    public StreamedContent getRelatorioRTF() throws SQLException, DocumentException, UnsupportedEncodingException{
        zerarIntitulacao();
        ByteArrayOutputStream relatorio = new ByteArrayOutputStream();
        Document document = new Document();
        RtfWriter2.getInstance(document, relatorio);
        return getRelatorio(relatorio, document, "rtf");
    }
    
    
     /**
     * Cria o documento PDF do relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @return documento PDF
     */
    @Override
    public StreamedContent getRelatorioPDF() throws SQLException, DocumentException, UnsupportedEncodingException{
        zerarIntitulacao();
        ByteArrayOutputStream relatorio = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, relatorio);
        return getRelatorio(relatorio, document, "pdf");
    }

    
    /**
     * Insere no relatório os componentes que o usuário marcou na view Relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @param Array de bytes, documento do relatório, extensão do arquivo.
     * @return StreamedContent do documento
     */
    private StreamedContent getRelatorio(ByteArrayOutputStream relatorio, Document document, String fileExtension)
            throws DocumentException, SQLException, UnsupportedEncodingException{
        preencherCampos();
        document.open();
        gerarCabecalho(document);
        if (componentesRelatorio.contains("instituicao")) gerarDadosInstitucionais(document);
        if (componentesRelatorio.contains("grupo")) gerarDadosDoGrupo(document);
        if (componentesRelatorio.contains("ensino")){
            document.newPage();
            gerarAtividades(document, ensino, "ensino", 0);
        }
        if (componentesRelatorio.contains("pesquisa")){
            document.newPage();
            gerarAtividades(document, pesquisa, "pesquisa", 0);
        }
        if (componentesRelatorio.contains("extensao")){
            document.newPage();
            gerarAtividades(document, extensao, "extensão", 0);
        }
        
        document.close();
        InputStream relatorioIn = new ByteArrayInputStream(relatorio.toByteArray());
        // gera o nome do documento
        StringBuilder nomeRelatorio = new StringBuilder();
        
        nomeRelatorio.append("datapet_");
        nomeRelatorio.append(grupo.getSigla());
        nomeRelatorio.append("_relat_ativ_");
        nomeRelatorio.append(formatoArquivo.format(getInicioRelatorio()));
        nomeRelatorio.append("_a_");
        nomeRelatorio.append(formatoArquivo.format(getFimRelatorio()));
        nomeRelatorio.append(".");
        nomeRelatorio.append(fileExtension);
        return new DefaultStreamedContent(relatorioIn, "", nomeRelatorio.toString());
        
        
//        nomeRelatorio.append("relatorio_");
//        nomeRelatorio.append(grupo.getSigla());
//        nomeRelatorio.append("_");
//        nomeRelatorio.append(formatoPtBr.format(new Date()));
//        nomeRelatorio.append(".");
//        nomeRelatorio.append(fileExtension);
//        return new DefaultStreamedContent(relatorioIn, "", nomeRelatorio.toString());
    }
    
    
    /**
     * Preenche os campos com as atividades de Ensino, Pesquisa e Extensão.
     * Preenche também a lista de petianos bolsistas ou não e inicia as variáveis grupo e cla.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */
    private void preencherCampos() throws SQLException{
        
        Connection conexao = Conexao.getConnection();
        PesquisaDao pesquisaDao = new  JDBCPesquisaDao(conexao);
        pesquisa = pesquisaDao.listarTodos(LoginController.getGrupo().getId(), getInicioRelatorio(), getFimRelatorio());
        
        EnsinoDao ensinoDao = new  JDBCEnsinoDao(conexao);
        ensino = ensinoDao.listarTodos(LoginController.getGrupo().getId(), getInicioRelatorio(), getFimRelatorio());
        
        ExtensaoDao extensaoDao = new  JDBCExtensaoDao(conexao);
        extensao = extensaoDao.listarTodos(LoginController.getGrupo().getId(), getInicioRelatorio(), getFimRelatorio());
        
        grupo = LoginController.getGrupo();
        CursoDao cursoDao = new JDBCCursoDao(conexao);
        grupo.setCurso(cursoDao.recuperarPorId(grupo.getCurso().getId())); // completa as informações do curso em "grupo"
        grupo.setTutor((Tutor) LoginController.getUsuario());
        grupo.setInstituicao(LoginController.getInstituicao());

        ClaDao claDao = new JDBCClaDao(conexao);
        cla = claDao.recuperarInterlocutorCla(LoginController.getInstituicao().getId());
        cla.setInstituicao(LoginController.getInstituicao());
        
        AlunoDao alunoDao = new JDBCAlunoDao(conexao);
        NotaDao notaDao = new JDBCNotaDao(conexao);
        ArrayList<Aluno> alunos = alunoDao.listarTodos(grupo.getId());
        // ERRO esta listando todos
        Iterator<Aluno> it = alunos.iterator();
        petianosBolsistas = new ArrayList<Aluno>();
        petianosNaoBolsistas = new ArrayList<Aluno>();
        while(it.hasNext()){
            Aluno a = it.next();
            if(!a.getDataIngressoPet().after(getFimRelatorio())){
               if(a.getDataSaidaPet() != null) {
                    if(!a.getDataSaidaPet().before(getInicioRelatorio())){
                        a.setNotas(notaDao.listarTodos(a.getId())); // recupera as notas dos alunos
                        if(a.isBolsista()) 
                            petianosBolsistas.add(a);
                        else petianosNaoBolsistas.add(a);
                    }
               }
               else{
                    a.setNotas(notaDao.listarTodos(a.getId())); // recupera as notas dos alunos
                    if(a.isBolsista()) 
                        petianosBolsistas.add(a);
                    else petianosNaoBolsistas.add(a);
               }
            }
        }
        conexao.close();
    }
    
    
    /**
     * Anexa no relatório um cabeçalho inicial.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @param recebe o documento do relatório
     */
    private void gerarCabecalho(Document document) throws DocumentException{
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        Paragraph titulo = new Paragraph("MEC/PET " + grupo.getSigla(), fonteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        Paragraph titulo2 = new Paragraph("Relatório de Atividades", fonteTitulo);
        titulo2.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo2);
        Paragraph titulo3 = new Paragraph(formatoPtBr.format(getInicioRelatorio()) + " a " + formatoPtBr.format(getFimRelatorio()), fonteTitulo);
        titulo3.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo3);
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
    }

    
    
    /**
     * Anexa no relatório os dados da Instituição do grupo PET.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @param recebe o documento do relatório
     */
    private void gerarDadosInstitucionais(Document document){
        try {
            document.add(new Paragraph(intitular(0) + ". DADOS INSTITUCIONAIS", fonteTitulo));
            document.add(new Paragraph(""));
            
            document.add(new Paragraph(intitular(1) + ". Instituição de Ensino Superior: "
                    + cla.getInstituicao().getNome(), fonteTexto));
            
            document.add(new Paragraph(intitular(1) + ". Interlocutor do PET na IES/ função ou cargo que ocupa na IES: "
                    + cla.getInterlocutor() + "/ Comitê Local de Avaliação e Acompanhamento (CLA)", fonteTexto));
            
            document.add(new Paragraph(intitular(1) + ". E-mail do interlocutor do PET: " 
                    + cla.getEmail(), fonteTexto));
            
            document.add(new Paragraph(intitular(1) + ". Pró-Reitor de graduação: " 
                    + substituirTextoEmBranco(cla.getInstituicao().getProReitor()), fonteTexto));
            
            document.add(new Paragraph(intitular(1) + ". E-mail do Pró-Reitor de graduação: " 
                    + substituirTextoEmBranco(cla.getInstituicao().getEmailProReitor()), fonteTexto));
        } catch (DocumentException ex) {
            Logger.getLogger(RelatorioGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Anexa no relatório os dados do grupo PET, petianos bolsistas e não bolsistas.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @param recebe o documento do relatório
     */
    private void gerarDadosDoGrupo(Document document) throws BadElementException, DocumentException{
        document.add(new Paragraph(""));
        document.add(new Paragraph(intitular(0) + ". IDENTIFICAÇÃO DO GRUPO PET", fonteTitulo));
        document.add(new Paragraph(""));

        document.add(new Paragraph(intitular(1) + ". Grupo: " 
                + grupo.getSigla(), fonteTexto));

        String tipoCurso;
        if (grupo.getCurso().getFormacao() == Curso.BACHARELADO)
            tipoCurso = "Bacharelado";
        else if (grupo.getCurso().getFormacao() == Curso.BACHARELADOLICENCIATURA)
            tipoCurso = "Bacharelado e licenciatura";
        else if (grupo.getCurso().getFormacao() == Curso.LICENCIATURA)
            tipoCurso = "Licenciatura";
        else
            tipoCurso = noLugarDeBranco;

        document.add(new Paragraph(intitular(1) + ". Curso de graduação ao qual o grupo está vinculado: " 
                + grupo.getCurso().getNome() + " (" + tipoCurso + ")", fonteTexto));

        document.add(new Paragraph(intitular(1) + ". Home Page do Grupo: " 
                + substituirTextoEmBranco(grupo.getSite()), fonteTexto));

        document.add(new Paragraph(intitular(1) + ". Tema: " 
                + substituirTextoEmBranco(grupo.getTema()), fonteTexto));

        document.add(new Paragraph(intitular(1) + ". Data de implantação do grupo: "
                + formatoPtBr.format(grupo.getImplantacao()), fonteTexto));

        document.add(new Paragraph(""));
        document.add(new Paragraph(intitular(1) + ". Informações sobre o tutor:", fonteTexto));

        document.add(new Paragraph(intitular(2) + ". Nome e titulação: "
                + grupo.getTutor().getTitulacao() + " " + grupo.getTutor().getNome(), fonteTexto));

        document.add(new Paragraph(intitular(2) + ". E-mail: "
                + grupo.getTutor().getEmail(), fonteTexto));

        document.add(new Paragraph(intitular(2) + ". Área: " 
                + substituirTextoEmBranco(grupo.getTutor().getArea()), fonteTexto));

        document.add(new Paragraph(intitular(2) + ". Data de ingresso no PET: "
                + formatoPtBr.format(grupo.getTutor().getDataIngressoPet()), fonteTexto));
        
        document.add(new Paragraph(""));
        document.add(new Paragraph(intitular(1) + ". Informações sobre os alunos:", fonteTexto));

        Table tabela = new Table(5, petianosBolsistas.size() + petianosNaoBolsistas.size() + 2);
        tabela.setAutoFillEmptyCells(true);
        tabela.setWidth(100);
        tabela.setAlignment(Table.ALIGN_CENTER);
        tabela.setPadding(5);
        
        
        Comparator comparator = new Comparator<Aluno>() {
            @Override
            public int compare(Aluno o1, Aluno o2) {
                int p = o1.getNome().compareTo(o2.getNome());
                return(p !=0 ? p : o1.getNome().compareTo(o2.getNome()));
            }
        };
        Collections.sort(petianosNaoBolsistas, comparator);
        Collections.sort(petianosBolsistas, comparator);
        
        int i = 0;
        if (!petianosBolsistas.isEmpty()){
            tabela.addCell(new Phrase("Nome dos bolsistas", fonteSubTitulo), new Point(i,0));
            tabela.addCell(new Phrase("Ingresso na IES", fonteSubTitulo), new Point(i,1));
            tabela.addCell(new Phrase("Ingresso no PET", fonteSubTitulo), new Point(i,2));
            tabela.addCell(new Phrase("Período letivo", fonteSubTitulo), new Point(i,3));
            tabela.addCell(new Phrase("Coeficiente de rendimento acadêmico geral", fonteSubTitulo), new Point(i,4));
            i++;
            
            for (int j = 0; j < petianosBolsistas.size(); j++){
                Aluno a = petianosBolsistas.get(j);
                String desligado = "";
                if(a.getDataSaidaPet() != null){
                    if (a.getDataSaidaPet().before(getFimRelatorio())) desligado = "*";
                    else desligado = "";
                }
                tabela.addCell(desligado + a.getNome(), new Point(i,0));
                tabela.addCell(formatoPtBr.format(a.getDataIngressoInst()), new Point(i,1));
                tabela.addCell(formatoPtBr.format(a.getDataIngressoPet()), new Point(i,2));
                tabela.addCell(String.valueOf(a.getPeriodo()), new Point(i,3));
                //tabela.addCell(String.valueOf((a.getUltimaNota() == -1) ? noLugarDeBranco : a.getUltimaNota()), new Point(i,4));
                tabela.addCell(String.valueOf(a.getCraGeral()), new Point(i, 4));
                i++;
            }
        }
        
        if (!petianosNaoBolsistas.isEmpty()){
            tabela.addCell(new Phrase("Nome dos não bolsistas", fonteSubTitulo), new Point(i, 0));
            tabela.addCell(new Phrase("Ingresso na IES", fonteSubTitulo), new Point(i, 1));
            tabela.addCell(new Phrase("Ingresso no PET", fonteSubTitulo), new Point(i, 2));
            tabela.addCell(new Phrase("Período letivo", fonteSubTitulo), new Point(i, 3));
            tabela.addCell(new Phrase("Coeficiente de rendimento acadêmico", fonteSubTitulo), new Point(i, 4));
            i++;

            for (int j = 0; j < petianosNaoBolsistas.size(); j++){
                Aluno a = petianosNaoBolsistas.get(j);
                String desligado = "";
                if(a.getDataSaidaPet() != null){
                    if (a.getDataSaidaPet().before(getFimRelatorio())) desligado = "*";
                    else desligado = "";
                }
                tabela.addCell(desligado + a.getNome(), new Point(i,0));
                tabela.addCell(formatoPtBr.format(a.getDataIngressoInst()), new Point(i,1));
                tabela.addCell(formatoPtBr.format(a.getDataIngressoPet()), new Point(i,2));
                tabela.addCell(String.valueOf(a.getPeriodo()), new Point(i,3));
                //tabela.addCell(String.valueOf((a.getUltimaNota() == -1) ? noLugarDeBranco : a.getUltimaNota()), new Point(i,4));
                tabela.addCell(String.valueOf(a.getCraGeral()), new Point(i, 4));
                i++;
            }
        }
        
        if (!petianosBolsistas.isEmpty() || !petianosNaoBolsistas.isEmpty()){
            document.add(tabela);
            Paragraph descricao = new Paragraph("* Alunos desligados no decorrer do período deste relatório.", fonteDescricao);
            descricao.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(descricao);
        }
        else
            document.add(new Paragraph("Não houve alunos neste grupo no período deste relatório.", fonteTexto));
    }

    /**
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @return lista de componentes do relatório.
     */
    public List<String> getComponentesRelatorio() {
        return componentesRelatorio;
    }

    /**
     * Define os componentes do relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     * @param lista de componentes do relatório.
     */
    public void setComponentesRelatorio(List<String> componentesRelatorio) {
        this.componentesRelatorio = componentesRelatorio;
    }

}
