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
	
	//fetch all items from backend
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
      $('#expanding').html('<i class="fa fa-arrow-circle-up fa-5x" style="color:white" ng-click="expand">');
    };


  });
//to show the rating as stars
angular.module('SHeroApp').filter('range', function() {
  return function(input, total) {
    total = parseInt(total);

    for (var i=0; i<total; i++) {
      input.push(i);
    }

    return input;
  };

});