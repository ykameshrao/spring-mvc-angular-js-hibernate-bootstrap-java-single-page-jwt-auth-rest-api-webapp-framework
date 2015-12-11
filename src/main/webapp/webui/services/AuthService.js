/**
 * Created by Y.Kamesh on 4/12/2015.
 * Referred: https://github.com/mpetersen/aes-example
 */

'use strict';
angular.module('App.Auth')
    .service('AuthService', ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout', 'BackendCfg',
        function (Base64, $http, $cookieStore, $rootScope, $timeout, BackendCfg) {
            var service = this;
            service.login = function (email, password, callback) {
                BackendCfg.setupHttp($http);
                // this.createCredentials(email, password);
                var user = {};
                var aesPack = this.encryptPassword(password);
                user.password = '';
                user.vpassword = '';
                user.iv = aesPack.iv;
                user.salt = aesPack.salt;
                user.keySize = aesPack.keySize;
                user.iterations = aesPack.iterations;
                user.encryptedPassword = aesPack.ciphertext;
                user.email = email;
                console.log('encryptedPassword: '+user.encryptedPassword);
                console.log('pass: '+user.password);
                console.log('email: '+user.email);
                $http.post(BackendCfg.url+'/api/user/authenticate', user )
                    .success(function (response) {
                        callback(response);
                    });
                console.log('login event posted...')
            };

            service.register = function (user, callback) {
                BackendCfg.setupHttp($http);
                // this.createCredentials(user.email, user.password);

                var aesPack = this.encryptPassword(user.password);
                user.password = '';
                user.vpassword = '';
                user.iv = aesPack.iv;
                user.salt = aesPack.salt;
                user.keySize = aesPack.keySize;
                user.iterations = aesPack.iterations;
                user.encryptedPassword = aesPack.ciphertext;

                console.log('encryptedPassword: '+user.encryptedPassword);
                console.log('pass: '+user.password);
                console.log('email: '+user.email);
                console.log('displayName: '+user.displayName);

                $http.post(BackendCfg.url+'/api/user/register', user )
                    .success(function (response) {
                        callback(response);
                    });
            };

            service.encryptPassword = function (password) {
                var aesPack = {};
                var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
                aesPack.iv = iv;
                aesPack.salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
                aesPack.keySize = 128;
                aesPack.iterations = 1000;

                var key = CryptoJS.PBKDF2(
                    "biZndDtCMkdeP8K0V15OKMKnSi85",
                    CryptoJS.enc.Hex.parse(aesPack.salt), { keySize: aesPack.keySize/32, iterations: aesPack.iterations });

                var encrypted = CryptoJS.AES.encrypt(password, key, { iv: CryptoJS.enc.Hex.parse(aesPack.iv) });
                aesPack.ciphertext = encrypted.ciphertext.toString(CryptoJS.enc.Base64);
                return aesPack;
            };

            service.createCredentials = function (email, password) {
                var authdata = Base64.encode(email + ':' + password);

                $rootScope.globals = {
                    currentUser: {
                        email: email,
                        authdata: authdata
                    }
                };

                $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata;
            };

            service.createJWTToken = function (user, token) {
                $rootScope.globals = {
                    currentUser: user,
                    token: token
                };

                $http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
            };

            service.setCredentials = function () {
                $cookieStore.put('globals', $rootScope.globals);
            };

            service.clearCredentials = function () {
                console.log("clearing credentials...");
                $rootScope.globals = {};
                $cookieStore.remove('globals');
                $http.defaults.headers.common.Authorization = '';
            };

            return service;
        }])
    .factory('Base64', function () {
        /* jshint ignore:start */

        var keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';

        return {
            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);

                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;

                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }

                    output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);

                return output;
            },

            decode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                var base64test = /[^A-Za-z0-9\+\/\=]/g;
                if (base64test.exec(input)) {
                    window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
                }
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

                do {
                    enc1 = keyStr.indexOf(input.charAt(i++));
                    enc2 = keyStr.indexOf(input.charAt(i++));
                    enc3 = keyStr.indexOf(input.charAt(i++));
                    enc4 = keyStr.indexOf(input.charAt(i++));

                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;

                    output = output + String.fromCharCode(chr1);

                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }

                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";

                } while (i < input.length);

                return output;
            }
        };

        /* jshint ignore:end */
    });