'use strict';

/**
 * @ngdoc function
 * @name SHeroApp.controller:UserMenuCtrl
 * @description
 * # UserMenuCtrl
 * Controller of the SHeroApp to control the UserMenu (right part of the navbar).
 */

angular.module('SHeroApp').controller('UserMenuCtrl', function() {
	var controller = this;

  	//If 'Login' is clicked a cookie with a UserId is set 
	controller.login = function() {
		var expiry = new Date();
		var date = new Date();
	  	expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
	  	document.cookie = "sheroUserId=123456; expires=" + expiry.toGMTString();
		controller.loggedIn = true;
	};

	//If 'Logout' is clicked the cookie is deleted
	controller.logout = function() {
		document.cookie = 'sheroUserId=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
		controller.loggedIn = false;
	};

    
    //Checks if a cookie is already set and if yes, shows the logged-in user menu
	if (document.cookie.indexOf("sheroUserId") >= 0) {
  		controller.login();
	}
});
