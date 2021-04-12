package br.gov.rs.defensoria.removal.web.angularjspicker.test.salvaedital

import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import groovy.json.JsonBuilder
import org.joda.time.LocalDateTime
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post 

class SalvaEditalVerificaResultado {
    MockMvc mvc
    def idEdital
    def oie
    def abertura
    def encerramento
    def apenasDesistencias
    def limite
    EditaisRepository editaisRepository

    SalvaEditalVerificaResultado(MockMvc mvc, int idEdital, ObjInfosEdital oie, LocalDateTime abertura, LocalDateTime encerramento, LocalDateTime limite, LocalDateTime apenasDesistencias, EditaisRepository editaisRepository) {
        this.mvc = mvc
        this.idEdital = idEdital
        this.oie = oie
        this.abertura = abertura
        this.encerramento = encerramento
        this.apenasDesistencias = apenasDesistencias
        this.limite = limite
        this.editaisRepository = editaisRepository
    }

    static SalvaEditalVerificaResultado builder(MockMvc mvc, int idEdital, ObjInfosEdital oie, LocalDateTime abertura, LocalDateTime encerramento, LocalDateTime limite, LocalDateTime apenasDesistencias, EditaisRepository editaisRepository) {
        return new SalvaEditalVerificaResultado(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository)
    }

    SalvaEditalVerificaResultado verifica() {
        String url = "/edital/${idEdital}/salva-edital"

        String json = new JsonBuilder(oie)

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))

        EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)

        assert edital.aberturaEdital == abertura
        assert edital.encerramentoEdital == encerramento
        assert edital.limitePrimeiraCandidatura == limite
        assert edital.apenasDesistenciasPermitidas == apenasDesistencias

        return this
    }
}
