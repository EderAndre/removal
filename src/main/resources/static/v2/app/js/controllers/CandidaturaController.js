removalServiceControllers.controller('CandidaturaController',function($scope,$filter,$timeout,maestro,captcha,candidaturaService,ngDialog) {
        
        maestro!=undefined? $scope.dataUser=maestro.config:$scope.dataUser=null
        
        $scope.captchaVal=''; 
       
        $scope.$on('editalEscolhido',function(event,args){
            if (args != null) {
                $scope.indexEdital = args; 
                maestro.editalBloqueado($scope.indexEdital);
                
                var editalCorrente = $scope.dataUser.editaisAtivos[$scope.indexEdital];
                $scope.editalCorrente = editalCorrente;
                $scope.editalCorrente.isModoSomenteDesistencias = editalCorrente.modos.indexOf("SOMENTE_DESISTENCIAS") < 0 ? false : true;
                if (maestro.isAdmin() || maestro.isOperador()) {
                    maestro.getVagas(editalCorrente.idEdital, null);    
                } else {
                    maestro.getVagas(editalCorrente.idEdital, editalCorrente.candidato.idCandidato);    
                }
            }        
        });
        
        $scope.$on('VagasDoCandidato',function(event,args){ 
            vagas=maestro.vagas; 
            $scope.order(vagas,['escolhida','ordem','ordemVaga'],false);
            $scope.vagasBackup= angular.copy($scope.vagas);
        }) 
      
        $scope.$on('CaptchaSolicitado',function(event,args){ $scope.captcha=captcha.captcha;})
        

        $scope.realizarPedido = function() {
            candidaturaService.realizaPedido($scope.editalCorrente.idEdital
                                            ,$scope.editalCorrente.candidato.idCandidato
                                            ,$scope.vagasEscolhidas
                                            ,$scope.captcha
                                            ,$scope.captchaVal
                                            );
        }; 
        
        $scope.foco=function(idElemento){$timeout(function(){angular.element("#"+idElemento).focus()})}
     
        $scope.selecionaVaga = function(vaga) {
            vaga.selecionada = !vaga.selecionada;
        };   
    
        $scope.trocaListaVagasSelecionadas = function(listaDestino) {
            var linhasEscolhidas=$filter('filter')($scope.vagas,{escolhida:true}).length
            var inc=1;
            angular.forEach($scope.vagas, function(vaga, key) {
                var listaDestinoDiferenteAtual = false
                if(listaDestino == 'escolhidas' && !vaga.escolhida || listaDestino == 'disponiveis' && vaga.escolhida)
                    listaDestinoDiferenteAtual = true
                if(vaga.selecionada && listaDestinoDiferenteAtual) {
                    vaga.escolhida = !vaga.escolhida;
                    vaga.selecionada=false
                    listaDestino === 'escolhidas'?vaga.ordem=linhasEscolhidas+inc:vaga.ordem=0
                    inc++;
                }
                
            });
             $scope.order($scope.vagas,['escolhida','ordem','ordemVaga'],false)
        }

        $scope.atualizaCaptchaVal=function(val){
            $scope.captchaVal=val
        }
     
        var orderBy = $filter('orderBy');
        $scope.order = function(array,predicate, reverse) {
               $scope.vagas = orderBy(array, predicate, reverse);
    
          };
          
          $scope.novoCaptcha=function(){
              captcha.getCaptcha();
          }
          
          $scope.confirmaOpcoes=function(){
            captcha.getCaptcha();
            $scope.limpaSelecao()
            if(angular.equals($scope.vagas,$scope.vagasBackup)){
                ngDialog.open({
                     template: 'semAlteracoes.html',
                    scope: $scope
                });
            }
            else{
                $scope.vagasEscolhidas=$filter('filter')($scope.vagas,{escolhida:true})
                angular.forEach($scope.vagasEscolhidas, function(vaga, key) {
                    vaga.ordem=key+1;    
                })
                ngDialog.open({
                      template: 'confirmacaoOpcoes.html',
                      className: 'ngdialog-theme-default dialogwidth800',
                    scope: $scope
                });
            }
        }
       
        $scope.confirmaExclusao = function () {
             var confirm1=ngDialog.openConfirm({
                        template:'confirmacaoExclusaoTotal.html'
                    }).then(function (value) {
                        $scope.excluiTodasOpcoes()
                    });        
        };
     
        $scope.excluiTodasOpcoes = function() {
                angular.forEach($scope.vagas, function(item, i) {
                    item.escolhida =false
                    item.selecionada =false
                })
        }
     
        $scope.limpaSelecao = function(option) {
            angular.forEach($scope.vagas, function(item, i) {
                if(option==null){
                    item.selecionada =false
                }else if(option=='disponiveis'){
                    if(item.escolhida==false){item.selecionada =false}
                }
                else if(option=='escolhidas'){
                        if(item.escolhida==true){item.selecionada =false}
                }
                
            })
        }
     
        $scope.firstUp = function(arr) {
            var j = 0
            angular.forEach(arr, function(item, i) {
                if (item.selecionada == true && item.escolhida == true && j == 0) {
                    $scope.limpaSelecao()
                    $scope.moveUp(arr, i)
                    ++j
                }
            })
        }
    
        $scope.firstDown = function(arr) {
            var j = 0
            angular.forEach(arr, function(item, i) {
                if (item.selecionada == true && item.escolhida == true && j == 0) {
                    $scope.limpaSelecao()
                    $scope.moveDown(arr, i)
                    ++j;
                }
            })
        }
    
        $scope.moveUp = function(arr, index) {
            var currItem = index
            arr[index]['selecionada'] = true;
            if (currItem > arr.length-$filter('filter')($scope.vagas,{escolhida:true}).length) {
                arr[currItem]['ordem'] = arr[currItem]['ordem']-1;
                arr.splice(currItem-1, 0, arr.splice(currItem, 1)[0])
                arr[currItem]['ordem'] = arr[currItem]['ordem']+1
            }
        }
    
        $scope.moveDown = function(arr, index) {
            var currItem = index
            if (currItem < arr.length) {
                arr[index]['selecionada'] = true;
                arr[currItem]['ordem'] = arr[currItem]['ordem']+1;
                arr.splice(currItem+1, 0, arr.splice(currItem, 1)[0])
                arr[currItem]['ordem'] = arr[currItem]['ordem']-1;
            }
        }    
});