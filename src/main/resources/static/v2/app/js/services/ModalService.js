removalServiceServices.service('ModalService', ['ngDialog', function(ngDialog) {
    this.showModal = function(titulo, conteudo) {
    	ngDialog.open({
    	    template: '\
    	    	<div class="panel panel-default">\
					<div class="panel-heading">\
				      <h3 class="panel-title">'+titulo+'</h3>\
				    </div>\
				    <div class="panel-body " style="word-wrap: break-word;">\
				      <span>'+conteudo+'</span>\
				    </div>\
				    <div class="panel-footer ngdialog-buttons">\
			           <button type="button" class="ngdialog-button ngdialog-button-secondary" ng-click="closeThisDialog(0)">Fechar <span class="glyphicon glyphicon glyphicon-log-out"></span></button>\
				   </div>\
				</div>',
     plain: true
    	});
    } 
}]);