package com.AchadosPerdidos.API.Domain.Repository.Interfaces;

import com.AchadosPerdidos.API.Domain.Entity.Instituicoes;
import java.util.List;

/**
 * Interface do Repository para Instituições de Ensino
 * Define operações de persistência para entidade Instituicoes
 * Ex: IFPR, UTFPR, UFPR, etc.
 */
public interface IInstituicaoRepository {
    List<Instituicoes> findActive();
    List<Instituicoes> findByType(String tipoInstituicao);
}
