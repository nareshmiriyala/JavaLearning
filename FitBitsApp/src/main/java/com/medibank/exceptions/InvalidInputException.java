package com.medibank.exceptions;

/**
 * Throw this exception when the console input is valid.
 * @author nareshm
 */
public class InvalidInputException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String reason) {
        super(reason);
    }
}
