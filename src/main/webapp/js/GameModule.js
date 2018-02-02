var gameModule = angular.module('gameModule', []);

gameModule.controller('lobbyController', ['$rootScope', '$scope', '$http', '$location',

    function (rootScope, scope, http, location) {

        scope.createNewGame = function () {

            http.post("/game/create", {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data, status, headers, config) {
                rootScope.gameId = data.id;
                location.path('/game/' + rootScope.gameId);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }
]);


gameModule.controller('gamesToJoinController', ['$scope', '$http', '$location',
    function (scope, http, location) {

        scope.gamesToJoin = [];

        http.get('/game/list').success(function (data) {
            scope.gamesToJoin = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });


        scope.joinGame = function (id) {

            var requestUrl = "/game/join/" + id;
            http.post(requestUrl, {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data) {
                location.path('/game/' + data.id);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        }

    }]);


gameModule.controller('playerGamesController', ['$scope', '$http', '$location', '$routeParams',
    function (scope, http, location, routeParams) {

        scope.playerGames = [];

        http.get('/game/player/list').success(function (data) {
            scope.playerGames = data;
        }).error(function (data, status, headers, config) {
            location.path('/player/panel');
        });

        scope.loadGame = function (id) {
            console.log(id);
            location.path('/game/' + id);
        }

    }]);


gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',
    function (rootScope, routeParams, scope, http) {
        // TODO: Connect to socket


        scope.reload = function getData() {

            http.get('/play/board').success(function (data) {
                scope.data = data
                scope.gameBoard = [];
                data.pits.forEach(function (pit) {
                    scope.gameBoard[pit.position] = pit.numberOfStones;
                })
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
            http.get('/play/turn').success(function (data) {
                scope.gameTurn = data;
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
            http.get('/play/state').success(function (data) {
                scope.gameState = data;
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
            http.get('/play/score').success(function (data) {
                scope.gameScore = data;
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load game properties";
            });
        }

        scope.reload();

        scope.move = function (id) {
            // TODO: Socket?
            http.post('/play/move/' + id).success(function (data) {
                scope.data = data
                scope.reload();
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do do move";
            });
        }

    }
]);