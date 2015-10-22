'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:ModalLoginCtrl
 * @description
 * # ModalLoginCtrl
 * Controller of the SHeroApp which is used for all operations inside the Modal for Login and Register.
 * The Login and the register are two views inside of tabs
 */
angular.module('SHeroApp').controller('ModalLoginCtrl', function ($scope, $modalInstance, UsersService, userDataService) {

    //variable to hold the input data from the user
    $scope.formData = {};
    $scope.passwords = {};
    
    //variable to check whether is user wants to login or to register and switch between the two options
    $scope.tab = 1;
    
    //variables for the labels depending on login or register
    $scope.title = 'Log In';
    $scope.link = 'Not registered?';

    //function called when the user clicks the login/register-button
    //dependent on the tab the login-function or the register-function is called
    $scope.okClicked = function () {
        if ($scope.tab === 1) {
            $scope.loginClicked();
            $modalInstance.close();
        } else if ($scope.tab === 2) {
            $scope.registerClicked();
        }
    };

    //function called when the user clicks the cancel-button
    $scope.cancelClicked = function () {
        $modalInstance.dismiss('cancel');
    };

    //function called when the user clicks the link to change the view
    //changes the view and also the lables for header, buttons, links, ...
    $scope.changeTab = function() {
        if ($scope.tab === 1) {
            $scope.tab = 2;
            $scope.title = 'Register';
            $scope.link = 'Back to login!';
            $scope.formData = {};
        } else if ($scope.tab === 2) {
            $scope.tab = 1;
            $scope.title = 'Log In';
            $scope.link = 'Not registered';
            $scope.formData = {};
        }
    };

    //function to check if a view/tab is opened
    $scope.checkTab = function(tab) {
        return ($scope.tab == tab);
    };
    
    //function called when the user is in login-view and clicks the login-button
    $scope.loginClicked = function() {
        $scope.loginUser();
    }
    
    $scope.loginUser = function() {
        var getUserPromise = UsersService.getUserByMail();
        getUserPromise.then(function(response) {
            userDataService.store(response.data);
            console.log("User "+ response.data.id +" logged in!");
            alert("User logged in");
        });
    }
    
    //function called when the user is in register-view and clicks the register-button
    $scope.registerClicked = function() {
        if ($scope.passwords.password === $scope.passwords.repeatPassword) {
            $scope.formData.passwordHash = Sha256.hash($scope.passwords.password);
            $scope.registerUser();
            $modalInstance.close();
        }
    }
    
    //function to send data of registering user to server
    $scope.registerUser = function() {
        var postUserPromise = UsersService.postUser($scope.formData);
        postUserPromise.then(function(response) {
            userDataService.store(response.data);
            console.log("User " + response.data.id + " created!")
            alert("User created");
        });
    }
});
