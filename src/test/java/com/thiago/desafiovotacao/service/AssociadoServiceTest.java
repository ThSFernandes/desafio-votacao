package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.mapper.AssociadoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.thiago.desafiovotacao.testdata.TestConstantes.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class AssociadoServiceTest {

    private AssociadoRepository repo;
    private AssociadoMapper mapper;
    private AssociadoService service;

    @BeforeEach
    void setUp() {
        repo = mock(AssociadoRepository.class);
        mapper = mock(AssociadoMapper.class);
        service = new AssociadoService(repo, mapper);
    }

    @Test
    @DisplayName("deve criar associado com sucesso e retornar DTO")
    void deveCriarAssociadoComSucesso() {
        Associado entidade = new Associado();
        entidade.setId(ID_ASSOCIADO_2);
        entidade.setNome(NOME_ASSOCIADO_2);

        when(mapper.toEntity(isA(AssociadoDto.class)))
                .thenReturn(entidade);
        when(repo.save(entidade))
                .thenReturn(entidade);

        AssociadoDto esperado = new AssociadoDto();
        esperado.setId(ID_ASSOCIADO_2);
        esperado.setNome(NOME_ASSOCIADO_2);
        when(mapper.toDto(entidade))
                .thenReturn(esperado);

        AssociadoDto resultado = service.criarAssociado(new AssociadoDto());

        assertSame(esperado, resultado);
        verify(mapper).toEntity(isA(AssociadoDto.class));
        verify(repo).save(entidade);
        verify(mapper).toDto(entidade);
    }

    @Test
    @DisplayName("deve lançar exceção quando mapper falhar ao criar associado")
    void deveLancarExceptionQuandoMapperFalha() {
        when(mapper.toEntity(isA(AssociadoDto.class)))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_MAPEAR));

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarAssociado(new AssociadoDto()));

        verify(mapper).toEntity(isA(AssociadoDto.class));
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("deve lançar exceção quando salvar associado falhar")
    void deveLancarExceptionQuandoSaveFalha() {
        Associado entidade = new Associado();
        when(mapper.toEntity(isA(AssociadoDto.class)))
                .thenReturn(entidade);
        when(repo.save(entidade))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_SAVE));

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarAssociado(new AssociadoDto()));

        verify(mapper).toEntity(isA(AssociadoDto.class));
        verify(repo).save(entidade);
    }

    @Test
    @DisplayName("deve buscar associado com sucesso quando existir no banco")
    void deveBuscarAssociadoComSucesso() {
        Associado entidade = new Associado();
        entidade.setId(ID_ASSOCIADO);
        entidade.setNome(NOME_ASSOCIADO);

        when(repo.findById(eq(ID_ASSOCIADO)))
                .thenReturn(Optional.of(entidade));

        AssociadoDto esperado = new AssociadoDto();
        esperado.setId(ID_ASSOCIADO);
        esperado.setNome(NOME_ASSOCIADO);
        when(mapper.toDto(entidade))
                .thenReturn(esperado);

        AssociadoDto resultado = service.buscarAssociado(ID_ASSOCIADO);

        assertSame(esperado, resultado);
        verify(repo).findById(eq(ID_ASSOCIADO));
        verify(mapper).toDto(entidade);
    }

    @Test
    @DisplayName("deve lançar exceção quando buscar associado não existente")
    void deveLancarExceptionQuandoBuscarNaoEncontrado() {
        when(repo.findById(eq(ID_ASSOCIADO)))
                .thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarAssociado(ID_ASSOCIADO));

        verify(repo).findById(eq(ID_ASSOCIADO));
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("deve deletar associado com sucesso quando existir no banco")
    void deveDeletarAssociadoComSucesso() {
        when(repo.existsById(eq(ID_ASSOCIADO)))
                .thenReturn(true);
        doNothing().when(repo).deleteById(eq(ID_ASSOCIADO));

        service.deletarAssociado(ID_ASSOCIADO);

        verify(repo).existsById(eq(ID_ASSOCIADO));
        verify(repo).deleteById(eq(ID_ASSOCIADO));
    }

    @Test
    @DisplayName("deve lançar exceção quando deletar associado inexistente")
    void deveLancarExceptionQuandoDeleteNaoExiste() {
        when(repo.existsById(eq(ID_ASSOCIADO)))
                .thenReturn(false);

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.deletarAssociado(ID_ASSOCIADO));

        verify(repo).existsById(eq(ID_ASSOCIADO));
        verify(repo, never()).deleteById(anyLong());
    }
}
