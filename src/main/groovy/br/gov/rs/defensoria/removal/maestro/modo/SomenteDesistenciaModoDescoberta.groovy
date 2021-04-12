package br.gov.rs.defensoria.removal.maestro.modo

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.ModoDataAtivacao
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.helper.ModoDescobertaDataHelper

@Service
class SomenteDesistenciaModoDescoberta implements ModoEditalDescoberta {

    @Autowired
    ModoDescobertaDataHelper modoDescobertaDataHelper

    @Override
    boolean isModoAtivo(ModoDataAtivacao modoDataAtivacao) {

        return LocalDateTime.now() >= modoDescobertaDataHelper.getData(modoDataAtivacao.dataApenasDesistenciasPermitidas)
    }

    @Override
    ModoEdital getModo() {

        return ModoEdital.SOMENTE_DESISTENCIAS
    }

}
