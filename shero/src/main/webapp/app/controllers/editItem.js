angular.module('SHeroApp')
    .controller('EditItemCtrl', function ($scope, $stateParams, $location, ItemsService, UsersService, SessionStorageService) {
        $scope.itemId = $stateParams.itemId;
    
        var rawItem = SessionStorageService.getUserItemSpecific($scope.itemId);
        $scope.item = ItemsService.getNeededItemInfo(rawItem[0]);
    
        $scope.deleteItem = function () {
            var deleteItemPromise = ItemsService.deleteItem($scope.itemId)
            deleteItemPromise.then(function(response) {
                var getUserPromise = UsersService.getUserById(SessionStorageService.getUserId());
                getUserPromise.then(function(response) {
                    SessionStorageService.updateUserData(response.data);
                    console.log("Item "+$scope.itemId+" deleted");
                    $location.path("/Home");
                });
            });
        }
        
        $scope.disabled = function(date, mode) {
         return true;
     }; 
    });
