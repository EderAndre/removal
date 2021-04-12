removalServiceControllers.controller('AvisosEditalController',  function($scope, $rootScope,ModalService, maestro,ngDialog) {
	
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null;
	
	$scope.$on('editalEscolhido',function(event,args){
    	maestro.getAvisosEdital($scope.editalCorrente.idEdital);
    });

	$scope.$on('AvisosEdital', function(){
		$scope.avisosEdital=maestro.avisosEdital;
	});
	
	$scope.modalAvisosEdital = function(avisoEdital) {
		ngDialog.open({
			template: '\
    	    	<div class="panel panel-default">\
					<div class="panel-heading">\
				      <h3 class="panel-title">'+avisoEdital.tituloAviso+'</h3>\
				    </div>\
				    <div class="panel-body ">\
				      <span>'+avisoEdital.dataAviso + "<hr/>" + avisoEdital.descricaoAviso+'</span>\
				    </div>\
				    <div class="panel-footer ngdialog-buttons">\
			           <button type="button" class="ngdialog-button ngdialog-button-secondary" ng-click="closeThisDialog(0)">Fechar <span class="glyphicon glyphicon glyphicon-log-out"></span></button>\
				   </div>\
				</div>',
		plain: true,
   		className: 'ngdialog-theme-default dialogwidth800',
 	    scope: $scope
 	});
	};
});