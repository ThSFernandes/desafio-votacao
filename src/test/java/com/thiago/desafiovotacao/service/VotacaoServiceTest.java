package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.exception.SessaoException;
import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotoDetalhadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.entity.Voto;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import com.thiago.desafiovotacao.model.mapper.VotoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static com.thiago.desafiovotacao.testdata.TestConstantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VotacaoServiceTest {

    private SessaoVotacaoRepository sessaoRepo;
    private AssociadoRepository associadoRepo;
    private VotoRepository votoRepo;
    private VotoMapper votoMapper;
    private SessaoVotacaoService sessaoService;
    private VotacaoService service;

    @BeforeEach
    void setUp() {
        sessaoRepo = mock(SessaoVotacaoRepository.class);
        associadoRepo = mock(AssociadoRepository.class);
        votoRepo = mock(VotoRepository.class);
        votoMapper = mock(VotoMapper.class);
        sessaoService = mock(SessaoVotacaoService.class);
        service = new VotacaoService(votoRepo, sessaoRepo, associadoRepo, votoMapper, sessaoService);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando sessão não existe")
    void deveLancarQuandoSessaoNaoEncontrada() {
        when(sessaoRepo.findById(ID_SESSAO_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarVoto(ID_SESSAO_INEXISTENTE, ID_ASSOCIADO_EXISTENTE, new CriarVotacaoDto(VOTO_SIM)));

        verify(sessaoRepo).findById(ID_SESSAO_INEXISTENTE);
        verifyNoInteractions(associadoRepo, votoRepo, votoMapper, sessaoService);
    }

    @Test
    @DisplayName("deve lançar SessaoException quando sessão não está em andamento")
    void deveLancarQuandoSessaoEncerrada() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.APROVADO);
        when(sessaoRepo.findById(ID_SESSAO_EXISTENTE)).thenReturn(Optional.of(sessao));

        assertThrows(SessaoException.class,
                () -> service.criarVoto(ID_SESSAO_EXISTENTE, ID_ASSOCIADO_EXISTENTE, new CriarVotacaoDto(VOTO_SIM)));

        verify(sessaoRepo).findById(ID_SESSAO_EXISTENTE);
        verifyNoInteractions(associadoRepo, votoRepo, votoMapper, sessaoService);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando associado não existe")
    void deveLancarQuandoAssociadoNaoEncontrado() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        sessao.setDataDeTermino(LocalDateTime.now().plusMinutes(1));
        when(sessaoRepo.findById(ID_SESSAO_EXISTENTE)).thenReturn(Optional.of(sessao));
        when(associadoRepo.findById(ID_ASSOCIADO_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarVoto(ID_SESSAO_EXISTENTE, ID_ASSOCIADO_INEXISTENTE, new CriarVotacaoDto(VOTO_SIM)));

        verify(associadoRepo).findById(ID_ASSOCIADO_INEXISTENTE);
        verifyNoInteractions(votoRepo, votoMapper, sessaoService);
    }

    @Test
    @DisplayName("deve criar voto e retornar VotacaoDto quando tudo válido")
    void deveCriarVotoComSucesso() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        sessao.setDataDeCriacao(LocalDateTime.now().minusMinutes(1));
        sessao.setDataDeTermino(LocalDateTime.now().plusMinutes(1));
        when(sessaoRepo.findById(ID_SESSAO_EXISTENTE)).thenReturn(Optional.of(sessao));

        Associado associado = new Associado();
        associado.setId(ID_ASSOCIADO_EXISTENTE);
        when(associadoRepo.findById(ID_ASSOCIADO_EXISTENTE)).thenReturn(Optional.of(associado));

        CriarVotacaoDto dtoIn = new CriarVotacaoDto(VOTO_SIM);
        Voto votoEntity = new Voto();
        when(votoMapper.votacaoDtoParaVoto(dtoIn)).thenReturn(votoEntity);
        when(votoRepo.save(votoEntity)).thenReturn(votoEntity);

        VotacaoDto esperado = new VotacaoDto();
        when(votoMapper.votoParaVotacaoDto(votoEntity)).thenReturn(esperado);

        VotacaoDto resultado = service.criarVoto(ID_SESSAO_EXISTENTE, ID_ASSOCIADO_EXISTENTE, dtoIn);

        assertSame(esperado, resultado);
        verify(votoMapper).votacaoDtoParaVoto(dtoIn);
        verify(votoRepo).save(votoEntity);
        verify(votoMapper).votoParaVotacaoDto(votoEntity);
        verifyNoInteractions(sessaoService);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando voto não encontrado")
    void deveLancarQuandoVotoNaoEncontrado() {
        when(votoRepo.findById(ID_VOTO_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarVotoPorId(ID_VOTO_INEXISTENTE));

        verify(votoRepo).findById(ID_VOTO_INEXISTENTE);
        verifyNoInteractions(votoMapper);
    }

    @Test
    @DisplayName("deve retornar VotacaoDto quando voto existe")
    void deveBuscarVotoPorIdComSucesso() {
        Voto voto = new Voto();
        when(votoRepo.findById(ID_VOTO_EXISTENTE)).thenReturn(Optional.of(voto));

        VotacaoDto dto = new VotacaoDto();
        when(votoMapper.votoParaVotacaoDto(voto)).thenReturn(dto);

        VotacaoDto resultado = service.buscarVotoPorId(ID_VOTO_EXISTENTE);

        assertSame(dto, resultado);
        verify(votoMapper).votoParaVotacaoDto(voto);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando listar votos de associado inexistente")
    void deveLancarQuandoListarAssociadoNaoEncontrado() {
        when(associadoRepo.findById(ID_ASSOCIADO_INEXISTENTE)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.listarVotosDoAssociado(ID_ASSOCIADO_INEXISTENTE));

        verify(associadoRepo).findById(ID_ASSOCIADO_INEXISTENTE);
        verifyNoInteractions(votoRepo, votoMapper);
    }

    @Test
    @DisplayName("deve retornar VotoDetalhadoDto com lista de votos vazia")
    void deveListarVotosComSucesso() {
        Associado associado = new Associado();
        associado.setId(ID_ASSOCIADO_EXISTENTE);
        when(associadoRepo.findById(ID_ASSOCIADO_EXISTENTE)).thenReturn(Optional.of(associado));
        when(votoRepo.findByAssociado(associado)).thenReturn(Collections.emptyList());

        VotoDetalhadoDto resultado = service.listarVotosDoAssociado(ID_ASSOCIADO_EXISTENTE);

        assertEquals(ID_ASSOCIADO_EXISTENTE, resultado.getIdAssociado());
        assertTrue(resultado.getVotos().isEmpty());
    }

    @Test
    @DisplayName("deve criar voto com VOTO_NAO e retornar VotacaoDto")
    void deveCriarVotoComNao() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        sessao.setDataDeCriacao(LocalDateTime.now().minusMinutes(1));
        sessao.setDataDeTermino(LocalDateTime.now().plusMinutes(1));
        when(sessaoRepo.findById(ID_SESSAO_EXISTENTE)).thenReturn(Optional.of(sessao));

        Associado associado = new Associado();
        associado.setId(ID_ASSOCIADO_EXISTENTE);
        when(associadoRepo.findById(ID_ASSOCIADO_EXISTENTE)).thenReturn(Optional.of(associado));

        CriarVotacaoDto dtoIn = new CriarVotacaoDto(VOTO_NAO);
        Voto votoEntity = new Voto();
        when(votoMapper.votacaoDtoParaVoto(dtoIn)).thenReturn(votoEntity);
        when(votoRepo.save(votoEntity)).thenReturn(votoEntity);

        VotacaoDto esperado = new VotacaoDto();
        when(votoMapper.votoParaVotacaoDto(votoEntity)).thenReturn(esperado);

        VotacaoDto resultado = service.criarVoto(ID_SESSAO_EXISTENTE, ID_ASSOCIADO_EXISTENTE, dtoIn);

        assertSame(esperado, resultado);
        verify(votoMapper).votacaoDtoParaVoto(dtoIn);
        verify(votoRepo).save(votoEntity);
        verify(votoMapper).votoParaVotacaoDto(votoEntity);
    }

}
