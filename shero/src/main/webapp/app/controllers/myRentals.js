angular.module('SHeroApp')
    .controller('MyRentalsCtrl', function ($scope, UsersService, SessionStorageService) {
        
        $scope.userId = SessionStorageService.getUserId();
        $scope.rentedItems = {}
        
        var rentedItemsPromise = UsersService.getRentedItems(userId);
        rentedItemsPromise.then(function(response) {
            $scope.rentedItems = response.data;
        });

    });
