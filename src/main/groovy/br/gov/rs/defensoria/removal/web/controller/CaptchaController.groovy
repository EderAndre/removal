package br.gov.rs.defensoria.removal.web.controller

import br.gov.rs.defensoria.removal.maestro.service.CaptchaService
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import groovy.json.*

@RestController("CaptchaControllerV2")
@RequestMapping("/captcha")
class CaptchaController {
	@Autowired
	private CaptchaService captchaService
	
	@RequestMapping(value = "/imagem", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE, headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    @ResponseBody byte[] geraImagem(HttpServletResponse response, @RequestParam("segredo") String segredo) {
		response.setContentType("image/png")
		OutputStream outputStream = response.getOutputStream() 
		captchaService.desenhaCaptcha(outputStream, segredo)	
		outputStream.close()
	}
	
	@RequestMapping("/gerar")
    String geraTextoCrifrado() {
		String textoPlano = captchaService.geraTextoCaptcha(4)
		def json = JsonOutput.toJson(URLEncoder.encode(captchaService.geraTextoCriptografado(textoPlano), 'UTF-8'))
		return json
	}
}
