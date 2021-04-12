package br.gov.rs.defensoria.removal.web.exception

class RequestComputeFechamentoException extends RuntimeException {

    RequestComputeFechamentoException() {
        super('Há um fechamento em andamento. Aguarde.')
    }
}
