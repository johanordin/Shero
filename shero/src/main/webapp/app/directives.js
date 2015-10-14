angular.module('SHeroApp')

  .directive('userMenu', function() {
    return {
      restrict: 'E',
      templateUrl: 'app/directives/Navbar/UserMenu.html',
      controller: 'UserMenuCtrl',
      controllerAs: 'usermenuCtrl'
    }
  })
  .directive('searchBar', function() {
  	return {
  		restrict: 'E',
  		templateUrl: 'app/directives/Navbar/SearchBar.html',
  		controller: 'SearchBarCtrl',
  		controllerAs: 'searchbarCtrl'
  	}
  });