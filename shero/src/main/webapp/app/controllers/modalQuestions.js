'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:
 * @description
 * # 
 * 
 */
angular.module('SHeroApp').controller('ModalQuestionsCtrl', function ($scope, $modalInstance) {
	

	
	
    $scope.okClicked = function () {
        console.log("Ok")
    };

    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };
    
    
});