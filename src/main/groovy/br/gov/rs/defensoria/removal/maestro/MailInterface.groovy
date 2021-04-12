package br.gov.rs.defensoria.removal.maestro

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender

interface MailInterface{

	@Autowired
	EmailSender(MailSender mailSender)
	
	String sendMimeEmail(String[] mailsToSend, String msg, String mailSubject, List <String> fileUrl)
	
	String sendSimpleEmail(String[] mailsToSend, String msg, String mailSubject)	
}

