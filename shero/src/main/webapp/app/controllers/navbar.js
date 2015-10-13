'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:NavbarCtrl
 * @description
 * # MainCtrl
 * Controller of the SHeroApp
 */
angular.module('SHeroApp')
  .controller('NavbarCtrl', function ($scope, $location) {
    $scope.awesomeThings = ['HTML5 Boilerplate', 'AngularJS', 'Karma'];
   
   	$scope.isActive = function (viewLocation) { 
        return viewLocation === $location.path();
    };
  });
