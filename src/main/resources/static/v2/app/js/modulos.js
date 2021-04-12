var removalServiceServices = angular.module('removalServiceServices', ['ngRoute']);

var removalServiceControllers = angular.module('removalServiceControllers', [ 'removalServiceServices',
                                                                              'ngDialog']);

var removalServiceApp = angular.module('removalServiceApp', [
    'ngRoute',
    'ngSanitize',
    'ngDialog',
    'removalServiceServices',
    'removalServiceControllers',
    'ui.bootstrap.datetimepicker'
]);

var editalBloqueadoModulo = angular.module('editalBloqueadoModulo', []);