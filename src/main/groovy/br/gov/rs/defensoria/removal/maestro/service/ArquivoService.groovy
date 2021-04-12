package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.api.ArquivoUpload
import br.gov.rs.defensoria.removal.api.ObjArquivo
import br.gov.rs.defensoria.removal.repository.ArquivosRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.ArquivosEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletResponse

@Component
class ArquivoService {
    @Value('${apply.files.directory}')
    private String filesDirectory

    @Autowired
    private ArquivosRepository arquivosRepository

    @Autowired
    EditaisRepository editaisRepository

    private String msgErro="Arquivo inexistente ou temporariamente indisponível! Tente novamente mais tarde"

    def exibeArquivoEdital(int idArquivo, HttpServletResponse response)  {
        def arquivo=getArquivo(idArquivo)
        if(caminhoDoArquivo(arquivo)){
            def caminhoCompletoArquivo=caminhoDoArquivo(arquivo)
            exibirArquivo(caminhoCompletoArquivo,response)
            return true
        }
        else{
            return msgErro
        }
    }

    private ArquivosEntity getArquivo(idArquivo){
        return arquivosRepository.findByIdArquivo(idArquivo)
    }

    private def caminhoDoArquivo(ArquivosEntity arquivo)  {
        if(arquivo!=null && arquivo.exibir!=0 && arquivo.nomeArquivo.trim()!=''){
            def arquivoString=arquivo.nomeArquivo
            def nomeArquivo=filesDirectory+arquivoString
            return nomeArquivo
        }
        else{
            msgErro=msgErro+String.format(" Possível exceção: O arquivo id: %d pode conter um nome vazio ou apenas com espaços." , arquivo.idArquivo)
            return false
        }
    }

    private exibirArquivo(String uri, HttpServletResponse response) throws IOException   {
        File arquivo = new File(uri)
        if(!arquivo.exists()){
             throw new RuntimeException(msgErro)
        }
        else{
            try{
                String extensaoArquivo=uri.substring(uri.lastIndexOf('.'))
                String dataHora=new LocalDateTime().now().toString()
                String nomeArquivo = String.format("arquivo_%s.%s",dataHora,extensaoArquivo)
                response.addHeader("Content-Disposition", String.format("attachment; filename=%s", nomeArquivo))
                response.setContentLength((int) arquivo.length())
                FileInputStream fileInputStream = new FileInputStream(arquivo)
                OutputStream responseOutputStream = response.getOutputStream()
                int bytes
                while ((bytes = fileInputStream.read()) != -1) {
                    responseOutputStream.write(bytes)
                }
            } catch (Exception e) {
                 throw new RuntimeException( msgErro+String.format(" Exceção: %s" , e))
            }
        }
    }

    List<ObjArquivo> getInfosArquivos(int idEdital) {

        List<ObjArquivo> infosArquivos = new ArrayList<ObjArquivo>()

        List<ArquivosEntity> arquivos = arquivosRepository.findByEditalIdEdital(idEdital)
        arquivos.each {
            DateTimeFormatter fmt = DateTimeFormat.forPattern('dd/MM/yyyy - HH:mm')
            String dataArquivo = fmt.print(it.dataArquivo)

            infosArquivos.add(new ObjArquivo(idArquivo: it.idArquivo,
            idEdital: it.edital.idEdital,
            dataArquivo: dataArquivo,
            descricaoArquivo: it.descricao,
            nomeArquivo: it.nomeArquivo,
            exibirArquivo: it.exibir))
        }

        return infosArquivos
    }

    boolean alteraStatus(int idArquivo){
        ArquivosEntity arquivo= getArquivo(idArquivo)
        if(arquivo.exibir==1){
            arquivo.exibir=0
        }else{
            arquivo.exibir=1
        }
        arquivosRepository.save(arquivo)
        return true
    }

    boolean novoArquivo(ArquivoUpload arquivo){
        def arquivoEntity=new ArquivosEntity(edital:editaisRepository.findByIdEdital(arquivo.idEdital)
        ,dataArquivo: LocalDateTime.now()
        ,descricao:arquivo.descricaoArquivo
        ,nomeArquivo:arquivo.nomeArquivo
        ,exibir:arquivo.exibir)
        arquivosRepository.save(arquivoEntity)
        return true
    }
}
