package br.gov.rs.defensoria.removal.api

import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital


class EditalTelaInicial {
    int idEdital
    String descricaoEdital
    boolean editalBloqueado
    String dataAbertura
    String horaAbertura
    String dataEncerramento
    String horaEncerramento
    String emailEnvio
    String emailResposta
    String emailAssinatura
    CandidatoTelaInicial candidato
    List<ModoEdital> modos
}
