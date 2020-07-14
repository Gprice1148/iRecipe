package com.gordon.iRecipe.exception;

public class iRecipeException extends RuntimeException {

    public iRecipeException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public iRecipeException(String exMessage) {
        super(exMessage);

    }
}
