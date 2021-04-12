package br.gov.rs.defensoria.removal.maestro

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
@EnableCaching
class CoreConfiguration {

    @Value('${mail.smtp.host}')
    private String mailSmtpHost

    @Value('${mail.smtp.port}')
    private int mailSmtpPort

    @Value('${mail.username}')
    private String mailUsername

    @Value('${mail.password}')
    private String mailPassword

    @Value('${mail.smtp.auth}')
    private String mailAuth

    @Value('${mail.smtp.starttls.enable}')
    private String mailStartTls

    @Value('${mail.protocol}')
    private String mailProtocol

    @Value('${mail.smtp.ssl.trust}')
    private String sSLTrust

    @Bean
    CacheManager getCacheManager() {

        return new ConcurrentMapCacheManager('system')
    }
    
    @Bean
    JavaMailSender javaMailSender() {
        JavaMailSenderImpl impl = new JavaMailSenderImpl()
        impl.setHost(mailSmtpHost)
        impl.setPort(mailSmtpPort)
        impl.setUsername(mailUsername)
        impl.setPassword(mailPassword)
        impl.setProtocol(mailProtocol)

        Properties props = impl.getJavaMailProperties()
        props.put('mail.smtp.auth', mailAuth)
        props.put('mail.smtp.starttls.enable', mailStartTls)
        props.put('mail.smtp.ssl.trust', sSLTrust)

        return impl
    }
}