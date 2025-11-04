package com.AchadosPerdidos.API.Exeptions;

public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Construtor que recebe apenas a mensagem de erro
     * 
     * @param message Mensagem explicando o que não foi encontrado
     * 
     * Exemplo:
     * throw new ResourceNotFoundException("Local de item não encontrado com ID: 5");
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Construtor que recebe mensagem e a causa raiz do erro
     * 
     * @param message Mensagem explicando o que não foi encontrado
     * @param cause A exceção original que causou este erro
     * 
     * Exemplo:
     * throw new ResourceNotFoundException("Erro ao buscar item", exception);
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Construtor auxiliar para criar mensagens formatadas
     * Útil quando você quer padronizar as mensagens
     * 
     * @param resourceName Nome do recurso (ex: "Local de Item", "Usuário")
     * @param fieldName Nome do campo usado na busca (ex: "ID", "email")
     * @param fieldValue Valor que foi buscado (ex: 5, "user@email.com")
     * 
     * Exemplo:
     * throw new ResourceNotFoundException("Local de Item", "ID", 5);
     * // Resultado: "Local de Item não encontrado com ID: 5"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s não encontrado com %s: %s", resourceName, fieldName, fieldValue));                  
    }
}
