describe('Testes de candidaturaController', function() {
	  beforeEach(module('removalServiceControllers'));
	
	  var $controller,$rootScope,$scope;
	
	  beforeEach(inject(function($injector){
		
		$rootScope=$injector.get('$rootScope');
		$httpBackend=$injector.get('$httpBackend');
		$controller =$injector.get('$controller');
	  }));
	 
	  var vagas = [{id: 1, ordem: 0, nomeExibicao: 'POA 1', escolhida: false, selecionada: true, tipoVaga:1, info: "Informações da vaga 1 com mais infomrações que a dois"},
	               {id: 2, ordem: 2, nomeExibicao: 'POA 2', escolhida: false, selecionada: false, tipoVaga:2,info:"Informações da vaga 2 "},
	               {id: 3, ordem: 1, nomeExibicao: 'POA 3', escolhida: true, selecionada: false, tipoVaga:3,info:null},
	               {id: 4, ordem: 0, nomeExibicao: 'POA 4', escolhida: false, selecionada: false, tipoVaga:1,info:''},
	               ];
	
	  describe('Testes de CandidaturaController', function() {
	    var $scope, controller;
		    beforeEach(function() {
		      $scope=$rootScope.$new()
		      controller = $controller('CandidaturaController', { '$scope': $scope});
		      $scope.vagas = vagas		      
		    });
		   
		    it('testa a troca de listas', function() {	
		    	$scope.trocaListaVagasSelecionadas('escolhidas')
			      expect($scope.vagas[3].escolhida).toEqual(true);
		    });
		    it('seleciona ou desseleciona vaga individual', function() {	
		    	$scope.selecionaVaga($scope.vagas[1])
			      expect($scope.vagas[1].selecionada).toEqual(true);
			});
		    it('movimenta acima na lista', function() {	
		    	$scope.moveUp($scope.vagas,1)
			      expect($scope.vagas[0].id).toEqual(1);
			});
		    it('movimenta abaixo na lista', function() {	
		    	$scope.moveDown($scope.vagas,2)
			      expect($scope.vagas[3].id).toEqual(3);
			});
		   
		   
		    it('movimenta primeira seleção acima na lista', function() {	
		    	$scope.firstUp($scope.vagas)
			      expect($scope.vagas[0].id).toEqual(1);
			});
		    it('movimenta  primeira seleção abaixo na lista', function() {	
		    	$scope.firstDown($scope.vagas)
			      expect($scope.vagas[1].id).toEqual(2);
			});
		   
		    it('desselecione todas as opções', function() {	
				$scope.limpaSelecao()
				 expect($scope.vagas[0].selecionada).toEqual(false);
			});
		    it('exclusão de todas as opções', function() {	
				   $scope.excluiTodasOpcoes()
					 expect($scope.vagas[2].escolhida).toEqual(false);
			});
		    it('testa alteração de captcha', function() {	
		    	spyOn($scope,'novoCaptcha')
		    	$scope.novoCaptcha()		    	
				expect($scope.novoCaptcha).toHaveBeenCalled();	
			});
	  });
	});