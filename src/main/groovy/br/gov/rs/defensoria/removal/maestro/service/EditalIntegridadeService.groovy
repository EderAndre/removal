package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.api.EditalIntegridade
import br.gov.rs.defensoria.removal.api.EditalIntegridadeTeste
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.*
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import java.nio.file.Files
import java.nio.file.Paths

@Component
class EditalIntegridadeService {

    @Autowired
    EditaisRepository editaisRepository


    @Value('${apply.files.directory}')
    private String filesDirectory

    @Value('${output.directory}')
    private String reportsDirectory


    EditalIntegridade executaTesteIntegridade(int idEdital) {
        EditaisEntity editalLocalizado = editaisRepository.findByIdEdital(idEdital)
        return testeIntegridadeUnificado(editalLocalizado)
    }

    EditalIntegridade testeIntegridadeUnificado(EditaisEntity edital) {
        EditaisEntity editalLocalizado = edital
        List<EditalIntegridadeTeste> resultadosTestes = new ArrayList<>()

        resultadosTestes.add(encerramentoPosteriorAbertura(editalLocalizado.aberturaEdital, editalLocalizado.encerramentoEdital))
        resultadosTestes.add(possuiAoMenosUmaVaga(editalLocalizado.vagasEdital))
        resultadosTestes.add(possuiAoMenosUmCandidato(editalLocalizado.candidato))
        resultadosTestes.add(antiguidadeUnicaNoConjunto(editalLocalizado.candidato))
        resultadosTestes.add(lotacaoUnicaNoConjunto(editalLocalizado.candidato))
        resultadosTestes.add(possuiAoMenosUmFechamento(editalLocalizado.fechamentos))
        resultadosTestes.add(naoPossuiVagasIguaisLotacao(editalLocalizado.vagasEdital, editalLocalizado.candidato))
        resultadosTestes.add(camposObrigatoriosEmail(editalLocalizado.assinaturaEmail, editalLocalizado.emailEnvio))
        resultadosTestes.add(formatosCorretosEmail(editalLocalizado.emailEnvio, 'envio', false))
        resultadosTestes.add(formatosCorretosEmail(editalLocalizado.emailResposta, 'resposta'))
        filesDirectory ? resultadosTestes.add(integridadeDePasta(filesDirectory)) : false
        reportsDirectory ? resultadosTestes.add(integridadeDePasta(reportsDirectory)) : false
        resultadosTestes.add(dataSomenteDesistenciasCoerente(editalLocalizado))

        return new EditalIntegridade(
                idEdital:editalLocalizado.idEdital,
                nomeEdital:editalLocalizado.descricaoEdital,
                totalCandidatos:editalLocalizado.candidato?editalLocalizado.candidato.size():0,
                totalVagas:editalLocalizado.vagasEdital?editalLocalizado.vagasEdital.size():0,
                totalFechamentos:editalLocalizado.fechamentos?editalLocalizado.fechamentos.size():0,
                totalCandidatosSemLotacao:editalLocalizado.candidato?editalLocalizado.candidato.findAll{it.lotacao==null}.size():0,
                testes:resultadosTestes
                )
    }

    EditalIntegridadeTeste encerramentoPosteriorAbertura(LocalDateTime dataAbertura, LocalDateTime dataEncerramento){
        def observacao
        def status=true
        def comparador= dataAbertura.toDate().compareTo( dataEncerramento.toDate())
        if(comparador==0){
            status=false
            observacao="Data de encerramento igual à data de abertura"
        }
        else if(comparador>0){
            status=false
            observacao="Data de encerramento anterior  à data de abertura"
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de Datas do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste possuiAoMenosUmaVaga(List<VagasEditalEntity> vagas){
        def observacao
        def status=true
        if(vagas==null || vagas.size()<=0 ){
            status=false
            observacao="Não existem vagas Cadastradas para este Edital"
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de Vagas do Edital",
        observacao:observacao)
    }


    EditalIntegridadeTeste possuiAoMenosUmCandidato(List<CandidatosEntity> candidatos){
        def status, observacao
        if(candidatos!=null && candidatos.size()>=1){
            status=true
        }
        else if(candidatos==null || candidatos.size()<=0 ){
            status=false
            observacao="Não existem candidatos Cadastrados para este Edital"
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de candidatos do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste antiguidadeUnicaNoConjunto(List<CandidatosEntity> candidatos){
        def observacao
        def status=true
        List <String> duplicados=[]
        candidatos.each{
            def antiguidade=it.antiguidade
            def quantidade=candidatos.findAll{it.antiguidade==antiguidade}
            if(quantidade.size()>1 ){
                status=false
                duplicados.add(Duplicidade: "Candidato id:"+it.idCandidato+" | Antiguidade nr:"+antiguidade)
            }
        }
        if(!status ){
            status=false
            observacao=duplicados
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de Antiguidade dos candidatos do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste lotacaoUnicaNoConjunto(List<CandidatosEntity> candidatos){
        def status, observacao
        List<String> duplicados = []
        status = true
        candidatos.each{
            UnidadesEntity lotacao = it.lotacao
            if (lotacao != null) {
                int quantidade = candidatos.findAll{it.lotacao == lotacao}.size()
                if (quantidade > 1) {
                    status = false
                    duplicados.add(Duplicidade:"Candidato id:" + it.idCandidato + "| lotacao id:" + lotacao.idUnidade + "| lotacao descrição:" + lotacao.descricao)
                }
            }
        }
        if (!status) {
            observacao = duplicados
        }

        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de lotação dos candidatos do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste possuiAoMenosUmFechamento(List<FechamentosEntity> fechamentos){
        def status, observacao
        if(fechamentos!=null && fechamentos.size()>=1){
            status=true
        }
        else if(fechamentos==null || fechamentos.size()<=0 ){
            status=false
            observacao="Não existem fechamentos cadastrados para este Edital"
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de Fechamentos do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste naoPossuiVagasIguaisLotacao(List<VagasEditalEntity> vagas, List<CandidatosEntity> candidatos){
        def observacao, unidade
        def status=true
        List <String> erros=[]
        vagas.each{
            unidade=it.unidade
            if(it.tipoVaga==1){
                CandidatosEntity candidatoAchado=candidatos.find{it.lotacao==unidade}
                if(candidatoAchado){
                    erros.add(Erro: 'Unidade ID:'+unidade.idUnidade+
                    '| Unidade desc:'+unidade.descricao+
                    '| Candidato ID:'+candidatoAchado.idCandidato+
                    '| Candidato Lotacao ID:'+candidatoAchado.lotacao.idUnidade)
                }
            }

        }
        if(erros.size()>0 ){
            status=false
            observacao=erros
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de Vagas frente à lotação dos Candidatos do Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste camposObrigatoriosEmail(String assinaturaEmail, String emailEnvio){
        def observacao
        def status=true
        List <String> erros=[]
        assinaturaEmail==null?erros.add(Erro:"Variável de assinatura do edital é nula"):false
        emailEnvio==null?erros.add(Erro:"e-mail de envio do edital é nulo"):false
        if(erros.size()>0 ){
            status=false
            observacao=erros
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:"Integridade de variaveis que não podem ser nulas no Edital",
        observacao:observacao)
    }

    EditalIntegridadeTeste formatosCorretosEmail(String email, String tag, boolean permitirNulo=true){
        def observacao
        def status=true
        List <String> erros=[]
        def emailPattern = /[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})/
        if(permitirNulo){
            !(email==~emailPattern) && email!=null?erros.add(Erro:String.format("o e-mail de %s %s não parece estar em formato correto",tag,email)):false
        }
        else{
            email==null || !(email==~ emailPattern)?erros.add(Erro:String.format("o e-mail de %s %s não parece estar em formato correto",tag,email)):false
        }
        if(erros.size()>0 ){
            status=false
            observacao=erros
        }
        return new EditalIntegridadeTeste(status:status,
        descricao:String.format("Integridade de formato de email de %s do edital",tag),
        observacao:observacao)
    }

    EditalIntegridadeTeste integridadeDePasta(String path){
        File caminho = new File(path)
        def observacao
        def status=true
        List <String> erros=[]
        if(!caminho.exists()){
            status=false
            erros.add("Diretório ${path} inexistente")
        }
        else{
            if(!Files.isWritable(Paths.get(path))){
                status=false
                erros.add("Diretório ${path} sem permissão de escrita")
            }
        }
        if(erros.size()>0 ){
            status=false
            observacao=erros
        }
        return new EditalIntegridadeTeste( status:status,
        descricao:String.format("Integridade da pasta %s no sistema",path),
        observacao:observacao)
    }

    EditalIntegridadeTeste dataSomenteDesistenciasCoerente(EditaisEntity edital){
        String observacao
        boolean status = edital.apenasDesistenciasPermitidas == null || edital.apenasDesistenciasPermitidas > edital.aberturaEdital && edital.apenasDesistenciasPermitidas < edital.encerramentoEdital
        if (!status) {
            observacao = 'A data de início do período de somente desistências deve estar entre a abertura e o fechamento.'
        }

        return new EditalIntegridadeTeste(status: status,
        descricao: 'Data de início do período de somente desistências entre abertura e fechamento',
        observacao: observacao)
    }
}
