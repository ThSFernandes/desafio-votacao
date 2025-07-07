package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import com.thiago.desafiovotacao.cpf.external.ValidadorDeCpfClient;
import com.thiago.desafiovotacao.exception.CpfUnableToVoteException;
import com.thiago.desafiovotacao.model.mapper.CpfMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.thiago.desafiovotacao.testdata.TestConstantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CpfServiceTest {

    private ValidadorDeCpfClient client;
    private CpfMapper mapper;
    private CpfService service;

    @BeforeEach
    void setUp() {
        client = mock(ValidadorDeCpfClient.class);
        mapper = mock(CpfMapper.class);
        service = new CpfService(client, mapper);
    }

    @Test
    @DisplayName("deve lançar CpfUnableToVoteException quando client lança exceção")
    void deveLancarQuandoClientLancaErro() {
        when(client.validarVoto(CPF_VALIDO))
                .thenThrow(new CpfUnableToVoteException(MSG_ERRO_MAPEAR));

        assertThrows(CpfUnableToVoteException.class,
                () -> service.verificaPermissaoDeVoto(CPF_VALIDO));

        verify(client).validarVoto(CPF_VALIDO);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("deve lançar CpfUnableToVoteException quando client retorna UNABLE_TO_VOTE")
    void deveLancarQuandoClientRetornaUnable() {
        when(client.validarVoto(CPF_INVALIDO)).thenReturn(ValidaVoto.UNABLE_TO_VOTE);

        assertThrows(CpfUnableToVoteException.class,
                () -> service.verificaPermissaoDeVoto(CPF_INVALIDO));

        verify(client).validarVoto(CPF_INVALIDO);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("deve lançar CpfUnableToVoteException quando mapper lança exceção")
    void deveLancarQuandoMapperLancaErro() {
        when(client.validarVoto(CPF_VALIDO)).thenReturn(ValidaVoto.ABLE_TO_VOTE);
        when(mapper.toEntity(ValidaVoto.ABLE_TO_VOTE))
                .thenThrow(new CpfUnableToVoteException(MSG_ERRO_MAPEAR));

        assertThrows(CpfUnableToVoteException.class,
                () -> service.verificaPermissaoDeVoto(CPF_VALIDO));

        verify(client).validarVoto(CPF_VALIDO);
        verify(mapper).toEntity(ValidaVoto.ABLE_TO_VOTE);
    }

    @Test
    @DisplayName("deve retornar ValidadorCpfDto quando tudo válido")
    void deveRetornarDtoQuandoTudoValido() {
        when(client.validarVoto(CPF_VALIDO)).thenReturn(ValidaVoto.ABLE_TO_VOTE);
        ValidadorCpfDto dto = new ValidadorCpfDto(ValidaVoto.ABLE_TO_VOTE);
        when(mapper.toEntity(ValidaVoto.ABLE_TO_VOTE)).thenReturn(dto);

        ValidadorCpfDto resultado = service.verificaPermissaoDeVoto(CPF_VALIDO);

        assertSame(dto, resultado);
        assertEquals(ValidaVoto.ABLE_TO_VOTE, resultado.getStatusValidacao());
        verify(client).validarVoto(CPF_VALIDO);
        verify(mapper).toEntity(ValidaVoto.ABLE_TO_VOTE);
    }

    @Test
    @DisplayName("deve lançar CpfUnableToVoteException quando CPF inválido")
    void deveLancarQuandoCpfInvalido() {
        when(client.validarVoto(CPF_INVALIDO)).thenReturn(ValidaVoto.UNABLE_TO_VOTE);

        assertThrows(CpfUnableToVoteException.class,
                () -> service.verificaPermissaoDeVoto(CPF_INVALIDO));

        verify(client).validarVoto(CPF_INVALIDO);
        verifyNoInteractions(mapper);
    }
}
