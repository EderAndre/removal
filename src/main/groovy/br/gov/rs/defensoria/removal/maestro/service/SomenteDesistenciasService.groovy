package br.gov.rs.defensoria.removal.maestro.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import br.gov.rs.defensoria.removal.api.DadosRealizacaoCandidatura
import br.gov.rs.defensoria.removal.api.ObjConstroiPedido
import br.gov.rs.defensoria.removal.api.UnidadeConstroiPedido
import br.gov.rs.defensoria.removal.api.Vaga

@Service
class SomenteDesistenciasService {

    @Autowired
    ObjConstroiPedidoService objConstroiPedidoService

    boolean isDesistencia(DadosRealizacaoCandidatura dadosRealizacaoCandidatura) {

        return isSemNovasVagas(dadosRealizacaoCandidatura) && isMesmaOrdem(dadosRealizacaoCandidatura)
    }

    private boolean isSemNovasVagas(DadosRealizacaoCandidatura dadosRealizacaoCandidatura) {
        List<Vaga> vagasNovoPedido = dadosRealizacaoCandidatura.pedido.candidatura.locais

        ObjConstroiPedido pedidoAnterior = objConstroiPedidoService.getObjConstroiPedido(dadosRealizacaoCandidatura.idEdital,
                dadosRealizacaoCandidatura.idCandidato)
        List<UnidadeConstroiPedido> vagasPedidoAnterior = pedidoAnterior.vagas.findAll { it.escolhida }

        boolean hasNew = false
        vagasNovoPedido.each { vagaNovoPedido ->
            UnidadeConstroiPedido procurada = vagasPedidoAnterior.find {
                it.id == vagaNovoPedido.id
            }

            if (procurada == null) {
                hasNew = true
            }
        }

        if (hasNew) {
            return false
        }

        return true
    }

    boolean isMesmaOrdem(DadosRealizacaoCandidatura dadosRealizacaoCandidatura) {
        List<Vaga> vagasNovoPedido = dadosRealizacaoCandidatura.pedido.candidatura.locais

        ObjConstroiPedido pedidoAnterior = objConstroiPedidoService.getObjConstroiPedido(dadosRealizacaoCandidatura.idEdital,
                dadosRealizacaoCandidatura.idCandidato)
        List<UnidadeConstroiPedido> vagasPedidoAnterior = pedidoAnterior.vagas.findAll { it.escolhida }

        boolean hasChange = false
        vagasPedidoAnterior.each { vagaPedidoAnterior ->
            Vaga ordemAvaliada = vagasNovoPedido.find {
                it.ordem == vagaPedidoAnterior.ordem
            }

            if (posicaoExistenteNoPedidoAnteriorEstaNoNovoPedido(ordemAvaliada)) {
                if (mudouVagaNaPosicao(ordemAvaliada, vagaPedidoAnterior)) {
                    if (ordemMudouDevidoInsercao(vagasPedidoAnterior, vagasNovoPedido)
                    || vagaTrocouDeOrdem(ordemAvaliada, vagasNovoPedido, vagasPedidoAnterior)) {

                        hasChange = true
                    }
                }
            }
        }

        if (hasChange) {
            return false
        }

        return true
    }

    private boolean posicaoExistenteNoPedidoAnteriorEstaNoNovoPedido(Vaga vaga) {

        return vaga != null
    }

    private boolean mudouVagaNaPosicao(Vaga ordemAvaliada, UnidadeConstroiPedido vagaPedidoAnterior) {

        return ordemAvaliada?.id != vagaPedidoAnterior.id
    }

    private boolean ordemMudouDevidoInsercao(List<UnidadeConstroiPedido> vagasPedidoAnterior, List<Vaga> vagasNovoPedido) {

        return vagasPedidoAnterior.size() < vagasNovoPedido.size()
    }

    private boolean vagaTrocouDeOrdem(Vaga suspeita, List<Vaga> vagasNovoPedido, List<UnidadeConstroiPedido> vagasPedidoAnterior) {
        List<UnidadeConstroiPedido> anterioresExcluidasDesistencias = new ArrayList<>()
        vagasPedidoAnterior.each { vagaPedidoAnterior ->
            Vaga procurada = vagasNovoPedido.find {
                it.id == vagaPedidoAnterior.id
            }
            if (procurada) {
                anterioresExcluidasDesistencias.add(vagaPedidoAnterior)
            }
        }

        UnidadeConstroiPedido suspeitaNaListaAnterior = anterioresExcluidasDesistencias.find { it.id == suspeita.id }

        for (UnidadeConstroiPedido anterior : anterioresExcluidasDesistencias) {
            boolean isSuspeitaPosteriorNaListaVelha = anterior.ordem < suspeitaNaListaAnterior.ordem

            Vaga anteriorNaListaNova = vagasNovoPedido.find { it.id == anterior.id }

            boolean isSuspeitaPosteriorNaListaNova = anteriorNaListaNova.ordem < suspeita.ordem

            if (isSuspeitaPosteriorNaListaVelha != isSuspeitaPosteriorNaListaNova) {
                return true
            }
        }

        return false
    }
}
