package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.SolicitacoesCandidatoEntity
import br.gov.rs.defensoria.removal.repository.mapper.SolicitacoesCandidatoMapper

@Component
class SolicitacoesCandidatoRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate
	
	@Autowired
	SolicitacoesCandidatoMapper solicitacoesCandidatoMapper

    List<SolicitacoesCandidatoEntity> getSolicitacoes(int idCandidato) {
		String sql = '''
					select data, count(*) as num_sol
						from candidaturas
						where (id_candidato = ?) 
						group by data
					'''

	
		return jdbcTemplate.query(sql, [idCandidato] as Object[], solicitacoesCandidatoMapper)
	}
	
}
