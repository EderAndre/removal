package br.gov.rs.defensoria.removal.web.captcha

import jj.play.ns.nl.captcha.Captcha
import jj.play.ns.nl.captcha.backgrounds.GradiatedBackgroundProducer
import jj.play.ns.nl.captcha.noise.StraightLineNoiseProducer
import jj.play.ns.nl.captcha.text.producer.DefaultTextProducer
import jj.play.ns.nl.captcha.text.renderer.DefaultWordRenderer

import java.awt.*
import java.util.List

class CaptchaImg {

	Captcha builder

	private CaptchaImg(int lenght) {
		List<Font> fonts = []
		fonts.add(new Font("Arial", Font.ITALIC, 40))
		fonts.add(new Font("Courier", Font.BOLD, 45))
		Color backColor = new Color(rand(), rand(), rand())
		Color fontColor = new Color(rand(), rand(), rand())
		def back=new GradiatedBackgroundProducer()
		back.setFromColor(backColor)
		Captcha captcha = new Captcha.Builder(125, 50)
				.addText( new DefaultTextProducer(lenght),new DefaultWordRenderer(fontColor, fonts))
				.addBackground(back)
				.addNoise(new StraightLineNoiseProducer())
				.addNoise().addBorder().build()
		builder= captcha
	}

    def getCaptchaObject(){
		return builder
	}
	private float rand(){
		Random rand = new Random()
        float rdm = rand.nextFloat()
        return rdm
	}
}