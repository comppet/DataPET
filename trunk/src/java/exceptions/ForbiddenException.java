/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Tiago Peres
 */
public class ForbiddenException extends RuntimeException {
    @Override
    public String getMessage(){
        return "Error 403: Forbidden. You don't have permission to execute this action.";
    }
}
