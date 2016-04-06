'use strict';

/* Services */

var services = angular.module('ngdemo.services', ['ngResource']);

/* Service FileUpload */
services.factory('FileFactory', ['$http', function ($http) {
    var factory = {};
    
    
    factory.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
    
    return factory;
}]);
