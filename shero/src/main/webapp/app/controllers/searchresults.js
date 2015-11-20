 /**
 * @ngdoc function
 * @name SHeroApp.controller:SearchResultsCtrl
 * @description
 * # SearchResultsCtrl
 * Controller of the SHeroApp
 */

angular.module('SHeroApp')
	.controller('SearchResultsCtrl', function($scope, $uibModal, ItemsService, SearchResultService) { 
  
	// Variable for items
	$scope.itemlist = {};
		
	//fetch all items from backend
	$scope.fetchItems = function() {
        $scope.itemlist = SearchResultService.getSearchResults();
        $scope.itemlist.forEach(function(item) {
            ItemsService.getNeededItemInfo(item);
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
     
     $scope.disabled = function(date, mode) {
         //return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
         return true;
     };
    
    //rent item --> itemId + period [1447977600000,1447977600000]
    $scope.rent = function(itemId, period) {
        var rentalData = {};
        rentalData.itemId = itemId;
        rentalData.period = period;
        var rentItemPromise = ItemsService.rentItem(rentalData);
        rentItemPromise.then(function(response) {
            console.log(JSON.stringify(response.data));
        });
    }
     
     
     // Send email Modal
     $scope.animationsEnabled = true;

     $scope.openContactModal = function(ownerId) {
    	 $scope.ownerId = ownerId;
       var modalQuestions = $uibModal.open({
         animation: $scope.animationsEnabled,
         templateUrl: 'app/views/ModalQuestions.html',
         controller: 'ModalQuestionsCtrl',
         scope : $scope
       });
       modalQuestions.result.then(function () {
       });
     };

     $scope.toggleAnimation = function () {
       $scope.animationsEnabled = !$scope.animationsEnabled;
     };
     

});