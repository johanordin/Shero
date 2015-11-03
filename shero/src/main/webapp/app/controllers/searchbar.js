'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:SearchBarCtrl
 * @description
 * # SearchBarCtrl
 * Controller of the SHeroApp which is used to control the search-fields inside of the navbar.
 */

angular.module('SHeroApp').controller('SearchBarCtrl', function($scope, ItemsService, AddressesService) {
	$scope.isHome = false;
    
    $scope.formData = {};
    $scope.formData.name = "";
    $scope.formData.city = "";
    $scope.formData.from = "";
    $scope.formData.to = "";
    
    var getCitiesPromise = AddressesService.getAllCities();
    getCitiesPromise.then(function(response) {
        //$scope.cities;
        console.log(JSON.stringify(response));
    });
    
    $scope.processForm = function () {
        var searchItemsPromise = ItemsService.searchItems($scope.formData.name, $scope.formData.city, $scope.formData.from, $scope.formData.to);
            searchItemsPromise.then(function(response) {
                console.log(JSON.stringify(response));
            });
    };
});
