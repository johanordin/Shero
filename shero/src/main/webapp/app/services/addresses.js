angular.module('SHeroApp')
    .factory('AddressesService', function($q, $timeout, $http) {
        
        var getAllCities = function() {
            return $http({
	    		method: 'GET',
	    		url: '/rest/addresses/cities',
	    	}).then(function(response){
                return response;
            });
        };
    
        var getCitySuggestions = function () {
            var cityNames = [];
            var distinctCitiesPromise = getAllCities();
            distinctCitiesPromise.then(function(response) {
                response.data.forEach (function(item) {
                    var jsonItem = {"name": item.city};
                    cityNames.push(jsonItem);
                });
            });
            return cityNames;
        };
    
        return {getCitySuggestions: getCitySuggestions};
    })