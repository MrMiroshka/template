angular.module('market').controller('storeController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';

    $scope.loadProducts = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null,
                name_product: $scope.filter ? $scope.filter.name_product : null,
                p: pageIndex,
                pageSize: 6
            }
        }).then(function (response) {
            countPages = response.data.totalPages;
            if (countPages < pageIndex) {
                pageIndex = 1;
            }
            $scope.generatePagesList();
            $scope.ProductsList = response.data.content;
            $scope.viewDiv($scope.ProductsList.length);
            $scope.isPaginationHeadOrFine();
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.get(contextPath + '/products/delete/' + productId)
            .then(function (response) {
                $scope.loadProducts();
                $scope.loadProductsBasket();
            })
    };

    $scope.aboutProduct = function (productId) {
        $http.get(contextPath + '/products/' + productId)
            .then(function (response) {
                $scope.ProductsList = [response.data];
                $scope.viewDiv($scope.ProductsList.length);
            })
        console.log(contextPath + '/products/' + productId)
    };

    $scope.putBasket = function (productId) {
        $http.get(contextPathCarts + '/cart/'+$localStorage.marketGuestId+'/add/' + productId,$scope.user)
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.changeCountProduct = function (productId, count) {

        $http({
            url: contextPath + '/cart/change',
            method: 'GET',
            params: {
                productId: productId,
                count: count
            }
        })
            .then(function (response) {
                $scope.loadProductsBasket();
            })

    };

    $scope.returnProducts = function () {
        $scope.loadProducts();
    }
    const form = document.getElementById('form');

    $scope.createProductJson = function () {
        $http.post(contextPath + '/products', $scope.newProductJson)
            .then(function (response) {
                $scope.loadProducts();
            })
    };

    $scope.viewDiv = function (length) {
        if (length > 1) {
            back.hidden = true;
        } else {
            back.hidden = false;
        }
    }


    var cont = document.getElementById("pagination");
    var pageIndex = 1;
    var countPages = 1;
    var head = document.getElementById("head");
    var fine = document.getElementById("fine");
    var back = document.getElementById('div_return');

    $scope.generatePagesList = function () {
        $scope.pagesList = [];
        for (let i = 0; i < countPages; i++) {
            $scope.pagesList.push(i + 1);
        }
    };

    cont.addEventListener("click", function (event) {
        if (event.target.innerText == "Следующая") {
            if (pageIndex < countPages) {
                pageIndex++;
                $scope.loadProducts();
            }
        } else if (event.target.innerText == "Предыдущая") {
            if (pageIndex > 1) {
                pageIndex--;
                $scope.loadProducts();
            }
        } else {
            if (event.target.innerText.length == 1) {
                pageIndex = event.target.innerText;
                $scope.loadProducts();
            }
        }

    });

    $scope.isPaginationHeadOrFine = function () {
        if (pageIndex == 1) {
            head.hidden = true;
            fine.hidden = false;
        } else if (pageIndex == countPages) {
            head.hidden = false;
            fine.hidden = true;
        } else {
            head.hidden = false;
            fine.hidden = false;
        }
    };


    $scope.loadProducts();
});