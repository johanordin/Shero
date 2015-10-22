angular.module('SHeroApp')
    .factory('userDataService', function($cookies, $localStorage, $rootScope) {
    
        //function to set the cookie with userId and the other userData (expected in JSON) in the localStorage
        var store = function (userData) {
            var expiry = new Date();
            var date = new Date();
            expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
            $cookies.put('SHeroUserId', userData.id, {'expires': expiry.toGMTString()});
            
            $rootScope.$storage = $localStorage.$default({
                firstname: userData.firstname,
                lastname: userData.lastname,
                addresses: userData.addresses,
                items: userData.items,
                userid: userData.id
            });
            $rootScope.loggedIn = true;
        }
        
        return {store: store};
    })