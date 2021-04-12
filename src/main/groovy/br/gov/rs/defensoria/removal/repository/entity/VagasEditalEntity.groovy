package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "vagas_edital")
class VagasEditalEntity {

    @Id
    @Column(name = "id_vaga_edital")
    int idVagaEdital

    @ManyToOne
    @JoinColumn(name = "id_edital")
    EditaisEntity edital

    @ManyToOne
    @JoinColumn(name = "id_unidade")
    UnidadesEntity unidade

    @Column(name = "tipo_vaga")
    int tipoVaga

    @Column(name = "ordem_vaga", nullable = true)
    int ordemVaga

    @OneToMany(mappedBy = "vagaEdital")
    List<CandidaturasEntity> candidaturas
}
