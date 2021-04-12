removalServiceControllers.directive('ckEditor', function () {
    return {
    
    	require: '?ngModel',
    	link: function ($scope, elm, attr,ngModel) {
       	var ck=CKEDITOR.replace(elm[0],
	             {on: {
	                 pluginsLoaded: function() {
	                     var editor = this;
	                     var config = editor.config;
	                     }
	             }})
       	 ck.on('instanceReady', function () {
            $scope.$apply(function () {
            	ck.setData(ngModel.$modelValue);
                ngModel.$setViewValue(ck.getData());
            });
        });
         ck.on('pasteState', function () {
            $scope.$apply(function () {
                ngModel.$setViewValue(ck.getData());
            });
        });
        ngModel.$render = function (value) {
           ck.setData(ngModel.$modelValue);
        };
       }
    }
})