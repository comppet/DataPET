/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import exceptions.ForbiddenException;
import exceptions.PageNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class Autenticacao implements PhaseListener {

    private FacesContext context;
    private ArrayList<String>[] permissoes;
    private String viewLogin;

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Tiago Peres
     * @throws JDOMException, IOException
     * @version 3.0
     * @since 3.0
     */
    public Autenticacao() throws JDOMException, IOException {
        // Inicializa a estrura de permissões
        permissoes = new ArrayList[LoginController.N_ROLES];
        for (int i = 0; i < LoginController.N_ROLES; i++) {
            permissoes[i] = new ArrayList();
        }

        lerPermissoes("/WEB-INF/permissoes.xml");
    }

    /**
     * Verifica permissoes do usuario e autentica o acesso ao sistema
     * de acordo com o nivel do usuario.
     *
     * @version 3.0
     * @since 3.0
     *
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        // Recupera a URL requisitada
        context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String uri = request.getRequestURI();
        Integer nivelUsuario;

        // Descobre o role do usuário logado
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        try{
            nivelUsuario = LoginController.getNivelUsuario();
        } catch (NullPointerException ex){
            nivelUsuario = null;
        }

        int usuarioLogado;
        if (LoginController.getNivelUsuario() == null) usuarioLogado = LoginController.VISITANTE;
        else usuarioLogado = LoginController.getNivelUsuario();


        // verifica se o usuário pode acessar a página em questão
        if (uri != null) {
            // Descobre o id da view a partir da URL
            String viewId = getViewId(uri);
            //System.out.println(viewId);
            if (viewId != null) {
                // Verifica se a página existe
                URL req;
                try {
                    req = FacesContext.getCurrentInstance().getExternalContext().getResource("/" + viewId + ".xhtml");
                } catch (MalformedURLException ex) {
                    req  = null;
                }
                if (req != null){
                    // Caso a view não pertença o conjunto de views acessíveis pelo usuário logado
                    if (!permissoes[usuarioLogado].contains(viewId) && !viewId.equals(viewLogin)) {
                        // Se o usuário ainda não efetuou login, redireciona-se para a página de login
                        if (usuarioLogado == LoginController.VISITANTE) {
                            try {
                                context.getExternalContext().redirect(viewLogin + ".xhtml");
                            } catch (IOException ex) {
                                Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } // caso contrário redireciona-se para a página de "permissão negada" ou "pagina nao encontrada"
                        else throw new ForbiddenException();
                    }
                }
            }
        }
        else {
            try {
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(Autenticacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return ViewId corrente de Autenticacao
     */
    private String getViewId(String url) {
        String vetorURL[] = url.split("/");
        String pagina = vetorURL[vetorURL.length - 1];
        int i = pagina.lastIndexOf(".");
        if (i != -1 && pagina.endsWith("xhtml")) {
            return pagina.substring(0, i);
        } else {
            return null;
        }
    }

    /**
     * @version 3.0
     * @since 3.0
     *
     */
    @Override
    public void beforePhase(PhaseEvent event) {
    }

    /**
     *
     * @version 3.0
     * @since 3.0
     * @return PhaseId corrente de Autenticacao
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    /**
     * Processa arquivo XML e verifica se a pagina corrente corresponde a
     * um view e suas permissoes correspondentes.
     *
     * @throws JDOMException, IOException
     * @version 3.0
     * @since 3.0
     */

    private void lerPermissoes(String path) throws JDOMException, IOException {

        InputStream resource;
        // Abre o arquivo XML para leitura
        context = FacesContext.getCurrentInstance();
        resource = context.getExternalContext().getResourceAsStream(path);

        // Criamos uma classe SAXBuilder que vai processar o XML
        SAXBuilder sb = new SAXBuilder();

        // Este documento agora possui toda a estrutura do arquivo.
        Document d = sb.build(resource);

        // Recuperamos o elemento root
        Element root = d.getRootElement();

        // Recuperamos os elementos filhos da raíz (roles)
        List segundoNivel = root.getChildren();
        Iterator sni = segundoNivel.iterator(); // segundoNivelIterator

        while (sni.hasNext()) {
            // recupera um elemento filho da raíz
            Element elemento = (Element) sni.next();

            // verifica se o elemento se refere às páginas
            if (elemento.getName().equals("redirecionamento")) {
                // recupera as informações de redirecionamento
                viewLogin = elemento.getChildText("login");
            } // verifica se o elemento se refere às permissões
            else if (elemento.getName().equals("permissoes")) {
                // recupera os elementos filhos de <permissoes>
                List roles = elemento.getChildren();
                Iterator ri = roles.iterator(); // roleIterator

                // Iteramos com os elementos roles
                int i = 0;
                while (ri.hasNext() && i < LoginController.N_ROLES) {
                    // Recupera i-ésismo filho válido da raíz
                    Element role = (Element) ri.next();

                    // Recupera o id correspondente ao role lido
                    int roleId = LoginController.getRoleId(role.getName());

                    // Verifica se o role lido é um role válido
                    if (roleId != -1) {
                        // Recupera os elementos filhos de role (views)
                        List views = role.getChildren();
                        Iterator vi = views.iterator(); // viewIterator

                        // Itera sobre os filhos de role (views)
                        while (vi.hasNext()) {
                            Element view = (Element) vi.next();
                            // Verifica se o elemento lido é realmente um <view>
                            if (view.getName().equals("view")) {
                                permissoes[roleId].add(view.getAttributeValue("id"));
                            }
                        }
                        ++i;
                    }
                }
            }
        }
    }
}
