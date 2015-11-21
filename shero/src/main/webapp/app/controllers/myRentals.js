angular.module('SHeroApp')
    .controller('MyRentalsCtrl', function ($scope, UsersService, ItemsService, SessionStorageService) {
        
        $scope.userId = SessionStorageService.getUserId();
        $scope.rentedItems = {};
        
        $scope.getRentedItems = function () {
            var rentedItemsPromise = UsersService.getRentedItems($scope.userId);
            rentedItemsPromise.then(function(response) {
                console.log(JSON.stringify(response.data));
                $scope.rentedItems = response.data;
            });
        };
    
        $scope.rateItem = function (rentalId, rating) {
            var rateData = {};
            rateData.rentalId = rentalId;
            rateData.rating = rating;
            var rateItemPromise = ItemsService.rateItem(rateData);
            rateItemPromise.then(function(response) {
                console.log(JSON.stringify(response.data));
                $scope.getRentedItems();
            });    
        };
    
        $scope.getRentedItems();

    });
