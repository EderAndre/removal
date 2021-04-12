package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.api.Fechamento
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity

/**
 * Classe ListaFechamentosService.
 *
 * <P> classe responsável pela exibição de lista de Fechamentos de determinado edital
 *para o usuário Operador ou Admin
 *
 * @author Eder André Soares, Unidade de Sistemas-DPE-RS
 *
 */
@Component
class ListaFechamentosService {

	@Autowired
	FechamentosRepository fechamentosRepository

	@Autowired
	EditaisRepository editaisRepository

	private String msgErro="Não existem fechamentos  listados para este edital"

	/**
	 * <p>
	 * Método que apresenta uma lista de objetos com os fechamentos de deteminado edital
	 *
	 * @param  idedital  identificador unico do edital no repositório
	 * @return lista de fechamentos
	 */
	List <Fechamento> exibeFechamentosEdital(int idEdital)  {
		List <Fechamento> fechamentos=[]
		DateTimeFormatter fmt = DateTimeFormat.forPattern('dd/MM/yyyy - HH:mm')
		def dataHoraAtual=new LocalDateTime().now()
		getFechamentos(idEdital).each{
			def relFech=''
			it.relatorioFechamento?relFech='/edital/'+it.edital.idEdital+'/fechamentos/'+it.idFechamento+'/relatorio/relatorioFechamento/download':''
			def relPrel=''
			it.relatorioPreliminar?relPrel='/edital/'+it.edital.idEdital+'/fechamentos/'+it.idFechamento+'/relatorio/relatorioPreliminar/download':''
			fechamentos.add(

					idFechamento: it.idFechamento,
					dataHora:fmt.print(it.dataFechamento),
					status:dataHoraAtual.compareTo(it.dataFechamento)<0?'Aguardando':'Realizado',
					relatorioFechamento:relFech,
					relatorioPreliminar:relPrel
					)
		}
		if(fechamentos.size>0){
			return fechamentos
		}
		else{
			return msgErro
		}
	}
	private  List <FechamentosEntity> getFechamentos(int idEdital){
		EditaisEntity edital=editaisRepository.findByIdEdital(idEdital)
		return edital.fechamentos
	}


}
