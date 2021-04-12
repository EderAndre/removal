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
@Table(name = "snap_locais", uniqueConstraints = [@UniqueConstraint(columnNames = ['id_local', 'id_fechamento'])])
class SnapLocaisEntity {

    @Id
    @TableGenerator(name="snapLocaisGen", table="sequence_table", pkColumnName="seq_name",
    valueColumnName="seq_count", pkColumnValue="snap_locais", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="snapLocaisGen")
    @Column(name = "id_linha")
    int idLinha

    @Column(name = "id_local")
    int idLocal

    @Column
    String nome

    @Column(name = "id_fechamento")
    int idFechamento

    @Column(name = "sem_dono")
    boolean semDono
}
