package com.safa.cabezon_backend.Exception;

public class EliminarNoExistenteException extends RuntimeException{
    public EliminarNoExistenteException(String mensage){
        super(mensage);
    }
}
