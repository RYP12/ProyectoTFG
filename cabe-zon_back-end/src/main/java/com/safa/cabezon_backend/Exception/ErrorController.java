package com.safa.cabezon_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> controlarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> controladorErrores(MethodArgumentNotValidException exception){

        Map<String, String> mapaErrores = new HashMap<>();
        for(FieldError error : exception.getBindingResult().getFieldErrors()){
            mapaErrores.put(error.getField(), error.getDefaultMessage());}
        return new ResponseEntity<>(mapaErrores, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EliminarNoExistenteException.class)
    public ResponseEntity<Map<String, String>> controlExcepcionEliminarnoExistente(EliminarNoExistenteException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
