removalServiceControllers.controller('InfosEditalController', function($scope,$rootScope, maestro,endPointsService) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
	
	$scope.$on('editalEscolhido',function(event,args){
		  endPointsService.getInfoEdital($scope.editalCorrente.idEdital);
	})

	$scope.$on('InfoEdital', function(event, res){
		$scope.infos = maestro.infoEdital;
	});
});
