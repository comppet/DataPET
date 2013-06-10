/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;

/** 
* @author Eduarso Silva
* @version 3.0
* @since 3.0
*/
public class Cla extends Usuario{

    private int idCla;
    private String interlocutor;
    private Instituicao instituicao;
    private String nome;
    private Date dataIngressoCla;
    private Date dataSaidaCla;
    
    /** 
    * Construtor da clase.
    * 
    * @author Eduarso Silva
    * @version 3.0
    * @since 3.0
    */
    public Cla(){
    }

    
    /** 
    * @author Eduarso Silva
    * @version 3.0
    * @since 3.0
    * @return interlocutor do CLA
    */
    public String getInterlocutor() {
        return interlocutor;
    }

    
    /** 
    * Muda o insterlocutor do CLA
    * 
    * @author Eduarso Silva
    * @version 3.0
    * @since 3.0
    * @param  Novo interlocutor.
    */
    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

     /** 
    * @author Eduarso Silva
    * @version 3.0
    * @since 3.0
    * @return instituição do CLA
    */
    public Instituicao getInstituicao() {
        return instituicao;
    }

    /** 
    * Muda a instituição do CLA
    * 
    * @author Eduarso Silva
    * @version 3.0
    * @since 3.0
    * @param  Uma instituição.
    */
    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    /**
     * @return the idCla
     */
    public int getIdCla() {
        return idCla;
    }

    /**
     * @param idCla the idCla to set
     */
    public void setIdCla(int idCla) {
        this.idCla = idCla;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the dataIngressoCla
     */
    public Date getDataIngressoCla() {
        return dataIngressoCla;
    }

    /**
     * @param dataIngressoCla the dataIngressoCla to set
     */
    public void setDataIngressoCla(Date dataIngressoCla) {
        this.dataIngressoCla = dataIngressoCla;
    }

    /**
     * @return the dataSaidaCla
     */
    public Date getDataSaidaCla() {
        return dataSaidaCla;
    }

    /**
     * @param dataSaidaCla the dataSaidaCla to set
     */
    public void setDataSaidaCla(Date dataSaidaCla) {
        this.dataSaidaCla = dataSaidaCla;
    }

}
