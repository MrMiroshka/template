angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';

    $scope.loadProductsBasket = function (pageIndex = 1) {
        $http({
            url: contextPathCarts + '/cart/' + $localStorage.marketGuestId,
            method: 'GET',
        }).then(function (response) {
            $scope.cart = response.data; 
        });
    };

    $scope.deleteProductBasket = function (productId) {

        $http({
            url: contextPathCarts + '/cart/'+$localStorage.marketGuestId+'/delete/' + productId,
            method: 'GET',
        })
            .then(function (response) {
                $scope.loadProductsBasket();
            })

    };

    $scope.deletAllProductBasket = function () {

        $http({
            url: contextPathCarts + '/cart/'+$localStorage.marketGuestId+'/delete/',
            method: 'GET',
        })
            .then(function (response) {
                $scope.loadProductsBasket();
            })
    };

    $scope.saveOrder = function () {
        $http.post(contextPath + '/orders',$scope.user)
            .then(function (response) {
                $scope.loadProductsBasket();
            })
    };

    $scope.loadProductsBasket();

});