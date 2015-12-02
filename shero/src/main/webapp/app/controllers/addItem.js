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
	    
	    // adjust time with UTC/GMT
	    var timestamp = new Date().setUTCHours(0, 0, 0, 0);
	    
	    $scope.formData.selectedDates=[timestamp];
        $scope.formData.tags = [];

	    //Variables for DatePicker
	    $scope.activeDate = null;
  		$scope.type = 'individual';
  		
  		// Variables for ImageUploader
  		$scope.outer_scope_flow = {};
		$scope.errors = [];
  		
  		// calling default upload() from flow
		$scope.triggerImage = function(value){	
			// Send the imageId with the request to create image
			$scope.outer_scope_flow.opts.query = {'itemId': value};
			$scope.outer_scope_flow.upload();
			//console.log('flow.upload() called  with itemId: ' + value);
		}
	
	    //onSubmit-function of user-form
	    $scope.processForm = function() {
            var postItemPromise = ItemsService.postItem($scope.formData);
            postItemPromise.then(function(response) {
                SessionStorageService.addUserItem(response.data);
                
                var itemId = response.data.id;
    	    	$scope.triggerImage(itemId);
    	    	//console.log("itemId: " + itemId);
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
		

		// limit image size
		$scope.validate = function (file) {
		   //console.log('filesize:' + file.size/(1024*1024) +'MB');
		   //2 MB limit
		   if (file.size > 2000000) {
			   $scope.errors.push({file:file, error: "Image is too big, try with another one"});
			   return false;
		  }
		  return true;
		}
		
		// 
		$scope.imageUpload = function($file, $event, $flow ){						
			//outer_scope_file = $file;
			$scope.outer_scope_flow = $flow;
			//outer_scope_event = $event;
		}
		
		// redirect
		$scope.redirect = function () {
			console.log("Redirect");
			$location.path("/Home");
//			setTimeout(function() { 
//				$location.path("/Home");
//			}, 3000);
			
		}		
	});
