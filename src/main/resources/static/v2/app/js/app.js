removalServiceApp.config(['$routeProvider',
    function($routeProvider) {
		$routeProvider.
		when('/edital/boas-vindas', {
			templateUrl: 'app/views/partials/edital/boas-vindas.html',
			controller: 'BoasVindasController'
		}).
		when('/edital/historico', {
			templateUrl: 'app/views/partials/candidatura/historico.html',
			controller: 'HistoricoController'
		}).
		when('/', {
			templateUrl: 'app/views/partials/candidatura/listas-confeccao-pedido.html',
			controller: 'CandidaturaController'
		}).
		when('/edital/confeccao-pedido', {
			templateUrl: 'app/views/partials/candidatura/listas-confeccao-pedido.html',
			controller: 'CandidaturaController'
		}).
		when('/edital/admin', {
			redirectTo: '/edital/admin/edital'
		}).
		when('/edital/admin/edital', {
			templateUrl: 'app/views/partials/admin/edital.html',
			controller: 'AdmEditaisController'
		}).
		when('/edital/admin/avisos', {
			templateUrl: 'app/views/partials/admin/avisos.html',
			controller: 'AdmAvisosController'
		}).
		when('/edital/admin/arquivos', {
			templateUrl: 'app/views/partials/admin/arquivos.html',
			controller: 'AdmArquivosController'
		}).
		when('/edital/admin/lista-candidatos', {
			templateUrl: 'app/views/partials/admin/lista-candidatos.html',
			controller: 'AdmCandidatosController'
		}).
		when('/edital/admin/fechamentos', {
			templateUrl: 'app/views/partials/admin/fechamentos.html',
			controller: 'AdmFechamentosController'
		}).
		when('/logout', {
			resolve : {
				logout : function($window, $rootScope) {
					$rootScope.$destroy;
					$window.open('/logout', "_self");				
				}
			}
		}).
		otherwise({
			redirectTo: '/edital/boas-vindas'
		});
	}    
]);
