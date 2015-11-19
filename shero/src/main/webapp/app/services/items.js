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
    
        var deleteItem = function (itemId) {
            return $http({
                method: "DELETE",
                url: 'rest/items/'+itemId
            }).then(function(response) {
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
             
            if (from && to) {
                params.from = from;
                params.to = to;
            }
            
            return $http({
                method: 'GET',
                url: '/rest/items',
                params : params
            }).then(function(response){
                return response; 
            });
        };
    
        var getSuggestions = function () {
            return $http({
                method: "GET",
                url: "/rest/items/itemsuggestions"
            }).then(function(response){
                return response;      
            })
        };
    
        var getItemSuggestions = function () {
            var itemNames = [];
            var distinctItemsPromise = getSuggestions();
            distinctItemsPromise.then(function(response) {
                response.data.forEach (function(item) {
                    var jsonItem = {"name": item.name};
                    itemNames.push(jsonItem);
                });
            });
            return itemNames;
        };
    
        var getNeededItemInfo = function (item) {
            item.availabilityDates = [];
            item.taglist = [];
            item.availabilityPeriods.forEach(function(availability) {
                // Convert to unixtime
                var unixtime = Date.parse(availability);
                item.availabilityDates.push(unixtime);
             });
            item.tags.forEach(function(tag) {
               item.taglist.push(tag.text); 
            });
            item.meanRating = item.sumRatings / item.numRatings;
            item.imgUrl = "/rest/items/image/" + item.id;
            return item;
        };
    
        return {
            postItem : postItem,
            deleteItem: deleteItem,
            searchItems: searchItems,
        	getAllItems: getAllItems,
            getItemSuggestions: getItemSuggestions,
            getNeededItemInfo: getNeededItemInfo
        };
    })