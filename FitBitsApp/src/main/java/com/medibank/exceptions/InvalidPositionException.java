package com.medibank.exceptions;

/**
 * Throw this exception when the position on the soccer pitch to move/entered is invalid.
 * @author nareshm
 */
public class InvalidPositionException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidPositionException(String reason) {
        super(reason);
    }
}