package br.gov.rs.defensoria.removal.maestro


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

import javax.mail.internet.MimeMessage

@Component
class EmailService implements MailInterface{

	@Autowired
    MailSender mailSender
	
	@Value('${mail.from}')
	String mailFrom
	
	@Autowired
	EmailSender(MailSender mailSender) {
		this.mailSender = mailSender
		
	}
	 String sendMimeEmail(String[] mailsToSend, String msg, String mailSubject='Assunto Indefinido', List <String> fileUrl=null) throws MailException{
		 String template = this.getClass().getResource('/templates/email.tpl').getText("UTF-8")
		 msg= template.replaceAll('__MSG',msg)
		 MimeMessage message = mailSender.createMimeMessage()
		 MimeMessageHelper helper
		 helper = new MimeMessageHelper(message, true,'UTF-8')
		 helper.setFrom(mailFrom)
		 helper.setTo(mailsToSend)
		 message.setSubject(mailSubject)
		 helper.setText(msg, true)
		 if(fileUrl){
			 fileUrl.each{
				 FileSystemResource file = new FileSystemResource(it)
				 helper.addAttachment(System.nanoTime()+'_'+file.getFilename(), file)
			 }
		 }
		 mailSender.send(message)
		 return "Email enviado com sucesso"
    }
	 String sendSimpleEmail(String[] mailsToSend, String msg, String mailSubject='Assunto Indefinido') throws MailException{
		  SimpleMailMessage message = new SimpleMailMessage()
         message.setFrom(mailFrom)
         message.setTo(mailsToSend)
         message.setSubject(mailSubject)
         message.setText(msg)
         mailSender.send(message)
		  return "Email enviado com sucesso"
	}
}