package com.AchadosPerdidos.API.Application.DTOs.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListDTO {
    private List<RoleDTO> Roles;
}
