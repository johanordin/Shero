 /**
 * @ngdoc function
 * @name SHeroApp.controller:SearchResultsCtrl
 * @description
 * # SearchResultsCtrl
 * Controller of the SHeroApp
 */

angular.module('SHeroApp')
	.controller('SearchResultsCtrl', function($scope, $uibModal, ItemsService, SearchResultService, SessionStorageService) { 
  
	// Variable for items
	$scope.itemlist = {};
    $scope.itemsToShow = {items: []};
    $scope.userId = SessionStorageService.getUserId();
    	
    $scope.loggedIn = SessionStorageService.getUserId() ? true : false;
    $scope.$watch(function () { return SessionStorageService.getUserId(); }, function (newVal) {
        if (newVal) {
        	$scope.loggedIn = true;
        } else {
        	$scope.loggedIn = false;
        }
    });
    
	//fetch all items from backend
	$scope.fetchItems = function() {
        $scope.itemsToShow = {items: []};
        $scope.itemlist = SearchResultService.getSearchResults();
        $scope.itemlist.forEach(function(item) {
            var item = ItemsService.getNeededItemInfo(item);
            if (item.show) {
                $scope.itemsToShow.items.push(item);
            }
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
     
     // Send email Modal
     $scope.animationsEnabled = true;

     $scope.openContactModal = function(itemID) {
       var modalQuestions = $uibModal.open({
         animation: $scope.animationsEnabled,
         templateUrl: 'app/views/ModalQuestions.html',
         controller: 'ModalQuestionsCtrl',
         resolve: {
        	 itemID: function () {
               return itemID;
             }
           }
       });
       modalQuestions.result.then(function () {
       });
     };

     $scope.toggleAnimation = function () {
       $scope.animationsEnabled = !$scope.animationsEnabled;
     };
     
     $scope.openRentItemModal = function(item) {
       var modalQuestions = $uibModal.open({
         animation: $scope.animationsEnabled,
         templateUrl: 'app/views/ModalRentItem.html',
         controller: 'ModalRentItemCtrl',
         size : 'lg', 
         resolve: {
             item: function () {
               return item;
             }
           }
       });
       modalQuestions.result.then(function () {
       });
     };
});