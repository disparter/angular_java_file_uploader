'use strict';

// Declare app level module which depends on filters, and services
angular.module('ngdemo', ['ngdemo.filters', 'ngdemo.services', 'ngdemo.directives', 'ngdemo.controllers']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/upload', {templateUrl: 'partials/file-upload.html', controller: 'FileController'});
        $routeProvider.otherwise({redirectTo: '/view1'});
    }]);
