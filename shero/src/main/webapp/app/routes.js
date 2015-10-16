/*angular.module('SHeroApp')
  
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/views/LandingPage.html',
        controller: 'MainCtrl'
      })
      .when('/SearchResults', {
        templateUrl: ''
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
*/
angular.module('SHeroApp')
  .config(['$stateProvider', function($stateProvider) {
    $stateProvider
      .state('/', {
        url: '/Landingpage',
        templateUrl: 'app/views/LandingPage.html',
        controller: 'MainCtrl',
        //authenticate: true
      })
     .state('SearchResults', {
        url:'/SearchResults',
        templateUrl: 'app/views/SearchResults.html',
        controller: 'MainCtrl',
        //authenticate: true
      })
    .state('EditUser', {
        url: '/Edituser',
        templateUrl: 'app/views/EditUser.html',
        controller: 'MainCtrl',
        //authenticate: true
      })
    .state('EditItem', {
        url: '/Edituser',
        templateUrl: 'app/views/EditUser.html',
        controller: 'MainCtrl',
        //authenticate: true
      })
 }]);