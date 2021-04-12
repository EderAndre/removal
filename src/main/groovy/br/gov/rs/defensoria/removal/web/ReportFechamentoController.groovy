package br.gov.rs.defensoria.removal.web


import br.gov.rs.defensoria.removal.maestro.FechamentoReports
import br.gov.rs.defensoria.removal.repository.DadosFechamentosRepository
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ReportFechamentoController {

    @Autowired
    FechamentoReports reports
    
    @Autowired
    DadosFechamentosRepository dadosFechamentosRepository

    @RequestMapping("/fechamento/{codFechamento}/report")
    String index(@PathVariable("codFechamento") int codFechamento) {
        generateReport(codFechamento)
    }
    
    @RequestMapping("/fechamento/edital/{idEdital}/report")
    String getReport(@PathVariable("idEdital") int idEdital) {
        FechamentosEntity fechamentoEntity = dadosFechamentosRepository.getGreater(idEdital)
        generateReport(fechamentoEntity.idFechamento)
    }
    
    private String generateReport(int codFechamento) {
        reports.generateReports(codFechamento)
    }
}