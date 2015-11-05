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
        $scope.cities = response.data;
    });
    
    $scope.processForm = function () {
        $scope.from = Date.parse($scope.formData.from)/1000;
        $scope.to = Date.parse($scope.formData.to)/1000;
        var searchItemsPromise = ItemsService.searchItems($scope.formData.name, $scope.formData.city, $scope.from, $scope.to);
            searchItemsPromise.then(function(response) {
                console.log(JSON.stringify(response));
                alert(JSON.stringify(response));
            });
    };
});
