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
    $scope.itemSuggestions = ItemsService.getItemSuggestions();
    $scope.citySuggestions = AddressesService.getCitySuggestions();
    
    $scope.formData = {};
    $scope.formData.item = {};
    $scope.formData.city = {};
    
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
    
    $scope.processForm = function () {
    	// calendar seems to return timestamps based on local time, which is not necessarily UTC
    	// Date.getTimezoneOffset() returns the time-zone offset from UTC, in minutes
    	var d = new Date();
    	var offset = (-1) * d.getTimezoneOffset() * 60000;
  
        if (!$scope.formData.item) {
            $scope.formData.item = {};
        }
        if(!$scope.formData.city) {
            $scope.formData.city = {};
        }
        
        var from = $scope.formData.from ? (Date.parse($scope.formData.from) + offset) : '';
        var to = $scope.formData.to ? (Date.parse($scope.formData.to) + offset) : '';

        var searchItemsPromise = ItemsService.searchItems($scope.formData.item.title, $scope.formData.city.title, from, to);
            searchItemsPromise.then(function(response) {
                SearchResultService.setSearchResults(response.data); 
                $location.path("/SearchResults");
            });
    };
});
