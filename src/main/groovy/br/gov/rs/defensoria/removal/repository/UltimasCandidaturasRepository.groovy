package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.UltimasCandidaturasEntity
import br.gov.rs.defensoria.removal.repository.mapper.UltimasCandidaturasMapper

@Component
class UltimasCandidaturasRepository {

	@Autowired
	JdbcTemplate jdbcTemplate

	@Autowired
	UltimasCandidaturasMapper ultimasCandidaturasMapper

    List<UltimasCandidaturasEntity> getUltimasCandidaturasByFechamento(int codFechamento, int idEdital) {
		String sql = '''
					select cr.id_candidatura
					       ,cr.id_vaga_edital
					       ,c.matricula
					       ,cr.ordem_candidatura
					  from candidaturas cr
					 inner join candidatos c on (c.id_candidato = cr.id_candidato)
					 where cr.data = (select max(data)
					                    from candidaturas cr2
					                   where cr2.id_candidato = cr.id_candidato
                                         and data <= (select data_fechamento
					                                    from fechamentos
					                                   where id_fechamento = ?))
                      and c.id_edital = ?
					'''

		return jdbcTemplate.query(sql, [codFechamento, idEdital] as Object[], ultimasCandidaturasMapper)
	}

    List<UltimasCandidaturasEntity> getUltimasCandidaturas(int idEdital, int idCandidato) {
		String sql = '''
					select cr.id_candidatura
					       ,cr.id_vaga_edital
					       ,c.matricula
					       ,cr.ordem_candidatura
					  from candidaturas cr
					 inner join candidatos c on (c.id_candidato = cr.id_candidato and c.id_candidato=?)
					 where cr.data = (select max(data)
					                    from candidaturas cr2
					                   where cr2.id_candidato = cr.id_candidato
                                         and c.id_candidato=? )
                      and c.id_edital = ?
					'''

		return jdbcTemplate.query(sql, [ idCandidato, idCandidato, idEdital] as Object[], ultimasCandidaturasMapper)
	}
}
