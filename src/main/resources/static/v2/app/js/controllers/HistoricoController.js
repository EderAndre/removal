removalServiceControllers.controller('HistoricoController',function($scope,$route,$location,$rootScope,ngDialog,maestro) {
	$scope.dataUser=maestro.config
	
	$scope.$on('editalEscolhido',function(event,args){
		$scope.indexEdital=args; 
    	var editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
    	maestro.getHistorico(editalCorrente.idEdital,editalCorrente.candidato.idCandidato)     	
    })
    
    $scope.$on('HistoricoDoCandidato',function(event,args){ $scope.historico=maestro.historico;})   
        
});