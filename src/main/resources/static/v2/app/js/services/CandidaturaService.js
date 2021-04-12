removalServiceServices.service('candidaturaService', ['$http','$timeout', '$rootScope','endPointsService', function($http,$timeout, $rootScope,endPointsService) {

    this.realizaPedido = function(editalId, candidatoId, locais, segredo, codigoDigitado) {
    	$http.post('/edital/'+editalId+'/candidato/'+candidatoId+'/candidatura/pedido',
    	{candidatura:{edital: editalId, candidato: candidatoId, locais: locais},captcha:{segredo:segredo, codigo:codigoDigitado}}).
    	then(function(response) {
    		$rootScope.$broadcast("BemSucedido", 'Candidatura Atualizada com sucesso');
    	}, function(response) {
    		if(response.status==417){
    			endPointsService.getMotivoBloqueio(editalId)
    			$rootScope.$on('InfoBloqueio', function(event, args) { $rootScope.motivoBloqueio=args.data.motivoBloqueio});
    			$timeout(function(){
    				$rootScope.$broadcast("NovoAviso", "Edital bloqueado pelo seguinte motivo: <b>"+$rootScope.motivoBloqueio+"</b>");
    			},500)
    		}
    		else{
    			$rootScope.$broadcast("NovoAviso", response);
    		}
    	});
    }
}]);