angular.module('SHeroApp').controller('ModalRateItemCtrl', function ($scope, $modalInstance, ItemsService, item) {
    
    $scope.item = item;
    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };

    //fuction called when user confirms the rating
    $scope.confirmClicked = function () {
    	$scope.rateItem($scope.item.id, $scope.userRating);
        $modalInstance.close();
    };

});