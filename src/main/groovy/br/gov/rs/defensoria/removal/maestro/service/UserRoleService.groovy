package br.gov.rs.defensoria.removal.maestro.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.PermissoesRepository
import br.gov.rs.defensoria.removal.repository.entity.PermissoesEntity

@Component
class UserRoleService {
    @Autowired
    PermissoesRepository permissoesRepository

    boolean isOperator(Integer matricula) {
        PermissoesEntity permissao = permissoesRepository.findByServidorMatricula(matricula)
        return permissao != null
    }

    boolean isEditalOperator(Integer matricula, Integer idEdital) {
        PermissoesEntity permissao = permissoesRepository.findByServidorMatricula(matricula)
        return permissao != null && permissao.edital.idEdital == idEdital
    }
}
