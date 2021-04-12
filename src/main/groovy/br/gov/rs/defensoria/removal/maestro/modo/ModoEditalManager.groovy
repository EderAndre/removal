package br.gov.rs.defensoria.removal.maestro.modo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.ModoDataAtivacao
import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.service.InfosEditalService

@Service
class ModoEditalManager {
    private List<ModoEditalDescoberta> modosEdital = new ArrayList<>()

    @Autowired
    InfosEditalService infosEditalService

    @Autowired
    SomenteDesistenciaModoDescoberta somenteDesistenciaModoDescoberta

    @Autowired
    LimitePrimeiraCandidaturaModoDescoberta limitePrimeiraCandidaturaModoDescoberta

    List<ModoEdital> getModosAtivos(Integer idEdital) {
        modosEdital.add(somenteDesistenciaModoDescoberta)
        modosEdital.add(limitePrimeiraCandidaturaModoDescoberta)

        ObjInfosEdital info = infosEditalService.getInfosEdital(idEdital)
        ModoDataAtivacao modoDataAtivacao = new ModoDataAtivacao(limitePrimeiraCandidatura: info.limitePrimeiraCandidatura,
        dataApenasDesistenciasPermitidas: info.dataApenasDesistenciasPermitidas)
        List<ModoEdital> modosAtivos = new ArrayList<>()

        try {
            modosEdital.each { modo ->
                if (modo?.isModoAtivo(modoDataAtivacao)) {
                    modosAtivos.add(modo.getModo())
                }
            }
        } catch (ConcurrentModificationException cme) {
        }

        return modosAtivos
    }

    boolean hasModoAtivo(Integer idEdital, ModoEdital modoEdital) {
        List<ModoEdital> modos = getModosAtivos(idEdital)

        return modos.contains(modoEdital)
    }
}
