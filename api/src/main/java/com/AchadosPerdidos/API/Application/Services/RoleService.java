package com.AchadosPerdidos.API.Application.Services;

import com.AchadosPerdidos.API.Application.DTOs.Role.RoleDTO;
import com.AchadosPerdidos.API.Application.Mapper.RoleModelMapper;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IRoleService;
import com.AchadosPerdidos.API.Domain.Entity.Role;
import com.AchadosPerdidos.API.Domain.Repository.RoleRepository;
import com.AchadosPerdidos.API.Exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleModelMapper roleModelMapper;

    @Override
    @Cacheable(value = "roles", key = "'all'")
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleModelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "roles", key = "#id")
    public RoleDTO getRoleById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID da role deve ser válido");
        }
        
        Role role = roleRepository.findById(id);
        if (role == null) {
            throw new ResourceNotFoundException("Role", "ID", id);
        }
        return roleModelMapper.toDTO(role);
    }

    @Override
    @Cacheable(value = "roles", key = "'nome_' + #nome")
    public RoleDTO getRoleByNome(String nome) {
        if (!StringUtils.hasText(nome)) {
            throw new IllegalArgumentException("Nome da role não pode ser vazio");
        }
        
        Role role = roleRepository.findByNome(nome);
        if (role == null) {
            throw new ResourceNotFoundException("Role", "nome", nome);
        }
        return roleModelMapper.toDTO(role);
    }

    @Override
    @Cacheable(value = "roles", key = "'active'")
    public List<RoleDTO> getActiveRoles() {
        List<Role> activeRoles = roleRepository.findActive();
        return activeRoles.stream()
                .map(roleModelMapper::toDTO)
                .collect(Collectors.toList());
    }
}
