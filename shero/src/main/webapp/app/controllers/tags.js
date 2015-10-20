
/**
 * @ngdoc function
 * @name SHeroApp.controller:TagsCtrl
 * @description
 * # TagsCtrl
 * Controller of the SHeroApp to load the already added tags from the server and add them to
 * the tags-input field of the AddItem-form to allow auto-complete.
 */

angular.module('SHeroApp')
	.controller('TagsCtrl', function($scope, $http, $resource) {
	   
	  	  $scope.tags = [
		  ];

		  var test = $http({
				method: 'GET',
				url: '/rest/tags',
			}).then(function successCallback(response) {
			    // this callback will be called asynchronously
			    // when the response is available
			    console.log("success: " + JSON.stringify(response));
			    console.log("success: " + response);
			    test = response.data;
			}, function errorCallback(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			    console.log("error: " + response);
			});
		   
		  $scope.loadTags = function(query) {
		    return test;
		  };
	});