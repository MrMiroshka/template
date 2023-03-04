(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.marketUser) {
            try {
                let jwt = $localStorage.marketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("User not authorization!!!");
                    delete $localStorage.marketUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (exp) {

            }
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketUser.token;
        }

        if (!$localStorage.marketGuestId){
            $http.get('http://localhost:5555/cart/api/v1/cart/generate_uuid')
                .then(function successCallback(response){
                $localStorage.marketGuestId = response.data.value;
            });
        }
    }
})();

angular.module('market').controller('indexController', function ($rootScope,$scope, $http,$location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/api/v1';
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';




    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/auth/' + $localStorage.marketGuestId, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marketUser = {username: $scope.user.username, token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;
                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };



    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $scope.OrderList = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.marketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.marketUser) {
            return true;
        } else {
            return false;
        }
    };


});




