angular.module('SHeroApp')
    .factory('ItemsService', function($q, $timeout, $http, $cookies) {
        
        var getAllItems = function() {
            return $http({
                method: "GET",
                url: "/rest/items/"
            }).then(function(response){
                return response;      
            })
        };
        
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
    
        return {postItem : postItem,
        		getAllItems : getAllItems};
    })