package br.gov.rs.defensoria.removal.report

class Utils {
  
    String abreviarNome(String nome){

        return nome
                .replaceAll('DEFENSORIA', 'D')
                .replaceAll('PUBLICA', 'P')
                .replaceAll('PÚBLICA', 'P')
                .replaceAll('ESPECIALIZADA', 'ESP')
    }
}
