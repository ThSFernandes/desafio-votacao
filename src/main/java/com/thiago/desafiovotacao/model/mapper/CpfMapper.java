package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CpfMapper {

    @Mapping(target = "statusValidacao", expression = "java(validaVoto)")
    ValidadorCpfDto toEntity(ValidaVoto validaVoto);
}
