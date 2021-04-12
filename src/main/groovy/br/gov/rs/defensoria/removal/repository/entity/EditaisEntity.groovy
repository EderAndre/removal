package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

import org.hibernate.annotations.Type
import org.joda.time.LocalDateTime

@Entity
@Table(name = "editais")
class EditaisEntity {

    @Id
    @Column(name = "id_edital")
    int idEdital

    @Column(name = "descricao_edital")
    String descricaoEdital

    @ManyToOne
    @JoinColumn(name = "tipo_edital", nullable = false)
    TipoEdital tipoEdital

    @Column(name = "abertura_edital", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    LocalDateTime aberturaEdital

    @Column(name = "encerramento_edital")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    LocalDateTime encerramentoEdital

    @Column(name = "limite_primeira_candidatura")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    LocalDateTime limitePrimeiraCandidatura

    @Column(name = "apenas_desistencias_permitidas")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    LocalDateTime apenasDesistenciasPermitidas

    @OneToMany(mappedBy = "edital")
    List<FechamentosEntity> fechamentos

    @OneToMany(mappedBy = "edital")
    @Column(name = "vagas_edital")
    List<VagasEditalEntity> vagasEdital

    @OneToMany(mappedBy = "edital")
    List<CandidatosEntity> candidato

    @OneToMany(mappedBy = "edital")
    List<AvisosEntity> avisos

    @OneToMany(mappedBy = "edital")
    List<ArquivosEntity> arquivos

    @Column(name = "assinatura_email_edital")
    String assinaturaEmail

    @Column(name = "email_resposta_edital")
    String emailResposta

    @Column(name = "email_envio_edital")
    String emailEnvio

    @Column(name = "bloqueado")
    boolean bloqueado
}
