package br.gov.rs.defensoria.removal

import br.gov.rs.defensoria.oauth.UserInfoTokenServices
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = [ITConfiguration, UserInfoTokenServices])
class ITConfiguration {
}
