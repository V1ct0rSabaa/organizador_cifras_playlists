package com.estudo.spring.gerenciador_cifras.config;

import com.estudo.spring.gerenciador_cifras.domain.exception.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<Map<String, String>> notFound(ResourceNotFoundException e) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage())); }
    @ExceptionHandler(DomainException.class)
    ResponseEntity<Map<String, String>> business(DomainException e) { return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage())); }
}
