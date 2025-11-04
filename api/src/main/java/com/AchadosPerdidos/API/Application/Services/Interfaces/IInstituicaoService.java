package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoUpdateDTO;

public interface IInstituicaoService {
    InstituicaoListDTO getAllInstituicoes();
    InstituicaoDTO getInstituicaoById(int id);
    InstituicaoDTO createInstituicao(InstituicaoDTO instituicaoDTO);
    InstituicaoDTO updateInstituicao(int id, InstituicaoDTO instituicaoDTO);
    boolean deleteInstituicao(int id);
    InstituicaoListDTO getActiveInstituicoes();
    InstituicaoListDTO getInstituicoesByType(String tipoInstituicao);
    InstituicaoDTO createInstituicaoFromDTO(InstituicaoCreateDTO createDTO);
    InstituicaoDTO updateInstituicaoFromDTO(int id, InstituicaoUpdateDTO updateDTO);
}
