package com.thiago.desafiovotacao.testdata;

import com.thiago.desafiovotacao.model.enums.TipoVoto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConstantes {

    public static final Long ID_ASSOCIADO = 1L;
    public static final Long ID_ASSOCIADO_2 = 2L;
    public static final String NOME_ASSOCIADO = "Pedro Henrique";
    public static final String NOME_ASSOCIADO_2 = "Gustavo";


    public static final Long ID_PAUTA = 1L;
    public static final String TITULO_PAUTA = "Proteção aos animais";
    public static final String DESCRICAO_PAUTA = "Iniciativa para aprovar a lei de castração de animais de rua";
    public static final String DATA_CRIACAO_PADRAO = "2025-07-07";

    public static final Long ID_SESSAO_VOTACAO = 1L;
    public static final Long ID_SESSAO_VOTACAO_INEXISTENTE = 99L;
    public static final String MSG_ERRO_SAVE_SESSAO = "erro ao salvar sessão";
    public static final Long DURACAO_PADRAO_MINUTOS = 5L;

    public static final Long ID_SESSAO_EXISTENTE = 1L;
    public static final Long ID_SESSAO_INEXISTENTE = 999L;

    public static final Long ID_ASSOCIADO_EXISTENTE = 2L;
    public static final Long ID_ASSOCIADO_INEXISTENTE = 888L;

    public static final Long ID_VOTO_EXISTENTE = 1L;
    public static final Long ID_VOTO_INEXISTENTE = 777L;

    public static final TipoVoto VOTO_SIM = TipoVoto.SIM;
    public static final TipoVoto VOTO_NAO = TipoVoto.NAO;

    public static final String MSG_ERRO_MAPEAR = "erro de mapeamento";
    public static final String MSG_ERRO_SAVE = "erro no save";

}
