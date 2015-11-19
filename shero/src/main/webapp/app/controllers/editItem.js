angular.module('SHeroApp')
    .controller('EditItemCtrl', function ($scope, $stateParams, ItemsService, SessionStorageService) {
        $scope.itemId = $stateParams.itemId;
    
        var rawItem = SessionStorageService.getUserItemSpecific($scope.itemId);
        $scope.item = ItemsService.getNeededItemInfo(rawItem[0]);
    });
