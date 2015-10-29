 /**
 * @ngdoc function
 * @name SHeroApp.controller:AddItemCtrl
 * @description
 * # AddItemCtrl
 * Controller of the SHeroApp which is used for all operations on the user-form for
 * adding a new item.
 */
angular.module('SHeroApp')
	.controller('AddItemCtrl', function($scope, $http, $location, $rootScope, ItemsService, SessionStorageService, fileUploadService) {
	    
		//Data of the user-form which is going to sent to server
	    $scope.formData = {};
	    $scope.formData.selectedDates=[new Date().setHours(0, 0, 0, 0)];
        $scope.formData.tags = [];

	    //Variables for DatePicker
	    $scope.activeDate = null;
  		$scope.type = 'individual';
	    
	    //onSubmit-function of user-form
	    $scope.processForm = function() {
            var postItemPromise = ItemsService.postItem($scope.formData);
            postItemPromise.then(function(response) {
                SessionStorageService.updateUserData(response.data);
                alert ("Item created!");
            });
	    }; 
  
	    //remove a selected date from the selectedDates-array
		$scope.removeDateFromSelected = function(dt) {
			$scope.formData.selectedDates.splice($scope.formData.selectedDates.indexOf(dt), 1);
		};

		//react on change of the view in the form and set variable of specified path to true
		$scope.$watch( function () { return $location.path(); }, function (path) {
			if (path=="/AddItem/GeneralInformation") {
				$scope.isGeneral = true;
				$scope.isAvailability = false;
				$scope.isPictue = false;
			} else if (path=="/AddItem/Availability") {
				$scope.isGeneral = false;
				$scope.isAvailability = true;
				$scope.isPictue = false;
			} else if (path=="/AddItem/Pictures") {
				$scope.isGeneral = false;
				$scope.isAvailability = false;
				$scope.isPictue = true;
			}
		});
    
        $scope.uploadFile = function(){
            
        	//var file2 = $scope.flow-drop;
            console.log('scope  : ' + $scope);
            //console.log('scope is : ' + scope);
            //console.log('scope is', scope);
            console.log('scope is test:');
            //var e = scope()
            //console.log('e : ' + e);
            //console.log('file  : ' + $file);
            //console.log('flow  : ' + $flow);
            
        	var file = $scope.myFile;
            var uploadUrl = "/UploadServlet";
	        console.log('file  : ' + file);
	        //console.log('file2 : ' + file2);
            fileUploadService.uploadFileToUrl(file, uploadUrl);
        };
	});
