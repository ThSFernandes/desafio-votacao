package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.entity.Voto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    @Mapping(target = "idAssociado",     source = "associado.id")
    @Mapping(target = "idSessaoVotacao", source = "sessaoVotacao.id")
    VotacaoDto votoParaVotacaoDto(Voto entidade);

    @Mapping(target = "id",           ignore = true)
    @Mapping(target = "dataCriacao",  ignore = true)
    @Mapping(target = "associado",    ignore = true)
    @Mapping(target = "sessaoVotacao",ignore = true)
    Voto votacaoDtoParaVoto(CriarVotacaoDto dto);
}
