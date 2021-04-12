package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.EditalIntegridade
import br.gov.rs.defensoria.removal.repository.entity.*
import org.joda.time.LocalDateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles("IT")
@SpringBootTest
class EditalIntegridadeServiceTest {

    @Value('${apply.files.directory}')
    private String filesDirectory

    @Autowired
    private EditalIntegridadeService servicoIntegridade

    EditalIntegridadeService servicoIntegridade

    @Before
    void setUp() {
        servicoIntegridade = new EditalIntegridadeService()
    }

    @Test
    void 'testa se abertura ocorre antes do encerramento'() {
        dataProviderDataFechamento().each {
            LocalDateTime abertura = dateFromArray(it[0])
            LocalDateTime encerramento = dateFromArray(it[1])
            assert servicoIntegridade.encerramentoPosteriorAbertura(abertura, encerramento).status == it[2]
        }
    }

    @Test
    void 'testa se existe pelo menos uma vaga cadastrada para o edital'() {
        dataProviderQuantidadeVagas().each {
            assert servicoIntegridade.possuiAoMenosUmaVaga(it[0]).status == it[1]
        }
    }

    @Test
    void 'testa se existe pelo menos um candidato cadastrado para o edital'() {
        dataProviderQuantidadeCandidatos().each {
            assert servicoIntegridade.possuiAoMenosUmCandidato(it[0]).status == it[1]
        }
    }

    @Test
    void 'testa se existe vaga que e lotacao de algum candidato cadastrado para o edital'() {
        dataProviderLotacaoVagas().each {
            assert servicoIntegridade.naoPossuiVagasIguaisLotacao(it[0], it[1]).status == it[2]
        }
    }

    @Test
    void 'testa se existe pelo menos um fechamento cadastrado para o edital'() {
        dataProviderFechamentos().each {
            assert servicoIntegridade.possuiAoMenosUmFechamento(it[0]).status == it[1]
        }
    }

    @Test
    void 'testa se existe candidatos com antiguidade duplicada no edital'() {
        dataProviderCandidatosMesmaAntiguidade().each {
            assert servicoIntegridade.antiguidadeUnicaNoConjunto(it[0]).status == it[1]
        }
    }

    @Test
    void 'testa se existe candidatos com a lotação duplicada no edital'() {
        dataProviderCandidatosLotacao().each {
            assert servicoIntegridade.lotacaoUnicaNoConjunto(it[0]).status == it[1]
        }
    }

    @Test
    void 'testa se um edital preenche todos os requisitos de integridade'() {
        dataProviderEdital().each {
            int contadorErros = 0
            EditalIntegridade unificado = servicoIntegridade.testeIntegridadeUnificado(it[0])
            int tamanho = unificado.testes.size()
            assert tamanho == 11

            unificado.testes.each{
                if (!it.status) {
                    contadorErros++
                }
            }
            assert contadorErros == it[1]
            assert (contadorErros==0) == it[2]
        }
    }

    @Test
    void 'testa se as variáveis obrigatórias do envio de email estão corretas no edital'() {
        dataProviderVariaveisNulasEmail().each{
            def cenario = servicoIntegridade.camposObrigatoriosEmail(it[0],it[1])
            assert cenario.status == it[2]
        }
    }
    @Test
    void 'testa se os endereços de email são adequados no edital'() {
        dataProviderFormatoEmail().each{
            def cenario=servicoIntegridade.formatosCorretosEmail(it[0],it[1],it[2])

            assert cenario.status == it[3]
        }
    }

    private LocalDateTime dateFromArray(List<Integer> dataArray) {
        return new LocalDateTime(dataArray[0], dataArray[1], dataArray[2], 0, 0)
    }

    def dataProviderFormatoEmail() {
        return [//   assinatura	   ,  	email de Envio , 	expectativa
            ['teste-sobrenome@defensoria.rs.gov.br', 'envio', false, true], ['teste-sobrenome-defensoria.rs.gov.br', 'envio', false, false], [null, 'envio', false, false], ['teste-sobrenome@defensoria.rs.gov.br', 'resposta', true, true], ['teste-sobrenome--------.rs.gov', 'resposta', true, false], [null, 'resposta', true, true],]
    }
    def dataProviderVariaveisNulasEmail() {
        return [//   assinatura	   ,  	email de Envio , 	expectativa
            ['<H3>ASSINATURA<H3>', 'teste@teste.com', true], [null, 'teste@teste.com', false], ['<H3>ASSINATURA<H3>', null, false], [null, null, false],]
    }
    def dataProviderDataFechamento() {
        return [//abertura	   ,  fechamento , expectativa
            [[2015, 01, 01], [2015, 02, 01], true], [[2015, 02, 01], [2015, 01, 01], false], [[2015, 02, 01], [2015, 02, 01], false],]
    }
    def dataProviderFechamentos() {

        //fechamentos ficticios para teste
        def fech1=new FechamentosEntity(idFechamento: 1,edital:null,dataFechamento:null)
        def fech2=new FechamentosEntity(idFechamento: 2,edital:null,dataFechamento:null)

        List <FechamentosEntity> listaNula=null
        List <FechamentosEntity> listaZerada=[]
        List <FechamentosEntity> lista1=[]
        lista1.add(fech1)
        List <FechamentosEntity> lista2=[]
        lista2.add(fech1)
        lista2.add(fech2)


        return [//lista de fechamentos,expectativa
            [lista1, true], // caso a lista passade tenha apenas 1 fechamento
            [lista2, true], // lista com mais de 1 fechamento
            [listaZerada, false], // lista sem vagas
            [listaNula, false],
            // lista nula
        ]
    }
    def dataProviderQuantidadeVagas() {
        //Unidades ficticias para teste
        def unidade1= new UnidadesEntity(idUnidade:1,codigo:null,descricao:'Unidade 1', comarca:null)
        def unidade4= new UnidadesEntity(idUnidade:4,codigo:null,descricao:'Unidade 4', comarca:null)
        def unidade3= new UnidadesEntity(idUnidade:3,codigo:null,descricao:'Unidade 3', comarca:null)
        //vagas ficticias para teste
        def vaga1=new VagasEditalEntity(idVagaEdital: 1,edital:null,unidade:null, tipoVaga:1,candidaturas:null)
        def vaga2=new VagasEditalEntity(idVagaEdital: 2,edital:null,unidade:null, tipoVaga:1,candidaturas:null)
        List <VagasEditalEntity> listaNula=null
        List <VagasEditalEntity> listaZerada=[]
        List <VagasEditalEntity> lista1=[]
        lista1.add(vaga1)
        List <VagasEditalEntity> lista2=[]
        lista2.add(vaga1)
        lista2.add(vaga2)


        return [//lista de vagas,expectativa
            [lista1, true], // caso a lista passade tenha apenas 1 vaga
            [lista2, true], // lista com mais de 1 vaga
            [listaZerada, false], // lista sem vagas
            [listaNula, false],// lista nula
        ]
    }

    def dataProviderQuantidadeCandidatos() {
        //candidatos ficticios para teste
        def candidato1=new CandidatosEntity(idCandidato: 1,edital:null,lotacao:null,antiguidade:1,funcao:null,candidaturas:null)
        def candidato2=new CandidatosEntity(idCandidato: 2,edital:null,lotacao:null,antiguidade:2,funcao:null,candidaturas:null)

        List <CandidatosEntity> listaNula=null
        List <CandidatosEntity> listaZerada=[]
        List <CandidatosEntity> lista1=[]
        lista1.add(candidato1)
        List <CandidatosEntity> lista2=[]
        lista2.add(candidato1)
        lista2.add(candidato2)

        return [//lista de candidatos,expectativa
            [lista1, true], // caso a lista passade tenha apenas 1 candidato
            [lista2, true], // lista com mais de 1 candidato
            [listaZerada, false], // lista sem vagas
            [listaNula, false],// lista nula
        ]
    }
    def dataProviderCandidatosLotacao() {
        //Unidades ficticias para teste
        def unidade1= new UnidadesEntity(idUnidade:1,codigo:null,descricao:'Unidade 1', comarca:null)
        def unidade2= new UnidadesEntity(idUnidade:2,codigo:null,descricao:'Unidade 2', comarca:null)
        //candidatos ficticios para teste
        def candidato1=new CandidatosEntity(idCandidato: 1,edital:null,lotacao:unidade1,antiguidade:1,funcao:null,candidaturas:null)
        def candidato2=new CandidatosEntity(idCandidato: 2,edital:null,lotacao:unidade2,antiguidade:2,funcao:null,candidaturas:null)
        def candidato3=new CandidatosEntity(idCandidato: 3,edital:null,lotacao:null,antiguidade:3,funcao:null,candidaturas:null)
        def candidato4=new CandidatosEntity(idCandidato: 4,edital:null,lotacao:unidade1,antiguidade:4,funcao:null,candidaturas:null)
        // a lista abaixo contem os candidatos sem unidades de lotação repetidas
        List <CandidatosEntity> lista3=[]
        lista3.add(candidato1)
        lista3.add(candidato2)
        lista3.add(candidato3)
        //a lista abaixo contem os candidatos 1 e 4 com a mesma lotação- Unidade 1
        List <CandidatosEntity> lista4=[]
        lista4.add(candidato1)
        lista4.add(candidato2)
        lista4.add(candidato4)

        return [//lista de candidatos,expectativa
            [lista3, true], // lista com mais de 1 candidato- lotação em duplicidade(1 e 3)
            [lista4, false],]
    }
    def dataProviderCandidatosMesmaAntiguidade() {
        //Unidades ficticias para teste
        def unidade1= new UnidadesEntity(idUnidade:1,codigo:null,descricao:'Unidade 1', comarca:null)
        def unidade2= new UnidadesEntity(idUnidade:2,codigo:null,descricao:'Unidade 2', comarca:null)
        //candidatos ficticios para teste
        def candidato1=new CandidatosEntity(idCandidato: 1,edital:null,lotacao:unidade1,antiguidade:1,funcao:null,candidaturas:null)
        def candidato2=new CandidatosEntity(idCandidato: 2,edital:null,lotacao:unidade2,antiguidade:2,funcao:null,candidaturas:null)
        def candidato3=new CandidatosEntity(idCandidato: 3,edital:null,lotacao:null,antiguidade:2,funcao:null,candidaturas:null)
        //lista sem antiguidades repetidas
        List <CandidatosEntity> lista1=[]
        lista1.add(candidato1)
        lista1.add(candidato2)
        // a lista abaixo contem os candidatos 2 e 3 com a mesma antiguidade- 2
        List <CandidatosEntity> lista2=[]
        lista2.add(candidato1)
        lista2.add(candidato2)
        lista2.add(candidato3)

        return [//lista de candidatos,expectativa
            [lista1, true], // lista correta
            [lista2, false],// lista com mesma antiguidade para dois candidatos
        ]
    }
    def dataProviderLotacaoVagas() {
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
        List <VagasEditalEntity> lista=[]
        lista.add(vaga1)
        lista.add(vaga2)
        lista.add(vaga3)
        lista.add(vaga4)
        //candidatos ficticios para teste
        def candidato1=new CandidatosEntity(idCandidato: 1,edital:null,lotacao:null,antiguidade:1,funcao:null,candidaturas:null)
        //candidato abaixo lotado na Unidade 4( vaga 4 do tipo 0)
        def candidato2=new CandidatosEntity(idCandidato: 2,edital:null,lotacao:unidade4,antiguidade:2,funcao:null,candidaturas:null)
        //candidato abaixo lotado na Unidade 2( vaga 2 do tipo 1) deve retornar erro
        def candidato3=new CandidatosEntity(idCandidato: 3,edital:null,lotacao:unidade2,antiguidade:2,funcao:null,candidaturas:null)
        //lista sem lotacao igual a vaga
        List <CandidatosEntity> listaCand2=[]
        listaCand2.add(candidato1)
        listaCand2.add(candidato2)
        //lista COM lotacao igual a vaga
        List <CandidatosEntity> listaCand3=[]
        listaCand3.add(candidato1)
        listaCand3.add(candidato2)
        listaCand3.add(candidato3)

        return [//lista de vagas,lista de candidatos, expectativa
            [lista, listaCand2, true], //listas sem lotacao==vaga.unidade(tipo1)
            [lista, listaCand3, false],//listas COM lotacao==vaga.unidade(candidato 3)
        ]
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
        EditaisEntity edital1 = new EditaisEntity(idEdital: 1,
        descricaoEdital: "Edital de teste 001- Correto",
        tipoEdital: tipoEdital,
        aberturaEdital: new LocalDateTime(2015, 1, 1, 0, 0),
        encerramentoEdital: new LocalDateTime(2015, 4, 1, 0, 0),
        fechamentos: listaFech,
        vagasEdital: listaVagas,
        candidato: listaCandOk,
        assinaturaEmail: "<h3>Assinatura</h3>",
        emailEnvio: "teste@defensoria.rs.gov.br",
        emailResposta: "teste-resposta@defensoria.rs.gov.br",
        apenasDesistenciasPermitidas: new LocalDateTime(2015, 3, 30, 0, 0)
        )

        EditaisEntity edital2 = new EditaisEntity(idEdital: 2,
        descricaoEdital: "Edital de teste 002- 3 erros",
        tipoEdital: tipoEdital,
        aberturaEdital: new LocalDateTime(2015, 2, 1, 0, 0), //erro 1 datas invertidas
        encerramentoEdital: new LocalDateTime(2015, 1, 1, 0, 0),
        fechamentos: listaFech,
        vagasEdital: listaVagas,
        candidato: listaCandFail, //erro 2 - candidato com lotação igual a vagas abertas
        assinaturaEmail: null, //erro3 - assinatura não pode ser nula
        emailEnvio: "teste@defensoria.rs.gov.br",
        emailResposta:"teste-resposta@defensoria.rs.gov.br",
        apenasDesistenciasPermitidas: null
        )

        EditaisEntity edital3 = new EditaisEntity(idEdital: 3,
        descricaoEdital: "Edital de teste 003- 4 erros",
        tipoEdital: tipoEdital,
        aberturaEdital: new LocalDateTime(2015, 2, 1, 0, 0), //erro 1 datas invertidas
        encerramentoEdital: new LocalDateTime(2015, 1, 1, 0, 0),
        fechamentos: null, //erro 2 fechamentos não nulo
        vagasEdital: listaVagas,
        candidato: listaCandFail, //erro 3 - candidato com lotação igual a vagas abertas
        assinaturaEmail: "<h3>Assinatura</h3>",
        emailEnvio: "teste@defensoria.rs.gov.br",
        emailResposta: null,
        apenasDesistenciasPermitidas: null
        )

        EditaisEntity edital4 = new EditaisEntity(idEdital: 3,
        descricaoEdital: "Edital de teste 004- 5 erros",
        tipoEdital: tipoEdital,
        aberturaEdital: new LocalDateTime(2015, 2, 1, 0, 0), //erro 1 datas invertidas
        encerramentoEdital: new LocalDateTime(2015, 1, 1, 0, 0),
        fechamentos: null,//erro 2 - tem que haver pelo menos 1 fechamento
        vagasEdital: listaVagas,
        candidato: listaCandFail,//erro 3 - candidato com lotação igual a vagas abertas
        assinaturaEmail: "<h3>Assinatura</h3>",
        emailEnvio: null, //erro 4 - envio não pode ser nulo
        emailResposta: "teste-resposta-defensoria.rs.gov.br", //erro 5 - formato incorreto
        apenasDesistenciasPermitidas: null
        )

        return [//edital, nr erros, expectativa
            [edital1, 0 , true], //edital corretamente preenchido
            [edital2, 4 , false], //edital com erros de data- duplicidade de antiguidade, lotação como vaga tipo 1(3 erros)
            [edital3, 4 , false], //edital com erros de data- duplicidade de antiguidade, lotação como vaga tipo 1, sem fechamentos(4 erros)
            [edital4, 7 , false],]
    }
}
