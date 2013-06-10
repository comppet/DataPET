/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 * Este é um javabean que representa uma Instituição e faz interação com
 * classes à nível Controller.
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */

public class Instituicao {
    private int id;
    private String nome;
    private String sigla;
    private String proReitor; // nome do pró-reitor de graduação
    private String emailProReitor; // email do pró-reitor de graduação

    public Instituicao() {
    }

    /**
     * Converte o id da instituição, que é do tipo "int", em "String" e retorna a conversão.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return id da instituição
     */
    public String getIdAsString() {
        return String.valueOf(id);
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return id da instituição
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id da instituição no atributo "id".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param id id da instituição.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return nome da instituição
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da instituição no atributo "nome".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param nome nome da instituição.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return sigla da instituição
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Define a sigla da instituição no atributo "sigla".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param sigla sigla da instituição.
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return e-mail do pró-reitor referente a instituição
     */
    public String getEmailProReitor() {
        return emailProReitor;
    }

    /**
     * Define o e-mail do pró-reitor da instituição no atributo "emailProReitor".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param emailProReitor e-mail do pró-reitor da instituição.
     */
    public void setEmailProReitor(String emailProReitor) {
        this.emailProReitor = emailProReitor;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return nome do pró-reitor da instituição.
     */
    public String getProReitor() {
        return proReitor;
    }

    /**
     * Define o nome do pró-reitor da instituição no atributo "proReitor".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param proReitor pró-reitor da instituição.
     */
    public void setProReitor(String proReitor) {
        this.proReitor = proReitor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    /**
     * Verifica se a instituição passada como parâmetro tem o atributo id igual
     * ao atributo "id" deste objeto, significando que os dois objetos são iguais.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param instituicao instituição.
     * @return true se a instituição passada por parâmetro tem o id igual ao
     * atributo "id".Caso contrário retorna false.
     */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals (Object instituicao){
        try{
            return (this.id == ((Instituicao) instituicao).getId());
        } catch (ClassCastException ex){
            return false;
        } catch (NullPointerException ex){
            return false;
        }
    }

}