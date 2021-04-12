describe('Testando serviço maestro', function () {
   beforeEach(function () {
      module('removalServiceServices');
      
   });

   it('Mensagem e titulo de Administrador correta 2 ou mais editais', inject(function (maestro) {
	   expect(maestro.boasVindas('[SUPER_USER]',2).titulo).toBe("Bem-vindo(a), Administrador(a)!");
	   expect(maestro.boasVindas('[SUPER_USER]',2).texto).toBe("Escolha um edital para prosseguir");
	}));
   it('Mensagem e titulo de Administrador correta 1 edital', inject(function (maestro) {
	   expect(maestro.boasVindas('[SUPER_USER]',1).titulo).toBe("Bem-vindo(a), Administrador(a)!");
	   expect(maestro.boasVindas('[SUPER_USER]',1).texto).toBe('Você foi habilitado a administrar um edital, clique abaixo para prosseguir');
	}));
   it('Mensagem e titulo de Operador correta 2 ou mais editais', inject(function (maestro) {
	   expect(maestro.boasVindas('[OPERADOR]',2).titulo).toBe("Bem-vindo(a), Operador(a)!");
	   expect(maestro.boasVindas('[OPERADOR]',2).texto).toBe("Escolha um edital para prosseguir");
	}));
   it('Mensagem e titulo de Operador correta 1 edital', inject(function (maestro) {
	   expect(maestro.boasVindas('[OPERADOR]',1).titulo).toBe("Bem-vindo(a), Operador(a)!");
	   expect(maestro.boasVindas('[OPERADOR]',1).texto).toBe('Você foi habilitado a operar um edital, clique abaixo para prosseguir');
	}));
   it('Mensagem e titulo de Candidato correta possui edital cadastrado', inject(function (maestro) {
	   expect(maestro.boasVindas('[CANDIDATO]',2).titulo).toBe("Bem-vindo(a)!");
	   expect(maestro.boasVindas('[CANDIDATO]',2).texto).toBe("Você foi habilitado a participar de um edital, clique abaixo para prosseguir");
	}));
   it('Mensagem e titulo de Candidato correta não possui edital cadastrado', inject(function (maestro) {
	   expect(maestro.boasVindas('[CANDIDATO]',0).titulo).toBe("Bem-vindo(a)!");
	   expect(maestro.boasVindas('[CANDIDATO]',0).texto).toBe('Você não está habilitado a participar de nenhum edital aberto!');
	}));
   

});