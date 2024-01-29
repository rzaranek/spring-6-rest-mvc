package guru.springframework.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by robertZ on 2024-01-29.
 */
//@ControllerAdvice
public class ExceptionController {
//    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handlerNotFound() {
        System.out.println("In Exception Controller");
        return ResponseEntity.notFound().build();
    }
}
