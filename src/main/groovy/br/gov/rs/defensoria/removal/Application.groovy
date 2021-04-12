package br.gov.rs.defensoria.removal

import br.gov.rs.defensoria.oauth.UserInfoTokenServices
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

import static org.springframework.boot.SpringApplication.run

@SpringBootApplication(scanBasePackageClasses = [Application, UserInfoTokenServices])
@EnableScheduling
class Application {

	static void main(String[] args) {
		run(Application, args)
	}
}