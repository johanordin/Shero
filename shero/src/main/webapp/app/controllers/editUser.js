angular.module('SHeroApp')
	.controller('EditUserCtrl', function($scope, $http) {

	    $scope.generalForm = {};
		$scope.addressForm = {};
	    
		// TODO: get userId/key of user that is logged in
		// TODO: retrieve respective general information and load data into forms

	    $scope.processGeneralForm = function() {
	    	console.log($scope.generalForm);
	    	// TODO: hash password?
	    	$http({
	    		method: 'POST', // TODO: should be PUT request for update
	    		url: '/rest/users',
	    		data: $scope.generalForm
	    	}).then(function successCallback(response) {
			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	    // TODO: retrieve user's addresses
	    // TODO: offer possibility to edit/delete an existing, or create a new address

	    $scope.processAddressForm = function() {
	    	$http({
	    		method: 'POST',
	    		url: '/rest/addresses',
	    		data: $scope.addressForm
	    	}).then(function successCallback(response) {
			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	});