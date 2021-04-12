package br.gov.rs.defensoria.removal.report

import static org.junit.Assert.*
import br.gov.rs.defensoria.removal.core.RemovalService
import br.gov.rs.defensoria.removal.domain.*
import br.gov.rs.defensoria.removal.report.ExportPretensionsToCSV
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions

import org.junit.Ignore
import org.junit.Test

class ExportPretensionsToCSVTest {

    @Test
    void generateCorrectHeader() {
        given:
        Office campoBom = new Office(id: 1, name: 'campo bom')
        Office sapiranga = new Office(id: 2, name: 'sapiranga')
        Office caxiasDoSul = new Office(id: 3, name: 'caxias do sul')
        Office novoHamburgo = new Office(id: 4, name: 'novo hamburgo')
        Office portoAlegre = new Office(id: 5, name: 'porto alegre')
        Office alvorada = new Office(id: 6, name: 'alvorada')
        Office gravatai = new Office(id: 7, name: 'gravatai')
        Office seberi = new Office(id: 8, name: 'seberi')
        Office pousoNovo = new Office(id: 9, name: 'pouso novo')
        Office garibaldi = new Office(id: 10, name: 'garibaldi')

        List<Office> choosable = new ArrayList<Office>()
        choosable.add(campoBom)
        choosable.add(sapiranga)
        choosable.add(caxiasDoSul)
        choosable.add(novoHamburgo)
        choosable.add(portoAlegre)
        choosable.add(alvorada)
        choosable.add(gravatai)
        when:
        def resultTable = new ResultTableWinnersWithPretensions(officesList: choosable)
        def defaultColumns = ['Antiguidade', 'Pessoa', 'Origem', 'Destino']
        def exportCSV = new ExportPretensionsToCSV(defaultColumns: defaultColumns)
        List<String> resultHeader = exportCSV.generateHeader(resultTable)

        then:
        assert resultHeader.size() == defaultColumns.size()+choosable.size()
    }

    boolean isLineSeparatorLinux(String separator) {
        return separator.length() == 1
    }
}
