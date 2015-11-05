angular.module('SHeroApp')
.service('fileUploadService', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var formData = new FormData();
        formData.append("file", file);
        $http({
            method: 'POST',
            url: uploadUrl,
            data: formData,
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
         })
        .success(function(data, status) {   
        	console.log("success");
         });
    }
}]);