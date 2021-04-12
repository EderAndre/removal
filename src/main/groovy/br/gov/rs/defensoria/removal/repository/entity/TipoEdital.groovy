package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.TableGenerator

@Entity
@Table(name = 'tipos_edital')
class TipoEdital {

    @Id
    @TableGenerator(name="table_tipos_edital_id", table="sequence_table", pkColumnName="seq_name",
    valueColumnName="seq_count", pkColumnValue="tipos_edital", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="table_tipos_edital_id")
    @Column(name = 'id')
    int id

    @Column(name = 'descricao')
    String descricao
}