package com.AchadosPerdidos.API.Domain.Entity;

public enum TipoItem {
    PERDIDO,
    ACHADO,
    DOADO;

    public static TipoItem fromString(String value) {
        for (TipoItem tipo : values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de item inválido: " + value);
    }
}


