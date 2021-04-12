package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.ITConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles("IT")
@SpringBootTest
class UserRoleServiceTest {
    
    @Autowired
    UserRoleService userRoleService

    @Test
    @Sql([
        '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql',
        '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql',
        '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql',
        '/br/gov/rs/defensoria/removal/maestro/operadores.sql']
    )
    void 'testa se a role OPERADOR é definida corretamente'() {
        assert userRoleService.isOperator(12345) == true
        assert userRoleService.isOperator(912389) == false //não é operador ou matricula não existe
        assert userRoleService.isEditalOperator(12345, 101) == true
        assert userRoleService.isEditalOperator(12345, 102) == false
    }
}
