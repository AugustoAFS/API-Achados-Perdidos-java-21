package com.AchadosPerdidos.API.Domain.Enum;

public enum Status_Item {
    ATIVO,        // Item ainda não foi resolvido
    DEVOLVIDO,    // Item ACHADO foi devolvido ao dono
    RESGATADO,    // Item PERDIDO foi encontrado pelo dono
    CANCELADO     // Item foi cancelado/removido
}


