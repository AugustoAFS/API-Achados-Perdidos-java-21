package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Endereco.EnderecoUpdateDTO;

import java.util.List;

public interface IEnderecoService {
    List<EnderecoDTO> getAllEnderecos();
    EnderecoDTO getEnderecoById(Integer id);
    EnderecoDTO createEndereco(EnderecoCreateDTO createDTO);
    EnderecoDTO updateEndereco(Integer id, EnderecoUpdateDTO updateDTO);
    EnderecoDTO deleteEndereco(Integer id);
    List<EnderecoDTO> getEnderecosByCidade(Integer cidadeId);
    List<EnderecoDTO> getActiveEnderecos();
}

