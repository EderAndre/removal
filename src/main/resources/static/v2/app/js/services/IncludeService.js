removalServiceApp.service('includeService',[function() {
	this.includeJS=function(listaArquivos){
		for (index = 0, len = listaArquivos.length; index < len; ++index) {
			$('body').append('<script type="text/javascript" src="'+ listaArquivos[index]+ '"></script>')
		}
	}
}]);