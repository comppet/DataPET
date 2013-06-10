/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Date;

/** 
* 
* @author Pedro Augusto
* @version 3.0
* @since 3.0
*/
public abstract class Petiano extends Usuario{

    private String nome;
    private String telefone;
    private String celular;
    private Date dataIngressoPet;
    private Date dataSaidaPet;
    private Grupo grupo;

    /*public int getIdGrupo(){
        return idGrupo;
    }*/
    
    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return nome do petiano.
    */
    public String getNome(){
        return nome;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return nome curto do petiano
    */
    public String getNomeCurto(){
        int capacidade = 15;
        int qtdeNomes = 0;
        StringBuilder nomeCurto = new StringBuilder(capacidade);
        String[] nomes = nome.split(" ");

        if (nomes[0].length() <= capacidade){
            nomeCurto.append(nomes[0]);
            capacidade -= nomes[0].length();
            qtdeNomes++;

            if (nomes.length > 1){
                // se o último nome mais o espaço entre ele e o nome anterior couber
                if (nomes[nomes.length - 1].length() + 1 <= capacidade){
                    capacidade -= nomes[nomes.length - 1].length() + 1;
                    qtdeNomes++;
                    // verifica-se a possibilidade de se colocar o(s) nome(s) do meio
                    while (nomes.length > qtdeNomes && (nomes[qtdeNomes - 1].length() + 1) <= capacidade){
                        nomeCurto.append(' ');
                        nomeCurto.append(nomes[qtdeNomes - 1]);
                        capacidade -= nomes[qtdeNomes - 1].length() + 1;
                        qtdeNomes++;
                    }
                    // verifica-se a possibilidade de se abreviar um dos nomes do meio, se ele existe
                    if (nomes.length > qtdeNomes && capacidade >= 3){
                        nomeCurto.append(' ');
                        nomeCurto.append(nomes[qtdeNomes - 1].charAt(0));
                        nomeCurto.append('.');
                    }
                    // coloca-se o último nome
                    nomeCurto.append(' ');
                    nomeCurto.append(nomes[nomes.length - 1]);
                }
                // verifica-se a possibilidade de se abreviar o último nome, caso ele não caiba
                else if (capacidade >= 3) {
                        nomeCurto.append(' ');
                        nomeCurto.append(nomes[nomes.length - 1].charAt(0));
                        nomeCurto.append('.');
                }
            }

            return nomeCurto.toString();
        }
        else{
            return nome.substring(0, capacidade - 1);
        }
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return data de ingresso no pet do petiano.
    */
    public Date getDataIngressoPet(){
        return dataIngressoPet;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return data de saída do pet do petiano.
    */
    public Date getDataSaidaPet(){
        return dataSaidaPet;
    }

    
    /** 
    * Muda o nome do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo nome.
    */
    public void setNome(String nome){
        this.nome = nome;
    }

    
    /** 
    * Muda a datade ingresso do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param nova data.
    */
    public void setDataIngressoPet(Date dataIngressoPet){
        this.dataIngressoPet = dataIngressoPet;
    }

    
    /** 
    * Muda a data de saída do pet do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param nova data de saída.
    */
    public void setDataSaidaPet(Date dataSaidaPet){
        this.dataSaidaPet = dataSaidaPet;
    }

    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return telefone do petiano.
    */
    public String getTelefone() {
        return telefone;
    }

    /** 
    * Muda o telefone do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo telefone.
    */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return celular do petiano.
    */
    public String getCelular() {
        return celular;
    }

    /** 
    * Muda o celular do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo celular.
    */
    public void setCelular(String celular) {
        this.celular = celular;
    }

    
    /** 
    * Passa o nome do petiano para String caso ele seja "null" ou vazio.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return Uma string do nome.
    */
    @Override
    public String toString(){
        if (nome == null) return "null";
        else if(nome.isEmpty()) return "Nameless";
        else return nome;
    }

    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return grupo do petiano.
    */
    public Grupo getGrupo() {
        return grupo;
    }

    /** 
    * Muda o grupo do petiano.
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo ngrupoome.
    */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}