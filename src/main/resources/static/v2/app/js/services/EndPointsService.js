removalServiceServices.service('endPointsService', ['extratorService','$rootScope', function( extratorService,$rootScope) {

    this.getConfig = function() {
    	 extratorService.extrator('/obj-tela-inicial', 'ConfigDoUsuario'); 
    }
    
    this.getMotivoBloqueio = function(idEdital) {
    	extratorService.extrator('/edital/'+ idEdital +'/bloqueado/info', 'InfoBloqueio');
   }
    this.getInfoEdital = function(idEdital) {
      	 extratorService.extrator('/edital/' + idEdital + '/infos-edital', 'InfoEdital');
      }

    this.getVagas = function(idEdital, idCandidato) {
    	if(idCandidato!=null){
    		extratorService.extrator('/edital/' + idEdital + '/obj-constroi-pedido/candidato/' + idCandidato, 'VagasDoCandidato');
    	}
    	else{
    		extratorService.extrator('/edital/' + idEdital + '/obj-constroi-pedido', 'VagasDoCandidato')	
    	}
   	    
   	 
   }

    this.getAvisosEdital = function(idEdital) {
    	extratorService.extrator('/edital/' + idEdital + '/avisos-edital/', 'AvisosEdital');	
    };
    
    this.getDocsAuxiliares = function(idEdital) {
    	extratorService.extrator('/edital/' + idEdital + '/infos-arquivos', 'DocsAuxiliaresEvento');
    };
    
    this.getHistorico = function(idEdital, idCandidato) {
      	 extratorService.extrator('/edital/' + idEdital + '/obj-historico/candidato/' + idCandidato, 'HistoricoDoCandidato');
      }
    this.getCaptcha = function() {
     	 extratorService.extrator("/captcha/gerar/",'CaptchaSolicitado');
     }
}]);