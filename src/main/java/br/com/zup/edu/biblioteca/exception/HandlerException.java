package br.com.zup.edu.biblioteca.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class HandlerException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        MensagemDeErro messages = new MensagemDeErro();
        e.getBindingResult().getFieldErrors().forEach(messages::adicionarErrorPeloFieldError);
        return ResponseEntity.badRequest().body(messages);
    }
	
}
 