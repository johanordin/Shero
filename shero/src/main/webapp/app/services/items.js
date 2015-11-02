angular.module('SHeroApp')
    .factory('ItemsService', function($q, $timeout, $http, $cookies) {
        
        var postItem = function(itemData) {
            var userId = $cookies.get('SHeroUserId');
            return $http({
	    		method: 'POST',
	    		url: '/rest/users/'+ userId +'/items',
	    		data: itemData
	    	}).then(function(response){
                return response;
            });
        };
    
        var searchItems = function (name, city, from, to) {
            return $http({
                method: 'GET',
                url: 'items',
                params: {name: name, city: city, from: from, to: to}
            }).then(function(response){
                return response; 
            });
        };
    
        return {postItem : postItem,
               searchItems: searchItems};
    })