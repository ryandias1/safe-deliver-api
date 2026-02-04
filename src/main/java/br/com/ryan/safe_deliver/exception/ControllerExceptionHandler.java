package br.com.ryan.safe_deliver.exception;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;

@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ex.getMessage(), "403"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage(), "400"));
    }

    @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundExcpetion(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage(), "404"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage) 
            .collect(Collectors.toList());
    
        String errorMessage = String.join(", ", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(errorMessage, "400"));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ExceptionResponse> handleServletException(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), "500"));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOException(IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), "500"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Ocorreu um erro interno no servidor.", "500"));
    }
}
