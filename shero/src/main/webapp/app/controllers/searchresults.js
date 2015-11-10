 /**
 * @ngdoc function
 * @name SHeroApp.controller:SearchResultsCtrl
 * @description
 * # SearchResultsCtrl
 * Controller of the SHeroApp
 */
 angular.module('SHeroApp')
 .controller('SearchResultsCtrl', function($scope, $location, ItemsService) { 

	// Variable for items
     $scope.itemlist = {};
     $scope.itemlist = [
         {id: '1', name:'surfboard',rating:2, price:20, location:'Placa Catalunya 20'},
         {id: '2', name:'thing',rating:5, price:50, location:'Placa Catalunya 20'},
         {id: '3', name:'car',rating:5, price:100, location:'Placa Catalunya 20'},
         {id: '4', name:'skateboard',rating:1, price:10, location:'Placa Catalunya 20'},
     ];
     $scope.ratingAndLocation = {rating: 5, location:'Placa Catalunya 5'};
	
     $scope.fetchItems = function() {
         var getAllItems = ItemsService.getAllItems();
         getAllItems.then(function(response) {
            //$scope.itemlist = response.data;           
             $scope.itemlist.forEach(function(item) {
                 item.availabilityDates = [];
                 item.availabilityPeriods.forEach(function(availability) {
                     availabilityDates.push(availability.availabilityDate);
                 });
             });
         });
     }; 


     $scope.fetchItems();
     
     $scope.sort = function(keyname){
         $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa
     }

});