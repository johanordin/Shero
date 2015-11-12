 /**
 * @ngdoc function
 * @name SHeroApp.controller:SearchResultsCtrl
 * @description
 * # SearchResultsCtrl
 * Controller of the SHeroApp
 */

angular.module('SHeroApp')
	.controller('SearchResultsCtrl', function($scope, ItemsService, SearchResultService) { 
  
	// Variable for items
	$scope.itemlist = {};
		
	//fetch all items from backend
	$scope.fetchItems = function() {
        $scope.itemlist = SearchResultService.getSearchResults();
        $scope.itemlist.forEach(function(item) {
            item.availabilityDates = [];
            item.taglist = [];
            item.availabilityPeriods.forEach(function(availability) {
                // Convert to unixtime
                var unixtime = Date.parse(availability.availabilityDate);
                item.availabilityDates.push(unixtime);
             });
            item.tags.forEach(function(tag) {
               item.taglist.push(tag.text); 
            });
            item.meanRating = item.sumRatings / item.numRatings;
         });
     }; 
     
     $scope.$watch(function () { return SearchResultService.getSearchResults(); }, function (newVal) {
        $scope.fetchItems(); 
    });
    
    angular.element(document).ready(function () {
        $scope.fetchItems();
    });
     
     $scope.sort = function(keyname){
    	 $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa     
     }

});