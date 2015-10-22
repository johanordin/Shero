angular.module('SHeroApp').controller('editUserCtrl', function($scope)){

		//Data of the user-form which is going to sent to server
		$scope.formData = {};


	    //onSubmit-function of user-form
	    $scope.processForm = function() {
	    	console.log($scope.formData);
	    }; 

	    //react on change of the view in the form and set variable of specified path to true
		$scope.$watch( function () { return $location.path(); }, function (path) {
			if (path=="/AddItem/Generalinformation") {
				$scope.isGeneral = true;
				$scope.isContact = false;
			} else if (path=="/AddItem/Contact") {
				$scope.isGeneral = false;
				$scope.isContact = true;
			} 
		});

		$scope.myVar = true;
    $scope.toggle = function() {
        $scope.myVar = !$scope.myVar;
    }




	}