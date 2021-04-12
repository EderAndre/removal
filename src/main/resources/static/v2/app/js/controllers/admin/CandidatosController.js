removalServiceControllers.controller('AdmCandidatosController', function($scope,$route, $rootScope,$http,adminService,maestro,ngDialog) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
				       
	$scope.$on('editalEscolhido',function(event,args){
		if (args != null) {
		   $scope.indexEdital=args; 
		    maestro.editalBloqueado($scope.indexEdital);
		    $scope.editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
		    adminService.getCandidatos($scope.editalCorrente.idEdital)
		}		
	 });
	
	$scope.$on('ListaCandidatosEvento', function(event, res){
		 $scope.candidatos=res.data;
	}); 
	
	$scope.$on('HistoricoDoCandidato',function(event,args){ $scope.historico=adminService.historico;})
    
    $scope.verHistorico=function(idCandidato){
    	adminService.getHistorico($scope.editalCorrente.idEdital,idCandidato)
    	    	ngDialog.open({
        		 template: 'modal-historico-candidato.html',
        	    scope: $scope
        	});
    }
	
	$scope.predicado = 'antiguidade';
   
	$scope.reverse = false;
   
	$scope.order = function(predicado) {
      $scope.reverse = ($scope.predicado === predicado) ? !$scope.reverse : false;
      $scope.predicado = predicado;
    };
});