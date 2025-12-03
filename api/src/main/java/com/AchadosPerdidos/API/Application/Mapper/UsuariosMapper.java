package com.AchadosPerdidos.API.Application.Mapper;

import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.UsuariosUpdateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.AlunoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Usuario.ServidorCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Campus.CampusDTO;
import com.AchadosPerdidos.API.Application.DTOs.Fotos.FotosDTO;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IUsuarioCampusService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotoUsuarioService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.ICampusService;
import com.AchadosPerdidos.API.Application.Services.Interfaces.IFotosService;
import com.AchadosPerdidos.API.Domain.Entity.Endereco;
import com.AchadosPerdidos.API.Domain.Entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuariosMapper {

    @Autowired
    private IUsuarioCampusService usuarioCampusService;

    @Autowired
    private IFotoUsuarioService fotoUsuarioService;

    @Autowired
    private ICampusService campusService;

    @Autowired
    private IFotosService fotosService;

    public UsuariosDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        Integer enderecoId = usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null;

        // Buscar campus do usuário
        List<CampusDTO> campusList = null;
        try {
            // Buscar todas as associações e filtrar por usuarioId
            var allUsuarioCampus = usuarioCampusService.getAllUsuarioCampus();
            if (allUsuarioCampus != null && allUsuarioCampus.getUsuarioCampus() != null) {
                campusList = allUsuarioCampus.getUsuarioCampus().stream()
                    .filter(uc -> uc.getUsuarioId() != null && uc.getUsuarioId().equals(usuario.getId()))
                    .filter(uc -> uc.getFlgInativo() == null || !uc.getFlgInativo())
                    .map(uc -> {
                        try {
                            // Buscar campus completo pelo ID
                            var campusDTO = campusService.getCampusById(uc.getCampusId());
                            return campusDTO;
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(c -> c != null)
                    .collect(Collectors.toList());
            }
        } catch (Exception e) {
            // Log silencioso, não falha se não conseguir buscar campus
        }

        // Buscar foto de perfil do usuário
        // Nota: findByUsuarioId não existe mais na interface IFotoUsuarioService
        // getFotoById também não existe mais na interface IFotosService
        FotosDTO fotoPerfil = null;
        try {
            // Buscar todas as fotos de usuário e filtrar por usuarioId
            var allFotosUsuario = fotoUsuarioService.getAllFotosUsuario();
            if (allFotosUsuario != null && allFotosUsuario.getFotoUsuarios() != null) {
                var fotoAtiva = allFotosUsuario.getFotoUsuarios().stream()
                    .filter(fu -> fu.getUsuarioId() != null && fu.getUsuarioId().equals(usuario.getId()))
                    .filter(fu -> fu.getFlgInativo() == null || !fu.getFlgInativo())
                    .findFirst();
                // Nota: getFotoById não existe mais na interface IFotosService
                // TODO: Implementar busca de foto se necessário
            }
        } catch (Exception e) {
            // Log silencioso, não falha se não conseguir buscar foto
        }

        return new UsuariosDTO(
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            usuario.getMatricula(),
            enderecoId,
            campusList,
            fotoPerfil,
            usuario.getDta_Criacao(),
            usuario.getFlg_Inativo(),
            usuario.getDta_Remocao()
        );
    }

    public Usuario toEntity(UsuariosDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setMatricula(dto.getMatricula());
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        usuario.setDta_Criacao(dto.getDtaCriacao());
        usuario.setFlg_Inativo(dto.getFlgInativo());
        usuario.setDta_Remocao(dto.getDtaRemocao());
        
        return usuario;
    }

    public Usuario fromCreateDTO(UsuariosCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        // Nota: O hash da senha é feito no serviço, não no mapper
        usuario.setMatricula(dto.getMatricula());
        usuario.setNumero_telefone(dto.getNumeroTelefone());
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        
        return usuario;
    }

    /**
     * Converte AlunoCreateDTO para entidade Usuario
     * Aluno não tem CPF obrigatório, apenas matrícula
     */
    public Usuario fromAlunoCreateDTO(AlunoCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(null); // Aluno não precisa de CPF
        usuario.setEmail(dto.getEmail());
        usuario.setMatricula(dto.getMatricula());
        usuario.setNumero_telefone(dto.getNumeroTelefone());
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        
        return usuario;
    }

    /**
     * Converte ServidorCreateDTO para entidade Usuario
     * Servidor não tem matrícula, apenas CPF
     */
    public Usuario fromServidorCreateDTO(ServidorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(dto.getNomeCompleto());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setMatricula(null); // Servidor não tem matrícula
        usuario.setNumero_telefone(dto.getNumeroTelefone());
        usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        
        return usuario;
    }

    public void updateFromDTO(Usuario usuario, UsuariosUpdateDTO dto) {
        if (usuario == null || dto == null) {
            return;
        }
        
        if (dto.getNomeCompleto() != null) {
            usuario.setNomeCompleto(dto.getNomeCompleto());
        }
        if (dto.getCpf() != null) {
            usuario.setCpf(dto.getCpf());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getMatricula() != null) {
            usuario.setMatricula(dto.getMatricula());
        }
        if (dto.getEnderecoId() != null) {
            usuario.setEndereco_id(toEndereco(dto.getEnderecoId()));
        }
        if (dto.getFlgInativo() != null) {
            usuario.setFlg_Inativo(dto.getFlgInativo());
        }
    }

    public UsuariosCreateDTO toCreateDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        return new UsuariosCreateDTO(
            usuario.getNomeCompleto(),
            usuario.getCpf(),
            usuario.getEmail(),
            null, // senha não é retornada
            usuario.getMatricula(),
            usuario.getNumero_telefone(),
            usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null,
            null // campusId não está na entidade Usuario, está na tabela usuario_campus
        );
    }

    public UsuariosUpdateDTO toUpdateDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuariosUpdateDTO dto = new UsuariosUpdateDTO();
        dto.setNomeCompleto(usuario.getNomeCompleto());
        dto.setCpf(usuario.getCpf());
        dto.setEmail(usuario.getEmail());
        dto.setMatricula(usuario.getMatricula());
        dto.setEnderecoId(usuario.getEndereco_id() != null ? usuario.getEndereco_id().getId() : null);
        dto.setFlgInativo(usuario.getFlg_Inativo());
        // campusId e fotoId não são preenchidos aqui, pois vêm das tabelas relacionadas
        return dto;
    }

    public UsuariosListDTO toListDTO(List<Usuario> usuarios) {
        if (usuarios == null) {
            return null;
        }
        
        List<UsuariosDTO> dtoList = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        
        return new UsuariosListDTO(dtoList, dtoList.size());
    }

    public UsuariosListDTO toListDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuariosDTO dto = toDTO(usuario);
        List<UsuariosDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        
        return new UsuariosListDTO(dtoList, 1);
    }

    private Endereco toEndereco(Integer id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}