angular.module('SHeroApp')
	.controller('AddItemCtrl', function($scope, $location) {
	    
	    $scope.formData = {};
	    $scope.formData.selectedDates=[];

	    $scope.activeDate = null;
  		$scope.type = 'individual';
	    
	    // function to process the form
	    $scope.processForm = function() {
	        alert('Now validate and send to server!');
	    }; 
  
		$scope.removeDateFromSelected = function(dt) {
			$scope.formData.selectedDates.splice($scope.formData.selectedDates.indexOf(dt), 1);
		};

		$scope.$watch( function () { return $location.path(); }, function (path) {
			//$scope.path = path;
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
	});