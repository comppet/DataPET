package Model;

/**
 *
 * @author Tiago Peres
 * @version 3.0
 * @since 3.0
 */
public abstract class AtividadePublica extends Atividade{
    protected Natureza natureza;
    private String publicoAlvo;

    /**
     * Herda todos os atributos da classe atividades e instancia uma natureza
     * 
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     */
    AtividadePublica(){
        super();
        natureza = new Natureza();
    }

   /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return natureza das atividades a serem exibidas
     */
    public Natureza getNatureza() {
        return natureza;
    }

    /**
     * Muda a natureza das atividades a serem exibidas.
     * 
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param natureza das atividades a serem exibidas
     */
    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

     /**
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @return publico alvo das atividades a serem exibidas
     */
    public String getPublicoAlvo() {
        return publicoAlvo;
    }

     /**
     * Muda o publico alvo das atividades a serem exibidas.
     * 
     * @author Tiago Peres
     * @version 3.0
     * @since 3.0
     * @param publico alvo das atividades a serem exibidas
     */
    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}
