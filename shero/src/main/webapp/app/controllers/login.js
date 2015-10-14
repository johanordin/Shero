'use strict';

angular.module('SHeroApp').controller('UserMenuCtrl', function() {
	var controller = this;

	controller.login = function() {
		var expiry = new Date();
		var date = new Date();
	  	expiry.setTime(date.getTime()+(30*60*1000)); // 30 minutes
	  	document.cookie = "sheroUserId=123456; expires=" + expiry.toGMTString();
		controller.loggedIn = true;
	};

	controller.logout = function() {
		document.cookie = 'sheroUserId=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
		controller.loggedIn = false;
	};

	if (document.cookie.indexOf("sheroUserId") >= 0) {
  		controller.login();
	}
});
