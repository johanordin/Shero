'use strict';

angular.module('SHeroApp').controller('ItemCtrl', function($scope, $http) {
	$scope.awesomeThings = [ 'HTML5 Boilerplate', 'AngularJS', 'Karma' ];

	$http.get('rest/items').success(function(data) {
		$scope.item = data;
	});
});