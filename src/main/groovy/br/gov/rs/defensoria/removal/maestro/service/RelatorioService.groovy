package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletResponse

@Component
class RelatorioService {
	@Value('${output.directory}')
	private String filesDirectory

	@Autowired
	private FechamentosRepository fechamentosRepository

	private String msgErro="Arquivo inexistente ou temporariamente indisponível! Tente novamente mais tarde"

	def exibeRelatorio(int idFechamento, HttpServletResponse response, String tipo)  {
		def fechamento=getFechamento(idFechamento)
		if(caminhoDoRelatorio(fechamento,tipo)){
			def caminhoCompletoArquivo=caminhoDoRelatorio(fechamento,tipo)
			exibirRelatorio(caminhoCompletoArquivo,response)
			return true
		}
		else{
			return msgErro
		}
	}

	private FechamentosEntity getFechamento(idFechamento){
		return fechamentosRepository.findByIdFechamento(idFechamento)
	}

	private def caminhoDoRelatorio(FechamentosEntity fechamento,String campoRelatorio)  {
		if(fechamento!=null){
			def arquivoString=fechamento.relatorioFechamento
			if(campoRelatorio=='relatorioPreliminar'){arquivoString=fechamento.relatorioPreliminar}
			def nomeRelatorio=filesDirectory+arquivoString
			return nomeRelatorio
		}
		else{
			msgErro=msgErro+String.format(" Possível exceção: O fechamento id: %d pode conter um relatório com nome vazio ou apenas com espaços." , fechamento.idFechamento)
			return false
		}
	}

	private exibirRelatorio(String uri, HttpServletResponse response) throws IOException   {
		File arquivo = new File(uri)
		if(!arquivo.exists()){
			return msgErro
		}
		else{
			try{
				String extensaoArquivo=uri.split("\\.")[1]
				String dataHora=new LocalDateTime().now().toString()
				String nomeArquivo = String.format("Relatório_%s.%s",dataHora,extensaoArquivo)
				response.addHeader("Content-Disposition", String.format("attachment; filename=%s", nomeArquivo))
				response.setContentLength((int) arquivo.length())
				FileInputStream fileInputStream = new FileInputStream(arquivo)
				OutputStream responseOutputStream = response.getOutputStream()
				int bytes
				while ((bytes = fileInputStream.read()) != -1) {
					responseOutputStream.write(bytes)
				}
			} catch (Exception e) {
				return msgErro+String.format(" Exceção: %s" , e)
			}
		}
	}
}
