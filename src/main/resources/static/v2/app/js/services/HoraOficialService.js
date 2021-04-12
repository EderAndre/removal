removalServiceServices.factory('horaOficial', function($http, $rootScope) {
	
	return function() {
		var req = $http.get('/hora-oficial');
		req.success(function(res) {
			$rootScope.horarioSistema = res.hora
		});
	}
});