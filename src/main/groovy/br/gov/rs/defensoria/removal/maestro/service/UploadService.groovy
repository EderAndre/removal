package br.gov.rs.defensoria.removal.maestro.service


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UploadService {

    @Value('${apply.files.directory}')
    private String filesDirectory

    private String msgErro="Upload falhou, pois ocorreram o(s) seguinte(s) erro(s): "

    void uploadArquivo(String name, byte[] file ) throws IOException   {
        File caminho = new File(filesDirectory)
        try {
            if(caminho.exists() && caminho.isDirectory()){
                BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(new File(filesDirectory+name)))
                stream.write(file)
                stream.close()
            }
            else{
                throw new RuntimeException("O diretório não existe.")
            }
        } catch (Exception e) {
            throw new RuntimeException( msgErro + e.getMessage())
        }
    }
}