/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Tiago Peres
 */
public class PageNotFoundException extends RuntimeException {
    @Override
    public String getMessage(){
        return "Error 404: Page not found.";
    }
}
