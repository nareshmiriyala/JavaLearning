var app = angular.module('myApp', []);
app.controller('HelloController', function($scope) {
    $scope.hello = "John";
    console.log('HelloController has been created');
});