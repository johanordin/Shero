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

        var searchItems = function (name, city, from, to) {
            params = {};

            if (name) {
                params.name = name;
            }

            if (city) {
                params.city = city;
            }
            
            // TODO: Request not working with from/to parameters
            // valid request but always returns an empty list. 
//            if (from && to) {
//                params.from = from;
//                params.to = to;
//            }

            return $http({
                method: 'GET',
                url: '/rest/items',
                params : params
            }).then(function(response){
                return response; 
            });
        };
    
        return {postItem : postItem,
               searchItems: searchItems,
        		getAllItems : getAllItems};
    })