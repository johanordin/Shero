'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:UserMenuCtrl
 * @description
 * # UserMenuCtrl
 * Controller of the SHeroApp to control the UserMenu (right part of the navbar).
 */

angular.module('SHeroApp').controller('UserMenuCtrl', function($scope, $uibModal, $rootScope, $log, SessionStorageService) {
    
  $scope.animationsEnabled = true;

  $scope.openModal = function (size) {

    var modalLogin = $uibModal.open({
      animation: $scope.animationsEnabled,
      templateUrl: 'app/views/Navbar/ModalLogin/ModalLogin.html',
      controller: 'ModalLoginCtrl',
      size: size
    });

    modalLogin.result.then(function () {
        
    });
  };

  $scope.toggleAnimation = function () {
    $scope.animationsEnabled = !$scope.animationsEnabled;
  };
    
	//If 'Logout' is clicked the cookie is deleted
	$scope.logout = function() {
		SessionStorageService.delete();
	};
});
