describe('Testando serviço de detecção de browsers', function () {
   beforeEach(function () {
      module('removalServiceServices');
      
   });
   var $window;
	
	  beforeEach(inject(function($injector){
		
		  $window=$injector.get('$window');
	  }));
	    
	
   it('Detecção de Firefox Correta', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: "Firefox" };
	   expect(browserService.detectaBrowser().ISFF).toBe(true);
	   expect(browserService.detectaBrowser().ISIE).toBe(false);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
  
   it('Detecção de Internet Explorer Correta-Trident', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " Trident" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE).toBe(true);
 
	}));
   it('Detecção de Internet Explorer Correta-MSIE', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE).toBe(true);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 6', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 6" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE6).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 7', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 7" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE7).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 8', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 8" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE8).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 9', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 9" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE9).toBe(true);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 10', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 10" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE10).toBe(true);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
   it('Detecção de Internet Explorer Correta-MSIE 11', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: "XX Trident" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE11UP).toBe(true);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
   it('Detecção de Internet Explorer Correta-acima de versao 9', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 10" };
	   expect(browserService.detectaBrowser().ISFF).toBe(false);
	   expect(browserService.detectaBrowser().ISIE9UP).toBe(true);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
   it('Detecção de Internet Explorer Correta-versões antigas', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: " MSIE 6" };
	   expect(browserService.detectaBrowser().ISOLD).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	   $window.navigator = {userAgent: " MSIE 7" };
	   expect(browserService.detectaBrowser().ISOLD).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	   $window.navigator = {userAgent: " MSIE 8" };
	   expect(browserService.detectaBrowser().ISOLD).toBe(true);
	   expect(browserService.naoHomologado()).toBe(true);
	  
	  
	}));
   it('Detecção de Chrome Correta', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: "Chrome" };
	   expect(browserService.detectaBrowser().ISCHROME).toBe(true);
	   expect(browserService.detectaBrowser().ISIE).toBe(false);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));
   it('Detecção de OPERA Correta', inject(function ($window,browserService) {
	   $window.navigator = {userAgent: "Opera" };
	   expect(browserService.detectaBrowser().ISOPERA).toBe(true);
	   expect(browserService.detectaBrowser().ISIE).toBe(false);
	   expect(browserService.naoHomologado()).toBe(false);
	  
	}));

   

});