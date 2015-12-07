angular.module('SHeroApp').controller('ModalRateItemCtrl', function ($scope, $modalInstance, ItemsService, item) {
    
    $scope.item = item;
    $scope.userRating = 1;
    
    $scope.rateItem = function (rentalId, rating) {
            var rateData = {};
            rateData.rentalId = rentalId;
            rateData.rating = rating;
            var rateItemPromise = ItemsService.rateItem(rateData);
            rateItemPromise.then(function(response) {
                console.log(JSON.stringify(response.data));
                $modalInstance.close();
            });    
        };
    
    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };

    //fuction called when user confirms the rating
    $scope.confirmClicked = function () {
        $scope.rateItem (item.rentalId, $scope.userRating);
    };
    

});