angular.module('SHeroApp')
	.controller('EditUserCtrl', function($scope, $http, $location, $rootScope) {

	    $scope.generalForm = {};
		$scope.addressForm = {};
	    
	    $scope.formData = {};      

        $scope.$watch(function() { return $scope.formData.addressId; }, function(addressId) {
        	// addressId changes initially, when initialized, but will be empty
        	if (addressId) {
				$scope.addressData.country = addressById[addressId].country;
        		$scope.addressData.city = addressById[addressId].city;
        		$scope.addressData.zipcode = addressById[addressId].zipcode;
        		$scope.addressData.street = addressById[addressId].street;
        		$scope.addressData.number = addressById[addressId].number;
        		$scope.addressData.additional = addressById[addressId].additional;
        	}
   		});

   		var addressById = {};

   		// get userId/data of user that is logged in
        $scope.init = function() {
        	var addresses = $scope.$storage.user.addresses;
	       	for (var i = 0; i < addresses.length; i++) {
    			console.log(addresses[i].id);
    			addressById[addresses[i].id] = addresses[i];
			}
		};

		// call init function when controller is loaded
        $scope.init();

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

	    $scope.processPasswordForm = function() {
	    	$http({
	    		method: 'POST',
	    		url: '/rest/TODO',
	    		data: $scope.addressForm
	    	}).then(function successCallback(response) {
			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	    //react on change of the view in the form and set variable of specified path to true
		$scope.$watch( function () { return $location.path(); }, function (path) {
			if (path=="/EditUser/General") {
				$scope.isGeneral = true;
				$scope.isAddress = false;
				$scope.isPassword = false;
			} else if (path=="/EditUser/Address") {
				$scope.isGeneral = false;
				$scope.isAddress = true;
				$scope.isPassword = false;
			} else if (path=="/EditUser/Password") {
				$scope.isGeneral = false;
				$scope.isAddress = false;
				$scope.isPassword = true;
			}
		});
	});