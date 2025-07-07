package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.mapper.PautaMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static com.thiago.desafiovotacao.testdata.TestConstantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PautaServiceTest {

    private PautaRepository repo;
    private PautaMapper mapper;
    private PautaService service;

    @BeforeEach
    void setUp() {
        repo = mock(PautaRepository.class);
        mapper = mock(PautaMapper.class);
        service = new PautaService(repo, mapper);
    }

    @Test
    @DisplayName("deve criar pauta com sucesso")
    void deveCriarPautaComSucesso() {
        Pauta entidade = new Pauta();
        entidade.setId(ID_PAUTA);
        entidade.setTitulo(TITULO_PAUTA);
        entidade.setDescricao(DESCRICAO_PAUTA);
        entidade.setDataCriacao(LocalDate
                .parse(DATA_CRIACAO_PADRAO)
                .atStartOfDay());

        when(mapper.toEntity(isA(PautaDto.class))).thenReturn(entidade);
        when(repo.save(entidade)).thenReturn(entidade);

        PautaDto esperado = new PautaDto();
        when(mapper.toDto(entidade)).thenReturn(esperado);

        PautaDto resultado = service.criarPauta(new PautaDto());

        assertSame(esperado, resultado);
        verify(mapper).toEntity(any(PautaDto.class));
        verify(repo).save(entidade);
        verify(mapper).toDto(entidade);
    }

    @Test
    @DisplayName("deve lançar exceção quando mapper falhar ao criar pauta")
    void deveLancarExceptionQuandoMapperFalhaEmCriacao() {
        when(mapper.toEntity(any(PautaDto.class)))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_MAPEAR));

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarPauta(new PautaDto()));

        verify(mapper).toEntity(any(PautaDto.class));
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("deve lançar exceção quando salvar pauta falhar")
    void deveLancarExceptionQuandoSaveFalhaEmCriacao() {
        Pauta entidade = new Pauta();
        when(mapper.toEntity(any(PautaDto.class))).thenReturn(entidade);
        when(repo.save(entidade))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_SAVE));

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarPauta(new PautaDto()));

        verify(mapper).toEntity(any(PautaDto.class));
        verify(repo).save(entidade);
    }

    @Test
    @DisplayName("deve buscar pauta com sucesso quando existir no banco")
    void deveBuscarPautaComSucesso() {
        Pauta entidade = new Pauta();
        entidade.setId(ID_PAUTA);
        when(repo.findById(eq(ID_PAUTA))).thenReturn(Optional.of(entidade));

        PautaDto esperado = new PautaDto();
        when(mapper.toDto(entidade)).thenReturn(esperado);

        PautaDto resultado = service.buscarPautaPeloId(ID_PAUTA);

        assertSame(esperado, resultado);
        verify(repo).findById(eq(ID_PAUTA));
        verify(mapper).toDto(entidade);
    }

    @Test
    @DisplayName("deve lançar exceção quando buscar pauta inexistente")
    void deveLancarExceptionQuandoBuscarNaoEncontrada() {
        when(repo.findById(eq(ID_PAUTA))).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarPautaPeloId(ID_PAUTA));

        verify(repo).findById(eq(ID_PAUTA));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("deve atualizar pauta com sucesso e retornar DTO")
    void deveAtualizarPautaComSucesso() {
        Pauta existente = new Pauta();
        existente.setId(ID_PAUTA);
        existente.setTitulo("titulo antigo");
        existente.setDescricao("descrição antiga");
        when(repo.findById(eq(ID_PAUTA)))
                .thenReturn(Optional.of(existente));

        Pauta salvo = new Pauta();
        salvo.setId(ID_PAUTA);
        when(repo.save(existente)).thenReturn(salvo);

        PautaDto esperado = new PautaDto();
        when(mapper.toDto(salvo)).thenReturn(esperado);

        PautaDto resultado = service.atualizarPauta(ID_PAUTA, new PautaDto());
        assertSame(esperado, resultado);
        verify(repo).findById(eq(ID_PAUTA));
        verify(repo).save(existente);
        verify(mapper).toDto(salvo);
    }


    @Test
    @DisplayName("deve lançar exceção quando atualizar pauta inexistente")
    void deveLancarExceptionQuandoAtualizarNaoEncontrada() {
        when(repo.findById(eq(ID_PAUTA))).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.atualizarPauta(ID_PAUTA, new PautaDto()));

        verify(repo).findById(eq(ID_PAUTA));
    }

    @Test
    @DisplayName("deve lançar exceção quando save falhar na atualização")
    void deveLancarExceptionQuandoSaveFalhaEmAtualizacao() {
        Pauta existente = new Pauta();
        existente.setId(ID_PAUTA);
        when(repo.findById(eq(ID_PAUTA)))
                .thenReturn(Optional.of(existente));

        when(repo.save(existente))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_SAVE));

        RecursoNaoEncontradoException ex = assertThrows(
                RecursoNaoEncontradoException.class,
                () -> service.atualizarPauta(ID_PAUTA, new PautaDto())
        );

        assertEquals(MSG_ERRO_SAVE, ex.getMessage());
        verify(repo).findById(eq(ID_PAUTA));
        verify(repo).save(existente);
    }


    @Test
    @DisplayName("deve deletar pauta com sucesso quando existir no banco")
    void deveDeletarPautaComSucesso() {
        when(repo.existsById(eq(ID_PAUTA))).thenReturn(true);
        doNothing().when(repo).deleteById(eq(ID_PAUTA));

        service.deletarPauta(ID_PAUTA);

        verify(repo).existsById(eq(ID_PAUTA));
        verify(repo).deleteById(eq(ID_PAUTA));
    }

    @Test
    @DisplayName("deve lançar exceção quando deletar pauta inexistente")
    void deveLancarExceptionQuandoDeleteNaoExiste() {
        when(repo.existsById(eq(ID_PAUTA))).thenReturn(false);

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.deletarPauta(ID_PAUTA));

        verify(repo).existsById(eq(ID_PAUTA));
        verify(repo, never()).deleteById(anyLong());
    }
}
