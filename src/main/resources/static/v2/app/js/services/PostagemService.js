removalServiceServices.service('postagemService',['$http','$window','$rootScope',  function($http,$window, $rootScope) {

	
    this.postaObjeto = function(url, objetoApi, mensagemSucesso) {
    	edital=url.split('/')
		edital=edital[2]
    	$http.post(url,objetoApi).
    	then(function(response) {
    		$rootScope.$broadcast("BemSucedido", mensagemSucesso);
    	}, function(response) {
    		if(response.status==417){
    			$window.open('/edital/'+edital+'/bloqueado', "_self");	
    		}
    		else{
    			$rootScope.$broadcast("NovoAviso", response);
    		}
    	});
    }
  
    this.postaForm = function(url, form, mensagemSucesso) {
    	edital=url.split('/')
		edital=edital[2]
    	$http.post(url,form,{
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).
    	then(function(response) {
    		$rootScope.$broadcast("BemSucedido", mensagemSucesso);
    	}, function(response) {
    		if(response.status==417){
    			$window.open('/edital/'+edital+'/bloqueado', "_self");	
    		}
    		else{
    			$rootScope.$broadcast("NovoAviso", response);
    		}
    	});
    }
    
}]);