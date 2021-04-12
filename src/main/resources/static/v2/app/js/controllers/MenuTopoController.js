removalServiceControllers
        .controller(
                'MenuTopoController',
                function($scope, $location, $rootScope, $route,$timeout,
                        endPointsService, maestro, adminService, ModalService) {

                    $scope.dataUser = {
                        perfil : maestro.perfis[0],
                        editaisAtivos : {}
                    }
                    var hidenLoader=function () {angular.element('#loader').css('display', 'none')}
                    var showLoader=function () {angular.element('#loader').css('display', 'block')}
                    $scope.$on('ConfigDoUsuario', function(event, args) {
                        $scope.dataUser = maestro.config
                    });

                    $scope.mostrarMenu = false

                    $scope.$on('NovoAviso', function(event, args) {
                        var mensagem = args.data.message;
                        if (isException(mensagem)) {
                            mensagem = getMessageException(mensagem);
                        }
                        
                        var html = "<p class='container-fluid alert-danger'><b>Erro !</b><span class='pull-right glyphicon glyphicon-question-sign alert-danger'></span><p>";
                        ModalService.showModal(html, mensagem);
                    });
                    
                    function isException(message) {
                        var position = message.indexOf('Exception: ');
                        if (position > -1) {
                            return true;
                        }
                        
                        return false;
                    }                    

                    function getMessageException(message) {
                        var tag = 'Exception: ';
                        var begin = message.lastIndexOf(tag) + tag.length;
                        var end = message.length;
                        
                        return message.substring(begin, end);
                    }

                    $scope.$on('BemSucedido', function(event, args) {
                        var html = "<p class='container-fluid alert-success'><b>Sucesso !</b><span class='pull-right glyphicon glyphicon-ok-sign alert-success'></span>";
                        ModalService.showModal(html, args);
                    });

                    $scope.$on('editalEscolhido', function(event, args) {
                        $scope.indexEdital = args;
                        if ($scope.indexEdital == null) {
                            $location.path('/edital/boas-vindas');
                        }

                    })
                    $rootScope
                            .$on(
                                    '$includeContentRequested',
                                    function() {
                                        if ($scope.editalCorrente) {
                                            maestro
                                                    .getAvisosEdital($scope.editalCorrente.idEdital);
                                            maestro
                                                    .getDocs($scope.editalCorrente.idEdital);
                                            adminService
                                                    .getIntegridade($scope.editalCorrente.idEdital)
                                            endPointsService
                                                    .getInfoEdital($scope.editalCorrente.idEdital);
                                        } else {
                                            $route.reload();
                                        }
                                    });
                    $scope.$on('$routeChangeStart',function(){showLoader()});
                    $scope.$on('$routeUpdate',function(){showLoader()});
                    $scope.$watch('$viewContentLoaded', function(){hidenLoader()});
                    $scope
                            .$on(
                                    '$routeChangeSuccess',
                                    function() {
                                        $rootScope.$broadcast(
                                                'editalEscolhido',
                                                $scope.indexEdital);
                                        $scope.editalCorrente = $scope.dataUser.editaisAtivos[$scope.indexEdital]
                                        if ($location.path() != "/edital/boas-vindas") {
                                            $scope.mostrarMenu = true;
                                        }
                                        if (maestro.config != undefined) {
                                            hidenLoader();
                                        } else {
                                            $timeout($route.reload(),1000);
                                        }

                                    })

                    $scope.alterarEditalAtivo = function() {
                        maestro.editalBloqueado($scope.indexEdital)
                        $rootScope.$broadcast('editalEscolhido',
                                $scope.indexEdital)
                        $scope.editalCorrente = $scope.dataUser.editaisAtivos[$scope.indexEdital]
                    }
                });