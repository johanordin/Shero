angular.module('SHeroApp')
 	.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.when('/AddItem', '/AddItem/GeneralInformation');
 		$urlRouterProvider.when('', '  /Home');

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
        		templateUrl: 'app/views/AddItem/addItem.html',
            controller: 'AddItemCtrl',
            controllerAs: 'addItemCtrl'
      		})
            .state('addItem.generalInformation', {
                url: '/GeneralInformation',
                templateUrl: 'app/views/AddItem/partials/generalInformation.html'
            })
            .state('addItem.availability', {
                url: '/Availability',
                templateUrl: 'app/views/AddItem/partials/availability.html'
            })
            .state('addItem.pictures', {
                url: '/Pictures',
                templateUrl: 'app/views/AddItem/partials/pictures.html'
            })

	      .state('editItem', {
	      	url: '/EditItem', 
	        templateUrl: 'app/views/EditItem.html'
	      })
 	});
