package br.gov.rs.defensoria.removal.maestro

import br.gov.rs.defensoria.removal.report.PreliminaryReport
import br.gov.rs.defensoria.removal.report.RemovalReports
import br.gov.rs.defensoria.removal.report.presentation.ResultRowPresenter
import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions
import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import groovy.util.logging.Slf4j
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
@Slf4j
class FechamentoReports {
    @Value('${output.directory}')
    private String reportsDirectory

    @Autowired
    FechamentosRepository fechamentoRepository

    @Autowired
    FechamentoResultLoader fechamentoResultLoader

    def generateReports(int fechamentoId) {
        log.info('Geracao de relatorios iniciada.')
        ResultTableWinnersWithPretensions resultFromReport = fechamentoResultLoader.getAllocationResultFromDB(fechamentoId)
        def fechamentoDate=this.getDateById(fechamentoId)
        def pretensionsReportInfo = generateRemovalReport(resultFromReport,fechamentoId, fechamentoDate)
        def resultReportInfo = generatePreliminaryReport(resultFromReport,fechamentoId, fechamentoDate)
        log.info('Fim da geracao de relatorios.')

        return [pretensionsInfo:pretensionsReportInfo, result: resultReportInfo]
    }

    String generateRemovalReport(ResultTableWinnersWithPretensions fechamentoResult, int fechamentoId, String fechamentoDate, String fileNamePrefix="RelSol") {
        String filename = generateFileName(fileNamePrefix, fechamentoId)
        new RemovalReports().generate(fechamentoResult, fechamentoDate, reportsDirectory, filename, "pdf")
        def fechamento = fechamentoRepository.findByIdFechamento(fechamentoId)
        fechamento.relatorioFechamento = "${filename}.pdf"
        fechamentoRepository.save(fechamento)
        return "${reportsDirectory}/${filename}.pdf"
    }

    String generatePreliminaryReport(ResultTableWinnersWithPretensions fechamentoResult, int fechamentoId, String fechamentoDate, String fileNamePrefix="RelResult") {
        String filename = generateFileName(fileNamePrefix, fechamentoId)
        ResultTablePresenter listPreliminary = pretensionsReportToPreliminaryReport(fechamentoResult)
        new PreliminaryReport().generatePreliminaryReport(listPreliminary, fechamentoDate, reportsDirectory, filename, "pdf")
        def fechamento = fechamentoRepository.findByIdFechamento(fechamentoId)
        fechamento.relatorioPreliminar = "${filename}.pdf"
        fechamentoRepository.save(fechamento)
        return "${reportsDirectory}/${filename}.pdf"
    }

    private String generateFileName(String fileNamePrefix, int fechamentoId) {

        return "${fileNamePrefix}-${fechamentoId}-${System.nanoTime()}"
    }

    private ResultTablePresenter pretensionsReportToPreliminaryReport(ResultTableWinnersWithPretensions fechamentoResult) {
        ResultTablePresenter listPreliminary = new ResultTablePresenter()
        fechamentoResult.rows.each {
            if(it.wonOffice==null) return
            listPreliminary.rows.add( new ResultRowPresenter(
                    [antiquity: it.antiquity, name: it.name, allocationName: it.originOffice, pretensionName: it.wonOffice]
                    ))
        }
        return listPreliminary
    }

    private String getDateById(int fechamentoId){
        FechamentosEntity fechamento = fechamentoRepository.findByIdFechamento(fechamentoId)
        LocalDateTime date = fechamento.dataFechamento
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
        String result = formatter.print(date)
        return result
    }
}