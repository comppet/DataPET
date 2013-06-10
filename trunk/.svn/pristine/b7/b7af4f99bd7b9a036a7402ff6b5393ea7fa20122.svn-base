package Controller;

import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import javax.faces.bean.RequestScoped;


/**
 * Esta é uma classe de controle responsável pelo envio de e-mails a partir do e-mail
 * "datapetcomppet@gmail.com"
 *
 * @author Nayara Cardoso
 * @version 3.0
 * @since 3.0
 */
@ManagedBean(name = "EmailController")
@RequestScoped
public class EmailController {

    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String USERNAME = "datapetcomppet";
    private static final String PASSWORD = "datapet123";
    private static final String EMAILORIGEM = "datapetcomppet@gmail.com";
    private String nome;
    private String assunto;
    private String mensagem;
    private ArrayList<String> destinatarios;
    private String origem;
    private String notificacao;
    private SimpleEmail conexao;

    /**
     * Construtor padrão da classe. Inicializa as variáveis.
     *
     * @author Nayara Cardoso
     * @throws EmailException
     * @version 3.0
     * @since 3.0
     */
    public EmailController() throws EmailException {
        conexao = new SimpleEmail();
        conexao.setHostName(HOSTNAME);
        conexao.setSmtpPort(587);
        conexao.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        conexao.setTLS(true);
        conexao.setFrom(EMAILORIGEM);
    }

    /**
     * Realiza o envio de uma mensagem via e-mail para os destinatários presentes no atributo "destinatarios"
     * ou envia uma mensagem para o e-mail do DataPET: datapetcomppet@gmail.com
     *
     * @author Nayara Cardoso
     * @throws EmailException
     * @version 3.0
     * @since 3.0
     */
    public void enviaEmail() throws EmailException {
        /*if(destinatarios != null){
        Iterator<String> it = destinatarios.iterator();
        while (it.hasNext())
        System.out.println(it.next());
        }*/
        if (nome != null && !nome.equals("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Esta mensagem foi enviada através do DataPET por: ");
            sb.append(nome);
            sb.append("\n\nMensagem:\n");
            sb.append(mensagem);
            mensagem = sb.toString();
        }

        conexao.setSubject(assunto);
        try{
            conexao.setMsg(mensagem);
            conexao.setFrom(origem);
            if (destinatarios != null) {
                Iterator<String> it = destinatarios.iterator();
                while (it.hasNext()) {
                    conexao.addTo(it.next());
                }
            } else {
                conexao.addTo("datapetcomppet@gmail.com");
            }
            conexao.send();
            if (notificacao == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado com sucesso!", "Informação"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, notificacao, "Informação"));
            }
            reset();
        } catch(EmailException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage() , null));
            throw ex;
        }
    }

    /**
     * Retorna o nome do usuário que enviou um e-mail através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return nome nome do usuário que enviou uma e-mail através do DataPET.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário que enviará um e-mail através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param nome nome do usuário que enviou um e-mail através do DataPET.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o assunto da mensagem que será enviada pelo DataPET para um ou mais usuários, ou o
     * assunto da mensagem que será enviada para o DataPET por um usuário.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return o assunto da mensagem que será enviada pelo DataPET para um ou mais usuários, ou o
     * assunto da mensagem que será enviada para o DataPET por um usuário.
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * Define o assunto da mensagem que será enviada para um ou mais usuários através do DataPET, ou o
     * assunto da mensagem que será enviada para o DataPET por um usuário.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param assunto assunto da mensagem que será enviada para um ou mais usuários através do DataPET, ou o
     * assunto da mensagem que será enviada para o DataPET por um usuário.
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * Retorna a mensagem que será enviada para um ou mais usuários através do DataPET, ou enviada para o DataPET
     * por um usuário.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return mensagem que será enviada para um ou mais usuários através do DataPET, ou enviada para o DataPET
     * por um usuário.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Define a mensagem que será enviada para um ou mais usuários através do DataPET, ou enviada para o DataPET
     * por um usuário.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param mensagem mensagem que será enviada para um ou mais usuários através do DataPET, ou enviada para o DataPET
     * por um usuário.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Retorna o endereço de e-mail origem, ou seja, o endereço que enviará a mensagem através do DataPET
     * para um ou mais usuários.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return endereço de e-mail origem, ou seja, o endereço que enviará a mensagem através do DataPET
     * para um ou mais usuários.
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * Define o endereço de e-mail origem, ou seja, o endereço que enviará a mensagem através do DataPET
     * para um ou mais usuários.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param origem o endereço de e-mail origem, ou seja, o endereço que enviará a mensagem através do DataPET
     * para um ou mais usuários.
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * Retorna a notificação que será exibida na tela assim que um usuário envia um e-mail através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return a notificação que será exibida na tela assim que um usuário envia um e-mail através do DataPET.
     */
    public String getNotificacao() {
        return notificacao;
    }

    /**
     * Define a notificação que será exibida na tela assim que um usuário envia um e-mail através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param notificacao notificação que será exibida na tela assim que um usuário envia um e-mail através do DataPET.
     */
    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    /**
     * Reinicia todos os atributos da classe, atribuindo-os o valor "null"
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     */
    private void reset() {
        nome = null;
        assunto = null;
        mensagem = null;
        destinatarios = null;
        origem = null;
        notificacao = null;
    }

    /**
     * Retorna a lista de e-mails de usuários do sistema que receberão um e-mail enviado através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @return lista de e-mails de usuários do sistema que receberão um e-mail enviado através do DataPET.
     */
    public ArrayList<String> getDestinatarios() {
        return destinatarios;
    }

    /**
     * Define a lista de e-mails de usuários do sistema que receberão um e-mail enviado através do DataPET.
     *
     * @author Nayara Cardoso
     * @version 3.0
     * @since 3.0
     * @param destinatarios lista de e-mails de usuários do sistema que receberão um e-mail enviado através do DataPET.
     */
    public void setDestinatarios(ArrayList<String> destinatarios) {
        this.destinatarios = destinatarios;
    }

  
}
