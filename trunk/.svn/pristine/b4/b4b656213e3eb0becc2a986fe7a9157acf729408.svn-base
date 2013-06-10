package Controller;

import Model.Aluno;
import Model.Atividade;
import Model.AtividadePublica;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.style.RtfFont;
import java.awt.Color;
import org.primefaces.model.StreamedContent;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Esta é uma classe voltada para geração do relatório, 
 * no qual recebe as informações da view em que o Tutor pode gerar o relatório.
 *
 * @author Tassyo Tchesco
 * @version 3.0
 * @since 3.0
 */

public abstract class RelatorioAtividadesController {
    
    /* Datas de início e fim das atividades presentes no relatório*/
    private Date inicioRelatorio;
    private Date fimRelatorio;
    
    /* Fontes utilizadas no arquivo .rtf do relatório*/
    protected static final RtfFont fonteTitulo = new RtfFont("Arial", (float) 14.0, Font.BOLD);
    protected static final RtfFont fonteSubTitulo = new RtfFont("Arial", (float) 12.0, Font.BOLD);
    protected static final RtfFont fonteTextoBold = new RtfFont("Arial", (float) 12.0, Font.BOLD);
    protected static final RtfFont fonteTexto = new RtfFont("Arial", (float) 12.0);
    protected static final RtfFont fonteDescricao = new RtfFont("Arial", (float) 10.0);
    
    private static final int indices[] = {0,0,0,0,0};
    protected static final String noLugarDeBranco = "---";
    protected static final SimpleDateFormat formatoPtBr = new SimpleDateFormat("dd/MM/yyyy");
    

     /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */
    public RelatorioAtividadesController(){
        //inicioRelatorio = SessionController.getInicioAno();
        inicioRelatorio = SessionController.getInicioAno();
        fimRelatorio = SessionController.getFimAno();
    }
    
    public abstract StreamedContent getRelatorioRTF() throws SQLException, DocumentException, UnsupportedEncodingException;
    
    public abstract StreamedContent getRelatorioPDF() throws SQLException, DocumentException, UnsupportedEncodingException;

    /**
     * Adiciona as atividades de Ensino, Pesquisa e Extensão no relatório
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */
    protected void gerarAtividades(Document document, ArrayList atividades, String tipo, int nivelTitulo) throws DocumentException, UnsupportedEncodingException {
        String semAtividades;
        String titulo;
        
        if (tipo.equals("pesquisa")){
            titulo = "ATIVIDADES DE PESQUISA";
            semAtividades = "Não houve atividades de pesquisa.";
        }
        else if (tipo.equals("ensino")){
            titulo = "ATIVIDADES DE ENSINO";
            semAtividades = "Não houve atividades de ensino.";
        }
        else{
            titulo = "ATIVIDADES DE EXTENSÃO";
            semAtividades = "Não houve atividades de extensão.";
        }
        
        document.add(new Paragraph(intitular(nivelTitulo) + ". " + titulo, fonteTitulo));
        document.add(new Paragraph(""));
        if (atividades.isEmpty())
            document.add(new Paragraph(semAtividades, fonteTexto));
        else{
            
            
            Comparator comparator2 = new Comparator<Atividade>() {

                @Override
                public int compare(Atividade o1, Atividade o2) {
                    int p = o1.getDataInicio().compareTo(o2.getDataInicio());
                    return p;
                }
            };
            Collections.sort(atividades, comparator2);
            
            
            
            Iterator<Atividade> it = atividades.iterator();
            while(it.hasNext()){
                Atividade a = it.next();

                String inicio;
                if (a.getDataInicio() != null)
                    inicio = formatoPtBr.format(a.getDataInicio());
                else inicio = noLugarDeBranco;

                String fim;
                if (a.getDataFim() != null)
                    fim = formatoPtBr.format(a.getDataFim());
                else fim = noLugarDeBranco;

                document.add(new Paragraph(intitular(nivelTitulo + 1) + ". " 
                        + a.getTitulo(), fonteSubTitulo));
                document.add(new Paragraph(""));
                
                Paragraph p;
                
                if (!tipo.equals("pesquisa")){
                    p = new Paragraph();
                    p.add(new Phrase("Natureza: ", fonteTextoBold));
                    p.add(new Phrase(((AtividadePublica) a).getNatureza().getNome(), fonteTexto));
                    document.add(p);
                    document.add(new Paragraph(""));
                }
                
                p = new Paragraph();
                p.add(new Phrase("Data de início: ", fonteTextoBold));
                p.add(new Phrase(inicio, fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Data de término: ", fonteTextoBold));
                p.add(new Phrase(fim, fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Parceiros: ", fonteTextoBold));
                p.add(new Phrase(substituirTextoEmBranco(a.getParceiros()), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Descrição: ", fonteTextoBold));
                StringBuffer stringDescricao;
                stringDescricao = transformaString(a.getDescricao());
                p.add(new Phrase(stringDescricao.toString(), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Justificativa: ", fonteTextoBold));
                StringBuffer stringJustificativa;
                stringJustificativa = transformaString(a.getJustificativa());
                p.add(new Phrase(stringJustificativa.toString(), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                if (!tipo.equals("pesquisa")){
                    p = new Paragraph();
                    p.add(new Phrase("Público Alvo: ", fonteTextoBold));
                    StringBuffer stringPublicoAlvo;
                    stringPublicoAlvo = transformaString(substituirTextoEmBranco(((AtividadePublica) a).getPublicoAlvo()));
                    p.add(new Phrase(stringPublicoAlvo.toString(), fonteTexto));
                    document.add(p);
                    document.add(new Paragraph(""));
                }
                
                
                p = new Paragraph();
                p.add(new Phrase("Resultados esperados: ", fonteTextoBold));
                StringBuffer stringResultadosEsperados;
                stringResultadosEsperados = transformaString(substituirTextoEmBranco(a.getResultadosEsperados()));
                p.add(new Phrase(stringResultadosEsperados.toString(), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Resultados alcançados: ", fonteTextoBold));
                StringBuffer stringResultadosAlcancados;
                stringResultadosAlcancados = transformaString(substituirTextoEmBranco(a.getResultadosAlcancados()));
                p.add(new Phrase(stringResultadosAlcancados.toString(), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = new Paragraph();
                p.add(new Phrase("Comentário: ", fonteTextoBold));
                StringBuffer stringComentario;
                stringComentario = transformaString(substituirTextoEmBranco(a.getComentario()));
                p.add(new Phrase(stringComentario.toString(), fonteTexto));
                document.add(p);
                document.add(new Paragraph(""));
                
                p = gerarResponsaveis(a.getResponsaveis());
                if (p != null) document.add(p);
                document.add(getSeparador());
                document.add(new Paragraph(""));
            }
        }
    }

    
    /* O iText possui um bug, que ao dar um ENTER, ele insere o simbolo ? no final da linha
     Esta função concerta este bug
     */
    public StringBuffer transformaString(String line){
        StringBuffer stringAux = new StringBuffer();
                for (int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) != '\r'){
                        stringAux.append(line.charAt(i));
                    }
                }
                return stringAux;
    }

    

     /**
     * Adiciona os responsaveis de cada atividade.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */		
    private Paragraph gerarResponsaveis(ArrayList<Aluno> responsaveis){
        Paragraph p = null;
        if (responsaveis.size() > 0){
            p = new Paragraph();
                
            if (responsaveis.size() > 1)
                p.add(new Phrase("Promotores da atividade: ", fonteTextoBold));
            else 
                p.add(new Phrase("Promotor da atividade: ", fonteTextoBold));
            
            Iterator<Aluno> itr = responsaveis.iterator();
            StringBuilder texto = new StringBuilder();
            texto.append(itr.next().getNome());
            while (itr.hasNext()){
                texto.append(", ");
                texto.append(itr.next().getNome());
            }
            texto.append(".");
            p.add(new Phrase(texto.toString(), fonteTexto));
        }
        return p;
    }
    

     /**
     * Formata a tabela usada no relatório.
     *
     * @author Tassyo Tchesco
     * @version 3.0
     * @since 3.0
     */
    protected static Table getSeparador() throws BadElementException{
        Table separador = new Table(1,1);
        separador.setWidth(100);
        separador.setBorder(0);
        separador.setBorderColor(Color.BLACK);
        separador.setBorderWidthBottom(1);
        return separador;
    }




    protected String intitular(int nivelTitulo){
        indices[nivelTitulo]++;
        for (int i = nivelTitulo + 1; i < indices.length; i++){
            indices[i] = 0;
        }
        StringBuilder titulo = new StringBuilder(2 * nivelTitulo + 1);
        for (int i = 0; i <= nivelTitulo; i++){
            titulo.append(indices[i]);
            if (i < nivelTitulo) titulo.append('.');
        }
        return titulo.toString();
    }
    
    protected void zerarIntitulacao(){
        for (int i = 0; i < indices.length; i++) indices[i] = 0;
    }
    
    protected String substituirTextoEmBranco(String s){
        if (s == null || s.equals(""))
            return noLugarDeBranco;
        else return s;
    }

    /**
     * @return the inicioRelatorio
     */
    public Date getInicioRelatorio() {
        return inicioRelatorio;
    }

    /**
     * @param inicioRelatorio the inicioRelatorio to set
     */
    public void setInicioRelatorio(Date inicioRelatorio) {
        this.inicioRelatorio = inicioRelatorio;
    }

    /**
     * @return the fimRelatorio
     */
    public Date getFimRelatorio() {
        return fimRelatorio;
    }

    /**
     * @param fimRelatorio the fimRelatorio to set
     */
    public void setFimRelatorio(Date fimRelatorio) {
        this.fimRelatorio = fimRelatorio;
    }

}
