'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:UserMenuCtrl
 * @description
 * # UserMenuCtrl
 * Controller of the SHeroApp to control the UserMenu (right part of the navbar).
 */

angular.module('SHeroApp').controller('UserMenuCtrl', function($scope, $uibModal, $rootScope, $log) {
    
  $scope.animationsEnabled = true;

  $scope.openModal = function (size) {

    var modalLogin = $uibModal.open({
      animation: $scope.animationsEnabled,
      templateUrl: 'app/views/Navbar/ModalLogin/ModalLogin.html',
      controller: 'ModalLoginCtrl',
      size: size,
      resolve: {
        items: function () {
        }
      }
    });

    modalLogin.result.then(function () {
      $log.info('Modal dismissed at: ' + new Date());
    });
  };

  $scope.toggleAnimation = function () {
    $scope.animationsEnabled = !$scope.animationsEnabled;
  };
    
	//If 'Logout' is clicked the cookie is deleted
	$scope.logout = function() {
		document.cookie = 'sheroUserId=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
		$rootScope.loggedIn = false;
	};
});
