angular.module('SHeroApp')
    .factory('UsersService', function($q, $timeout, $http) {
        
        var postUser = function(userData) {
            return $http({
                method: "POST",
                url: "/rest/users",
                data: userData
            }).then(function(response){
                return response;
            })
        };
    
        var getUser = function() {
            return $http({
                method: "GET",
                url: "/rest/users/mail/max%40mustermann.de",
            }).then(function(response){
                return response;
            })
        };
    
        return {
            postUser : postUser,
            getUser: getUser
        };
    })