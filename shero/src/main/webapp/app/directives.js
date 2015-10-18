  /* CUSTOM DIRECTIVES  */

angular.module('SHeroApp')
	
	//directive for the userMenu (right side of the navbar)
  .directive('userMenu', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/Navbar/UserMenu.html',
      controller: 'UserMenuCtrl',
      controllerAs: 'usermenuCtrl'
    }
  })

  	//directive for the loginButton in the navbar
  .directive('loginButton', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/Navbar/LoginButton.html',
      controller: 'LoginCtrl',
      controllerAs: 'LoginCtrl'
    }
  })

  //directive for the search-form in the navbar
  .directive('searchBar', function() {
  	return {
  		restrict: 'E',
  		templateUrl: 'app/directives/Navbar/SearchBar.html',
  		controller: 'SearchBarCtrl',
  		controllerAs: 'searchbarCtrl'
  	}
  })
  
  /*special directive which provides a function for a button to link to a state
  * ui-sref for a button
  * call: <button go-to='Link/to/State'>Test</button>
  */ 
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