package br.gov.rs.defensoria.removal.repository

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import br.gov.rs.defensoria.removal.repository.mapper.FechamentosMapper
import br.gov.rs.defensoria.removal.repository.mapper.PreCandidatosMapper
import br.gov.rs.defensoria.removal.repository.mapper.PreCandidaturasMapper

@Component
class DadosFechamentosRepository {

	@Autowired
	JdbcTemplate jdbcTemplate

	@Autowired
	PreCandidatosMapper preCandidatosMapper

	@Autowired
	PreCandidaturasMapper preCandidaturasMapper

	@Autowired
	FechamentosMapper fechamentosMapper

	List<CandidatosEntity> getCandidatos(int idFechamento) {
		String sql = '''
					SELECT distinct c.id_candidato
						   ,c.antiguidade
						   ,c.id_edital
						   ,c.id_funcao
						   ,c.id_lotacao
						   ,c.matricula
					  FROM snap_pretensoes p
					 inner join candidatos c on (p.matricula = c.matricula)
					 inner join fechamentos f on (p.cod_fechamento = f.id_fechamento)
					 where f.id_fechamento = ?
				'''
		jdbcTemplate.query(sql, [idFechamento] as Object[], preCandidatosMapper)
	}

	List<CandidaturasEntity> getCandidaturas(int idFechamento) {
		String sql = '''
					SELECT p.id_pretensao
					      ,p.ordem
					      ,c.id_candidato
					      ,p.cod_local
					  FROM snap_pretensoes p
					 inner join candidatos c on (c.matricula = p.matricula)
					 where cod_fechamento = ?
                     order by c.id_candidato,
                              p.ordem
				'''
		jdbcTemplate.query(sql, [idFechamento] as Object[], preCandidaturasMapper)
	}

	FechamentosEntity getGreater(int idEdital) {
		String sql = '''
					SELECT id_fechamento,
					       data_fechamento,
					       id_edital
					  FROM fechamentos
					 where data_fechamento = (SELECT max(data_fechamento)
					                            FROM fechamentos
					                           where data_fechamento <= ?
					                             and id_edital = ?)
					'''
		LocalDateTime date = LocalDateTime.now()
		String strDate = date.toString()
		FechamentosEntity fechamento = jdbcTemplate.queryForObject(sql, [strDate, idEdital] as Object[], fechamentosMapper)
		return fechamento
	}

}
