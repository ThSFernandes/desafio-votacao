package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoDto toDto(Associado entidade);

    @Mapping(target = "id", ignore = true)
    Associado toEntity(AssociadoDto dto);
}
