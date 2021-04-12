package br.gov.rs.defensoria.removal.maestro

import br.gov.rs.defensoria.removal.report.presentation.OfficePresentation
import br.gov.rs.defensoria.removal.report.presentation.Pretension
import br.gov.rs.defensoria.removal.report.presentation.ResultRowWinnersWithPretensions
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions
import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.SnapLocaisRepository
import br.gov.rs.defensoria.removal.repository.SnapPretensoesRepository
import br.gov.rs.defensoria.removal.repository.SnapResultadosRepository
import br.gov.rs.defensoria.removal.repository.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FechamentoResultLoader {
    @Autowired
    private SnapPretensoesRepository snapPretensoesRepository
    
    @Autowired
    private SnapResultadosRepository snapResultadosRepository
    
    @Autowired
    private SnapLocaisRepository snapLocaisRepository
    
    @Autowired 
    private FechamentosRepository fechamentosRepository

    @Transactional
    ResultTableWinnersWithPretensions getAllocationResultFromDB(int fechamentoId) {
        List<ResultRowWinnersWithPretensions> rows = new ArrayList<ResultRowWinnersWithPretensions>()
        FechamentosEntity fechamento = fechamentosRepository.findByIdFechamento(fechamentoId)
        EditaisEntity edital = fechamento.getEdital()

        def pretensoes = snapPretensoesRepository.findByCodFechamento(fechamentoId)
        List<SnapResultadosEntity> resultados = snapResultadosRepository.findByCodFechamento(fechamentoId)

        List<VagasEditalEntity> vagasEdital = edital.vagasEdital
        List<SnapLocaisEntity> locais = snapLocaisRepository.findByIdFechamento(fechamentoId)

        edital.candidato.sort {it.antiguidade}  .each {
            CandidatosEntity candidato = it
            
            def pretensao = pretensoes.findAll { it.matricula == candidato.servidor.matricula }
            if(pretensao.size() == 0) return
            
            SnapResultadosEntity wonResult = resultados.find { it.matricula == candidato.servidor.matricula }
            VagasEditalEntity wonOffice = vagasEdital.find {it.idVagaEdital == wonResult?.codLocal}
            
            rows.add (
                new ResultRowWinnersWithPretensions(
                    antiquity: candidato.antiguidade,
                    name: candidato.servidor.nomeCompleto,
                    originOffice: candidato.lotacao?.descricao,
                    wonOffice: wonOffice?.unidade?.descricao,
                    wonOfficeId: wonOffice?.idVagaEdital,
                    wonOrder: wonResult?.ordemGanha,
                    pretensions: loadCandidatePretensions(candidato, locais, pretensao)
                )
            )
        }
        
        List<OfficePresentation> officePresentationList = new ArrayList<OfficePresentation>()
        locais.each { local -> officePresentationList.add(new OfficePresentation(name: local.nome, hasOwner: !local.semDono, available: false))    }
        
        return new ResultTableWinnersWithPretensions(officesList: officePresentationList, rows: rows)
    }

    private List<Pretension> loadCandidatePretensions(CandidatosEntity candidato, List<SnapLocaisEntity> locais,  List<SnapPretensoesEntity> pretensoes) {
        List<Pretension> pretensionsPresentation = new ArrayList<Pretension>()
        locais.each { local ->
            SnapPretensoesEntity pretensao = pretensoes.find { it.codLocal == local.idLocal }
            if(pretensao == null) {
                pretensionsPresentation.add(new Pretension(officeId: local.idLocal))
            } else {
                pretensionsPresentation.add(new Pretension(officeId: local.idLocal, order: pretensao.ordem, wanted: true, hasOwner: local.semDono))
            }
        }

        return pretensionsPresentation
    }
}
