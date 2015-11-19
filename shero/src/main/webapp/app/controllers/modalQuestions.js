'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:
 * @description
 * # 
 * 
 */
angular.module('SHeroApp').controller('ModalQuestionsCtrl', function ($scope, $modalInstance, MessageService) {

	$scope.formData = {};
	
	console.log("ownerId: " + $scope.ownerId);
	
    $scope.sendMessage = function() {
    	var data = {};
    	data.name = $scope.formData.name;
    	data.mail = $scope.formData.mail;
    	data.message = $scope.formData.message;
    	data.ownerId = $scope.ownerId;
        
        console.log("name   : " + data.name);
        console.log("mail   : " + data.mail);
        console.log("message: " + data.message);
        console.log("ownerId : " + data.ownerId);
        
        var postMesg = MessageService.postMessage(data);
        postMesg.then(function(response) {
        	
        	//TODO: check that the response is valid.
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