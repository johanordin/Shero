
/**
 * @ngdoc function
 * @name SHeroApp.controller:TagsCtrl
 * @description
 * # TagsCtrl
 * Controller of the SHeroApp to load the already added tags from the server and add them to
 * the tags-input field of the AddItem-form to allow auto-complete.
 */

angular.module('SHeroApp')
	.controller('TagsCtrl', function($scope, $resource) {
	    var tags = $resource('tags.json');
	   
	  	$scope.loadTags = function(query) {
	    	return tags.query().$promise;
	  	};
	});