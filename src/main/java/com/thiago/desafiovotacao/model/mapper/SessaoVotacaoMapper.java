package com.thiago.desafiovotacao.model.mapper;

import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = VotoMapper.class)
public interface SessaoVotacaoMapper {

    @Mapping(target = "idPauta", source = "pauta.id")
    SessaoVotacaoDto sessaoVotacaoParaSessaoDto(SessaoVotacao entidade);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pauta", ignore = true)
    @Mapping(target = "statusVotacao", ignore = true)
    @Mapping(target = "dataDeCriacao", ignore = true)
    @Mapping(target = "dataDeTermino", ignore = true)
    @Mapping(target = "votos", ignore = true)
    SessaoVotacao sessaodtoParaSessaoVotacao(CriacaoSessaoVotacaoDto dto);


    @Mapping(target = "id",              source = "sessao.id")
    @Mapping(target = "dataDeCriacao",   source = "sessao.dataDeCriacao")
    @Mapping(target = "dataDeTermino",   source = "sessao.dataDeTermino")
    @Mapping(target = "totalSim",        expression = "java(totalSim)")
    @Mapping(target = "totalNao",        expression = "java(totalNao)")
    @Mapping(target = "resultado",       expression = "java(resultado)")
    ResultadoSessaoDto toResultadoSessaoDto(
            SessaoVotacao sessao,
            long totalSim,
            long totalNao,
            long totalVotos,
            StatusVotacao resultado);
}



