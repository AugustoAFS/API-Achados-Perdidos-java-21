package com.AchadosPerdidos.API.Domain.Validator;

/**
 * Utilitário simples para validações de entidades de domínio.
 * Atualmente contém validações básicas de CNPJ utilizadas nas Services.
 */
public final class EntityValidator {

    private static final String CNPJ_REGEX = "\\d{14}";

    private EntityValidator() {
    }

    /**
     * Valida se o CNPJ possui o formato correto (14 dígitos numéricos).
     *
     * @param cnpj valor a ser validado
     * @throws IllegalArgumentException caso o CNPJ seja nulo ou não possua 14 dígitos
     */
    public static void validateCnpjFormat(String cnpj) {
        if (cnpj == null || !cnpj.matches(CNPJ_REGEX)) {
            throw new IllegalArgumentException("CNPJ deve conter exatamente 14 dígitos numéricos");
        }
    }

    /**
     * Validação simples de CNPJ apenas para evitar casos óbvios inválidos.
     * Não executa toda a regra da Receita, mas impede sequências com todos os dígitos iguais.
     *
     * @param cnpj valor a ser validado (já deve estar no formato correto)
     * @return true se passar na validação básica
     */
    public static boolean isValidCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches(CNPJ_REGEX)) {
            return false;
        }

        char firstChar = cnpj.charAt(0);
        boolean allEquals = cnpj.chars().allMatch(ch -> ch == firstChar);
        return !allEquals;
    }
}

