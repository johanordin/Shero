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
    	data.itemID = $scope.itemID;
    	data.renterID = $scope.renterID;
    	data.message = $scope.formData.message;

        console.log("itemID   : " + data.itemID);
        console.log("renterID : " + data.renterID);
        console.log("message  : " + data.message);
        
        var postMesg = MessageService.postMessage(data);
        postMesg.then(function(response) {        	
		    console.log("status: " + response.status);
		    console.log("data: " + JSON.stringify(response.data)); 
        });
        console.log("after request");
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