package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.junit.Before
import org.junit.Test

import br.gov.rs.defensoria.removal.repository.entity.ArquivosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.TipoEdital

class ArquivoServiceTest {

    private ArquivoService servico

    @Before
    void setUp() {
        servico = new ArquivoService()
    }

    @Test
    void testaSeOCaminhoDoArquivoSolicitadoEstaCorreto() {
        servico.filesDirectory="caminho/de/teste/"
        assert servico.caminhoDoArquivo(dataProviderArquivoOK()) == servico.filesDirectory+'teste.png'
    }

    @Test
    void 'testa se retorna falso quando o arquivo está indisponível'() {
        assert !servico.caminhoDoArquivo(dataProviderArquivoFail())
    }

    @Test
    void 'testa se retorna falso quando o nome do arquivo é vazio'() {
        assert !servico.caminhoDoArquivo(dataProviderArquivoFail2())
    }

    private ArquivosEntity dataProviderArquivoOK() {
        ArquivosEntity arquivoOk = new ArquivosEntity(
                idArquivo: 1
                ,edital:dataProviderEditalOk()
                ,dataArquivo:dateFromArray([2015, 03, 01])
                ,descricao:'Arquivo liberado'
                ,nomeArquivo:'teste.png'
                ,exibir:1)
        return arquivoOk
    }
    private ArquivosEntity dataProviderArquivoFail() {
        ArquivosEntity arquivoFail = new ArquivosEntity(
                idArquivo: 2
                ,edital:dataProviderEditalOk()
                ,dataArquivo:dateFromArray([2015, 02, 01])
                ,descricao:'Arquivo bloqueado'
                ,nomeArquivo:'teste.pdf'
                ,exibir:0)//bloqueado
        return arquivoFail
    }
    private ArquivosEntity dataProviderArquivoFail2() {
        ArquivosEntity arquivoFail2 = new ArquivosEntity(
                idArquivo: 3
                ,edital:dataProviderEditalOk()
                ,dataArquivo:dateFromArray([2015, 02, 01])
                ,descricao:'Arquivo vazio'
                ,nomeArquivo:''//vazio
                ,exibir:0)
        return arquivoFail2
    }

    private EditaisEntity dataProviderEditalOk(){
        TipoEdital tipoEdital = new TipoEdital(id: 1,
        descricao: 'defensores')

        def edital1=new EditaisEntity(idEdital:1
        ,descricaoEdital: "Edital de teste 001- Correto"
        ,tipoEdital: tipoEdital
        ,aberturaEdital:dateFromArray([2015, 01, 01])
        ,encerramentoEdital:dateFromArray([2015, 04, 01])
        ,fechamentos:null
        ,vagasEdital:null
        ,candidato:null
        ,assinaturaEmail:"<h3>Assinatura</h3>"
        ,emailEnvio:"teste@defensoria.rs.gov.br"
        ,emailResposta:"teste-resposta@defensoria.rs.gov.br"
        )
        return edital1
    }

    private LocalDateTime dateFromArray(List<Integer> dataArray) {
        return new LocalDateTime(dataArray[0], dataArray[1], dataArray[2], 0, 0)
    }
}
