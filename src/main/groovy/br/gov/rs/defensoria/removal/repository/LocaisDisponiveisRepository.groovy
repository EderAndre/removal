package br.gov.rs.defensoria.removal.repository

import br.gov.rs.defensoria.removal.repository.entity.LocaisDisponiveisEntity
import br.gov.rs.defensoria.removal.repository.mapper.LocaisDisponiveisMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

import static org.joda.time.LocalDateTime.now

@Component
class LocaisDisponiveisRepository {

    private final SUCESSIVEIS = '''SELECT v.id_vaga_edital,
                                          v.ordem_vaga,
                                          v.id_unidade AS unidade,
                                          u.descricao,
                                          1 AS Edital,
                                         'Edital' AS status
                                     FROM vagas_edital v
                                    INNER JOIN unidades u on (v.id_unidade = u.id_unidade)
                                    WHERE v.tipo_vaga = 1
                                      AND v.id_edital = ?'''

    private final VENCEDORES = '''SELECT r.matricula
                                    FROM snap_resultados r
                                   WHERE r.cod_fechamento = (SELECT id_fechamento
                                                               FROM fechamentos
                                                              WHERE data_fechamento = (SELECT MAX(data_fechamento)
                                                                                         FROM fechamentos
                                                                                        WHERE id_fechamento IN (SELECT cod_fechamento
                                                                                                                  FROM snap_resultados
                                                                                                                 GROUP BY cod_fechamento)
                                                                                          AND data_fechamento <= ?
                                                                                       )
                                                             )
                                     AND matricula <> 0'''

    private final VAGADAS = '''SELECT v.id_vaga_edital,
                                      v.ordem_vaga,
                                      v.id_unidade AS unidade,
                                      u.descricao,
                                      0 as Edital,
                                      'Vagas' AS status
                                 FROM ( ''' + VENCEDORES + ''') AS vencedores
                                INNER JOIN candidatos c ON (vencedores.matricula = c.matricula)
                                INNER JOIN unidades u ON (c.id_lotacao = u.id_unidade)
                                INNER JOIN vagas_edital v ON (v.id_unidade = u.id_unidade)
                                WHERE v.id_edital = ?
                                  AND c.id_edital = v.id_edital'''

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    LocaisDisponiveisMapper locaisDisponiveisMapper

    List<LocaisDisponiveisEntity> getLocaisDisponiveis(int idEdital) {
        String sql = SUCESSIVEIS
                .concat(' union ')
                .concat(VAGADAS) + '''
                          union
                          select v.id_vaga_edital,
                                v.ordem_vaga,
                                v.id_unidade AS unidade,
                               u.descricao,
                               0 AS Edital,
                               'NaoSucesAtualmente' AS status
                          from (select c.matricula
                                  from candidaturas cr
                                 inner join candidatos c on (cr.id_candidato = c.id_candidato)
                                 where c.matricula not in ( ''' + VENCEDORES + ''')
                                 group by c.matricula
                                having max(cr.data) <= ?) as perdedores
                          inner join candidatos c on (perdedores.matricula = c.matricula)
                          inner join unidades u on (c.id_lotacao = u.id_unidade)
                          inner join vagas_edital v on (v.id_unidade = u.id_unidade)
                          where v.id_edital = ?
                            and c.id_edital = v.id_edital
                        '''

        String now = now().toString()

        return getData(sql, [idEdital, now, idEdital, now, now, idEdital])
    }

    List<LocaisDisponiveisEntity> getSucessiveis(int editalId) {

        return getData(SUCESSIVEIS, [editalId])
    }

    List<LocaisDisponiveisEntity> getVagadas(int editalId, String referenceDate) {

        return getData(VAGADAS, [referenceDate, editalId])
    }

    List<LocaisDisponiveisEntity> getNaoSucessiveis(int editalId, String referenceDate) {
        String sql = '''SELECT v.id_vaga_edital,
                               v.ordem_vaga,
                               v.id_unidade AS unidade,
                               u.descricao,
                               0 AS Edital,
                               'NaoSucesAtualmente' AS status
                          FROM (SELECT c.matricula
                                  FROM candidaturas cr
                                 RIGHT JOIN candidatos c ON (cr.id_candidato = c.id_candidato)
                                 WHERE c.matricula NOT IN ( ''' + VENCEDORES + ''')
                                 GROUP BY c.matricula) AS perdedores
                          INNER JOIN candidatos c ON (perdedores.matricula = c.matricula)
                          INNER JOIN unidades u ON (c.id_lotacao = u.id_unidade)
                          INNER JOIN vagas_edital v ON (v.id_unidade = u.id_unidade)
                          WHERE v.id_edital = ?
                            AND c.id_edital = v.id_edital'''

        return getData(sql, [referenceDate, editalId])
    }

    private List<LocaisDisponiveisEntity> getData(String sql, List<Serializable> args) {

        return jdbcTemplate.query(sql, args as Object[], locaisDisponiveisMapper)
    }
}
