package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.maestro.FechamentoReports
import br.gov.rs.defensoria.removal.maestro.FechamentoService
import br.gov.rs.defensoria.removal.repository.DadosFechamentosRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static org.joda.time.LocalDateTime.now

@Service
class FechamentoExecutor {

    @Autowired
    private FechamentoService fechamentoService

    @Autowired
    private EditaisRepository editalRepository

    @Autowired
    private FechamentoReports reportService

    @Autowired
    private DadosFechamentosRepository dadoFechamentoRepository

    void execute() {
        editalRepository.findByEncerramentoEditalGreaterThanEqual(now()).each {
            fechamentoService.computeFechamento(true, it.idEdital, true, true)
            reportService.generateReports(dadoFechamentoRepository.getGreater(it.idEdital).idFechamento)
        }
    }
}
