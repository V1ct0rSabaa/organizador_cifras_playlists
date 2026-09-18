package com.estudo.spring.gerenciador_cifras.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String id) {
        super(resource + " não encontrado: " + id);
    }
}
