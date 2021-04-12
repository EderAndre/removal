describe('Testando serviço de postagem', function () {
   beforeEach( module('removalServiceServices'));
   var $httpBackend,$rootScope;
   beforeEach(function () {
       inject(function ($injector) {
           $rootScope = $injector.get('$rootScope');
           $httpBackend = $injector.get('$httpBackend');
           authRequestHandler = $httpBackend.whenPOST('/retorno').respond(200)
       })
   });
   afterEach(function() {
	     $httpBackend.verifyNoOutstandingExpectation();
	     $httpBackend.verifyNoOutstandingRequest();
	   });
 it('Teste de serviço de postagem de objetos quando ok', inject(function (postagemService) {
	 $rootScope.$on('NovoAviso',function(){$rootScope.retornoDePostagem=false})
	 $rootScope.$on('BemSucedido',function(){$rootScope.retornoDePostagem=true})
	   $httpBackend.expectPOST('/retorno').respond(200)
	   postagemService.postaObjeto('/retorno',{teste:'teste'},'Teste de Postagem')
	   $httpBackend.flush();
	   $rootScope.$digest()
	   expect($rootScope.retornoDePostagem).toBe(true); 
	}));
 it('Teste de serviço de postagem de objetos quando falha', inject(function (postagemService) {
	 $rootScope.$on('NovoAviso',function(){$rootScope.retornoDePostagem=false})
	 $rootScope.$on('BemSucedido',function(){$rootScope.retornoDePostagem=true})
	   $httpBackend.expectPOST('/retorno').respond(401)
	   postagemService.postaObjeto('/retorno',{teste:'teste'},'Teste de Postagem')
	   $httpBackend.flush();
	   $rootScope.$digest()
	   expect($rootScope.retornoDePostagem).toBe(false); 
	}));

});