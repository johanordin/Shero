angular.module('SHeroApp')
    .controller('EditItemCtrl', function ($scope, $stateParams, UsersService, SessionStorageService) {
        $scope.itemId = $stateParams.itemId;
    
        $scope.item = SessionStorageService.getUserItemSpecific($scope.itemId);        
    });
