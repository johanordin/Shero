  /* CUSTOM DIRECTIVES  */

angular.module('SHeroApp')

  .directive('userMenu', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/Navbar/UserMenu.html',
      controller: 'UserMenuCtrl',
      controllerAs: 'usermenuCtrl'
    }
  })

  .directive('loginButton', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/Navbar/LoginButton.html',
      controller: 'LoginCtrl',
      controllerAs: 'LoginCtrl'
    }
  })

  .directive('searchBar', function() {
  	return {
  		restrict: 'E',
  		templateUrl: 'app/directives/Navbar/SearchBar.html',
  		controller: 'SearchBarCtrl',
  		controllerAs: 'searchbarCtrl'
  	}
  })
  .directive( 'goTo', function ( $location ) {
      return function ( scope, element, attrs ) {
        var path;
        
        attrs.$observe( 'goTo', function (val) {
          path = val;
        });
        
        element.bind( 'click', function () {
          scope.$apply( function () {
            $location.path( path );
          });
        });
      };
    });