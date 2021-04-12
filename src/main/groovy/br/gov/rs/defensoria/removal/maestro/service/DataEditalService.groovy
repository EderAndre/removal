package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.maestro.exception.DataApenasDesistenciasIncoerenteException
import br.gov.rs.defensoria.removal.maestro.util.TimeUtil

@Service
class DataEditalService {

    @Autowired
    TimeUtil timeUtil

    LocalDateTime getApenasDesistenciasPermitidas(ObjInfosEdital infosEdital) {
        LocalDateTime apenasDesistenciasPermitidas

        if (isValidaDataApenasDesistencias(infosEdital)) {
            apenasDesistenciasPermitidas = timeUtil.convertPickerAngularJS(infosEdital.dataApenasDesistenciasPermitidas)
        }

        return apenasDesistenciasPermitidas
    }

    private boolean isValidaDataApenasDesistencias(ObjInfosEdital infosEdital) {
        if (isDataAnalisavel(infosEdital.dataApenasDesistenciasPermitidas)) {
            if (isDataIncoerente(infosEdital)) {
                throw new DataApenasDesistenciasIncoerenteException()
            }

            return true
        }

        return false
    }

    private boolean isDataAnalisavel(String dataApenasDesistenciasPermitidas) {
        boolean isUndefined = dataApenasDesistenciasPermitidas.equals('undefined')
        boolean isNulaString = dataApenasDesistenciasPermitidas.equals('null')
        boolean isNula = dataApenasDesistenciasPermitidas == null

        return !(isUndefined || isNulaString || isNula)
    }

    private boolean isDataIncoerente(ObjInfosEdital infosEdital) {
        LocalDateTime abertura = timeUtil.convertPickerAngularJS(infosEdital.aberturaEdital)
        LocalDateTime encerramento = timeUtil.convertPickerAngularJS(infosEdital.encerramentoEdital)
        LocalDateTime apenasDesistenciasPermitidas = timeUtil.convertPickerAngularJS(infosEdital.dataApenasDesistenciasPermitidas)

        return apenasDesistenciasPermitidas <= abertura || apenasDesistenciasPermitidas >= encerramento
    }
}
