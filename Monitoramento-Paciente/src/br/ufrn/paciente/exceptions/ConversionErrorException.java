/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.paciente.exceptions;

/**
 *
 * @author jorge
 */
public class ConversionErrorException extends Exception {

    /**
     * Creates a new instance of
     * <code>ConversionErrorException</code> without detail message.
     */
    public ConversionErrorException() {
    }

    /**
     * Constructs an instance of
     * <code>ConversionErrorException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConversionErrorException(String msg) {
        super(msg);
    }
}
