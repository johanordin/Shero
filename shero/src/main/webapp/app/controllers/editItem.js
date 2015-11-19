angular.module('SHeroApp')
    .controller('EditItemCtrl', function ($scope, $stateParams, $location ItemsService, SessionStorageService) {
        $scope.itemId = $stateParams.itemId;
    
        var rawItem = SessionStorageService.getUserItemSpecific($scope.itemId);
        $scope.item = ItemsService.getNeededItemInfo(rawItem[0]);
    
        var deleteItem = function () {
            var deleteItemPromise = ItemsService.deleteItem($scope.itemId)
            deleteItemPromise.then(function(response) {
                //SessionStorageService.updateUserItemAll(response.data);
                console.log("Item "+$scope.itemId+" deleted");
                $location.path("/Home");
            });
        }
    });
