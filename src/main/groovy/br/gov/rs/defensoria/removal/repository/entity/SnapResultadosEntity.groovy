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
@Table(name = "snap_resultados", uniqueConstraints = [@UniqueConstraint(columnNames = ['cod_fechamento', 'cod_local'])])
class SnapResultadosEntity {

    @Id
    @TableGenerator(name="snapResultadosGen", table="sequence_table", pkColumnName="seq_name",
    valueColumnName="seq_count", pkColumnValue="snap_resultados", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="snapResultadosGen")
    @Column(name = "id_resultado")
    int idResultado

    @Column
    int matricula

    @Column(name = "cod_local")
    int codLocal

    @Column(name = "cod_fechamento")
    int codFechamento

    @Column(name = "ordem_ganha")
    int ordemGanha
}
