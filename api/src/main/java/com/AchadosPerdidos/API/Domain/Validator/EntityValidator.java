package com.AchadosPerdidos.API.Domain.Validator;

import com.AchadosPerdidos.API.Exeptions.BusinessException;

import java.util.regex.Pattern;

public class EntityValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$");
    
    public static void validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new BusinessException("O campo '" + fieldName + "' é obrigatório");
        }
    }
    
    public static void validateRequired(Integer value, String fieldName) {
        if (value == null) {
            throw new BusinessException("O campo '" + fieldName + "' é obrigatório");
        }
    }
    
    public static void validateMinLength(String value, int minLength, String fieldName) {
        if (value != null && value.length() < minLength) {
            throw new BusinessException(
                String.format("O campo '%s' deve ter no mínimo %d caracteres", fieldName, minLength)
            );
        }
    }
    
    public static void validateMaxLength(String value, int maxLength, String fieldName) {
        if (value != null && value.length() > maxLength) {
            throw new BusinessException(
                String.format("O campo '%s' deve ter no máximo %d caracteres", fieldName, maxLength)
            );
        }
    }
    
    public static void validateEmail(String email) {
        if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BusinessException("Email inválido");
        }
    }
    
    public static void validateCpfFormat(String cpf) {
        if (cpf != null && !CPF_PATTERN.matcher(cpf).matches()) {
            throw new BusinessException("CPF deve conter exatamente 11 dígitos numéricos");
        }
    }
    
    public static void validateCnpjFormat(String cnpj) {
        if (cnpj != null && !CNPJ_PATTERN.matcher(cnpj).matches()) {
            throw new BusinessException("CNPJ deve conter exatamente 14 dígitos numéricos");
        }
    }
    
    public static void validatePhoneFormat(String phone) {
        if (phone != null && !PHONE_PATTERN.matcher(phone).matches()) {
            throw new BusinessException("Telefone deve conter entre 10 e 11 dígitos numéricos");
        }
    }
    
    public static void validatePositive(Integer value, String fieldName) {
        if (value != null && value <= 0) {
            throw new BusinessException(
                String.format("O campo '%s' deve ser um valor positivo", fieldName)
            );
        }
    }
    
    public static boolean isValidCpf(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }
        
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;
        
        if (firstDigit != (cpf.charAt(9) - '0')) {
            return false;
        }
        
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;
        
        return secondDigit == (cpf.charAt(10) - '0');
    }
    
    public static boolean isValidCnpj(String cnpj) {
        if (cnpj == null || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            return false;
        }
        
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }
        
        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weights1[i];
        }
        int firstDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        
        if (firstDigit != (cnpj.charAt(12) - '0')) {
            return false;
        }
        
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += (cnpj.charAt(i) - '0') * weights2[i];
        }
        int secondDigit = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        
        return secondDigit == (cnpj.charAt(13) - '0');
    }
}
