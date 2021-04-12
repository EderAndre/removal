var includeJS=function (filesArray){
	for (index = 0, len = filesArray.length; index < len; ++index) {
		document.write('<script type="text/javascript" src="'+ filesArray[index]+ '"></script>')
	}
}
includeJS([
           /* componentes */
           'bower_components/jquery/dist/jquery.js'
           ,'bower_components/jquery-ui/jquery-ui.js'
           ,'bower_components/moment/moment.js'
           ,'bower_components/angular/angular.js'
           ,'bower_components/angular-route/angular-route.js'
           ,'bower_components/angular-sanitize/angular-sanitize.js'
           ,'bower_components/ngDialog/js/ngDialog.js'
           ,'bower_components/bootstrap/dist/js/bootstrap.min.js'
           ,'bower_components/ckeditor/ckeditor.js'
           ,'bower_components/angular-bootstrap-datetimepicker/src/js/datetimepicker.js'
           /*aplicação base */
           ,'app/js/modulos.js'
           ,'app/js/app.js'
            /*serviços */
           ,'app/js/services/ModalService.js'
           ,'app/js/services/IncludeService.js'
           ,'app/js/services/CandidaturaService.js'
           ,'app/js/services/ExtratorService.js'
           ,'app/js/services/EndPointsService.js'
           ,'app/js/services/PostagemService.js'
           ,'app/js/services/MaestroService.js'
           ,'app/js/services/AdminService.js'
           ,'app/js/services/CaptchaService.js'
           ,'app/js/services/HoraOficialService.js'
           ,'app/js/services/BrowserService.js'
           
           /*diretivas*/
           ,'app/js/directives/FocusOn.js'
           ,'app/js/directives/NgRightClick.js'
           ,'app/js/directives/admin/ckEditor.js'
           /*controllers*/
           ,'app/js/controllers/CandidaturaController.js'
           ,'app/js/controllers/AvisosController.js'
           ,'app/js/controllers/AvisosEditalController.js'
           ,'app/js/controllers/HistoricoController.js'
           ,'app/js/controllers/MenuLateralController.js'
           ,'app/js/controllers/MenuTopoController.js'
           ,'app/js/controllers/InfosEditalController.js'
           ,'app/js/controllers/HorarioSistemaController.js'
           ,'app/js/controllers/DocsAuxiliaresController.js'
           ,'app/js/controllers/BoasVindasController.js'
           ,'app/js/controllers/InfosEditalController.js'
           ,'app/js/controllers/HorarioSistemaController.js'
           ,'app/js/controllers/admin/MenuAdminController.js'
           ,'app/js/controllers/admin/AvisosController.js'
           ,'app/js/controllers/admin/ArquivosController.js'
           ,'app/js/controllers/admin/EditaisController.js'
           ,'app/js/controllers/admin/CandidatosController.js'
           ,'app/js/controllers/admin/FechamentosController.js'
           ,'app/js/controllers/admin/IntegridadeController.js'
           ])
