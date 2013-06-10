/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/** 
* @author Pedro Augusto
* @version 3.0
* @since 3.0
    */
public class Tutor extends Petiano {

    private String titulacao;
    private String area;

    
    /** 
    * Construtor da classe.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    */
    public Tutor(){
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return titulação do tutor.
    */
    public String getTitulacao(){
        return titulacao;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return área do tuor.
    */
    public String getArea(){
        return area;
    }

    
    /** 
    * Muda a titulação do tutor.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param  Nova titulação.
    */
    public void setTitulacao(String titulacao){
        this.titulacao = titulacao;
    }

    
    /** 
    * Muda a área de estudos do tutor.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param  Nova área de estudos do tutor.
    */
    public void setArea(String area){
        this.area = area;
    }

}
