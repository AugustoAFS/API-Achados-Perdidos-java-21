package com.AchadosPerdidos.API.Exeptions;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ValidationException(String fieldName, String validation) {
        super(String.format("Validação falhou para o campo '%s': %s", fieldName, validation));
    }
}
