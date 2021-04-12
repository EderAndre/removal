removalServiceControllers.controller('HorarioSistemaController', function($interval, horaOficial) {	
	horaOficial();
	$interval(function() {
		horaOficial();
	}, 60000);		
});