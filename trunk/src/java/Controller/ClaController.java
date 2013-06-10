/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Dao.ClaDao;
import Dao.Conexao;
import Dao.JDBCClaDao;
import Model.Cla;
import Model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author edusr
 */
@ManagedBean(name="ClaController")
@ViewScoped
public class ClaController extends UsuarioController<Cla, ClaDao>{

    private TreeMap<String, String> tmap;
    private String id;

    public ClaController() throws SQLException {
        super();
        setDao(new JDBCClaDao(Conexao.getConnection()));
        setUsuario(new Cla());
        
    }

    private boolean isSeguro(){
        Usuario uLogado = LoginController.getUsuario();
        return (uLogado.equals(getUsuario()) || LoginController.isAdmin());
    }

    
    @Override
    public void prepararEdicao() throws SQLException{
        getUsuario().setId(Integer.parseInt(id));
        super.prepararEdicao();
        //System.out.println("Id Cla: " + getUsuario().getId());
        if (getUsuario() == null) {
            System.out.println("CLA é NULL!");
            setUsuario(new Cla());
            getUsuario().setInstituicao(LoginController.getInstituicao());
        } 
    }

    @Override
    public void salvar() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        getDao().setConexao(Conexao.getConnection());
        getDao().getConexao().setAutoCommit(false);
        try{
            if(LoginController.isCLA()){
                getUsuario().setIdCla(LoginController.getUsuario().getId());
            }
            if(LoginController.isAdmin()){
                getUsuario().setNome(getUsuario().getInterlocutor());
            }
            super.salvar();
            
            getDao().getConexao().commit();
            context.addMessage(null, new FacesMessage("O CLA foi cadastrado com sucesso!"));
            
            // envia e-mail de boas vindas
            ArrayList<String> destinatarios = new ArrayList<String>(); //define destino para o email
            destinatarios.add(getUsuario().getEmail());
            boasVindas("CLA", getUsuario().getSenha(), destinatarios);
        } catch(SQLException ex){
            if (ex.getErrorCode() == 1062)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O CLA não foi cadastrado. O endereço de e-mail informado já existe no sistema.",
                        null));
            else
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                        "Caso o problema persista cantacte o administrador do sistema.",
                        null));
            Logger.getLogger(ClaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            getDao().getConexao().setAutoCommit(true);
            getDao().getConexao().close();
        }
    }

    @Override
    public String editar() throws SQLException{
        //if (!isSeguro()) throw new ForbiddenException();
        
        FacesContext context = FacesContext.getCurrentInstance();
        String view;
        getDao().setConexao(Conexao.getConnection());
        getDao().getConexao().setAutoCommit(false);
        try{
            view = super.editar();
            getDao().getConexao().commit();
            context.addMessage(null, new FacesMessage("As informações do CLA foram alteradas com sucesso!"));
        } catch (SQLException ex){
            if (ex.getErrorCode() == 1062)
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O endereço de e-mail informado já existe no sistema.",
                        null));
            else
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Ocorreu um erro na conexão com o banco de dados. Por favor, tente mais tarde." +
                        "Caso o problema persista contacte o administrador do sistema.",
                        null));
            view = "";
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            getDao().getConexao().setAutoCommit(true);
            getDao().getConexao().close();
            setUsuario(new Cla());
        }
        return view;
    }

    public ArrayList<Cla> recuperarPorInstituicao(int idInstituicao) throws SQLException{
        getDao().setConexao(Conexao.getConnection());
        ArrayList<Cla> clas = getDao().recuperarPorInstituicao(idInstituicao);
        getDao().getConexao().close();
        return clas;
    }

    public TreeMap<String, String> tMapCla(int idInstituicao) throws SQLException{
        getDao().setConexao(Conexao.getConnection());
        setTmap(new TreeMap<String, String>());
        ArrayList<Cla> clas = getDao().recuperarPorInstituicao(idInstituicao);
        for(int i = 0; i < clas.size(); i++){
            String id = String.valueOf(clas.get(i).getId());
            String nome = clas.get(i).getNome();
            tmap.put(nome, id);
        }
        getDao().getConexao().close();
        return tmap;
    }

    /**
     * @return the tmap
     */
    public TreeMap<String, String> getTmap() throws SQLException {
        tmap = tMapCla(LoginController.getInstituicao().getId());
        return tmap;
    }

    /**
     * @param tmap the tmap to set
     */
    public void setTmap(TreeMap<String, String> tmap) {
        this.tmap = tmap;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
