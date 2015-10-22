angular.module('SHeroApp')
    .factory('tagService', function($q, $timeout, $http) {
        
        var getTags = function() {
            return $http({
                method: "GET",
                url: "/rest/tags"
            }).then(function(response){
                return response;
            })
        };
        return {getTags : getTags};
    })