angular.module('SHeroApp')
    .controller('MyRentalsCtrl', function ($scope, UsersService, ItemsService, SessionStorageService) {
        
        $scope.userId = SessionStorageService.getUserId();
        $scope.rentedItems = {};
        
        $scope.getRentedItems = function () {
            var rentedItemsPromise = UsersService.getRentedItems($scope.userId);
            rentedItemsPromise.then(function(response) {
                console.log(JSON.stringify(response.data));
                $scope.rentedItems = response.data;
                
                $scope.rentedItems.forEach(function(item) {
                    ItemsService.getNeededItemInfo(item);
                });
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
    
        $scope.openModal = function(item){
            var modalLogin = $uibModal.open({
              animation: $scope.animationsEnabled,
              templateUrl: 'app/views/ModalRateItem.html',
             controller: 'ModalRateItemCtrl',
             size : 'lg',
             resolve: {
                 item: function () {
                   return item;
                 }
               }        
            };
         
    });
