removalServiceServices.factory('browserService',['$window', '$rootScope',function($window, $rootScope) {

    var factory = {}; 
        
    factory.detectaBrowser=function(){
    	
    	var ua  = $window.navigator.userAgent;
    	var agl={}

        agl.ISFF     = ua.indexOf('Firefox') != -1;
        agl.ISOPERA  = ua.indexOf('Opera') != -1;
        agl.ISCHROME = ua.indexOf('Chrome') != -1;
        agl.ISSAFARI = ua.indexOf('Safari') != -1 && !agl.ISCHROME;
        agl.ISWEBKIT = ua.indexOf('WebKit') != -1;

        agl.ISIE   = ua.indexOf('Trident') > 0 || navigator.userAgent.indexOf('MSIE') > 0;
        agl.ISIE6  = ua.indexOf('MSIE 6') > 0;
        agl.ISIE7  = ua.indexOf('MSIE 7') > 0;
        agl.ISIE8  = ua.indexOf('MSIE 8') > 0;
        agl.ISIE9  = ua.indexOf('MSIE 9') > 0;
        agl.ISIE10 = ua.indexOf('MSIE 10') > 0;
        agl.ISOLD  = agl.ISIE6 || agl.ISIE7 || agl.ISIE8;

        agl.ISIE11UP = ua.indexOf('MSIE') == -1 && ua.indexOf('Trident') > 0;
        agl.ISIE10UP = agl.ISIE10 || agl.ISIE11UP;
        agl.ISIE9UP  = agl.ISIE9 || agl.ISIE10UP;
    	
    	return agl
    	
    }
    
    factory.naoHomologado=function(){
        return this.detectaBrowser().ISOLD
    }
     
    return factory;
    
}]);