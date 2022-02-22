/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg374_instruction_encoder;

/**
 *
 * @author omnic
 */
public class InstException extends Exception {

    /**
     * Creates a new instance of <code>InstException</code> without detail
     * message.
     */
    public InstException() {
    }

    /**
     * Constructs an instance of <code>InstException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public InstException(String msg) {
        super(msg);
    }
}
