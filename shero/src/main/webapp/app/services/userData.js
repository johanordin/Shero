angular.module('SHeroApp')
    .factory('userDataService', function($cookies, $sessionStorage, $rootScope) {
    
        $rootScope.$storage = $sessionStorage;
    
        //function to set the cookie with userId and the other userData (expected in JSON) in the localStorage
        var store = function (userData) { 
            var expiry = new Date();
            var date = new Date();
            expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
            $cookies.put('SHeroUserId', userData.id, {'expires': expiry.toGMTString()});
            
            $rootScope.$storage.user = userData;
            $rootScope.loggedIn = true;
        };
        
        var deleteUserData = function () {
            var expiry = new Date();
            var date = new Date();
            expiry.setTime(date.getTime()-(30*60*1000)); //Cookie expires before 30 Minutes
            $cookies.put('SHeroUserId', '', {'expires': expiry.toGMTString()})
            
            $rootScope.$storage = $sessionStorage.$reset();            
            $rootScope.loggedIn = false;
        }
        
        var updateUserDate = function (userData) {
            $rootScope.$storage.user = userData;
        }
        
        return {
            store: store,
            delete: deleteUserData,
            updateUserData: updateUserDate
        };
    })