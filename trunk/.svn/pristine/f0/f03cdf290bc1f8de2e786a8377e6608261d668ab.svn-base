/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import java.util.Date;


/**
 * Este é um javabean que representa uma Anotação e faz interação com
 * classes à nível Controller.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public class Anotacao {

    private int id;
    private Date data;
    private String texto;
    private Aluno aluno;
    private boolean carater; // positivo = true; negativo = false


    public Anotacao(){
        aluno = new Aluno();
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return id da anotação
     */
    public Date getData(){
        return data;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return texto da anotação
     */
    public String getTexto(){
        return texto;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return descrição da anotação
     */
    public String getDescricao(){
        StringBuffer sb = new StringBuffer(43);
        int intFinal = texto.length();
        if (intFinal > 40) intFinal = 40;
        sb.append(texto.subSequence(0, intFinal));
        if (texto.length() > 40) sb.append("...");
        System.out.println("Descrevendo...");
        return sb.toString();
    }

    /**
     * Define a data da anotação no atributo "data".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param data data da anotação.
     */
    public void setData(Date data){
        this.data = data;
    }

    /**
     * Define o texto da anotação no atributo "texto".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param texto texto da anotação.
     */
    public void setTexto(String texto){
        this.texto = texto;
    }

    /*public ArrayList<Anotacao> getAnotacoes(){

    }*/

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return Aluno Aluno referente a anotação
     */
    public Aluno getAluno() {
        return aluno;
    }

    /**
     * Define o aluno referente a anotação no atributo "aluno".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param aluno aluno referente a anotação.
     */
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return id da anotação
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id da anotação no atributo "id".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param id id da anotação.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return carater da anotação
     */
    public boolean getCarater() {
        return carater;
    }

    /**
     * Define o carater da anotação no atributo "carater".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param carater carater da anotação.
     */
    public void setCarater(boolean carater) {
        this.carater = carater;
    }

    /**
     * Verifica se a anotação passada como parâmetro tem o atributo id igual
     * ao atributo "id" deste objeto, significando que os dois objetos são iguais.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param anotacao anotação.
     * @return true se a anotação passada por parâmetro tem o id igual ao
     * atributo "id".Caso contrário retorna false.
     */
    @Override
    public boolean equals(Object o){
        Anotacao other;
        try{
            other = (Anotacao) o;
            if (other.getId() == id) return true;
            else return false;
        }
        catch(ClassCastException ex){
            return false;
        }
    }

    

}
