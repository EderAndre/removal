package br.gov.rs.defensoria.removal.web.controller



import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import br.gov.rs.defensoria.removal.api.DadosRealizacaoCandidatura
import br.gov.rs.defensoria.removal.api.PedidoCandidatura
import br.gov.rs.defensoria.removal.maestro.service.RegistraCandidaturaService

@RestController
@RequestMapping("/edital/{editalId}/candidato/{candidatoId}/candidatura")
class CandidaturaController {

    @Autowired
    RegistraCandidaturaService registraCandidaturaService

    @RequestMapping(value = "/pedido", method = RequestMethod.POST)
    void pedido(@PathVariable Integer editalId,
                @PathVariable Integer candidatoId, @RequestBody PedidoCandidatura pedido) {

        LocalDateTime dataBase = LocalDateTime.now()

        DadosRealizacaoCandidatura dadosRealizacaoCandidatura = new DadosRealizacaoCandidatura(pedido: pedido,
        idEdital: editalId,
        idCandidato: candidatoId,
        dataBase: dataBase)

        registraCandidaturaService.novaCandidatura(dadosRealizacaoCandidatura)
    }

    @RequestMapping(value = "/pedido/data-base/{dataBase}", method = RequestMethod.POST)
    void realizaPedido(@PathVariable Integer editalId,
                       @PathVariable Integer candidatoId, @PathVariable String dataBase, @RequestBody PedidoCandidatura pedido) {

        DateTimeFormatter formatter = DateTimeFormat.forPattern('dd-MM-yyyy HH:mm')
        LocalDateTime data = formatter.parseLocalDateTime(dataBase)

        DadosRealizacaoCandidatura dadosRealizacaoCandidatura = new DadosRealizacaoCandidatura(pedido: pedido,
        idEdital: editalId,
        idCandidato: candidatoId,
        dataBase: data)

        registraCandidaturaService.novaCandidatura(dadosRealizacaoCandidatura)
    }
}
