 /**
 * @ngdoc function
 * @name SHeroApp.controller:AddItemCtrl
 * @description
 * # AddItemCtrl
 * Controller of the SHeroApp which is used for all operations on the user-form for
 * adding a new item.
 */
angular.module('SHeroApp')
	.controller('AddItemCtrl', function($scope, $http, $location, ItemsService, fileUpload) {
	    
		//Data of the user-form which is going to sent to server
	    $scope.formData = {};
	    $scope.formData.selectedDates=[new Date().setHours(0, 0, 0, 0)];

	    //Variables for DatePicker
	    $scope.activeDate = null;
  		$scope.type = 'individual';
	    
	    //onSubmit-function of user-form
	    $scope.processForm = function() {
            var postItemPromise = ItemsService.postItem($scope.formData);
            postItemPromise.then(function(response) {
                console.log("Item " + response.data.id + " created!");
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
            var file = $scope.myFile;
            console.log('file is ' );
            console.dir(file);
            var uploadUrl = "/UploadServlet";
            console.dir(uploadUrl);
            fileUpload.uploadFileToUrl(file, uploadUrl);
        };
	})

//    .controller('myCtrl', function($scope, fileUpload){
//    
//        $scope.uploadFile = function(){
//            var file = $scope.myFile;
//            console.log('file is ' );
//            console.dir(file);
//            var uploadUrl = "/fileUpload";
//            fileUpload.uploadFileToUrl(file, uploadUrl);
//        };
//    
//    });