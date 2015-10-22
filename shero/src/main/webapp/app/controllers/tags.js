
/**
 * @ngdoc function
 * @name SHeroApp.controller:TagsCtrl
 * @description
 * # TagsCtrl
 * Controller of the SHeroApp to load the already added tags from the server and add them to
 * the tags-input field of the AddItem-form to allow auto-complete.
 */

angular.module('SHeroApp')
	.controller('TagsCtrl', function($scope, $http, $resource, tagService) {
	   	  
   	  	//holds selected tags
  	  	$scope.tags = [];

		//loads tags from server
	  	$scope.allTags;
    
        var getTagsPromise = tagService.getTags();
        getTagsPromise.then(function(response) {
            $scope.allTags = response.data;
            
            $scope.loadTags = function(query) {
                console.log("outside: " + $scope.allTags);
                return $scope.allTags;
            };
        });
	});