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
			console.log(response.data);
			console.log(JSON.stringify(response.data));
			
			$scope.itemlist = response.data;
			
			console.log("scope itemlist: " + $scope.itemlist);
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
