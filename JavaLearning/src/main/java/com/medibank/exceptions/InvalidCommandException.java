package com.medibank.exceptions;

/**
 * Created by NARESHM on 3/06/2015.
 */
public class InvalidCommandException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCommandException(String reason) {
        super(reason);
    }
}
