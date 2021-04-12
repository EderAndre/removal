removalServiceControllers.controller('AdmEditaisController', function($scope, $interval, ngDialog, adminService, maestro) {
    $scope.dataUser = maestro != undefined ? maestro.config : null;

    $scope.$on('editalEscolhido', function(event, args) {
        if (args != null) {
            $scope.indexEdital = args;
            maestro.editalBloqueado($scope.indexEdital);
            $scope.editalCorrente = $scope.dataUser.editaisAtivos[$scope.indexEdital];
            maestro.getInfoEdital($scope.editalCorrente.idEdital);
        }
    });

    $scope.$on('InfoEdital', function(event, res) {
        $scope.edital = maestro.infoEdital;
    });

    $scope.$on('TesteDeIntegridade', function(event, res) {
        $scope.integridade = res.data;
    });

    $scope.salvarEdital = function() {    
        var edital = {
            idEdital : $scope.edital.idEdital,
            aberturaEdital : String($scope.edital.aberturaEdital),
            descricaoEdital : $scope.edital.descricaoEdital,
            encerramentoEdital : String($scope.edital.encerramentoEdital),
            limitePrimeiraCandidatura : String($scope.edital.limitePrimeiraCandidatura),
            dataApenasDesistenciasPermitidas: String($scope.edital.dataApenasDesistenciasPermitidas),
            bloqueado : $scope.edital.bloqueado,
            assinaturaEmailEdital : $scope.edital.assinaturaEmailEdital,
            emailRespostaEdital : $scope.edital.emailRespostaEdital,
            emailEnvioEdital : $scope.edital.emailEnvioEdital
        };
    
        adminService.alteraEdital(edital);
    }

    $scope.testarIntegridade = function() {
        adminService.getIntegridade($scope.editalCorrente.idEdital);
        var dialog = ngDialog.open({
            template : 'edital-integridade.html',
            className : 'ngdialog-theme-default dialogwidth600',
            scope : $scope
            }
        );
        $scope.resultado = [];
        $scope.contador = 0;
        $interval(function() {
            if ($scope.contador + 1 == 14) {
                $interval.cancel();
            } else {
                $scope.resultado.push($scope.integridade.testes[$scope.contador]);
                $scope.contador++;
            }
        }, 1000);
    
    };
    
    $scope.limpaDataSomenteDesistencias = function() {
        $scope.edital.dataApenasDesistenciasPermitidas = undefined;
    }
});