removalServiceControllers.controller('AdmAvisosController', function($scope, $rootScope,$http,$timeout, ngDialog,maestro, adminService, $filter) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
	
	$scope.$on('editalEscolhido',function(event,args){
		if (args != null) {
		   $scope.indexEdital=args; 
		    maestro.editalBloqueado($scope.indexEdital);
		    $scope.editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
		    maestro.getAvisosEdital($scope.editalCorrente.idEdital)
			}		
	});
			
	$scope.$on('AvisosEdital', function(){
		$scope.avisos=maestro.avisosEdital;
	});
	
	$scope.model={}
		
	$scope.modalNovoAviso = function() {		
		var dialog = ngDialog.open({
			template: 'modal-novo-aviso.html'
			,className: 'ngdialog-theme-default dialogwidth800'
			, scope:$scope
			});
	};
	
	$scope.salvarAviso=function(){
		adminService.getData($scope.editalCorrente.idEdital)
		$timeout(function(){
			var aviso = {
					idEdital: $scope.editalCorrente.idEdital,
					dataAviso: $filter('date')($scope.model.dataAviso, 'dd/MM/yyyy - HH:mm'),
					tituloAviso: $scope.model.tituloAviso,
					descricaoAviso: $scope.model.descricaoAviso,
					excluidoAviso:$scope.model.exluidoAviso,
					dataInclusao: $filter('date')(adminService.dataAtual, 'dd/MM/yyyy - HH:mm')
					}
			adminService.postaAviso($scope.editalCorrente.idEdital,aviso)
		},200)
		
	}
	
	$scope.excluirRestaurarAviso = function(aviso) {
		if (aviso.excluidoAviso) {
			aviso.excluidoAviso = 0
		} 
		else {
			aviso.excluidoAviso = 1
		}

		adminService.alteraStatusAviso($scope.editalCorrente.idEdital,aviso);
	};
	
	$scope.mascaraTempo = function() {
		$scope.model.dataAviso = $filter('date')($scope.model.dataAviso, 'dd/MM/yyyy - HH:mm');
	}
});