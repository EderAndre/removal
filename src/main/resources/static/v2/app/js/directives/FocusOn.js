removalServiceControllers.directive('focusOn',
        function(){
            return {
                        link: function (scope, element,attr) {
                    		scope.$watch(attr.focusOn,function(e){
                    			element[0].focus()       	
                			})
                }
            };
    });