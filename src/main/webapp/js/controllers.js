'use strict';

/* Controllers */


var app = angular.module('ngdemo.controllers', []);


// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});


/* File Controller */
app.controller('FileController', ['$scope', 'FileFactory', function($scope, FileFactory){
    
    $scope.fileFactory = FileFactory;
    
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "/ngdemo/rest/files/upload";
        this.fileFactory.uploadFileToUrl(file, uploadUrl);
    };
    
}]);
