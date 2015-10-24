'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Main-controller of the SHeroApp. Does literally nothing right now.
 */
angular.module('SHeroApp')
    .controller('MainCtrl', function ($scope, $cookies, UsersService, userDataService) {
        if ($cookies.get('SHeroUserId') != undefined) {
            var getUserPromise = UsersService.getUserById($cookies.get('SHeroUserId'));
            getUserPromise.then(function(response) {
                userDataService.store(response.data);
                console.log("User "+ response.data.id +" logged in!");
            });        
        };
    });
