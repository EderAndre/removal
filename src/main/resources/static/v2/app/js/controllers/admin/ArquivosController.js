removalServiceControllers.controller('AdmArquivosController', function($scope,$route, $rootScope,$http,adminService,maestro,ngDialog) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
				       
	$scope.$on('editalEscolhido',function(event,args){
		if (args != null) {
		   $scope.indexEdital=args; 
		    maestro.editalBloqueado($scope.indexEdital);
		    $scope.editalCorrente=$scope.dataUser.editaisAtivos[$scope.indexEdital]
		    maestro.getDocs($scope.editalCorrente.idEdital)
		}		
	 });
	
	$scope.$on('AlteraStatusArquivo', function(){ $route.reload()});
	
	$scope.$on('DocsAuxiliaresEvento', function(event, res){
		 angular.forEach(maestro.docsEdital, function(value, key) {
	    		value.linkDoc=value.linkDoc.replace('idEdital',$scope.editalCorrente.idEdital)
		 }); 
		 $scope.docs=maestro.docsEdital;
	}); 
	
    $scope.model={exibirArquivo:1, descricaoArquivo:""}
    
    $scope.setFiles = function(element) {
		$scope.$apply(function($scope) {
			$scope.files = element.files[element.files.length-1]
		});
	};
	$scope.status = function(idArquivo) {
    	adminService.alteraStatus($scope.editalCorrente.idEdital,idArquivo);
    };
   
    $scope.enviaArquivo = function() {
    	var fd = new FormData()
		fd.append("uploadedFile", $scope.files)
		fd.append('descricao', $scope.model.descricaoArquivo)
		fd.append('exibir', $scope.model.exibirArquivo)
		adminService.postaArquivo($scope.editalCorrente.idEdital,fd);
    };
	
    $scope.inclusaoArquivo=function(){
        	ngDialog.open({
        		 template: 'uploadArquivo.html',
        	    scope: $scope
        	});
    }
});