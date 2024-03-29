angular.module('SHeroApp')
    .factory('MessageService', function($q, $timeout, $http) {
        
        var postMessage = function(formData) {
            return $http({
                method: "POST",
                url: "/rest/users/question", //TODO: valid rest url
                data: formData
            }).then(function(response){
                return response;
            })
        };
 
        return {
        	postMessage : postMessage
        };
    })