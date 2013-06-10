/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;

/**
 * Este é um javabean que representa uma operação de ativação ou desativação sofrida por um grupo PET
 * e faz interação com classes à nível Controller.
 *
 * @author Douglas Antunes
 * @version 3.0
 * @since 3.0
 */
public class GrupoHistorico {
    public static final int ATIVACAO = 0;
    public static final int DESATIVACAO = 1;

    private int id;
    private Grupo grupo;
    private int operacao;
    private String justificativa;
    private Date data;

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return id da operação de desativação ou reativação sofrida pelo grupo PET (atributo "grupo").
     */
    public int getId() {
        return id;
    }

    /**
     * Define o id da operação de desativação ou reativação sofrida pelo grupo PET (atributo "grupo"),
     * no atributo "id".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param id id da operação de desativação ou reativação sofrida pelo grupo PET (atributo "grupo").
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return tipo de operação sofrida pelo grupo PET (atributo "grupo"), 0 = Ativação e 1 = Desativação.
     */
    public int getOperacao() {
        return operacao;
    }

    /**
     * Define o tipo de operação sofrida pelo grupo PET (atributo "grupo"), 0 = Ativação e 1 = Desativação.
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param operacao operação sofrida pelo grupo PET (atributo "grupo"), 0 = Ativação e 1 = Desativação.
     */
    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return justificativa para a operação de desativação ou reativação sofrida
     * pelo grupo PET (atributo "grupo").
     */
    public String getJustificativa() {
        return justificativa;
    }

    /**
     * Define a justificativa para a operação de desativação ou reativação sofrida
     * pelo grupo PET (atributo "grupo"), no atributo "justificativa".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param justificativa justificativa para a operação de desativação ou
     * reativação sofrida pelo grupo PET (atributo "grupo").
     */
    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return data em que um grupo PET (atributo "grupo") sofreu uma operação de desativação ou reativação.
     */
    public Date getData() {
        return data;
    }

    /**
     * Define a data em que um grupo PET (atributo "grupo") sofreu uma
     * operação de desativação ou reativação, no atributo "data".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param data data em que um grupo PET (atributo "grupo") sofreu uma operação de desativação ou reativação.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return grupo PET que sofreu a operação de desativação ou reativação.
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Define o grupo PET que sofreu a operação de desativação ou reativação, no atributo
     * "grupo".
     *
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param grupo grupo PET que sofreu a operação de desativação ou reativação.
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}