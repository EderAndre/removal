package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.api.ObjConstroiPedido
import br.gov.rs.defensoria.removal.api.UnidadeConstroiPedido
import br.gov.rs.defensoria.removal.repository.*
import br.gov.rs.defensoria.removal.repository.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.joda.time.LocalDateTime.now

@Component
class ObjConstroiPedidoService {

    @Autowired
    CandidatosRepository candidatosRepository

    @Autowired
    EditaisRepository editaisRepository

    @Autowired
    UltimasCandidaturasRepository ultimasCandidaturasRepository

    @Autowired
    VagasEditalRepository vagasEditalRepository

    @Autowired
    LocaisDisponiveisRepository locaisDisponiveisRepository

    ObjConstroiPedido getObjConstroiPedido(int idEdital, Integer idCandidato) {

        EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)

        List<UnidadeConstroiPedido> vagas = new ArrayList<UnidadeConstroiPedido>()

        List<LocaisDisponiveisEntity> locaisDisponiveis = getLocaisDisponiveis(idEdital)

        if (idCandidato != null) {
            CandidatosEntity candidato = candidatosRepository.findByIdCandidato(idCandidato)
            VagasEditalEntity vagaLotacao = vagasEditalRepository.findByEditalAndUnidade(edital, candidato.lotacao)
            locaisDisponiveis.remove(locaisDisponiveis.find {
                it.idVagaEdital == vagaLotacao?.idVagaEdital
            })

            List<UltimasCandidaturasEntity> candidaturas = ultimasCandidaturasRepository.getUltimasCandidaturas(idEdital, idCandidato)
            candidaturas.each {
                if(it.idVagaEdital){
                    LocaisDisponiveisEntity local = locaisDisponiveis.find { ld->
                        ld.idVagaEdital == it.idVagaEdital
                    }

                    int tipoVaga = getCorVaga(locaisDisponiveis, it.idVagaEdital)

                    UnidadeConstroiPedido ucp = new UnidadeConstroiPedido(id: it.idVagaEdital,
                    ordem: it.ordemCandidatura,
                    nomeExibicao: local.descricao,
                    escolhida: true,
                    tipoVaga: tipoVaga)

                    vagas.add(ucp)

                    locaisDisponiveis.remove(local)
                }
            }
        }

        locaisDisponiveis.each {

            int tipoVaga = getCorVaga(locaisDisponiveis, it.idVagaEdital)

            UnidadeConstroiPedido ucp = new UnidadeConstroiPedido(id: it.idVagaEdital,
            nomeExibicao: it.descricao,
            ordemVaga: it.ordemVaga,
            tipoVaga: tipoVaga)

            vagas.add(ucp)
        }

        ObjConstroiPedido ocp = new ObjConstroiPedido(idEdital: idEdital,
        idCandidato: idCandidato,
        vagas: vagas)
        return ocp
    }

    private List<LocaisDisponiveisEntity> getLocaisDisponiveis(int editalId) {
        String referenceDate = now().toString()

        return locaisDisponiveisRepository.getSucessiveis(editalId)
                .plus(locaisDisponiveisRepository.getVagadas(editalId, referenceDate))
                .plus(locaisDisponiveisRepository.getNaoSucessiveis(editalId, referenceDate))
    }

    private int getCorVaga(List<LocaisDisponiveisEntity> locaisDisponiveis, int idVaga) {

        String status = locaisDisponiveis.find {it.idVagaEdital == idVaga}?.status
        int cod = 0
        if (status.equals('Vagas')) {
            cod = 1
        } else if (status.equals('NaoSucesAtualmente')) {
            cod = 2
        }

        return cod
    }
}
