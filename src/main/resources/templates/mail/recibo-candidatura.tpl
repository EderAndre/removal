<b>Defensoria Pública - Sistema de Remoção</b>
<hr>
<br>
<b>ID Funcional:</b> <%= model.matricula %> <br>
<b>Candidato:</b> <%= model.nome %> <br>
<br>
<b>Solicitação de número <%= model.nroSolicitacao %>, todas as anteriores serão desconsideradas.</b>
<br>
<b>Vagas solicitadas (por ordem de preferência):</b>
<ul>
	<% model.vagas.each { print "<li>${it}</li>" } %>
</ul>
<br>
<b>Data e hora da solicitação:</b> <%= model.dataSolicitacao %><br>
<b>Data e hora da confirmação da solicitação:</b> <%= model.dataConfirmacao %><br>
<b>Endereço do sistema:</b>&nbsp;<a href="<%= endereco_sistema %>"><%= endereco_sistema %></a>
<br>
<br>
Você está recebendo este e-mail por ter solicitado classificação, remoção ou aditamento pelo formulário eletrônico.<br>
Caso esteja recebendo este e-mail por engano desconsidere o mesmo e entre em contato com <%= contato_suporte %>.
<br>
<br>
<%= assinatura %>
<br>
<%= rodape %>