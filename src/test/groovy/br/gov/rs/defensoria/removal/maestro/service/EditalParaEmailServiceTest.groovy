package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.junit.Test

import br.gov.rs.defensoria.removal.api.EditalParaEmail
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import br.gov.rs.defensoria.removal.repository.entity.TipoEdital
import br.gov.rs.defensoria.removal.repository.entity.UnidadesEntity
import br.gov.rs.defensoria.removal.repository.entity.VagasEditalEntity

class EditalParaEmailServiceTest {

    @Test
    void testaSeOObjetoEstaRealizandoCorretamenteAConversao() {

        dataProviderEdital().each{
            EditalParaEmail edital= new EditalParaEmailService().formataEditalParaEmail(it[0])
            println edital.idEdital+" - "+edital.assinaturaEmail+" - "+edital.emailEnvio+" - "+edital.emailResposta
            assert (edital.assinaturaEmail!=null) == it[1]
            assert (edital.emailEnvio!=null) == it[1]
            assert (edital.emailResposta!=null) == it[1]
            assert (edital.idEdital>0) == it[1]
        }
    }

    private LocalDateTime dateFromArray(List<Integer> dataArray) {
        return new LocalDateTime(dataArray[0], dataArray[1], dataArray[2], 0, 0)
    }

    def dataProviderEdital() {

        //Unidades ficticias para teste
        def unidade1= new UnidadesEntity(idUnidade:1,codigo:null,descricao:'Unidade 1', comarca:null)
        def unidade2= new UnidadesEntity(idUnidade:2,codigo:null,descricao:'Unidade 2', comarca:null)
        def unidade3= new UnidadesEntity(idUnidade:3,codigo:null,descricao:'Unidade 3', comarca:null)
        def unidade4= new UnidadesEntity(idUnidade:4,codigo:null,descricao:'Unidade 4', comarca:null)

        //vagas ficticias para teste
        def vaga1=new VagasEditalEntity(idVagaEdital: 1,edital:null,unidade:unidade1, tipoVaga:1,candidaturas:null)
        def vaga2=new VagasEditalEntity(idVagaEdital: 2,edital:null,unidade:unidade2, tipoVaga:1,candidaturas:null)
        def vaga3=new VagasEditalEntity(idVagaEdital: 3,edital:null,unidade:unidade3, tipoVaga:1,candidaturas:null)
        def vaga4=new VagasEditalEntity(idVagaEdital: 4,edital:null,unidade:unidade4, tipoVaga:0,candidaturas:null)
        //vagas ficticias para teste-vaga 4 é do tipo 0
        List <VagasEditalEntity> listaVagas=[]
        listaVagas.add(vaga1)
        listaVagas.add(vaga2)
        listaVagas.add(vaga3)
        listaVagas.add(vaga4)
        //candidatos ficticios para teste
        def candidato1=new CandidatosEntity(idCandidato: 1,edital:null,lotacao:null,antiguidade:1,funcao:null,candidaturas:null)
        //candidato abaixo lotado na Unidade 4( vaga 4 do tipo 0)
        def candidato2=new CandidatosEntity(idCandidato: 2,edital:null,lotacao:unidade4,antiguidade:2,funcao:null,candidaturas:null)
        //candidato abaixo lotado na Unidade 2( vaga 2 do tipo 1) deve retornar erro
        def candidato3=new CandidatosEntity(idCandidato: 3,edital:null,lotacao:unidade2,antiguidade:2,funcao:null,candidaturas:null)
        //lista sem lotacao igual a vaga
        List <CandidatosEntity> listaCandOk=[]
        listaCandOk.add(candidato1)
        listaCandOk.add(candidato2)
        //lista COM lotacao igual a vaga
        List <CandidatosEntity> listaCandFail=[]
        listaCandFail.add(candidato1)
        listaCandFail.add(candidato2)
        listaCandFail.add(candidato3)
        //fechamentos ficticios para teste
        def fech1=new FechamentosEntity(idFechamento: 1,edital:null,dataFechamento:dateFromArray([2015, 02, 01]))
        def fech2=new FechamentosEntity(idFechamento: 2,edital:null,dataFechamento:dateFromArray([2015, 03, 01]))
        List <FechamentosEntity> listaFech=[]
        listaFech.add(fech1)
        listaFech.add(fech2)

        TipoEdital tipoEdital = new TipoEdital(id: 1,
        descricao: 'defensores')

        //edital correto
        def edital1=new EditaisEntity(idEdital:1
        ,descricaoEdital: "Edital de teste 001- Correto"
        ,tipoEdital: tipoEdital
        ,aberturaEdital:dateFromArray([2015, 01, 01])
        ,encerramentoEdital:dateFromArray([2015, 04, 01])
        ,fechamentos:listaFech
        ,vagasEdital:listaVagas
        ,candidato:listaCandOk
        ,assinaturaEmail:'<h3>Nome Completo de Quem Assina<br>Cargo de Quem Assina</h3>'
        ,emailResposta:'email-resposta@defensoria.rs.gov.br'
        ,emailEnvio:'email-envio@defensoria.rs.gov.br'
        )
        //edital com campos obrigatórios de emails nulos
        def edital2=new EditaisEntity(idEdital:0
        ,descricaoEdital: "Edital de teste 002- Incorreto"
        ,tipoEdital: tipoEdital
        ,aberturaEdital:dateFromArray([2015, 01, 01])
        ,encerramentoEdital:dateFromArray([2015, 04, 01])
        ,fechamentos:listaFech
        ,vagasEdital:listaVagas
        ,candidato:listaCandOk
        ,assinaturaEmail:null
        ,emailResposta:null
        ,emailEnvio:null
        )

        return [
            [edital1,   true],//edital corretamente preenchido
            [edital2,   false]//edital faltando dados obrigatórios
        ]
    }
}