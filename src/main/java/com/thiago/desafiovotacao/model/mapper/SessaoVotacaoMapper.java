package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessaoVotacaoMapper {

    SessaoVotacaoDto sessaoVotacaoParaSessaoDto(SessaoVotacao entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "statusVotacao", ignore = true)
    @Mapping(target = "dataDeCriacao", ignore = true)
    @Mapping(target = "dataDeTermino", ignore = true)
    @Mapping(target = "votos", ignore = true)
    SessaoVotacao sessaodtoParaSessaoVotacao(CriacaoSessaoVotacaoDto dto);
}


