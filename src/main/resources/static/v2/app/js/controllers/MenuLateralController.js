removalServiceControllers.controller('MenuLateralController', function($scope,
        $route, $location, $window, $rootScope, ngDialog, maestro) {
    maestro != undefined ? $scope.dataUser = maestro.config
            : $scope.dataUser = null
    $scope.historico = true
    $scope.inicio = false
    $scope.isAdmin = maestro.isAdmin()
    $scope.isOperador = maestro.isOperador()
    if ($location.path() == "/edital/historico") {
        $scope.historico = false
    }
    if ($location.path() != "/") {
        $scope.inicio = true
        $scope.isAdmin = false
        $scope.isOperador = false
    }

    $scope.abreAjuda = function(option) {
        $window.open('http://web01.defpub.local/remocao/ajuda/' + option,
                '_blank');
    }

    $scope.abreBoxAjuda = function() {
        ngDialog.open({
            template : 'boxAjuda.html',
            scope : $scope
        });
    }
});