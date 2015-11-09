angular.module('SHeroApp')
	.controller('EditUserCtrl', function($scope, $http, $location, $rootScope, SessionStorageService) {

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
        	var addresses = SessionStorageService.getUserAddressAll();      	
	       	if (addresses) {
	        	for (var i = 0; i < addresses.length; i++) {
	    			addressById[addresses[i].id] = addresses[i];
				}	
        	}
		};

		// call init function when controller is loaded
        $scope.init();
		
		//update user info
	    $scope.processGeneralForm = function() {
			
	    	console.log($scope.generalForm);
	    	var userId = SessionStorageService.getUserId();
	    	console.log("userId: " + userId);
	    	$http({
	    		method: 'PUT', // TODO: should be PUT request for update
	    		url: '/rest/users/' + userId,
	    		data: $scope.generalForm
	    	}).then(function successCallback(response) {
	    		SessionStorageService.updateUserData(response.data);
	    		
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
	    }; 
			    
			//delete Address
		$scope.deleteAddress = function() {
			var addressId = $scope.addressData.id;
			console.log(addressId);
			// added check whether input is empty or not
			if (addressId) {
	    	$http({
	    		method: 'DELETE',
	    		url: '/rest/addresses/' + addressId,
	    		data: $scope.addressData 
	    	}).then(function successCallback(response) {
	    		SessionStorageService.deleteUserAddress(addressId);  
			    console.log("success: " + JSON.stringify(response.data));
                alert ("Address deleted!");
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
			
			}
			
			
		}
		
		
		// adds new address 
	    $scope.processNewAddress = function() {
	    	var userId = SessionStorageService.getUserId();
	    	var address = $scope.addressData;
	    	address.id = undefined; // TODO: really necessary?

			if ($scope.addressForm.country == undefined){
				alert("Address is empty");
			} else{
	    	$http({
	    		method: 'POST',
	    		url: '/rest/users/' + userId + '/addresses',
	    		data: address  
	    	}).then(function successCallback(response) {
	    		SessionStorageService.addUserAddress(response.data);
			    console.log("success: " + JSON.stringify(response.data));
                alert("New address added!");
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
			} 			
	    }; 
		
		// update address
	    $scope.processAddressForm = function() {
	    	var addressId = $scope.addressData.id;
			
			// added check whether input is empty or not
			if (addressId) {
	    	$http({
	    		method: 'PUT',
	    		url: '/rest/addresses/' + addressId,
	    		data: $scope.addressData  // this is what we send
	    	}).then(function successCallback(response) {
	    		SessionStorageService.updateUserAddressSpecific(response.data);
			    console.log("success: " + JSON.stringify(response.data));
                				
			}, function errorCallback(response) {
			    console.log("error: " + response);
			});
			
			}
	    }; 

	    $scope.processPasswordForm = function() {
	    	
	    if ($scope.passwordForm.password.localeCompare($scope.passwordForm.reppassword) == 0) {
					
			var hashedPassword = Sha256.hash($scope.passwordForm.password);
	    	var userId = SessionStorageService.getUserId();
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
		} else {
			alert ("Password and Confirm password don't match!");
		}
		
		
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