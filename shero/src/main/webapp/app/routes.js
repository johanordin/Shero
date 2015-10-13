angular.module('SHeroApp')
  
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/views/LandingPage.html',
        controller: 'MainCtrl'
      })
      .when('/SearchResults', {
        templateUrl: 'app/views/SearchResults.html'
      })
      .when('/EditUser', {
        templateUrl: 'app/views/EditUser.html',
        controller: 'MainCtrl'
      })
      .when('/AddItem', {
        templateUrl: 'app/views/AddItem.html'
      })
      .when('/EditItem', {
        templateUrl: 'app/views/EditItem.html'
      })
      .otherwise({
        redirectTo: '/'
      }); 
  });