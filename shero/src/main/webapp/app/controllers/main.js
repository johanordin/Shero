'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the SHeroApp
 */
angular.module('SHeroApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
   
  });
