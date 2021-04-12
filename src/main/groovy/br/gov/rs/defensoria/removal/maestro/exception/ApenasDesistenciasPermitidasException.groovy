package br.gov.rs.defensoria.removal.maestro.exception

class ApenasDesistenciasPermitidasException extends RuntimeException {

    ApenasDesistenciasPermitidasException() {
        super('Apenas desistências estão permitidas.')
    }
}
