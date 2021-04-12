package br.gov.rs.defensoria.removal.maestro.exception

class VedadaMudancaOrdemException extends RuntimeException {

    VedadaMudancaOrdemException() {
        super('Mudanças na ordem de preferência estão vedadas.')
    }
}
