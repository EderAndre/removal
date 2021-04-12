package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.maestro.util.TimeUtil
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity

@Service
class InfosEditalService {

    @Autowired
    EditaisRepository editaisRepository

    @Autowired
    TimeUtil timeUtil

    @Autowired
    DataEditalService dataEditalService

    ObjInfosEdital getInfosEdital(int idEdital) {

        EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)

        String dataAbertura = edital.aberturaEdital.toString()
        String dataEncerramento = edital.encerramentoEdital.toString()
        String limitePrimeiraCandidatura = edital.limitePrimeiraCandidatura?.toString()
        String dataApenasDesistenciasPermitidas = edital.apenasDesistenciasPermitidas?.toString()

        return new ObjInfosEdital(idEdital: edital.idEdital,
        aberturaEdital: dataAbertura,
        descricaoEdital: edital.descricaoEdital,
        encerramentoEdital: dataEncerramento,
        limitePrimeiraCandidatura: limitePrimeiraCandidatura,
        dataApenasDesistenciasPermitidas: dataApenasDesistenciasPermitidas,
        bloqueado: edital.bloqueado,
        tipoEdital: edital.tipoEdital.id,
        assinaturaEmailEdital: edital.assinaturaEmail,
        emailRespostaEdital: edital.emailResposta,
        emailEnvioEdital: edital.emailEnvio)
    }

    void setEdital(ObjInfosEdital oie) {

        LocalDateTime apenasDesistenciasPermitidas = dataEditalService.getApenasDesistenciasPermitidas(oie)

        EditaisEntity edital = editaisRepository.findByIdEdital(oie.idEdital)

        edital.aberturaEdital = timeUtil.convertPickerAngularJS(oie.aberturaEdital)
        edital.descricaoEdital = oie.descricaoEdital
        edital.encerramentoEdital = timeUtil.convertPickerAngularJS(oie.encerramentoEdital)
        edital.limitePrimeiraCandidatura = timeUtil.convertPickerAngularJS(oie.limitePrimeiraCandidatura)
        edital.apenasDesistenciasPermitidas = apenasDesistenciasPermitidas
        edital.bloqueado = oie.bloqueado
        edital.assinaturaEmail = oie.assinaturaEmailEdital
        edital.emailResposta = oie.emailRespostaEdital
        edital.emailEnvio = oie.emailEnvioEdital

        editaisRepository.save(edital)
    }
}
