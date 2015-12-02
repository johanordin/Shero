'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:
 * @description
 * # 
 * 
 */
angular.module('SHeroApp').controller('ModalQuestionsCtrl', function ($scope,  $modalInstance, MessageService, SessionStorageService, itemID) {
	
	// input from user
	$scope.formData = {};
	
    $scope.itemID = itemID;
    $scope.renterID  = SessionStorageService.getUserId();
    $scope.firstName = SessionStorageService.getUserFirstname();
    $scope.lastName = SessionStorageService.getUserFirstname();
    $scope.userEmail = SessionStorageService.getUserEmailAddress();
	
    $scope.sendMessage = function() {
    	var data = {};
    	data.itemId = $scope.itemID;
    	data.renterId = $scope.renterID;
    	data.text = $scope.formData.message;
        console.log(JSON.stringify(data));
        
        var postMesg = MessageService.postMessage(data);
        postMesg.then(function(response) {        	
		    console.log("data: " + JSON.stringify(response.data)); 
        });
        $modalInstance.close();
        
    }

    $scope.okClicked = function () {
        console.log("Ok");
        $scope.sendMessage();
        
    };

    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };
    
    
});