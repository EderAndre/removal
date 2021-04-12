package br.gov.rs.defensoria.removal.maestro.job

import br.gov.rs.defensoria.removal.maestro.service.FechamentoExecutor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Profile(value = ['dev', 'prod'])
@Slf4j
class FechamentoJob {

    @Autowired
    private FechamentoExecutor fechamentoExecutor

    @Scheduled(cron = '${momento-tentativa-fechamento}')
    void execute() {
        log.info('Iniciada verificacao agendada de fechamentos')
        fechamentoExecutor.execute()
    }
}
