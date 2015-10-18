angular.module('SHeroApp')
	.controller('TagsCtrl', function($scope, $resource) {
	    var tags = $resource('tags.json');
	   
	  	$scope.loadTags = function(query) {
	    	return tags.query().$promise;
	  	};
	});