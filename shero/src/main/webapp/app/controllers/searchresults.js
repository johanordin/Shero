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
	
	//fetch all items from backend
	$scope.fetchItems = function() {
		var getAllItems = ItemsService.getAllItems();
		getAllItems.then(function(response) {
			$scope.itemlist = response.data;           
            
            $scope.itemlist.forEach(function(item) {
                item.availabilityDates = [];
                item.availabilityPeriods.forEach(function(availability) {
                    availabilityDates.push(availability.availabilityDate);
                });
            });
			console.log(JSON.stringify($scope.itemlist));
        });
    }; 
	
    // Call fetch items function when controller is loaded
    $scope.fetchItems();
	
	$scope.show = false; 
	
	$scope.expand = function(){
		console.log("show")
		$scope.show = true;
		$scope.fetchItems();
	};
  

  
  
  
  
});
