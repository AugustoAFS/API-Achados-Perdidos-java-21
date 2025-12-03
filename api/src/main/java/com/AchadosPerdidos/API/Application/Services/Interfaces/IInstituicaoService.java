package com.AchadosPerdidos.API.Application.Services.Interfaces;

import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoListDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoCreateDTO;
import com.AchadosPerdidos.API.Application.DTOs.Instituicao.InstituicaoUpdateDTO;

public interface IInstituicaoService {
    InstituicaoListDTO getAllInstituicoes();
    InstituicaoDTO getInstituicaoById(int id);
    InstituicaoListDTO getActiveInstituicoes();
    InstituicaoDTO createInstituicao(InstituicaoDTO instituicaoDTO);
    InstituicaoDTO updateInstituicao(int id, InstituicaoDTO instituicaoDTO);
    boolean deleteInstituicao(int id);
}
