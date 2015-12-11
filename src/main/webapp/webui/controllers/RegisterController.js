/**
 * Created by Y.Kamesh on 4/12/2015.
 */
angular.module('App.Auth')
    .controller('RegisterController', RegisterController);

RegisterController.$inject = ['$location', '$scope', '$rootScope', 'AuthService', 'FlashMessage'];
function RegisterController($location, $scope, $rootScope, AuthService, FlashMessage) {
    var rc = this;
    console.log('register controller');
    rc.register = function (admin) {
        console.log('received the register event for user: '+rc.user.username);
        $rootScope.isSubmitted = true;
        rc.dataLoading = true;
        rc.user.admin = admin;
        AuthService.register(rc.user, function (response) {
            if (response.code==200) {
                AuthService.createJWTToken(response.result.user, response.result.token);
                AuthService.setCredentials();
                $location.path('/app');
            } else {
                rc.error = response.result;
                rc.details = response.details;
                rc.dataLoading = false;
                $rootScope.isSubmitted = false;
            }
        });
    };
};