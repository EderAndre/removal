package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.TodosOsLocaisEntity
import br.gov.rs.defensoria.removal.repository.mapper.TodosOsLocaisMapper

@Component
class TodosOsLocaisRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate
	
	@Autowired
	TodosOsLocaisMapper todosOsLocaisMapper

    List<TodosOsLocaisEntity> getTodosOsLocais(int idEdital) {
		String sql = '''
					 select vagas.id_local,
					        vagas.descricao,
					        candidatos.matricula ocupante
					   from (select v.id_vaga_edital id_local,
					                u.descricao,
					                v.id_unidade
					           from vagas_edital v
					          inner join unidades u on (v.id_unidade = u.id_unidade)
					          where id_edital = ?) vagas
					   left join (select c.matricula,
					                     c.id_lotacao
					                from candidatos c
					               where id_edital = ?) candidatos on (vagas.id_unidade = candidatos.id_lotacao)
					'''
		return jdbcTemplate.query(sql, [idEdital, idEdital] as Object[], todosOsLocaisMapper)
	}
}
