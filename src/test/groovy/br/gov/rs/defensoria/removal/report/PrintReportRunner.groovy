package br.gov.rs.defensoria.removal.report

import java.io.IOException
import java.text.SimpleDateFormat
import java.util.List

import br.gov.rs.defensoria.removal.core.RemovalService
import br.gov.rs.defensoria.removal.domain.*
import br.gov.rs.defensoria.removal.report.presentation.ResultRowWinnersWithPretensions
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions


class PrintReportRunner {

	static void main(String[] args) {
		System.out.println("Gerando relatório...")
		String sData = "24/04/2015 18:00:00"
		def rem=new RemovalReports()
		rem.generate(dataSource(),sData,"build/reports/","testeRefactor2","pdf")
		
		
		/*	new RemovalReports().generate(dataSource(),"build/reports/","teste","html")
		 new RemovalReports().generate(dataSource(),"build/reports/","teste","csv")
		 new RemovalReports().generate(dataSource(),"build/reports/","teste","xls")
		 new RemovalReports().generate(dataSource(),"build/reports/","teste","rtf")
		 new RemovalReports().generate(dataSource(),"build/reports/","teste","kkk")*/
	
	}

	
	
	static ResultTableWinnersWithPretensions dataSource() {
		List<Office> choosable = new ArrayList<Office>()
		List<Person> candidates = new ArrayList<Person>()
		List<Office> pretensions = new ArrayList<Office>()
		
		Office campoBom = new Office(id: 1, name: '2ª campo bom') 
		Office sapiranga = new Office(id: 2, name: 'sapiranga')  
		Office caxiasDoSul = new Office(id: 3, name: 'caxias do sul')
		Office novoHamburgo = new Office(id: 4, name: 'novo hamburgo')
		Office portoAlegre = new Office(id: 5, name: 'porto alegre')
		Office alvorada = new Office(id: 6, name: 'alvorada')
		Office gravatai = new Office(id: 7, name: 'gravatai')
		Office seberi = new Office(id: 8, name: 'seberi')
		Office pousoNovo = new Office(id: 9, name: 'pouso novo')
		Office garibaldi = new Office(id: 10, name: 'garibaldi')
		Office santamaria = new Office(id: 10, name: 'santa maria')
		Office charqueadas = new Office(id: 10, name: 'charqueadas')
		
		pretensions.add(sapiranga)
	
		pretensions.add(portoAlegre)
		pretensions.add(pousoNovo)
		Person pedro = new Person(id: 2, name: 'pedro', allocation: novoHamburgo, pretensions: pretensions, antiquity: 11)
		novoHamburgo.setOccupant(pedro)
		
		pretensions = new ArrayList<Office>()
		pretensions.add(alvorada)
		pretensions.add(sapiranga)
		pretensions.add(gravatai)
		Person maria = new Person(id: 1, name: 'maria', allocation: sapiranga, pretensions: pretensions, antiquity: 10)
		sapiranga.setOccupant(maria)
		
		pretensions = new ArrayList<Office>()
		pretensions.add(gravatai)
		pretensions.add(portoAlegre)
		pretensions.add(novoHamburgo)
		Person paula = new Person(id: 3, name: 'paula', allocation: alvorada, pretensions: pretensions, antiquity: 999)
		alvorada.setOccupant(paula)
		
		pretensions = new ArrayList<Office>()
		pretensions.add(portoAlegre)
		pretensions.add(novoHamburgo)
		pretensions.add(garibaldi)
		Person jose = new Person(id: 4, name: 'jose', allocation: gravatai, pretensions: pretensions, antiquity: 19)
		gravatai.setOccupant(jose)
		
		pretensions = new ArrayList<Office>()
		pretensions.add(novoHamburgo)
		pretensions.add(alvorada)
		pretensions.add(sapiranga)
		Person carlos = new Person(id: 5, name: 'carlos', allocation: portoAlegre, pretensions: pretensions, antiquity: 18)
		portoAlegre.setOccupant(carlos)
		
		candidates.add(pedro)
		candidates.add(maria)
		candidates.add(paula)
		candidates.add(jose)
		candidates.add(carlos)
		
		choosable.add(campoBom)
		choosable.add(sapiranga)
		choosable.add(caxiasDoSul)
		choosable.add(novoHamburgo)
		choosable.add(portoAlegre)
		choosable.add(alvorada)
		choosable.add(gravatai)
		choosable.add(santamaria)
		choosable.add(charqueadas)
		
		def resultTableWinnersWithPretensions = new ResultTableWinnersWithPretensions()

		def removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(choosable, candidates)
		return resultTableWinnersWithPretensions.generateTable(candidates, result)
	}
}