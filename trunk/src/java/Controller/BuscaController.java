/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Dao.GrupoDao;
import Dao.Conexao;
import Dao.EnsinoDao;
import Dao.ExtensaoDao;
import Dao.JDBCEnsinoDao;
import Dao.JDBCExtensaoDao;
import Dao.JDBCGrupoDao;
import Dao.JDBCPesquisaDao;
import Dao.PesquisaDao;
import Model.Ensino;
import Model.Extensao;
import Model.Grupo;
import Model.Pesquisa;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Tiago Peres
 */
@ManagedBean(name="BuscaController")
@ViewScoped
public class BuscaController extends RelatorioAtividadesController{
    private ArrayList<SelectItem> gruposCheckboxes;
    private List<String> gruposSelecionados;
    private List<String> tiposSelecionados;
    private boolean relatorioGerado = false;
    private Map<Integer, Grupo> mapaGrupos; // mapeia todo id de grupo para um grupo
    private Map<Integer, ArrayList<Ensino>> mapaEnsino; // mapeia todo id de grupo para suas atividades de ensino
    private Map<Integer, ArrayList<Extensao>> mapaExtensao; // mapeia todo id de grupo para suas atividades de extensão
    private Map<Integer, ArrayList<Pesquisa>> mapaPesquisa; // mapeia todo id de grupo para suas atividades de pesquisa
    
    public BuscaController() throws SQLException{
        super();
        // recupera os grupos e cria as checkboxes
        gruposCheckboxes = recuperarGrupos();
    }
    
    private ArrayList<SelectItem> recuperarGrupos() throws SQLException{
        GrupoDao gdao = new JDBCGrupoDao(Conexao.getConnection());
        ArrayList<Grupo> grupos = gdao.listarTodos(LoginController.getInstituicao().getId());
        ArrayList<SelectItem> itens = new ArrayList<SelectItem>();
        Iterator<Grupo> it = grupos.iterator();
        mapaGrupos = new HashMap<Integer, Grupo>();
        while(it.hasNext()){
            Grupo g = it.next();
            if (g.isAtivado()){
                itens.add(new SelectItem(g.getId(), g.getSigla()));
                mapaGrupos.put(g.getId(), g);
            }
        }
        return itens;
    }
    
    @Override
    public StreamedContent getRelatorioRTF() throws SQLException, DocumentException, UnsupportedEncodingException{
        zerarIntitulacao();
        ByteArrayOutputStream relatorio = new ByteArrayOutputStream();
        Document document = new Document();
        RtfWriter2.getInstance(document, relatorio);
        return getRelatorio(relatorio, document, "rtf");
    }
    
    @Override
    public StreamedContent getRelatorioPDF() throws SQLException, DocumentException, UnsupportedEncodingException{
        zerarIntitulacao();
        ByteArrayOutputStream relatorio = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, relatorio);
        return getRelatorio(relatorio, document, "pdf");
    }
    
    private StreamedContent getRelatorio(ByteArrayOutputStream relatorio, Document document, String fileExtension) 
            throws SQLException, DocumentException, UnsupportedEncodingException {
        preencherAtividades();
        document.open();
        gerarCabecalho(document);
        gerarResumo(document);
        
        // gerar as atividades por grupo
        for (int i = 0; i < gruposSelecionados.size(); i++){
            document.newPage();
            Grupo grupo = mapaGrupos.get(Integer.parseInt(gruposSelecionados.get(i)));
            document.add(new Paragraph(intitular(0) + ". " + grupo.getSigla(), fonteTitulo));
            document.add(new Paragraph());
            if (tiposSelecionados.contains("ensino")) gerarAtividades(document, mapaEnsino.get(grupo.getId()), "ensino", 1);
            if (tiposSelecionados.contains("extensao")) gerarAtividades(document, mapaExtensao.get(grupo.getId()), "extensao", 1);
            if (tiposSelecionados.contains("pesquisa")) gerarAtividades(document, mapaPesquisa.get(grupo.getId()), "pesquisa", 1);
        }
        
        document.close();
        
        InputStream relatorioIn = new ByteArrayInputStream(relatorio.toByteArray());
        // gera o nome do documento
        StringBuilder nomeRelatorio = new StringBuilder();
        nomeRelatorio.append("relatorio_busca_cla_");
        nomeRelatorio.append(formatoPtBr.format(new Date()));
        nomeRelatorio.append(".");
        nomeRelatorio.append(fileExtension);
        return new DefaultStreamedContent(relatorioIn, "", nomeRelatorio.toString());
    }
    
    private void preencherAtividades() throws SQLException{
        // cria as conexões e inicializa as estruturas de dados
        Connection conn = Conexao.getConnection();
        EnsinoDao ensinoDao = null;
        ExtensaoDao extensaoDao = null;
        PesquisaDao pesquisaDao = null;
        Iterator<String> itTipo = tiposSelecionados.iterator();
        while (itTipo.hasNext()){
            String tipo = itTipo.next();
            if (tipo.equals("ensino")){
                ensinoDao = new JDBCEnsinoDao(conn);
                mapaEnsino = new HashMap<Integer, ArrayList<Ensino>>();
            }
            else if (tipo.equals("extensao")){
                extensaoDao = new JDBCExtensaoDao(conn);
                mapaExtensao = new HashMap<Integer, ArrayList<Extensao>>();
            }
            else{
                pesquisaDao = new JDBCPesquisaDao(conn);
                mapaPesquisa = new HashMap<Integer, ArrayList<Pesquisa>>();
            }
        }
        
        // recupera as atividades por grupo
        Iterator<String> itGrupo = gruposSelecionados.iterator();
        while (itGrupo.hasNext()){
            int idGrupo = Integer.parseInt(itGrupo.next());
            if (ensinoDao != null)
                mapaEnsino.put(idGrupo, ensinoDao.listarTodos(idGrupo, getInicioRelatorio(), getFimRelatorio()));
            if (extensaoDao != null)
                mapaExtensao.put(idGrupo, extensaoDao.listarTodos(idGrupo, getInicioRelatorio(), getFimRelatorio()));
            if (pesquisaDao != null)
                mapaPesquisa.put(idGrupo, pesquisaDao.listarTodos(idGrupo, getInicioRelatorio(), getFimRelatorio()));
        }
        
    }
    
    public void gerarCabecalho(Document document) throws DocumentException{
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        Paragraph titulo = new Paragraph("Relatório de busca", fonteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        Paragraph subTitulo = new Paragraph("Gerado pelo CLA (" + LoginController.getInstituicao().getSigla() + ") em " + formatoPtBr.format(new Date()), fonteSubTitulo);
        subTitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(subTitulo);
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
        document.add(new Paragraph(""));
    }
    
    public void gerarResumo(Document document) throws DocumentException, SQLException{
        document.add(new Paragraph(intitular(0) + ". RESUMO", fonteTitulo));
        document.add(new Paragraph("Número de atividades de ensino, extensão e pesquisa de cada grupo.", fonteSubTitulo));
        Table tabela = new Table(tiposSelecionados.size() == 1 ? 2 : tiposSelecionados.size() + 2, 
                gruposSelecionados.size() == 1 ? 2 : gruposSelecionados.size() + 2);
        tabela.setAutoFillEmptyCells(true);
        tabela.setWidth(100);
        tabela.setPadding(5);

        int coluna = 0;
        for (int i = 0; i < tiposSelecionados.size(); i++){
            String tipo = tiposSelecionados.get(i);
            if (tipo.equals("ensino")){
                tabela.addCell(new Phrase("Ensino", fonteTextoBold), new Point(0, ++coluna));
            }
            else if (tipo.equals("extensao")){
                tabela.addCell(new Phrase("Extensão", fonteTextoBold), new Point(0, ++coluna));
            }
            else{
                tabela.addCell(new Phrase("Pesquisa", fonteTextoBold), new Point(0, ++coluna));
            }
        }
        if (tiposSelecionados.size() > 1)
            tabela.addCell(new Phrase("Total", fonteTextoBold), new Point(0, ++coluna));
        
        // preenche os nomes dos grupos e os valores
        int i;
        int totalEnsino = 0;
        int totalExtensao = 0;
        int totalPesquisa = 0;
        for (i = 0; i < gruposSelecionados.size(); i++){
            Grupo grupo = mapaGrupos.get(Integer.parseInt(gruposSelecionados.get(i)));
            tabela.addCell(new Phrase(grupo.getSigla(), fonteTextoBold), new Point(i + 1, 0));
            // valores nas colunas
            int totalLinha = 0;
            coluna = 0;
            for (int j = 0; j < tiposSelecionados.size(); j++){
                String tipo = tiposSelecionados.get(j);
                if (tipo.equals("ensino")){
                    int quantidade = mapaEnsino.get(grupo.getId()).size();
                    tabela.addCell(new Phrase(String.valueOf(quantidade), fonteTexto), new Point(i + 1, ++coluna));
                    totalLinha += quantidade;
                    totalEnsino += quantidade;
                }
                else if (tipo.equals("extensao")){
                    int quantidade = mapaExtensao.get(grupo.getId()).size();
                    tabela.addCell(new Phrase(String.valueOf(quantidade), fonteTexto), new Point(i + 1, ++coluna));
                    totalLinha += quantidade;
                    totalExtensao += quantidade;
                }
                else{
                    int quantidade = mapaPesquisa.get(grupo.getId()).size();
                    tabela.addCell(new Phrase(String.valueOf(quantidade), fonteTexto), new Point(i + 1, ++coluna));
                    totalLinha += quantidade;
                    totalPesquisa += quantidade;
                }
            }
            if (tiposSelecionados.size() > 1)
                tabela.addCell(new Phrase(String.valueOf(totalLinha), fonteTextoBold), new Point(i + 1, ++coluna));
        }
        
        if (gruposSelecionados.size() > 1){
            tabela.addCell(new Phrase("Total", fonteTextoBold), new Point(i + 1, 0));
            coluna = 0;
            for (int j = 0; j < tiposSelecionados.size(); j++){
                String tipo = tiposSelecionados.get(j);
                if (tipo.equals("ensino"))
                    tabela.addCell(new Phrase(String.valueOf(totalEnsino), fonteTextoBold), new Point(i + 1, ++coluna));
                else if (tipo.equals("extensao"))
                    tabela.addCell(new Phrase(String.valueOf(totalExtensao), fonteTextoBold), new Point(i + 1, ++coluna));
                else
                    tabela.addCell(new Phrase(String.valueOf(totalPesquisa), fonteTextoBold), new Point(i + 1, ++coluna));
            }
            if (tiposSelecionados.size() > 1)
                tabela.addCell(new Phrase(String.valueOf(totalEnsino + totalExtensao + totalPesquisa), fonteTextoBold), new Point(i + 1, ++coluna));
        }
        
        document.add(tabela);
    }   

    /**
     * @return the gruposCheckboxes
     */
    public ArrayList<SelectItem> getGruposCheckboxes() {
        return gruposCheckboxes;
    }

    /**
     * @param gruposCheckboxes the gruposCheckboxes to set
     */
    public void setGruposCheckboxes(ArrayList<SelectItem> gruposCheckboxes) {
        this.gruposCheckboxes = gruposCheckboxes;
    }

    /**
     * @return the gruposSelecionados
     */
    public List<String> getGruposSelecionados() {
        return gruposSelecionados;
    }

    /**
     * @param gruposSelecionados the gruposSelecionados to set
     */
    public void setGruposSelecionados(List<String> gruposSelecionados) {
        this.gruposSelecionados = gruposSelecionados;
    }

    /**
     * @return the tiposSelecionados
     */
    public List<String> getTiposSelecionados() {
        return tiposSelecionados;
    }

    /**
     * @param tiposSelecionados the tiposSelecionados to set
     */
    public void setTiposSelecionados(List<String> tiposSelecionados) {
        this.tiposSelecionados = tiposSelecionados;
    }

    /**
     * @return the relatorioGerado
     */
    public boolean isRelatorioGerado() {
        boolean retorno = relatorioGerado;
        relatorioGerado = false;
        return retorno;
    }
}
