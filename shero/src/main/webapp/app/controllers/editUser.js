angular.module('SHeroApp')
	.controller('EditUserCtrl', function($scope, $http, $location, $rootScope) {

	    $scope.generalForm = {};
		$scope.addressForm = {};
	    $scope.passwordForm = {};

	    $scope.formData = {};	      
	    $scope.addressData = {};

        $scope.$watch(function() { return $scope.formData.addressId; }, function(addressId) {
        	// addressId changes initially, when initialized, but will be empty
        	if (addressId) {
        		$scope.generalForm.id = addressId;
        		$scope.addressData.id = addressId;//addressById[addressId].id;
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
	    	console.log("genId: " + $scope.generalForm.id);

	    	var userId = $scope.$storage.user.id;
	    	// TODO: hash password?
	    	$http({
	    		method: 'PUT', // TODO: should be PUT request for update
	    		url: '/rest/users/' + userId,
	    		data: $scope.generalForm
	    	}).then(function successCallback(response) {
			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	    // TODO: offer possibility to delete an existing address
	    $scope.processNewAddress = function() {
	    	var userId = $scope.$storage.user.id;
	    	var address = $scope.addressData;
	    	address.id = undefined; // TODO: really necessary?

	    	$http({
	    		method: 'POST',
	    		url: '/rest/users/' + userId + '/addresses',
	    		data: address
	    	}).then(function successCallback(response) {
	    		// TODO: again update local storage
			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	    $scope.processAddressForm = function() {
	    	console.log($scope.addressData.country);
	    	console.log($scope.addressData.city);
	    	console.log($scope.addressData.street);

	    	var addressId = $scope.addressData.id;

	    	$http({
	    		method: 'PUT',
	    		url: '/rest/addresses/' + addressId,
	    		data: $scope.addressData
	    	}).then(function successCallback(response) {

	    		// TODO: FABI, please update user data
	    		// response looks like:
	    		// {"id":5075345673814016,"country":"countryyy","city":"BarcelonaA","zipcode":"101","street":"Avd. Diagonal"
				// ,"number":"21","additional":"some additional bullshit"}

			    console.log("success: " + response);
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 

	    $scope.processPasswordForm = function() {

	    	// TODO: make sure password and confirmed password match

	    	var hashedPassword = $scope.passwordForm.password;
	    	var userId = $scope.$storage.user.id; // TODO: find better way to retrieve id...
	    	console.log("id: " + userId);

	    	$http({
	    		method: 'PUT',
	    		url: '/rest/users/' + userId + '/mail',
	    		data: '{"hashedPassword":"' + hashedPassword + '"}'
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