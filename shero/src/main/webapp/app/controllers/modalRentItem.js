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
    
    $scope.item = item

    
    $scope.formData.selectedDates=[];
    //console.log (JSON.stringify($scope.item.availabilityDates));
    //console.log (JSON.stringify($scope.formData.selectedDates));
    
    //Variables for DatePicker
    $scope.activeDate = null;
	$scope.type = 'individual';
	
	$scope.title = 'Rent a Item';
	
	// TODO: need to disable the dates that not are available
    $scope.disabled = function(date, mode) {
        var disabled = true;
        $scope.item.availabilityDates.forEach(function (availableDate) {
            if (date.setUTCHours(0, 0, 0, 0) === availableDate) {
                disabled = false;
            }
        });
        return disabled;
    };
	
	
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