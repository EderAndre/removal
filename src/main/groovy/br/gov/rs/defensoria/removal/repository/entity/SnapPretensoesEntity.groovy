package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.TableGenerator
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "snap_pretensoes", uniqueConstraints = [@UniqueConstraint(columnNames = ['cod_fechamento', 'cod_local', 'matricula'])])
class SnapPretensoesEntity {

    @Id
    @TableGenerator(name="snapPretensoesGen", table="sequence_table", pkColumnName="seq_name",
    valueColumnName="seq_count", pkColumnValue="snap_pretensoes", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="snapPretensoesGen")
    @Column(name = "id_pretensao")
    int idPretensao

    @Column
    int matricula

    @Column(name = "cod_fechamento")
    int codFechamento

    @Column(name = "cod_local")
    Integer codLocal

    @Column
    int ordem
}
