removalServiceServices.factory('adminService',['$http','$route', '$rootScope','extratorService','postagemService','endPointsService',function($http, $route, $rootScope,extratorService,postagemService,endPointsService) {

    var factory = {}; 
        
    factory.postaArquivo=function(idEdital, formulario){
    	postagemService.postaForm(
       			'/edital/'+idEdital+"/arquivo/upload"
       			,formulario
       			,'Arquivo incluído com sucesso')
       			$route.reload()
    }
    
    factory.postaAviso=function(idEdital, aviso){
       	postagemService.postaObjeto(
       			'/edital/'+idEdital+"/salva-aviso"
       			,aviso
       			,'Aviso incluído com sucesso')
       			$route.reload()
    }
    
    factory.alteraStatusAviso=function(idEdital, aviso){
    	postagemService.postaObjeto(
    			"/edital/"+idEdital+"/remove-restaura-aviso"
    			, aviso
    			, 'Status do aviso alterado com sucesso')
    }
    
    factory.alteraEdital=function(edital){
      	postagemService.postaObjeto(
    			'/edital/'+edital.idEdital+'/salva-edital'
    			, edital
    			,'Edital alterado com sucesso')
    }
    
    factory.alteraStatus=function(idEdital,idArquivo){
    	extratorService.extrator('/edital/'+idEdital+"/arquivo/"+idArquivo+"/status/altera",'AlteraStatusArquivo')
    	
    }
    factory.getCandidatos=function(idEdital,idArquivo){
    	extratorService.extrator('/edital/'+idEdital+"/candidato/lista",'ListaCandidatosEvento')
    	
    	
    }
    factory.getFechamentos=function(idEdital){
    	extratorService.extrator('/edital/'+idEdital+"/fechamentos/lista",'ListaFechamentosEvento')
    	
    	
    }
    
    factory.getData=function(idEdital){
    		$http.get('/edital/'+idEdital+'/data-oficial').then(function(response) {
			 factory.dataAtual=response.data.data
    		}, function(response) {
        		$rootScope.$broadcast("NovoAviso", response);
        	})
    }
    
    factory.getHistorico=function(idEdital, idCandidato){
    	endPointsService.getHistorico(idEdital, idCandidato)
    }
    $rootScope.$on('HistoricoDoCandidato',function(event,args){factory.historico=args.data.registros})
    
    factory.getIntegridade=function(idEdital){
    	extratorService.extrator('/edital/'+idEdital+"/teste/integridade",'TesteDeIntegridade')
    }
    
    return factory;
    
}]);