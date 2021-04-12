package br.gov.rs.defensoria.removal.web.controller

import br.gov.rs.defensoria.removal.api.ArquivoUpload
import br.gov.rs.defensoria.removal.api.ObjArquivo
import br.gov.rs.defensoria.removal.maestro.service.ArquivoService
import br.gov.rs.defensoria.removal.maestro.service.UploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse

@RestController
class ArquivosController {

    @Autowired
    ArquivoService arquivoService

    @Autowired
    UploadService uploadService

    @RequestMapping(value="/edital/{editalId}/arquivo/{idArquivo}/download", method=RequestMethod.GET)
    visualizarArquivoController(
            @PathVariable int idArquivo
            , @PathVariable int editalId
            , HttpServletResponse response
    )throws Exception{
        def executor=arquivoService.exibeArquivoEdital(idArquivo, response)
        if(executor!=true)
            throw new Exception(executor)
    }

    @RequestMapping('/edital/{idEdital}/infos-arquivos')
    List<ObjArquivo> getInfosArquivos(@PathVariable int idEdital) {

        return arquivoService.getInfosArquivos(idEdital)
    }

    @RequestMapping(value="/edital/{idEdital}/arquivo/upload", method=RequestMethod.POST)
    void uploadDeArquivo(@RequestParam(value="uploadedFile") MultipartFile file, @RequestParam String descricao, @RequestParam int exibir, @PathVariable int idEdital){
        if(file.isEmpty()){
            throw new RuntimeException( "O arquivo postado é vazio.")
        }
        String prefixo = "${idEdital}_${System.nanoTime()}_"
        String nomeOriginal = file.getOriginalFilename()
        String nome = prefixo + nomeOriginal
        nome = nome.replaceAll("[^a-zA-Z0-9.-]", "_")

        ArquivoUpload arquivo=new ArquivoUpload(
                idEdital: idEdital
                ,descricaoArquivo:descricao
                ,exibir:exibir
                ,nomeArquivo:nome
                )

        uploadService.uploadArquivo(nome, file.getBytes())
        arquivoService.novoArquivo(arquivo)
    }

    @RequestMapping(value="/edital/{idEdital}/arquivo/{idArquivo}/status/altera", method=RequestMethod.GET)
    boolean alteraStatusArquivo(@PathVariable("idEdital") int idEdital, @PathVariable("idArquivo") int idArquivo){
        arquivoService.alteraStatus(idArquivo)
    }
}
