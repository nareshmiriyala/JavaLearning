function Hello($scope, $http) {
    $http.get('http://localhost:8080/com.dellnaresh/services/customers/1').
        success(function(data) {
            $scope.greeting = data;
        });
}