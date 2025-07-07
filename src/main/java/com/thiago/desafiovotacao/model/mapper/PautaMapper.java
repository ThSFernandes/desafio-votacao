package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    PautaDto toDto(Pauta entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Pauta toEntity(PautaDto dto);
}
