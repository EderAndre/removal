package br.gov.rs.defensoria.removal.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ComputeFechamentoAlreadyRunningResponse
import br.gov.rs.defensoria.removal.maestro.FechamentoService
import br.gov.rs.defensoria.removal.web.exception.RequestComputeFechamentoException

@RestController
@RequestMapping('fechamento')
class FechamentoController {

    private static Boolean isComputeFechamentoAlreadyRunning

    @Autowired
    FechamentoService fechamentoService

    @RequestMapping('{codFechamento}/{shouldPersistIn}/{shouldPersistOut}')
    String index(@PathVariable("codFechamento") int codFechamento,
                 @PathVariable("shouldPersistIn") boolean shouldPersistIn,
                 @PathVariable("shouldPersistOut") boolean shouldPersistOut) {

        computeFechamento(false, codFechamento, shouldPersistIn, shouldPersistOut)
    }

    @RequestMapping('edital/{idEdital}/{shouldPersistIn}/{shouldPersistOut}')
    String computeFechamentoByEdital(@PathVariable("idEdital") int idEdital,
                                     @PathVariable("shouldPersistIn") boolean shouldPersistIn,
                                     @PathVariable("shouldPersistOut") boolean shouldPersistOut) {

        computeFechamento(true, idEdital, shouldPersistIn, shouldPersistOut)
    }

    private String computeFechamento(boolean isByEdital, Integer codFechamentoOrEdital, boolean shouldPersistIn, boolean shouldPersistOut) {
        if (isComputeFechamentoAlreadyRunning) {
            throw new RequestComputeFechamentoException()
        }

        isComputeFechamentoAlreadyRunning = true
        fechamentoService.computeFechamento(isByEdital, codFechamentoOrEdital, shouldPersistIn, shouldPersistOut)
        isComputeFechamentoAlreadyRunning = false
    }

    @RequestMapping('reset-lock')
    ComputeFechamentoAlreadyRunningResponse resetLock() {
        isComputeFechamentoAlreadyRunning = false

        return new ComputeFechamentoAlreadyRunningResponse(isComputeFechamentoAlreadyRunning: isComputeFechamentoAlreadyRunning)
    }
}