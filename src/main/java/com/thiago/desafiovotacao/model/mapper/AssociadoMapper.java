package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    AssociadoDto associadoParaAssociadoDto (Associado dto);

    @Mapping(target = "id", ignore = true)
    Associado associadoDtoParaAssociado(AssociadoDto entidade);
}
