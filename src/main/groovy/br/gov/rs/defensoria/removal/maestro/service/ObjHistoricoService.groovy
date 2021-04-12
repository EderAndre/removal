package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.api.EscolhaHistorico
import br.gov.rs.defensoria.removal.api.ObjHistorico
import br.gov.rs.defensoria.removal.api.RegistroHistorico
import br.gov.rs.defensoria.removal.repository.CandidatosRepository
import br.gov.rs.defensoria.removal.repository.CandidaturasRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.UnidadesRepository
import br.gov.rs.defensoria.removal.repository.VagasEditalRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.UnidadesEntity
import br.gov.rs.defensoria.removal.repository.entity.VagasEditalEntity

@Component
class ObjHistoricoService {

    @Autowired
    CandidatosRepository candidatosRepository

    @Autowired
    CandidaturasRepository candidaturasRepository

    @Autowired
    VagasEditalRepository vagasEditalRepository

    @Autowired
    EditaisRepository editaisRepository

    @Autowired
    UnidadesRepository unidadesRepository

    ObjHistorico getHistorico(idEdital, idCandidato) {

        CandidatosEntity candidato = candidatosRepository.findByIdCandidato(idCandidato)

        List<CandidaturasEntity> candidaturas = candidaturasRepository.findByCandidato(candidato)

        EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)

        List<VagasEditalEntity> vagas = vagasEditalRepository.findByEdital(edital)

        List<UnidadesEntity> unidades = unidadesRepository.findAll()

        List<LocalDateTime> datas = new ArrayList<LocalDateTime>()
        candidaturas.each {
            if(it.idCandidatura>1000)
            datas.add(it.data)
        }

        List<RegistroHistorico> registros = new ArrayList<RegistroHistorico>()

        datas = datas.unique()
        datas = datas.reverse()
        datas.each {
            DateTimeFormatter fmt = DateTimeFormat.forPattern('dd/MM/yyyy - HH:mm')
            String data = fmt.print(it)

            List<EscolhaHistorico> opcoes = new ArrayList<EscolhaHistorico>()
            candidaturas.findAll { cand ->    cand.data == it}.each { op ->

                VagasEditalEntity vaga = vagas.find { vaga -> vaga.idVagaEdital == op.vagaEdital?.idVagaEdital}
                UnidadesEntity unidade = unidades.find { u -> u == vaga?.unidade}
                String descricao = unidade?.descricao

                opcoes.add(new EscolhaHistorico(ordem: op.ordemCandidatura,
                descricao: descricao))
            }

            RegistroHistorico registro = new RegistroHistorico(data: data,
            opcoes: opcoes)

            registros.add(registro)
        }

        return new ObjHistorico(registros: registros)
    }
}
