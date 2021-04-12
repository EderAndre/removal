removalServiceServices.factory('maestro', function($rootScope,$location,$http,$route, endPointsService,browserService) {

    var factory = {}; 
    
    factory.perfis=['[CANDIDATO]','[OPERADOR]','[SUPER_USER]']
   
    factory.getInfoEdital=function(idEdital){endPointsService.getInfoEdital(idEdital)}
    	$rootScope.$on('InfoEdital',function(event,args){factory.infoEdital=args.data})
    
    factory.getHistorico=function(idEdital, idCandidato){
    	if(this.config.perfil==this.perfis[0] &&  idCandidato!=undefined &&  idCandidato!=null){
    		endPointsService.getHistorico(idEdital, idCandidato)
    	}
    	}
    	$rootScope.$on('HistoricoDoCandidato',function(event,args){factory.historico=args.data.registros})
    
    factory.getAvisosEdital=function(idEdital){endPointsService.getAvisosEdital(idEdital)}
    	$rootScope.$on('AvisosEdital',function(event,args){	factory.avisosEdital=args.data;})
    
    factory.getDocs=function(idEdital){endPointsService.getDocsAuxiliares(idEdital)}
    	$rootScope.$on('DocsAuxiliaresEvento',function(event,args){
    	var list=[]
    	 angular.forEach(args.data, function(value, key) {
			var doc = {idDoc: value.idArquivo,
						rotuloDoc: value.descricaoArquivo,
						dataDoc: value.dataArquivo,
						linkDoc: '/edital/' +value.idEdital+ '/arquivo/' +value.idArquivo+ '/download',
						exibir: value.exibirArquivo};
			list.push(doc);
		}); 
    	factory.docsEdital=list
    	})
 
    factory.getVagas=function(idEdital, idCandidato){endPointsService.getVagas(idEdital, idCandidato);}
    	$rootScope.$on('VagasDoCandidato',function(event,args){factory.vagas=args.data.vagas})
    
    factory.getConfig=function(){endPointsService.getConfig();}
    	$rootScope.$on('ConfigDoUsuario',function(event,args){
    		factory.config=args.data;
    		factory.config.editaisAtivos=args.data.editais;
    	})
    	factory.getConfig()
		                                  
    factory.isAdmin = function() {
        if (this.config != undefined) {
            if (this.config.perfil == this.perfis[2]) {
                return true;
            }
        }
        return false;
    };
    
    factory.isOperador = function() {
    	if (this.config != undefined) {
            if (this.config.perfil == this.perfis[1]) {
                return true
            }	
    	}
        return false
    }
   
    factory.boasVindas = function(perfil, quantidadeEditais) {
    	var homolog='Seu navegador não foi homologado para esta aplicação! <br>Utilize um dos navegadores abaixo, ou versões superiores:<br>\
    				<table width="100%" class="table table-striped table-bordered">\
							<tr class="success text-center">\
								<td>Navegador</td>\
								<td>Versão mínima recomendada</td>\
							</tr>\
							<tr>\
								<td>Internet Explorer</td>\
								<td>10</td>\
							</tr>\
							<tr>\
								<td >Mozilla Firefox</td>\
								<td>38.0.0</td>\
							</tr>\
							<tr>\
								<td>Chrome</td>\
								<td>31.0.0</td>\
							</tr>\
							<tr>\
								<td >Safari</td>\
								<td >8.0</td>\
							</tr>\
							<tr>\
								<td>Opera</td>\
								<td >31</td>\
							</tr>\
						</table>\
						'
    	 var boasVindasSwitch=[
    	                       {titulo:"Bem-vindo(a)!",texto:'Você não está habilitado a participar de nenhum edital aberto!',prosseguir:false}
    	                      ,{titulo:"Bem-vindo(a)!",texto:'Você foi habilitado a participar de um edital, clique abaixo para prosseguir',prosseguir:true}
    	           			  ,{titulo:"Bem-vindo(a), Operador(a)!",texto:'Você foi habilitado a operar um edital, clique abaixo para prosseguir',prosseguir:true}
    	           			  ,{titulo:"Bem-vindo(a), Operador(a)!",texto:'Escolha um edital para prosseguir',prosseguir:false}
    	           			  ,{titulo:"Bem-vindo(a), Administrador(a)!",texto:'Você foi habilitado a administrar um edital, clique abaixo para prosseguir',prosseguir:true}
  	           			  	  ,{titulo:"Bem-vindo(a), Administrador(a)!",texto:'Escolha um edital para prosseguir',prosseguir:false}
  	           			  	  ,{titulo:"Atenção!",texto:homolog,prosseguir:false}
    	           			  ]
    	 if( browserService.naoHomologado()){return boasVindasSwitch[6]}
    	 else if(perfil==this.perfis[0] && quantidadeEditais==0){return boasVindasSwitch[0]}
    	 else if(perfil==this.perfis[0]){return boasVindasSwitch[1]}
    	 else if(perfil==this.perfis[1]&& quantidadeEditais==1){return boasVindasSwitch[2]}
    	 else if(perfil==this.perfis[1] && quantidadeEditais>1){return boasVindasSwitch[3]}
    	 else if(perfil==this.perfis[2] && quantidadeEditais==1){return boasVindasSwitch[4]}
    	 else if(perfil==this.perfis[2] && quantidadeEditais>1){return boasVindasSwitch[5]}

    }
    factory.editalBloqueado= function(indexEdital) {
    	var edital=this.config.editaisAtivos[indexEdital]
     	if(edital!=null && edital.editalBloqueado==true && !this.isAdmin()){
    	$rootScope.$broadcast('editalEscolhido',null)
    	$location.path('/edital/boas-vindas')
    	$rootScope.$broadcast("NovoAviso", 'O Edital '+edital.idEdital+'-'+edital.descricaoEdital+' está temporariamente bloqueado, entre em contato com a equipe de Unidade de Sistema para maiores informações');
     	}
    }
    return factory;
});