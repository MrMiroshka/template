angular.module('market').controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';

    $scope.loadOrders = function () {
        $http.get(contextPath + '/orders', $scope.user)
        $http({
            url: contextPath + '/orders',
            method: 'GET',
        }).then(function (response) {
            $scope.OrderList = response.data;
        });
    };


    $scope.loadOrders();


});