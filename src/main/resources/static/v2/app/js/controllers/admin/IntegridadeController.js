removalServiceControllers.controller('IntegridadeController', function($scope, $http,maestro,adminService) {
		       
			$scope.$on('editalEscolhido',function(event,args){
				if (args != null) {
				   $scope.indexEdital=args; 
				    maestro.editalBloqueado($scope.indexEdital);
				    $scope.editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
				    $scope.editalCorrente.idEdital
				    adminService.getIntegridade($scope.editalCorrente.idEdital)
				}		
			 });
			
			$scope.$on('TesteDeIntegridade', function(event, res){
				$scope.statusOk = true;
				$scope.msg = [];
				var semProblemas = "Sem problemas com a integridade.";
				$scope.msg.push(semProblemas);
				var testes = res.data.testes;
				angular.forEach(testes, function(value, key) {
					if (value.status == false) {
						if ($scope.msg.indexOf(semProblemas) > -1) {
							$scope.msg = [];
						}
						$scope.statusOk = false;
						$scope.msg.push(value.descricao);
					}
				});
				
			}); 
});