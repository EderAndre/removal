package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import br.gov.rs.defensoria.removal.api.Candidatura
import br.gov.rs.defensoria.removal.api.Vaga
import br.gov.rs.defensoria.removal.maestro.mail.model.CandidaturaReciboMailModel
import br.gov.rs.defensoria.removal.repository.CandidatosRepository
import br.gov.rs.defensoria.removal.repository.CandidaturasRepository
import br.gov.rs.defensoria.removal.repository.RestricoesCandidaturasRepository
import br.gov.rs.defensoria.removal.repository.SolicitacoesCandidatoRepository
import br.gov.rs.defensoria.removal.repository.VagasEditalRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.VagasEditalEntity

@Component
class CandidaturaService {
    @Autowired
    EmailServiceImpl emailService

    @Autowired
    CandidaturasRepository candidaturasRepository

    @Autowired
    VagasEditalRepository vagasEditalRepository

    @Autowired
    CandidatosRepository candidatosRepository

    @Autowired
    RestricoesCandidaturasRepository restricoesCandidaturasRepository

    @Autowired
    SolicitacoesCandidatoRepository solicitacoesCandidatoRepository

    boolean novaCandidatura(Candidatura candidatura) {
        LocalDateTime dataSolicitacao = LocalDateTime.now()
        salvaCandidatura(candidatura, dataSolicitacao)
        enviaReciboCandidatura(candidatura, dataSolicitacao)
    }

    @Transactional
    boolean salvaCandidatura(Candidatura candidatura, LocalDateTime dataSolicitacao = LocalDateTime.now()) {
        def candidato = candidatosRepository.findByIdCandidato(candidatura.candidato)
        if(candidato==null || candidato.edital.idEdital != candidatura.edital) {
            throw new RuntimeException("Não foi encontrado candidato (${candidatura.candidato}) no edital (${candidatura.edital}).")
        }
        if(candidatura.locais.size() == 0) {
            persisteCandidatura(null, candidato, 0, dataSolicitacao)
        } else {
            boolean ordemVagaEmSequencia = true

            if(candidaturaPossuiVagasRepetidas(candidatura.locais)) {
                throw new RuntimeException("A candidatura do candidato (${candidatura.candidato}) possui vagas repetidas.")
            }

            candidatura.locais.sort{it.ordem}.eachWithIndex { vaga, idx ->
                def vagaEdital = vagasEditalRepository.findByIdVagaEdital(vaga.id)
                if(vagaEdital==null) {
                    throw new RuntimeException("Não foi encontrada vaga de edital (${vaga.id}) no edital (${candidatura.edital}).")
                }

                //verifica se o candidato não está se candidatando para sua lotação atual
                if (candidato.lotacao != null) {
                    if (vagaEdital.unidade.idUnidade == candidato.lotacao.idUnidade) {
                        throw new RuntimeException("O candidato (${candidatura.candidato}) não pode se candidatar a sua lotação atual (vaga:${vaga.id}, unidade: ${vagaEdital.unidade.idUnidade})")
                    }
                }

                //verifica se a vaga não está impedida para o candidato
                if(vagaImpedidaParaCandidato(vaga.id, candidatura.candidato)) {
                    throw new RuntimeException("A vaga (${vaga.id}) está impedida para o candidato (${candidatura.candidato}.")
                }

                if(vaga.ordem != idx+1) {
                    ordemVagaEmSequencia = false
                }
                persisteCandidatura(vagaEdital, candidato, vaga.ordem, dataSolicitacao)
            }
            if(ordemVagaEmSequencia == false) {
                throw new RuntimeException("A ordem das vagas da candidatura não é sequencial.")
            }
        }
        return true
    }

    boolean vagaImpedidaParaCandidato(int vagaId, int candidatoId) {
        Integer unidadeId = vagasEditalRepository.findByIdVagaEdital(vagaId).unidade.idUnidade
        return restricoesCandidaturasRepository.countByCandidatoIdCandidatoAndUnidadeIdUnidade(candidatoId, unidadeId) > 0
    }

    private boolean candidaturaPossuiVagasRepetidas(List<Vaga> locais) {
        return locais.groupBy({it.id}).size() != locais.size()
    }

    private CandidaturasEntity persisteCandidatura(VagasEditalEntity vaga, CandidatosEntity candidato, int ordem, LocalDateTime dataSolicitacao) {
        return candidaturasRepository.save(new CandidaturasEntity(vagaEdital: vaga, candidato: candidato, ordemCandidatura: ordem, data: dataSolicitacao))
    }

    private void enviaReciboCandidatura(Candidatura candidatura, LocalDateTime dataSolicitacao) {
        def candidato = candidatosRepository.findByIdCandidato(candidatura.candidato)
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")
        String data = fmt.print(dataSolicitacao)
        int solicitacoes=solicitacoesCandidatoRepository.getSolicitacoes(candidatura.candidato).size()
        List<String> vagas = []
        candidatura.locais.each {
            vagas.add(vagasEditalRepository.findByIdVagaEdital(it.id).unidade.descricao)
        }
        vagas.size==0?vagas.add("<div style='border:1px dashed #ff0000;padding:10px;'>Você não definiu nenhuma opção para concorrer à remoção</div>"):false
        def modelo = new CandidaturaReciboMailModel(
                email: candidato.servidor.emailServidor,
                matricula: candidato.servidor.matricula,
                nome: candidato.servidor.nomeCompleto,
                nroSolicitacao: solicitacoes,
                vagas: vagas,
                dataSolicitacao: data,
                dataConfirmacao: data, //FIXME: Talvez seja necessário remover este campo, visto que nesta versão do sistema a solicitação não é mais realizada em 2 etapas.
                )

        emailService.enviarReciboCandidatura(modelo, candidatura.edital)
    }
}
