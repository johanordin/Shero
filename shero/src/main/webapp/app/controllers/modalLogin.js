'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:ModalLoginCtrl
 * @description
 * # ModalLoginCtrl
 * Controller of the SHeroApp which is used for all operations inside the Modal for Login and Register.
 * The Login and the register are two views inside of tabs
 */
angular.module('SHeroApp').controller('ModalLoginCtrl', function ($scope, $modalInstance, UsersService, SessionStorageService) {

    //variable to hold the input data from the user
    $scope.formData = {};
    $scope.sendData = {};
    
    //variable to check whether is user wants to login or to register and switch between the two options
    //tab = 1 --> login-view
    //tab = 2 --> register-view
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
        var mail = $scope.formData.mail;
        var hashedPassword = Sha256.hash($scope.formData.password);

        var getUserPromise = UsersService.getUserByMail(mail, hashedPassword);
        getUserPromise.then(function(response) {
            if (response.data.status === 'error' || response.status != '200') {
                // do not proceed with login
                alert("User could not be logged in.");

                // TODO: should now reopen the login modal
            } else {
                // copy data into local storage and initiate session
                SessionStorageService.store(response.data);
                console.log("User "+ response.data.id +" logged in!");
            } 
        });
    }
    
    //function called when the user is in register-view and clicks the register-button
    $scope.registerClicked = function() {
        $scope.sendData.firstname = $scope.formData.firstname;
        $scope.sendData.lastname = $scope.formData.lastname;
        $scope.sendData.emailAddress = $scope.formData.emailAddress;
        $scope.sendData.passwordHash = Sha256.hash($scope.formData.password);
        $scope.registerUser();
        $modalInstance.close();
    }
    
    //function to send data of registering user to server
    $scope.registerUser = function() {
        var postUserPromise = UsersService.postUser($scope.sendData);
        postUserPromise.then(function(response) {
            SessionStorageService.store(response.data);
            console.log("User " + response.data.id + " created!")
        });
    }
});
