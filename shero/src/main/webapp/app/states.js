/* DEFINE THE STATES */

angular.module('SHeroApp')
 	.config(function($stateProvider, $urlRouterProvider) {
 		//redirects /AddItem directly to /AddItem/GeneralInformation
 		$urlRouterProvider.when('/AddItem', '/AddItem/GeneralInformation');
    $urlRouterProvider.when('/EditUser', '/EditUser/General');

 		//redirects empty states to Home-View
 		$urlRouterProvider.when('', '/Home');
 		$urlRouterProvider.when('#/', '/Home');

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
 			//.state('app', {
 			//	abstract: true,
 				/*data: {
        			requireLogin: true
      			}*/
 			//})
        
          	//Edit User
      		.state('editUser', {
      			url: '/EditUser', 
        		templateUrl: 'app/views/EditUser/EditUser.html',
            controller: 'EditUserCtrl'
      		})
          .state('editUser.general', {
            url : '/General',
            templateUrl: 'app/views/EditUser/partials/General.html'
          })
          .state('editUser.address', {
            url: '/Address',
            templateUrl: 'app/views/EditUser/partials/Address.html'
          })
          .state('editUser.password', {
            url: '/Password',
            templateUrl: 'app/views/EditUser/partials/Password.html'
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
 	})


.config(['flowFactoryProvider', function (flowFactoryProvider) {
	  flowFactoryProvider.defaults = {
	    target: '/UploadServlet',
	    testChunks: false,
	    permanentErrors: [404, 500, 501],
	    maxChunkRetries: 1,
	    chunkRetryInterval: 5000,
	    simultaneousUploads: 4,
	    singleFile: true
	  };
	  flowFactoryProvider.on('catchAll', function (event) {
	    //console.log('catchAll', arguments);
	  });
	  // Can be used with different implementations of Flow.js
	  // flowFactoryProvider.factory = fustyFlowFactory;
	}]);
