angular.module('SHeroApp')

  .directive('navLogin', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/NavbarItemsLoggedIn.html'
    }
  });