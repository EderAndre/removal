package br.gov.rs.defensoria.removal.maestro.exception

class ConvertPickerAngularJSDataNulaException extends RuntimeException {

    ConvertPickerAngularJSDataNulaException() {
        super('Não é possível converter datas nulas.')
    }
}
