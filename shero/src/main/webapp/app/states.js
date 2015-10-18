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
        
            /* APP STATE  */
 			.state('app', {
 				abstract: true,
 				/*data: {
        			requireLogin: true
      			}*/
 			})
        
          /* EDIT USER STATE  */
      		.state('app.editUser', {
      			url: '/EditUser', 
        		templateUrl: 'app/views/EditUser.html',
      		})
        
          /* ADD ITEM STATE  */
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

          /* EDIT ITEM STATE  */
	      .state('editItem', {
	      	url: '/EditItem', 
	        templateUrl: 'app/views/EditItem.html'
	      })
 	});
