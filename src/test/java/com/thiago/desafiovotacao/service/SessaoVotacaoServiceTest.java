package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import com.thiago.desafiovotacao.model.enums.TipoVoto;
import com.thiago.desafiovotacao.model.mapper.SessaoVotacaoMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.thiago.desafiovotacao.testdata.TestConstantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SessaoVotacaoServiceTest {

    private PautaRepository pautaRepo;
    private SessaoVotacaoMapper mapper;
    private SessaoVotacaoRepository sessaoRepo;
    private VotoRepository votoRepo;
    private SessaoVotacaoService service;

    @BeforeEach
    void setUp() {
        pautaRepo = mock(PautaRepository.class);
        mapper = mock(SessaoVotacaoMapper.class);
        sessaoRepo = mock(SessaoVotacaoRepository.class);
        votoRepo = mock(VotoRepository.class);
        service = new SessaoVotacaoService(sessaoRepo, pautaRepo, mapper, votoRepo);
    }

    @Test
    @DisplayName("deve criar sessão de votação com sucesso quando pauta existe")
    void deveCriarVotacaoComSucesso() {
        Pauta pauta = new Pauta();
        pauta.setId(ID_PAUTA);
        when(pautaRepo.findById(ID_PAUTA)).thenReturn(Optional.of(pauta));

        SessaoVotacao entidade = new SessaoVotacao();
        entidade.setPauta(pauta);
        entidade.setDataDeCriacao(LocalDate.now().atStartOfDay());
        entidade.setDataDeTermino(LocalDate.now().plusDays(1).atStartOfDay());
        when(mapper.toEntity(any())).thenReturn(entidade);
        when(sessaoRepo.save(entidade)).thenReturn(entidade);

        SessaoVotacaoDto dto = new SessaoVotacaoDto();
        when(mapper.toDto(entidade)).thenReturn(dto);

        SessaoVotacaoDto result = service.criarSessaoVotacao(ID_PAUTA, new CriacaoSessaoVotacaoDto());
        assertSame(dto, result);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando pauta não existe ao criar sessão")
    void deveLancarExcecaoQuandoPautaNaoEncontrada() {
        when(pautaRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarSessaoVotacao(ID_SESSAO_VOTACAO_INEXISTENTE, new CriacaoSessaoVotacaoDto()));
    }

    @Test
    @DisplayName("deve propagar exceção quando salvar sessão lança erro")
    void devePropagarExcecaoQuandoSaveFalha() {
        Pauta pauta = new Pauta();
        pauta.setId(ID_PAUTA);
        when(pautaRepo.findById(ID_PAUTA)).thenReturn(Optional.of(pauta));

        SessaoVotacao entidade = new SessaoVotacao();
        when(mapper.toEntity(any())).thenReturn(entidade);
        when(sessaoRepo.save(entidade))
                .thenThrow(new RecursoNaoEncontradoException(MSG_ERRO_SAVE_SESSAO));

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.criarSessaoVotacao(ID_PAUTA, new CriacaoSessaoVotacaoDto()));
    }

    @Test
    @DisplayName("deve buscar sessão detalhada com sucesso quando sessão existe")
    void deveBuscarSessaoDetalhadaComSucesso() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.APROVADO);
        when(sessaoRepo.findById(ID_SESSAO_VOTACAO)).thenReturn(Optional.of(sessao));
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(any(), any())).thenReturn(0L);

        SessaoVotacaoDto dto = new SessaoVotacaoDto();
        when(mapper.toDto(sessao)).thenReturn(dto);

        SessaoVotacaoDto result = service.buscarSessaoPorIdDetalhado(ID_SESSAO_VOTACAO);
        assertSame(dto, result);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando não encontra sessão detalhada")
    void deveLancarExcecaoQuandoSessaoDetalhadaNaoEncontrada() {
        when(sessaoRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarSessaoPorIdDetalhado(ID_SESSAO_VOTACAO_INEXISTENTE));
    }

    @Test
    @DisplayName("deve buscar resultado de sessão com sucesso quando sessão em andamento")
    void deveBuscarResultadoComSucesso() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setDataDeCriacao(LocalDateTime.now().minusMinutes(DURACAO_PADRAO_MINUTOS));
        sessao.setDataDeTermino(LocalDateTime.now().plusMinutes(DURACAO_PADRAO_MINUTOS));
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        when(sessaoRepo.findById(ID_SESSAO_VOTACAO)).thenReturn(Optional.of(sessao));
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(any(), eq(TipoVoto.SIM))).thenReturn(2L);
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(any(), eq(TipoVoto.NAO))).thenReturn(3L);
        when(sessaoRepo.save(any())).thenReturn(sessao);

        ResultadoSessaoDto dto = new ResultadoSessaoDto();
        when(mapper.toResultadoDto(any(), eq(2L), eq(3L), eq(5L), any()))
                .thenReturn(dto);

        ResultadoSessaoDto result = service.buscarSessaoPorIdParaResultado(ID_SESSAO_VOTACAO);
        assertSame(dto, result);
    }

    @Test
    @DisplayName("deve lançar RecursoNaoEncontradoException quando não encontra sessão para resultado")
    void deveLancarExcecaoQuandoResultadoNaoEncontrado() {
        when(sessaoRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.buscarSessaoPorIdParaResultado(ID_SESSAO_VOTACAO_INEXISTENTE));
    }


    @Test
    @DisplayName("deve expirar sessão e alterar para APROVADO quando SIM são maioria após término")
    void deveExpirarSessaoEAlterarParaAprovadoAposExpiracao() {

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        sessao.setDataDeCriacao(LocalDateTime.now().minusMinutes(10));
        sessao.setDataDeTermino(LocalDateTime.now().minusSeconds(1));

        when(votoRepo.countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.SIM)).thenReturn(7L);
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.NAO)).thenReturn(3L);
        when(sessaoRepo.save(sessao)).thenReturn(sessao);

        service.validarStatusDaSessao(sessao);

        assertEquals(StatusVotacao.APROVADO, sessao.getStatusVotacao());
        verify(votoRepo).countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.SIM);
        verify(votoRepo).countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.NAO);
        verify(sessaoRepo).save(sessao);
    }

    @Test
    @DisplayName("deve expirar sessão e alterar para REPROVADO quando NÃO são maioria após término")
    void deveExpirarSessaoEAlterarParaReprovadoAposExpiracao() {

        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.EM_ANDAMENTO);
        sessao.setDataDeCriacao(LocalDateTime.now().minusMinutes(10));
        sessao.setDataDeTermino(LocalDateTime.now().minusSeconds(1));

        when(votoRepo.countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.SIM)).thenReturn(3L);
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.NAO)).thenReturn(7L);
        when(sessaoRepo.save(sessao)).thenReturn(sessao);

        service.validarStatusDaSessao(sessao);

        assertEquals(StatusVotacao.REPROVADO, sessao.getStatusVotacao());
        verify(votoRepo).countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.SIM);
        verify(votoRepo).countBySessaoVotacaoAndTipoVoto(sessao, TipoVoto.NAO);
        verify(sessaoRepo).save(sessao);
    }

    @Test
    @DisplayName("deve manter status original quando já definido")
    void deveManterStatusQuandoJaDefinido() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.APROVADO);

        service.validarStatusDaSessao(sessao);
        assertEquals(StatusVotacao.APROVADO, sessao.getStatusVotacao());
    }

    @Test
    @DisplayName("deve alterar para EMPATE quando apurar resultado com votos empatados")
    void deveAlterarParaEmpateQuandoEmpateDeVotos() {
        SessaoVotacao sessao = new SessaoVotacao();
        sessao.setStatusVotacao(StatusVotacao.APROVADO);
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(any(), eq(TipoVoto.SIM))).thenReturn(3L);
        when(votoRepo.countBySessaoVotacaoAndTipoVoto(any(), eq(TipoVoto.NAO))).thenReturn(3L);
        when(sessaoRepo.save(sessao)).thenReturn(sessao);

        service.apurarResultado(sessao);
        assertEquals(StatusVotacao.EMPATE, sessao.getStatusVotacao());
    }
}
