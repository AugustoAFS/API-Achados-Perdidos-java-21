package com.AchadosPerdidos.API.Exeptions;

/**
 * Exceção para erros de regras de negócio
 * Usada quando uma operação viola uma regra de negócio específica
 */
public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(String resourceName, String operation, String reason) {
        super(String.format("Não é possível %s %s: %s", operation, resourceName, reason));
    }
}

