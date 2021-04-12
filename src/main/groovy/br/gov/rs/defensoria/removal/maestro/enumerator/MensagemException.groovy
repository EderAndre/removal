package br.gov.rs.defensoria.removal.maestro.enumerator

enum MensagemException {
    DATA_SOMENTE_DESISTENCIAS('A data que restringe as candidaturas a somente desistências precisa ser posterior à abertura do edital.')

    final String mensagem

    MensagemException(String mensagem) {
        this.mensagem = mensagem
    }
}
