removalServiceControllers.controller('AvisosController', [ '$scope', '$rootScope', function($scope, $rootScope) {
        $scope.aviso = { mensagem : "", tipo : "sucesso" };

        function defaultSpringErrorHandler(exception) {
                if (exception.data && exception.data.status == 500) {
                        $scope.aviso = { mensagem : "Um erro inesperado ocorreu no sistema. Entre em contato com a Unidade de Desenvolvimento de Sistemas." }
                        return true;
                }
                return false;
        }

        $rootScope.$on("NovoAviso", function(event, aviso) {
                if (defaultSpringErrorHandler(aviso) == false) {
                        $scope.aviso = aviso;
                }
        });
}]);