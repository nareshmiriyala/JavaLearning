package com.medibank.exceptions;

/**
 * Throws this exception when the coach enters wrong command.
 * @author nareshm
 */
public class InvalidCommandException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCommandException(String reason) {
        super(reason);
    }
}
