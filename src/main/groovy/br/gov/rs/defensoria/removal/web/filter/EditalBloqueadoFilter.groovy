package br.gov.rs.defensoria.removal.web.filter

import br.gov.rs.defensoria.removal.api.EditalStatus
import br.gov.rs.defensoria.removal.maestro.service.StatusEditalService
import br.gov.rs.defensoria.removal.web.provider.UserApiProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class EditalBloqueadoFilter extends GenericFilterBean {

    @Autowired
    StatusEditalService statusEditalService

    @Autowired
    UserApiProvider uap

    @Override
    void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
    throws IOException, ServletException {

        final HttpServletRequest httpReq = (HttpServletRequest) req
        final HttpServletResponse httpResp = (HttpServletResponse) resp

        CharSequence perfil = uap.removalRole
        int matricula = uap.matricula

        String uri = httpReq.getRequestURI()
        Integer idEdital = extraiEditalIdFromURI(uri)
        String uriBloqueio = "/edital/${idEdital}/bloqueado"

        if (perfil.contains('CANDIDATO')) {
            if(uri.startsWith("/edital") && !uri.startsWith(uriBloqueio) ) {
                EditalStatus editalStatus = statusEditalService.getEditalStatus(idEdital, matricula)
                if(editalStatus.bloqueado) {
                    resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Bloqueio de edital definido pelo sistema")
                    return
                }
            }

            if (uri.endsWith("/restrito")) {
                throw new Exception('Operação proibida.')
            }
        }

        chain.doFilter(httpReq, httpResp)
    }

    Integer extraiEditalIdFromURI(String uri) {
        def matcher = uri =~ /\/edital\/(?<idEdital>\d+)(\/?.*?)/
        if(matcher.matches()) {
            return matcher.group('idEdital') as Integer
        }
        return null
    }
}
