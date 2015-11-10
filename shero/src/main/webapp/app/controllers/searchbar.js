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
        console.log($scope.formData.name);
        console.log($scope.formData.city);
        console.log($scope.formData.from);
        console.log($scope.formData.to);
        
        var from = $scope.formData.from ? (Date.parse($scope.formData.from)/1000) : '';
        var to = $scope.formData.to ? (Date.parse($scope.formData.to)/1000) : '';
        
        console.log(from);
        console.log(to);

        var searchItemsPromise = ItemsService.searchItems($scope.formData.name, $scope.formData.city, from, to);
            searchItemsPromise.then(function(response) {
                console.log(JSON.stringify(response));
                alert(JSON.stringify(response));
            });
    };
});
