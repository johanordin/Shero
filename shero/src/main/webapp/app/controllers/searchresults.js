 /**
 * @ngdoc function
 * @name SHeroApp.controller:SearchResultsCtrl
 * @description
 * # SearchResultsCtrl
 * Controller of the SHeroApp
 */

angular.module('SHeroApp')
	.controller('SearchResultsCtrl', function($scope, $rootScope, $location, ItemsService, sharedItemList) { 
  
	// Variable for items
	$scope.itemlist;
	//$scope.itemlist = $rootScope.itemList;             //NEW by Max
	
	//$scope.itemlist = newItemService.getItems();
	//console.log("items: " + $scope.itemlist);
		
	//fetch all items from backend
	$scope.fetchItems = function() {
		var getAllItems = ItemsService.getAllItems();
		getAllItems.then(function(response) {       
		$scope.itemlist = response.data;           
            
            $scope.itemlist.forEach(function(item) {
                item.availabilityDates = [];
                item.availabilityPeriods.forEach(function(availability) {
                	
                	// Convert to unixtime
                	var unixtime = Date.parse(availability.availabilityDate);
                    item.availabilityDates.push(unixtime);
                 });
             });
         });
     }; 
     

     $scope.updateItems = function() {
    	 $scope.itemlist = $rootScope.itemList;
    	 
     };
     
     
     
     $scope.fetchItems();
     
     $scope.sort = function(keyname){
    	 $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa
         $scope.updateItems();
         
     }

});