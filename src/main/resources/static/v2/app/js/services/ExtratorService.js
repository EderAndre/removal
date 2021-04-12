removalServiceServices.service('extratorService', ['$http','$window', '$rootScope', function($http, $window,$rootScope) {

    this.extrator = function(url,variavelRetorno) {
    	edital=url.split('/')
		edital=edital[2]
    	$http.get(url).
    	then(function(response) {
    		$rootScope.$broadcast(variavelRetorno,response);
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