package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.EditalStatus
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.modo.ModoEditalManager
import br.gov.rs.defensoria.removal.maestro.util.MsgEditalBloqueado
import br.gov.rs.defensoria.removal.repository.CandidaturasRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.ServidoresRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity

@Service
class StatusEditalService {

    @Autowired
    EditaisRepository editaisRepository

    @Autowired
    ServidoresRepository servidoresRepository

    @Autowired
    CandidaturasRepository candidaturasRepository

    @Autowired
    ModoEditalManager modoEditalManager

    EditalStatus getEditalStatus(Integer idEdital, int matricula) {

        EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)
        EditalStatus editalStatus = new EditalStatus()

        if (modoEditalManager.hasModoAtivo(idEdital, ModoEdital.LIMITE_PRIMEIRA_CANDIDATURA)) {
            ServidoresEntity servidor = servidoresRepository.findByMatricula(matricula)
            List<CandidatosEntity> candidatos = servidor.candidato
            CandidatosEntity candidato = candidatos.find {
                it.edital.idEdital == edital.idEdital
            }

            List<CandidaturasEntity> candidaturas = candidaturasRepository.findByCandidatoAndDataLessThanEqual(candidato, edital.limitePrimeiraCandidatura)
            if (candidaturas.size() == 0) {
                String msg = MsgEditalBloqueado.SEM_CANDIDATURA_ATE_DATA_LIMITE.getMsg()

                return new EditalStatus(bloqueado: true,
                motivoBloqueio: String.format(msg, idEdital))
            }
        }

        if (edital == null) {
            String msg = MsgEditalBloqueado.EDITAL_INEXISTENTE_PARA_O_CODIGO.getMsg()

            return new EditalStatus(bloqueado: true,
            motivoBloqueio: String.format(msg, idEdital))
        } else {
            if (edital.bloqueado) {
                String msg = MsgEditalBloqueado.EDITAL_BLOQUEADO_PELO_ADMINISTRADOR.getMsg()

                return new EditalStatus(bloqueado: true,
                motivoBloqueio: msg)
            }

            if (LocalDateTime.now() > edital.encerramentoEdital) {
                DateTimeFormatter fmt = DateTimeFormat.forPattern('dd/MM/yyyy - HH:mm')
                String enc = fmt.print(edital.encerramentoEdital)
                String msg = MsgEditalBloqueado.EDITAL_JA_ENCERRADO.getMsg()

                return new EditalStatus(bloqueado: true,
                motivoBloqueio: String.format(msg, enc))
            }

            if (LocalDateTime.now() < edital.aberturaEdital) {
                DateTimeFormatter fmt = DateTimeFormat.forPattern('dd/MM/yyyy - HH:mm')
                String ab = fmt.print(edital.aberturaEdital)
                String msg = MsgEditalBloqueado.EDITAL_AINDA_NAO_ABRIU.getMsg()

                return new EditalStatus(bloqueado: true,
                motivoBloqueio: String.format(msg, ab))
            }
        }

        return editalStatus
    }
}
