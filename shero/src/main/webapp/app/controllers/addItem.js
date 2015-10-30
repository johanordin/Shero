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
		
		$scope.obj = {};
		
		$scope.$on('flow::fileAdded', function (event, $flow, flowFile) {
			  //event.preventDefault();//prevent file from uploading
				//console.log('flow' + $flow);
				console.log('flow::fileAdded');
				//console.log('flowFile' + flowFile);
		});
		
		$scope.test = function($file, $event, $flow ){
			console.log('$file  	 : ' + $file);
			console.log('$event  	 : ' + $event);
			console.log('$flow  	 : ' + $flow);
			
			
			var file = $file;
            var uploadUrl = "/UploadServlet";
	        console.log('file  : ' + file);
	        
			fileUploadService.uploadFileToUrl(file, uploadUrl);
		}
		
		
		$scope.uploader = {};
		
        $scope.uploadFile = function(){
                        
        	
        	console.log('flow  : ' + $scope.uploader);
        	//console.log(JSON.stringify($scope.uploader));
        	
        	//console.log('flow  : ' + $flow);
        	//console.log(JSON.stringify($flow));
        	console.log('test  : ');
        	//console.log(JSON.stringify($scope.obj));
	        console.log('scope.obj  	 : ' + $scope.obj);
	        console.log('scope.obj.flow  : ' + $scope.obj.flow);
	        
	        var file = $scope.obj.flow;
        	
//        	var file = $scope.myFile;
	        
            var uploadUrl = "/UploadServlet";
	        console.log('file  : ' + file);
//	        
//	            
//	        //console.log('file2 : ' + file2);
	        //fileUploadService.uploadFileToUrl(file, uploadUrl);
        };
	});
