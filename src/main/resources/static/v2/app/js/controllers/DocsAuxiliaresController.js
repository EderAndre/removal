removalServiceControllers.controller('DocsAuxiliaresController', function($scope,$rootScope,maestro) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null;
	
	$scope.$on('editalEscolhido',function(event,args){
		maestro.getDocs($scope.editalCorrente.idEdital);
    });

	$scope.$on('DocsAuxiliaresEvento', function(event, res){
		$scope.docs=maestro.docsEdital;
	}); 
});