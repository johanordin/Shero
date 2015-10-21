'use strict';

 /**
 * @ngdoc function
 * @name SHeroApp.controller:ModalLoginCtrl
 * @description
 * # ModalLoginCtrl
 * Controller of the SHeroApp which is used for all operations inside the Modal for Login and Register.
 * The Login and the register are two views inside of tabs
 */
angular.module('SHeroApp').controller('ModalLoginCtrl', function ($scope, $modalInstance, $rootScope, items) {

    //variable to hold the input data from the user
    $scope.formData = {};
    
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
        } else if ($scope.tab === 2) {
            $scope.registerClicked();
        }
        $modalInstance.close();
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
        }
    };

    //function to check if a view/tab is opened
    $scope.checkTab = function(tab) {
        return ($scope.tab == tab);
    };
    
    //function called when the user is in login-view and clicks the login-button
    $scope.loginClicked = function() {
        var expiry = new Date();
            var date = new Date();
            expiry.setTime(date.getTime()+(30*60*1000)); //Cookie expires in 30 Minutes
            document.cookie = "sheroUserId=123456; expires=" + expiry.toGMTString();
            $rootScope.loggedIn = true;
    }
    
    //function called when the user is in register-view and clicks the register-button
    $scope.registerClicked = function() {
        console.log('Register clicked!');
    }
});