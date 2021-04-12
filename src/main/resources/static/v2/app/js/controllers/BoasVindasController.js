removalServiceControllers.controller('BoasVindasController', function($scope,
        $templateCache,$timeout, $rootScope, $route, $location, $window, maestro,
        endPointsService, ModalService) {
   
    $scope.$on('$viewContentLoaded', function() {
        $templateCache.removeAll();
        maestro.getConfig();
        $timeout(function() {
            $scope.dataUser = maestro.config;
            var perfil;
            var editaisAtivos;
            if ($scope.dataUser != undefined) {
                perfil = $scope.dataUser.perfil;
                editaisAtivos = $scope.dataUser.editaisAtivos.length;
            } else {
                $route.reload();
            }
            $scope.boasVindas = maestro.boasVindas(perfil, editaisAtivos);
        }, 250)
    })

    $scope.$on('NovoAviso', function(event, args) {
        ModalService.showModal("Erro!", angular.toJson(args))
    });

    $scope.$on('editalEscolhido', function(event, args) {
        $scope.indexEdital = args
    })

    $scope.$on('$routeChangeSuccess', function() {
        if ($scope.indexEdital != null) {
            $scope.selecionaEdital()
        }
    })

    $scope.logout = function() {
        ($window.location.href = '../logout')
    }

    $scope.selecionaEdital = function() {
        $rootScope.$broadcast('editalEscolhido', $scope.indexEdital);

        if (maestro.isAdmin()) {
            $location.path('/edital/admin/');
        } else if (maestro.isOperador()) {
            $location.path('/edital/admin/avisos');
        } else {
            var url = '/';
            $location.path(url);
        }
    }
});