package br.gov.rs.defensoria.removal.web.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import br.gov.rs.defensoria.removal.api.Captcha
import br.gov.rs.defensoria.removal.api.PedidoCandidatura
import br.gov.rs.defensoria.removal.maestro.service.CandidaturaService
import br.gov.rs.defensoria.removal.maestro.service.CaptchaService
import br.gov.rs.defensoria.removal.web.provider.UserApiProvider

@Controller

class IndexController {

	@Autowired
	UserApiProvider uap
	
	@RequestMapping("/")
    String welcomeHandler() {
		return "redirect:/v2/"
    }
	@RequestMapping("/v2/")
    String indexHandler() {
		return "forward:/v2/index.html"
    }
	
}
