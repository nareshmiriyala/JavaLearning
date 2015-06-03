package com.medibank.exceptions;

/**
 * Created by nareshm on 3/06/2015.
 */
public class InvalidInputException extends  Exception {
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String reason) {
        super(reason);
    }
}
