angular.module('SHeroApp')
    .controller('NavBarCtrl', function ($scope, $state) {
         $scope.$watch(function () { return $state.current.name; }, function (newVal) {
            if (newVal === "home") {
                $scope.home = true;
            } else {
                $scope.home = false;
            }
        });
    });
