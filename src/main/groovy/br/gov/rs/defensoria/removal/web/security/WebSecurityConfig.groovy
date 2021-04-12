package br.gov.rs.defensoria.removal.web.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableOAuth2Sso
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value('${uaa.url}')
	String uaaUrl

	@Value('${removal.url}')
	String removalUrl

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()

		http
				.authorizeRequests()
				.antMatchers(getAllowed()).permitAll()
				.anyRequest()
				.authenticated().and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher('/logout'))
				.invalidateHttpSession(true)
				.deleteCookies('XSRF-TOKEN', 'JSESSIONID')
				.clearAuthentication(true)
				.logoutSuccessUrl("$uaaUrl/logout.do?redirect=$removalUrl")
				.and().headers().frameOptions().sameOrigin()
	}

	private String[] getAllowed() {

		return [
				'/assets/**',
				'/fechamento/**',
                '/actuator/metrics/**',
                '/actuator/info/**',
                '/actuator/health/**',
		]
	}
}
