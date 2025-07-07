package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ItemVotoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.entity.Voto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VotoMapper {

    @Mapping(target = "idAssociado", source = "associado.id")
    @Mapping(target = "idSessaoVotacao", source = "sessaoVotacao.id")
    VotacaoDto toDto(Voto entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "associado", ignore = true)
    @Mapping(target = "sessaoVotacao", ignore = true)
    Voto toEntity(CriarVotacaoDto dto);

    @Mapping(target = "tipoVoto", source = "tipoVoto")
    @Mapping(target = "nomePauta", source = "sessaoVotacao.pauta.titulo")
    @Mapping(target = "dataVoto", source = "dataCriacao")
    ItemVotoDto toItemDto(Voto voto);

    List<ItemVotoDto> toItemDtoList(List<Voto> votos);
}

