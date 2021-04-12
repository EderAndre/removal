package br.gov.rs.defensoria.removal.maestro.service

import java.awt.Color
import java.awt.Font

import javax.imageio.ImageIO

import jj.play.ns.nl.captcha.Captcha
import jj.play.ns.nl.captcha.backgrounds.GradiatedBackgroundProducer
import jj.play.ns.nl.captcha.text.producer.DefaultTextProducer
import jj.play.ns.nl.captcha.text.producer.TextProducer
import jj.play.ns.nl.captcha.text.renderer.DefaultWordRenderer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CaptchaService {
    @Autowired
    private CipherService cipherService

    @Value('${captcha.chave_secreta}')
    private String CHAVE_SECRETA
    /*
     * Gera "desafio" (caracteres) que serão exibidos na imagem do captcha
     * */
    String geraTextoCaptcha(int caracteres) {
        return new DefaultTextProducer(caracteres).getText()
    }

    /*
     * Cifra (criptografa) o texto do captcha utilizando a CHAVE_SECRETA
     * */
    String geraTextoCriptografado(String texto) {
        return cipherService.encrypt(CHAVE_SECRETA, texto)
    }

    /*
     * Reverte o texto cifrado em texto plano
     */
    String obtemTextoPlano(String textoCifrado) {
        return cipherService.decrypt(CHAVE_SECRETA, textoCifrado)
    }

    /*
     * Identifica se o segredo corresponde ao valor fornecido no parametro texto.
     * */
    boolean verificaCatpcha(String segredo, String texto) {
        if (segredo.length() < 6) {
            return false
        }
        return segredo == geraTextoCriptografado(texto)
    }

    /*
     * Desenha imagem do captcha apartir do texto cifrado
     * */
    private void desenhaCaptcha(OutputStream out, String textoCifrado, int largura=125, int altura=50) {
        String textoCaptcha = obtemTextoPlano(textoCifrado)
        List<Font> fonts = []
        fonts.add(new Font("Arial", Font.ITALIC, 40))
        Color backColor = new Color(rand(), rand(), rand())
        Color fontColor = new Color(rand(), rand(), rand())
        def back = new GradiatedBackgroundProducer()
        back.setFromColor(backColor)
        Captcha captcha = new Captcha.Builder(largura, altura)
                .addText(new FixedTextProducer(text: textoCaptcha), new DefaultWordRenderer(fontColor, fonts))
                .addBackground(back)
                .addBorder().build()
        ImageIO.write(captcha.getImage(), "png", out)
    }

    private float rand(){
        Random rand = new Random()
        float rdm = rand.nextFloat()
        return rdm
    }
}

class FixedTextProducer implements TextProducer {
    private String text

    @Override
    String getText() {
        return text
    }
}
