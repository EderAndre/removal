package br.gov.rs.defensoria.removal.maestro.exception

import br.gov.rs.defensoria.removal.maestro.enumerator.MensagemException

class DataApenasDesistenciasPermitidasException extends RuntimeException {

    DataApenasDesistenciasPermitidasException() {
        super(MensagemException.DATA_SOMENTE_DESISTENCIAS.mensagem)
    }
}
