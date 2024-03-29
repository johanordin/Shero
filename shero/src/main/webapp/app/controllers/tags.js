
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

		//loads tags from server
	  	$scope.allTags;
    
        var getTagsPromise = tagService.getTags();
        getTagsPromise.then(function(response) {
            $scope.allTags = response.data;
            
            $scope.loadTags = function(query) {
                return $scope.allTags;
            };
        });
	});