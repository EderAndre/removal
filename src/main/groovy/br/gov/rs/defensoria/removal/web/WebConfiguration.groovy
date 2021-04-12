package br.gov.rs.defensoria.removal.web

import br.gov.rs.defensoria.removal.core.RemovalService
import br.gov.rs.defensoria.removal.web.filter.EditalBloqueadoFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.servlet.DispatcherType
import javax.servlet.Filter

import static java.util.EnumSet.allOf
import static org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME

@Configuration
class WebConfiguration {

    @Autowired
    private EditalBloqueadoFilter editalBloqueadoFilter

    @Bean
    FilterRegistrationBean securityFilterChain(@Qualifier(DEFAULT_FILTER_NAME) Filter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter)
        registration.setOrder(0)
        registration.setName(DEFAULT_FILTER_NAME)
        return registration
    }

    @Bean
    FilterRegistrationBean editalSecutiryFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean()
        registration.setFilter(editalBloqueadoFilter)
        registration.setDispatcherTypes(allOf(DispatcherType))
        registration.addUrlPatterns("/edital/*")
        return registration
    }

    @Bean
    RemovalService removalService() {
        return new RemovalService()
    }
}
