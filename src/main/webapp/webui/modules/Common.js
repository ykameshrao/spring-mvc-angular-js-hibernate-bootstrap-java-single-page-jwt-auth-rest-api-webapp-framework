/**
 * Created by Y.Kamesh on 4/14/2015.
 */
var commonModule = angular.module('App.Common', []);

commonModule.constant('BackendCfg',  {
    url: 'http://localhost:8080',
    setupHttp: function(http) {
        http.defaults.useXDomain = true;
        http.defaults.withCredentials = true;
    }
});

var compareTo = function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {

            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };

            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
};
commonModule.directive('compareTo', compareTo);
