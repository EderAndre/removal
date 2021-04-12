package br.gov.rs.defensoria.removal.maestro.exception

class DataApenasDesistenciasIncoerenteException extends RuntimeException {

    DataApenasDesistenciasIncoerenteException() {
        super('A data de início do período de somente desistências deve estar entre as datas de abertura e encerramento do edital.')
    }
}
