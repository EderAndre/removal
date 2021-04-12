removalServiceApp.directive('ngRightClick',
				function($parse,$route) {
					return function(scope, element, attrs) {
						var rightClickDisabled = attrs.ngRightClick;
						if (!rightClickDisabled) {
							element.bind('contextmenu', function(event) {
								scope.$apply(function() {
									event.preventDefault();
								});
							});
							element.bind('keydown',function(event) {
												scope.$apply(function(
																$rootScope) {
															if ((event.which
																	|| event.keyCode || event.charCode) == 116
																	|| ((event.which|| event.keyCode || event.charCode) == 82 && event.ctrlKey)) { // f5
																// keyCode
																event.preventDefault();
																$route.reload();
															}
														});
											});
						}
					}
});