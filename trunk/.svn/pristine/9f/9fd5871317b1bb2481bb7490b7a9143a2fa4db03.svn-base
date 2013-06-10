/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/** 
* @author Pedro Augusto
* @version 3.0
* @since 3.0
* 
*/
public abstract class Usuario {

    private int id;
    private String email;
    private String senha;

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return ID do usuário
    */
    public int getId(){
        return id;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return e-mail do usuário
    */
    public String getEmail(){
        return email;
    }

    
    /** 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return senha do usuário
    */
    public String getSenha(){
        return senha;
    }

    
    /** 
    * Muda o ID do usuário
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param Novo ID de usuário
    */
    public void setId(int id){
        this.id = id;
    }

    
    /** 
    * Muda o e-mail do usuário
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param novo e-mail
    */
    public void setEmail(String email){
        this.email = email;
    }

    
    
    /** 
    * Muda a senha do usuário
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param nova senha.
    */
    public void setSenha(String senha){
        this.senha = senha;
    }

    
    /** 
    * Verifica se o usuário logado é o mesmo atra´ves do objeto usuário. 
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @param Objeto usuário.
    * @return verdadeiro ou falso.
    */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object u){
        try{
            return (id == ((Usuario) u).getId());
        }
        catch(ClassCastException e){
            return false;
        }
    }

    
    /** 
    * Calcula o valor da função Hash
    * 
    * @author Pedro Augusto
    * @version 3.0
    * @since 3.0
    * @return valor calculado da função hash.
    */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        return hash;
    }

}
