editalBloqueadoModulo.controller('msgErroController', function($scope, $rootScope,$http, $window) {
   
	var url = $window.location.href + '/info';
	$http.get(url).then(function(res) {
        $scope.msg = res.data.motivoBloqueio;
    });
});