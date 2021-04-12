removalServiceControllers.controller('MenuAdminController',function($scope,$rootScope,maestro) {
	maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
	$scope.isAdmin=maestro.isAdmin()
	$scope.isOperador=maestro.isOperador()
});