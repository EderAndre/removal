describe('Testando serviço extração', function () {
   beforeEach( module('removalServiceServices'));
   var $httpBackend,$rootScope;
   beforeEach(function () {
       inject(function ($injector) {
           $rootScope = $injector.get('$rootScope');
           $httpBackend = $injector.get('$httpBackend');
           authRequestHandler = $httpBackend.when('GET', '/retorno')
           .respond({resposta: 'sucesso'});
       })
   });
   afterEach(function() {
	     $httpBackend.verifyNoOutstandingExpectation();
	     $httpBackend.verifyNoOutstandingRequest();
	   });
 it('Teste de serviço de extração quando ok', inject(function (extratorService) {
	 $rootScope.$on('NovoAviso',function(){$rootScope.retornoDeExtracao=false})
	 $rootScope.$on('TestaExtracao',function(){$rootScope.retornoDeExtracao=true})
	   $httpBackend.expectGET('/retorno');
	   extratorService.extrator('/retorno','TestaExtracao')
	   $httpBackend.flush();
	   $rootScope.$digest()
	   expect($rootScope.retornoDeExtracao).toBe(true); 
	}));
 it('Teste de serviço de extração quando falha', inject(function (extratorService) {
	   
	 $rootScope.$on('NovoAviso',function(){$rootScope.retornoDeExtracao=false})
	 $rootScope.$on('TestaExtracao',function(){$rootScope.retornoDeExtracao=true})
	   $httpBackend.expectGET('/retorno').respond(401, '');
	   extratorService.extrator('/retorno','TestaExtracao')
	   $httpBackend.flush();
	   $rootScope.$digest()
	   expect($rootScope.retornoDeExtracao).toBe(false); 
	}));
});