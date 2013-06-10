/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 * Este é um javabean que representa um grupo PET e faz interação com classes à nível Controller.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public class Grupo {
    // constantes: tipos de grupo:

    public static final int SESU = 0;
    public static final int INSTITUCIONAL = 1;
    public static final int SECAD = 2;
    // atributos de instância
    private int id;
    private Instituicao instituicao;
    private Curso curso;
    private String sigla;
    private int tipo;
    private Date Implantacao;
    private String site;
    private String tema;
    private String telefone;
    private boolean ativado;
    private Tutor tutor; // representa o tutor atual do grupo

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return id do grupo PET
     */
    public int getId() {
        return id;
    }

    /**
     * Converte o id do grupo PET, que é do tipo "int", em "String" e retorna a conversão.
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return id do grupo PET
     */
    public String getIdAsString() {
        return String.valueOf(id);
    }

    /**
     * Define o id do grupo PET no atributo "id".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param id id do grupo PET.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return a instituição de ensino superior a qual o grupo PET pertence.
     */
    public Instituicao getInstituicao() {
        return instituicao;
    }

    /**
     * Define a instituição de ensino superior a qual o grupo PET pertence no atributo "instituicao".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param instituicao de ensino superior a qual pertence o grupo PET.
     */
    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return sigla do grupo PET
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Define a sigla do grupo PET no atributo "sigla".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param sigla sigla do grupo PET.
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return o tipo do grupo PET (SESU, INSTITUCIONAL e SECAD).
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Define o tipo do grupo PET (SESU, INSTITUCIONAL e SECAD).
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param tipo do grupo PET (SESU, INSTITUCIONAL e SECAD).
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return data de implantação do grupo PET.
     */
    public Date getImplantacao() {
        return Implantacao;
    }

    /**
     * Define a data de implantação do grupo PET no atributo "Implantacao".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param Implantacao data de implantação do grupo PET.
     */
    public void setImplantacao(Date Implantacao) {
        this.Implantacao = Implantacao;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return o endereço do website do grupo PET.
     */
    public String getSite() {
        return site;
    }

    /**
     * Define o endereço do website do grupo PET no atributo "site".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param site endereço do website do grupo PET.
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return o tema do grupo PET.
     */
    public String getTema() {
        return tema;
    }

     /**
     * Define o tema do grupo PET no atributo "tema".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param tema tema do grupo PET.
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return o número do telefone do grupo PET.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o número do telefone do grupo PET no atributo "telefone".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param tema tema do grupo PET.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Verifica se o grupo PET está ativo ou desativado.
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return true se o grupo está ativo e false se está desativado.
     */
    public boolean isAtivado() {
        return ativado;
    }

    /**
     * Define o status do grupo PET, ativado ou desativado.
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param ativado status do grupo PET (ativado ou desativado).
     */
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }

    /**
     * Verifica se o grupo PET passado como parâmetro tem o atributo id igual ao atributo
     * "id" deste objeto, significando que os dois objetos são iguais.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param g grupo PET.
     * @return true se grupo PET passado por parâmetro tem o id igual ao atributo "id".
     * Caso contrário retorna false.
     */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object g) {
        try {
            return (this.id == ((Grupo) g).getId());
        } catch (ClassCastException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    /**
     * Sobrescrição do método "toString()", retorna a sigla do grupo PET.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param g grupo PET.
     * @return Sigla do grupo PET.
     */
    @Override
    public String toString() {
        return sigla;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return tutor do grupo PET.
     */
    public Tutor getTutor() {
        return tutor;
    }

    /**
     * Define o tutor do grupo PET no atributo "tutor".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param ativado status do grupo PET (ativado ou desativado).
     */
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    /**
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @return o curso ao qual o grupo PET está vinculado.
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * Define o curso ao qual o grupo PET está vinculado, no atributo "curso".
     *
     * @author Douglas Antunes
     * @version 3.0
     * @since 3.0
     * @param curso curso ao qual o grupo PET está vinculado.
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
