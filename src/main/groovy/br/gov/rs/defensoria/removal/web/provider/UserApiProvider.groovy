package br.gov.rs.defensoria.removal.web.provider

import br.gov.rs.defensoria.oauth.AuthenticatedUserService
import br.gov.rs.defensoria.removal.maestro.service.UserRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import static br.gov.rs.defensoria.oauth.AuthenticatedUserService.currentAuthentication
import static br.gov.rs.defensoria.removal.core.auth.UaaRole.ROLE_DEV
import static java.lang.Integer.parseInt
import static org.apache.commons.lang3.StringUtils.EMPTY

@Component
class UserApiProvider {
    @Value('${dpe.permission.superuser.desenvolvimentoti}')
    Boolean isDesenvolvimentoTISuperuser

    AuthenticatedUserService authenticatedUserService
    UserRoleService userRoleService

    @Autowired
    UserApiProvider(AuthenticatedUserService authenticatedUserService, UserRoleService userRoleService) {
        this.authenticatedUserService = authenticatedUserService
        this.userRoleService = userRoleService
    }

    int getMatricula() {

        return parseInt(getRegistry() ?: '0')
    }

    String getNome() {

        return getRegistry()
    }

    private String getRegistry() {

        return currentAuthentication()?.getUserAuthentication()?.getCredentials() ?: EMPTY
    }

    String getRemovalRole(){
        Set<String> rolesOfUser =["CANDIDATO"]
        def grupos = authenticatedUserService.roles()
        if (grupos.contains(ROLE_DEV) && isDesenvolvimentoTISuperuser){
            rolesOfUser =["SUPER_USER"]
        }else if(userRoleService.isOperator(getMatricula())){
            rolesOfUser =["OPERADOR"]
        }
        return rolesOfUser
    }
}