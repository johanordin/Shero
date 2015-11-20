'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:
 * @description
 * # 
 * 
 */
angular.module('SHeroApp').controller('ModalRentItemCtrl', function ($scope, $modalInstance, item) {

	//Data of the user-form which is going to sent to server
    $scope.formData = {};
    
    $scope.item = item
    console.log("item: " + $scope.item.name);
    
    // adjust time with UTC/GMT
    var timestamp = new Date().setUTCHours(0, 0, 0, 0);
    
    $scope.formData.selectedDates=[timestamp];
    $scope.formData.tags = [];
    $scope.formData.availabilityDates = $scope.item.availabilityDates;
    
    //Variables for DatePicker
    $scope.activeDate = null;
	$scope.type = 'individual';
	
	$scope.title = 'Rent a Item';
	
	// TODO: need to disable the dates that not are available
    $scope.disabled = function(date, mode) {
        return ( ( date.getDay() === 0 || date.getDay() === 6 ) );
        //return true;
    };
	
	
    //remove a selected date from the selectedDates-array
	$scope.removeDateFromSelected = function(dt) {
		$scope.formData.selectedDates.splice($scope.formData.selectedDates.indexOf(dt), 1);
	};
	
    $scope.okClicked = function () {
        console.log("Ok");
    };

    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };
    
    
});