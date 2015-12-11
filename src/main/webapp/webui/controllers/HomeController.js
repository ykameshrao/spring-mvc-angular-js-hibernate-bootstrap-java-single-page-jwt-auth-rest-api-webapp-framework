/**
 * Created by Y.Kamesh on 4/13/2015.
 */
angular.module('App')
    .controller('HomeController', HomeController);

function HomeController($scope, $location) {
    var home = this;
    home.currentUser = null;
};