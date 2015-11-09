angular.module('SHeroApp')
    .factory('SessionStorageService', function($cookies, $sessionStorage, $rootScope) {
    
        $rootScope.$storage = $sessionStorage;
    
        //function to split the user JSON into fields and store them in SessionStorage
        var store = function (userData) {
            $rootScope.$storage.SHeroId = userData.id;
            $rootScope.$storage.SHeroFirstname = userData.firstname;
            $rootScope.$storage.SHeroLastname = userData.lastname;
            $rootScope.$storage.SHeroEmailAddress = userData.emailAddress;
            $rootScope.$storage.SHeroPasswordHash = userData.passwordHash;
            $rootScope.$storage.SHeroItems = userData.items;
            $rootScope.$storage.SHeroAddresses = userData.addresses;
            $rootScope.$storage.SHeroReceivedRatings = userData.receivedRatings;
        };
    
        //function to set the cookie with userId and the other userData (expected in JSON) in the sessionStorage
        var storeUserData = function (userData) { 
            var expiry = new Date();
            var date = new Date();
            expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
            $cookies.put('SHeroUserId', userData.id, {'expires': expiry.toGMTString()});
            
            store(userData);
            
            $rootScope.loggedIn = true;
        };
        
        //function to delete all userdata from sessionStorage and to delete cookie
        var deleteUserData = function () {
            var expiry = new Date();
            var date = new Date(); 
            expiry.setTime(date.getTime()-(30*60*1000)); //Cookie expires before 30 Minutes
            $cookies.put('SHeroUserId', '', {'expires': expiry.toGMTString()})
            
            $rootScope.$storage = $sessionStorage.$reset();            
            $rootScope.loggedIn = false;
        };
    
        //function to add a new address at the end of the addresses array in the sessionStorage
        var addUserAddress = function (newAddress) {
            $rootScope.$storage.SHeroAddresses.push(newAddress);
        };
    
        //function to add a new item at the end of the items array in the sessionStorage
        var addUserItem = function (newItem) {
            $rootScope.$storage.SHeroItems.push(newItems);  
        };
    
        //function to add a new item at the end of the items array in the sessionStorage
        var addUserItem = function (newItem) {
            $rootScope.$storage.SHeroItems.push(newItem);  
        };
    
        //function to adda new receivedRating at the end of the ratings array in the sessionStorage
        var addUserRating = function (newRating) {
            $rootScope.$storage.SHeroReceivedRatings = newRating;
        }
    
        //function to reset the userData (JSON) in the session storage
        var updateUserData = function (userData) {
            store(userData);
        };
    
        //function to update one specific address in the userData in the sessionStorage
        var updateUserAddressSpecific = function (newAddress) {
            for (var index = 0; index < $rootScope.$storage.SHeroAddresses.length; ++index) {
                var address = $rootScope.$storage.SHeroAddresses[index];
                if(address.id == newAddress.id) {
                    $rootScope.$storage.SHeroAddresses[index] = newAddress;
                }
            }
        };
    
        //function to set a new addresses array as the users addresses in the sessionStorage
        var updateUserAddressAll = function (newAddresses) {
            $rootScope.$storage.SHeroAddresses = newAddresses;
        }
        
        //function to update the user's firstname in the sessionStorage
        var updateUserFirstname = function (newFirstname) {
            $rootScope.$storage.SHeroFirstname = newFirstname;
        };
        
        //function to update the user's lastname in the sessionStorage
        var updateUserLastname = function (newLastname) {
            $rootScope.$storage.SheroLastname = newLastname;
        };
        
        //function to update the user's mail address in the SessionStorage
        var updateUserEmailAddress = function (newMailAddress) {
            $rootScope.$storage.SHeroEmailAddress = newMailAddress;
        };
    
        //function to update one specific item in the items array in the SessionStorage
        var updateUserItemSpecific = function (newItem) {
            for (var index = 0; index < $rootScope.$storage.SHeroItems.length; ++index) {
                var item = $rootScope.$storage.SHeroItems[index];
                if(item.id == newItem.id) {
                    $rootScope.$storage.SHeroItems[index] = newItem;
                }
            }
        };
        
        //function to set a new items array as the user's items in the SessionStorage
        var updateUserItemAll = function (newItems) {
            $rootScope.$storage.SHeroItems = newItems;  
        };
        
        //function to update one specific rating in the sessionStorage
        var updateUserRatingSpecific = function (newRating) {
              for (var index = 0; index < $rootScope.$storage.SHeroReceivedRatings.length; ++index) {
                var rating = $rootScope.$storage.SHeroReceivedRatings[index];
                if(rating.id == newRating.id) {
                    $rootScope.$storage.SHeroReceivedRatings[index] = newRating;
                }
            }
        };
        
        //function to set a new ratings array as the user's ratings in the SessionStorage
        var updateUserRatingAll = function (newRatings) {
            $rootScope.$storage.SHeroReceivedRatings = newRatings;
        }
    
        var getUserAddressSpecific = function (addressId) {
            for (var index = 0; index < $rootScope.$storage.SHeroAddresses.length; ++index) {
                var address = $rootScope.$storage.SHeroAddresses[index];
                if(address.id == addressId) {
                    return address;
                }
            }
        };
    
        var getUserAddressAll = function () {
            return $rootScope.$storage.SHeroAddresses;  
        };
    
        var getUserId = function () {
            return $rootScope.$storage.SHeroId;  
        };
    
        var getUserFirstname = function () {
            return $rootScope.$storage.SHeroFirstname;
        };
        
        var getUserLastname = function () {
            return $rootScope.$storage.SHeroLastname;
        };
        
        var getUserEmailAddress = function () {
            return $rootScope.$storage.SHeroEmailAddress;  
        };
    
        var getUserPasswordHash = function () {
            return $rootScope.$storage.SHeroPasswordHash;  
        };
    
        var getUserItemSpecific = function (itemId) {
            for (var index = 0; index < $rootScope.$storage.SHeroItems.length; ++index) {
                var item = $rootScope.$storage.SHeroItems[index];
                if(item.id == itemId) {
                    return item;
                }
            }
        };
        
        var getUserItemAll = function () {
            return $rootScope.$storage.SHeroItems;  
        };
    
        var getUserRatingSpecific = function (ratingId) {
            for (var index = 0; index < $rootScope.$storage.SHeroReceivedRatings.length; ++index) {
                var rating = $rootScope.$storage.SHeroReceivedRatings[index];
                if(rating.id == ratingId) {
                    return rating;
                }
            } 
        };
    
        var getUserRatingAll = function () {
            return $rootScope.$storage.SHeroReceivedRatings;  
        };

        //function to delete an address
        var deleteUserAddress = function (addressId) {
            for (var index = 0; index < $rootScope.$storage.SHeroAddresses.length; ++index) {
                var address = $rootScope.$storage.SHeroAddresses[index];
                if(address.id == newAddress.id) {
                    $rootScope.$storage.SHeroAddresses.remove(index);
                }
            }
        };
        return {
            store: storeUserData,
            delete: deleteUserData,
            addUserAddress: addUserAddress,
            addUserItem: addUserItem,
            addUserReceivedRating: addUserRating,
            updateUserData: updateUserData,
            updateUserAddressSpecific: updateUserAddressSpecific,
            updateUserAddressAll: updateUserAddressAll,
            updateUserFirstname: updateUserFirstname,
            updateUserLastname: updateUserLastname,
            updateUserEmailAddress: updateUserEmailAddress,
            updateUserItemSpecific: updateUserItemSpecific,
            updateUserItemAll: updateUserItemAll,
            updateUserRatingSpecific: updateUserRatingSpecific,
            updateUserRatingAll: updateUserRatingAll,
            getUserAddressSpecific: getUserAddressSpecific,
            getUserAddressAll: getUserAddressAll,
            getUserId: getUserId,
            getUserFirstname: getUserFirstname,
            getUserLastname: getUserLastname,
            getUserEmailAddress: getUserEmailAddress,
            getUserPasswordHash: getUserPasswordHash,
            getUserItemSpecific: getUserItemSpecific,
            getUserItemAll: getUserItemAll,
            getUserRatingSpecific: getUserRatingSpecific,
            getUserRatingAll: getUserRatingAll,
            deleteUserAddress: deleteUserAddress
        };
    })