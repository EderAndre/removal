package br.gov.rs.defensoria.removal.maestro.modo

import br.gov.rs.defensoria.removal.api.ModoDataAtivacao
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital

interface ModoEditalDescoberta {

    boolean isModoAtivo(ModoDataAtivacao modoDataAtivacao)

    ModoEdital getModo()
}
