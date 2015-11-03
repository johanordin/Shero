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
    
        return {getAllCities: getAllCities};
    })