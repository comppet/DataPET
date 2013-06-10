/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.ArrayList;
import java.util.Date;


/** 
* @author Pedro Augusto, Tassyo Tchesco
* @version 3.0
* @since 3.0
*/
public class Aluno extends Petiano {

    private boolean bolsista;
    private int periodo = 1; // 1 é o primeiro valor possível, e não 0.
    private Date dataIngressoInst;
    private float craGeral;
    private ArrayList<Nota> notas;

    
    /** 
    * Verifica se o aluno é bolsista.
    * 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @return True se for bolsistas e False se não.
    */
    public boolean isBolsista(){
        return bolsista;
    }
     
    
    /** 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @return Período do aluno.
    */
    public int getPeriodo(){
        return periodo;
    }

    
    /** 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @return data de ingresso na Instituição.
    */
    public Date getDataIngressoInst(){
        return dataIngressoInst;
    }

    
    /** 
    * Modifica o aluno bolsista ou não bolsista
    * 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @param True para bolsista e False para não bolsista.
    */
    public void setBolsista(boolean bolsista){
        this.bolsista = bolsista;
    }

    
    /** 
    * Modifica o período do aluno
    * 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @param novo período.
    */
    public void setPeriodo(int periodo){
        this.periodo = periodo;
    }

    
    /** 
    * Modifica a data de ingresso da instituição do aluno
    * 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @param nova data de ingresso.
    */
    public void setDataIngressoInst(Date dataIngressoInst){
        this.dataIngressoInst = dataIngressoInst;
    }

    /** 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @return Lista de notas do aluno.
    */
    public ArrayList<Nota> getNotas() {
        return notas;
    }

    /** 
    * Modifica as notas do aluno.
    * 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @param Lista de todas as notas.
    */
    public void setNotas(ArrayList<Nota> notas) {
        this.notas = notas;
    }

    
    /** 
    * @author Pedro Augusto, Tassyo Tchesco
    * @version 3.0
    * @since 3.0
    * @return a última nota do aluno, ou -1 caso não tenha notas.
    */
    public float getUltimaNota(){
        if(notas != null && notas.size() > 0){
            return notas.get(0).getValor();
        }
        else{
            return -1; // se o aluno não tiver notas cadastradas, por padrão será retornado o valor -1
        }
    }

    /**
     * @return the craGeral
     */
    public float getCraGeral() {
        return craGeral;
    }

    /**
     * @param craGeral the craGeral to set
     */
    public void setCraGeral(float craGeral) {
        this.craGeral = craGeral;
    }

}
