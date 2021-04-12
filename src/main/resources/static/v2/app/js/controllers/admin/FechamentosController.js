removalServiceControllers.controller('AdmFechamentosController', function($scope,$route, $rootScope,$http,adminService,maestro,ngDialog) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
				       
	$scope.$on('editalEscolhido',function(event,args){
		if (args != null) {
		   $scope.indexEdital=args; 
		    maestro.editalBloqueado($scope.indexEdital);
		    $scope.editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
		    adminService.getFechamentos($scope.editalCorrente.idEdital)
		}		
	 });
	
	$scope.$on('ListaFechamentosEvento', function(event, res){
		 $scope.fechamentos=res.data;
	}); 
	
});