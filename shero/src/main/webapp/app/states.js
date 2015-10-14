angular.module('SHeroApp', ['ui.router'])
 	.config(function($stateProvider, $urlRouterProvider) {
 		$urlRouterProvider.otherwise('/Home');

 		$stateProvider
 			.state('home', {
 				url: '/Home',
 				templateUrl:'app/views/LandingPage.html'
 			})
 			.state('searchResult', {
 				url: '/SearchResults',
        		templateUrl: 'app/views/SearchResults.html'
      		})
 			.state('app', {
 				abstract: true,
 				/*data: {
        			requireLogin: true
      			}*/
 			})
      		.state('app.editUser', {
      			url: '/EditUser', 
        		templateUrl: 'app/views/EditUser.html',
      		})
      		.state('addItem', {
      			url: '/AddItem',
        		templateUrl: 'app/views/AddItem.html'
      		})
	      .state('editItem', {
	      	url: '/EditItem', 
	        templateUrl: 'app/views/EditItem.html'
	      })
 	});
