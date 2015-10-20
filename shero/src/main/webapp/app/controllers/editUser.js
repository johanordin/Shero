angular.module('SHeroApp').controller('editUserCtrl', function($scope) {

		//Data of the user-form which is going to sent to server
	    $scope.formData = {};


	    //onSubmit-function of user-form
	    $scope.processForm = function() {
	        console.log($scope.formData);
	    }; 

	}