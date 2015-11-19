angular.module('SHeroApp')
    .factory('SearchResultService', function($localStorage) {
    
        var setSearchResults = function (results) {
            $localStorage.SHeroSearchItems = results;
        };
        
        var getSearchResults = function () {
            return $localStorage.SHeroSearchItems;  
        };
    
        return {setSearchResults: setSearchResults,
                getSearchResults: getSearchResults};
    })