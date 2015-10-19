/* DEFINE THE STATES */

angular.module('SHeroApp')
 	.config(function($stateProvider, $urlRouterProvider) {
 		//redirects /AddItem directly to /AddItem/GeneralInformation
 		$urlRouterProvider.when('/AddItem', '/AddItem/GeneralInformation');
 		//redirects empty states to Home-View
 		$urlRouterProvider.when('', '  /Home');

 		$stateProvider
 			//Landing Page
 			.state('home', {
 				url: '/Home',
 				templateUrl:'app/views/LandingPage.html'
 			})
 			
 			//Search Results
 			.state('searchResult', {
 				url: '/SearchResults',
        		templateUrl: 'app/views/SearchResults.html'
      		})
        
            //Abstract state
      		//Acts as parent for all states which need authentication
 			.state('app', {
 				abstract: true,
 				/*data: {
        			requireLogin: true
      			}*/
 			})
        
          	//Edit User
      		.state('app.editUser', {
      			url: '/EditUser', 
        		templateUrl: 'app/views/EditUser.html',
      		})
        
          //Add Item
      		.state('addItem', {
      			url: '/AddItem',
        		templateUrl: 'app/views/AddItem/AddItem.html',
            controller: 'AddItemCtrl',
            controllerAs: 'addItemCtrl'
      		})
      			//Child-State of AddItem to show first page of the user-form
	            .state('addItem.generalInformation', {
	                url: '/GeneralInformation',
	                templateUrl: 'app/views/AddItem/partials/GeneralInformation.html'
	            })
	            //Child-State of AddItem to show second page of the user-form
	            .state('addItem.availability', {
	                url: '/Availability',
	                templateUrl: 'app/views/AddItem/partials/Availability.html'
	            })
	            //Child-State of AddItem to show third page of the user-form
	            .state('addItem.pictures', {
	                url: '/Pictures',
	                templateUrl: 'app/views/AddItem/partials/Pictures.html'
	            })

          //Edit item
	      .state('editItem', {
	      	url: '/EditItem', 
	        templateUrl: 'app/views/EditItem.html'
	      })
 	});
