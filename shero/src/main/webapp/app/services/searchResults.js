angular.module('SHeroApp')
    .factory('SearchResultService', function() {
        
        var searchResults = {};
    
        var setSearchResults = function (results) {
            searchResults = results;
        };
        
        var getSearchResults = function () {
            return searchResults;  
        };
    
        return {setSearchResults: setSearchResults,
                getSearchResults: getSearchResults};
    })