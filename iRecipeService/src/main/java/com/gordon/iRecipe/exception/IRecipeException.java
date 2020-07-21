package com.gordon.iRecipe.exception;

public class IRecipeException extends RuntimeException {

    public IRecipeException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public IRecipeException(String exMessage) {
        super(exMessage);
    }

}
