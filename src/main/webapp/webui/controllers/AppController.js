/**
 * Created by Y.Kamesh on 4/8/2015.
 */
angular.module('App')
    .controller('AppController', AppController);

AppController.$inject = ['$location', '$scope', '$rootScope', 'AuthService', 'FlashMessage'];
function AppController($location, $scope, $rootScope, AuthService, FlashMessage) {
    var app = this;
    console.log("app controller");

    app.logout = function () {
        console.log('received the logout event for user: '+$scope.currentUser.email);
        AuthService.clearCredentials();
        $location.path('/');
    };
};