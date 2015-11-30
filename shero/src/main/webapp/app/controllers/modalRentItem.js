'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:
 * @description
 * # 
 * 
 */
angular.module('SHeroApp').controller('ModalRentItemCtrl', function ($scope, $modalInstance, ItemsService, item) {

	//Data of the user-form which is going to sent to server
    $scope.formData = {};
    
    $scope.item = item;

    $scope.formData.selectedDates=[];
    
    //Variables for DatePicker
    $scope.activeDate = null;
	$scope.type = 'individual';  
	
	$scope.title = 'Rent an Item';
	
	var date = new Date();
	// Date.getTimezoneOffset() returns the time-zone offset from UTC, in minutes
	var offset = (-1) * date.getTimezoneOffset() * 60000;
	
    $scope.disabled = function(date, mode) {
        var disabled = true;
        var currentTime = new Date().setHours(0, 0, 0, 0) + offset;
        $scope.item.availabilityDates.forEach(function (availableDate) {
            if (((date.setHours(0, 0, 0, 0) + offset) === availableDate) && (currentTime < availableDate)) {
                disabled = false;
            }
        });
        return disabled;
    };
    
    $scope.getDayClass = function(date, mode) {
        var customClass = 'available';
        return customClass;
    }
	
    //remove a selected date from the selectedDates-array
	$scope.removeDateFromSelected = function(dt) {
		$scope.formData.selectedDates.splice($scope.formData.selectedDates.indexOf(dt), 1);
	};
	
    $scope.okClicked = function () {
        $scope.rent($scope.item.id, $scope.formData.selectedDates);
    };

    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };
    
        //rent item --> itemId + period [1447977600000,1447977600000]
    $scope.rent = function(itemId, period) {
        var rentalData = {};
        rentalData.itemId = itemId;
        rentalData.period = period;
        var rentItemPromise = ItemsService.rentItem(rentalData);
        rentItemPromise.then(function(response) {
            console.log(JSON.stringify(response.data));
            $modalInstance.dismiss('cancel');
        });
    }
});