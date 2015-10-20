angular.module('SHeroApp').controller('ModalLoginCtrl', function ($scope, $modalInstance, $rootScope, items) {
    
  $scope.ok = function () {
    var expiry = new Date();
		var date = new Date();
	  	expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
	  	document.cookie = "sheroUserId=123456; expires=" + expiry.toGMTString();
		$rootScope.loggedIn = true;
    
    $modalInstance.close();
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});