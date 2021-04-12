package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.api.CandidatoTelaInicial
import br.gov.rs.defensoria.removal.api.EditalTelaInicial
import br.gov.rs.defensoria.removal.api.ObjTelaInicial
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.modo.ModoEditalManager
import br.gov.rs.defensoria.removal.repository.CandidatosRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.ServidoresRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity

@Component
class ObjTelaInicialService {

    @Autowired
    ServidoresRepository servidoresRepository

    @Autowired
    CandidatosRepository candidatosRepository

    @Autowired
    EditaisRepository editaisRepository

    @Autowired
    ModoEditalManager modoEditalManager

    ObjTelaInicial getObjTelaInicial(int matricula, CharSequence perfil, String nome) {
        String nomeCompleto = nome

        List<EditalTelaInicial> editais = new ArrayList<EditalTelaInicial>()

        List<EditalTelaInicial> editaisSuperUser = new ArrayList<EditalTelaInicial>()

        List<EditalTelaInicial> editaisOutrosPapeis = new ArrayList<EditalTelaInicial>()

        List<EditaisEntity> todosOsEditais = editaisRepository.findAll()
        todosOsEditais.sort{it.aberturaEdital}
        todosOsEditais = todosOsEditais.reverse()

        LocalDateTime dataBase = LocalDateTime.now()

        todosOsEditais.each {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy")

            String dataAbertura = fmt.print(it.aberturaEdital)
            String dataEncerramento = fmt.print(it.encerramentoEdital)

            fmt = DateTimeFormat.forPattern("HH:mm")

            String horaAbertura = fmt.print(it.aberturaEdital)
            String horaEncerramento = fmt.print(it.encerramentoEdital)

            List<ModoEdital> modos = modoEditalManager.getModosAtivos(it.idEdital)

            EditalTelaInicial eti = new EditalTelaInicial(idEdital: it.idEdital,
            descricaoEdital: it.descricaoEdital,
            editalBloqueado: it.bloqueado,
            dataAbertura: dataAbertura,
            horaAbertura: horaAbertura,
            dataEncerramento: dataEncerramento,
            horaEncerramento: horaEncerramento,
            emailEnvio: it.emailEnvio,
            emailResposta: it.emailResposta,
            emailAssinatura: it.assinaturaEmail,
            modos: modos)

            editaisSuperUser.add(eti)
        }
        editais = editaisSuperUser

        ServidoresEntity servidor = servidoresRepository.findByMatricula(matricula)

        if (servidor != null && servidor.nomeCompleto != null) {
            nomeCompleto = servidor.nomeCompleto
        }

        if (perfil.contains('CANDIDATO')) {

            List<CandidatosEntity> candidatos = candidatosRepository.findByServidor(servidor)

            editaisSuperUser.each {
                CandidatosEntity candidato = candidatos.find { c ->
                    c.edital.idEdital == it.idEdital
                }
                if (candidato != null) {
                    nomeCompleto = servidor.nomeCompleto

                    String dataHora = it.dataEncerramento + it.horaEncerramento
                    DateTimeFormatter formatter = DateTimeFormat.forPattern('dd/MM/yyyyHH:mm')
                    LocalDateTime encerramento = formatter.parseLocalDateTime(dataHora)

                    if (encerramento > dataBase) {
                        int idLotacao = candidato.lotacao != null ? candidato.lotacao.idUnidade : 0

                        CandidatoTelaInicial cti = new CandidatoTelaInicial(idCandidato: candidato.idCandidato,
                        antiguidade: candidato.antiguidade,
                        idLotacao: idLotacao,
                        descricaoLotacao: candidato.lotacao?.descricao)

                        it.candidato = cti

                        editaisOutrosPapeis.add(it)
                    }
                }
            }
            editais = editaisOutrosPapeis
        }

        ObjTelaInicial oti = new ObjTelaInicial(perfil: perfil,
        matricula: matricula,
        nomeCompleto: nomeCompleto,
        editais: editais)

        return oti
    }
}
