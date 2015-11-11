'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:SearchBarCtrl
 * @description
 * # SearchBarCtrl
 * Controller of the SHeroApp which is used to control the search-fields inside of the navbar.
 */

angular.module('SHeroApp').controller('SearchBarCtrl', function($scope, $location, ItemsService, AddressesService, SearchResultService) {
	$scope.isHome = false;
    
    $scope.formData = {};
    
    $scope.datepickerStatus = {
        fromOpened: false,
        toOpened: false
    };
    $scope.dateOptions = {
        formatYear: 'yyyy',
        startingDay: 1
    };
    
    $scope.minDate = $scope.minDate ? null : new Date();
    
    $scope.fromOpen = function($event) {
        $scope.datepickerStatus.fromOpened = true;
    };
    $scope.toOpen = function($event) {
        $scope.datepickerStatus.toOpened = true;
    };
    
    var getCitiesPromise = AddressesService.getAllCities();
    getCitiesPromise.then(function(response) {
        $scope.cities = response.data;
    });
    
    $scope.processForm = function () {
        var from = $scope.formData.from ? (Date.parse($scope.formData.from)) : '';
        var to = $scope.formData.to ? (Date.parse($scope.formData.to)) : '';

        var searchItemsPromise = ItemsService.searchItems($scope.formData.name, $scope.formData.city, from, to);
            searchItemsPromise.then(function(response) {
                SearchResultService.setSearchResults(response.data); 
                $location.path("/SearchResults");
            });
    };
});
