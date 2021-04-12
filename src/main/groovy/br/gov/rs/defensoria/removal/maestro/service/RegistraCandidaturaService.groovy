package br.gov.rs.defensoria.removal.maestro.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.Captcha
import br.gov.rs.defensoria.removal.api.DadosRealizacaoCandidatura
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.exception.ApenasDesistenciasPermitidasException
import br.gov.rs.defensoria.removal.maestro.modo.ModoEditalManager

@Service
class RegistraCandidaturaService {

    @Autowired
    SomenteDesistenciasService somenteDesistenciasService

    @Autowired
    CandidaturaService candidaturaService

    @Autowired
    CaptchaService captchaService

    @Autowired
    ModoEditalManager modoEditalManager

    void novaCandidatura(DadosRealizacaoCandidatura dadosRealizacaoCandidatura) {
        verificaCaptchaCasoInvalidoAborta(dadosRealizacaoCandidatura.pedido.captcha)

        if (modoEditalManager.hasModoAtivo(dadosRealizacaoCandidatura.idEdital, ModoEdital.SOMENTE_DESISTENCIAS)) {
            if (!somenteDesistenciasService.isDesistencia(dadosRealizacaoCandidatura)) {
                throw new ApenasDesistenciasPermitidasException()
            }
        }

        candidaturaService.novaCandidatura(dadosRealizacaoCandidatura.pedido.candidatura)
    }

    private void verificaCaptchaCasoInvalidoAborta(Captcha captcha) {
        if(captcha == null || captchaService.verificaCatpcha(URLDecoder.decode(captcha.segredo), captcha.codigo) == false)
            throw new RuntimeException("O captcha digitado não corresponde a imagem.")
    }
}
