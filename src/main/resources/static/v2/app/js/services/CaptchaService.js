removalServiceServices.factory('captcha', function($rootScope, endPointsService) {

    var factory = {}; 
    
    factory.getCaptcha=function(){endPointsService.getCaptcha();}
    $rootScope.$on('CaptchaSolicitado',function(event,args){factory.captcha=args.data})
    
    return factory;
});