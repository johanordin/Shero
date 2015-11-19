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
    
        var getUserByMail = function(mail, password) {
            return $http({
                method: 'GET',
                url: '/rest/users/mail/' + mail,
                // GET request can't contain data, only parameters
                params: {hashedPassword: password} 
            }).then(function(response){
                return response;
            })
        };
    
        var getUserById = function(userId) {
            return $http({
                method: "GET",
                url: "/rest/users/id/"+userId
            }).then(function(response){
                return response;      
            })
        };
    
        var getRentedItems = function (userId) {
            return $http({
                method: "GET",
                //@TODO change url!
                url: "/rest/users/"+userId+"/rentals"
            }).then (function(response){
                return response;
            })
        };
    
        return {
            postUser : postUser,
            getUserByMail: getUserByMail,
            getUserById: getUserById,
            getRentedItems: getRentedItems
        };
    })