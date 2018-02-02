var kalahApp = angular.module('kalahApp', ['ngRoute', 'gameModule']);




kalahApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    when('/about', {
        templateUrl: 'templates/about.html'
    }).
    when('/player', {
        templateUrl: 'templates/lobby.html',
        controller: 'lobbyController'
    }).
    when('/game/:id', {
        templateUrl: 'templates/game.html',
        controller: 'gameController'
    }).
    // when('/player/panel', {
    //     templateUrl: 'templates/player-panel.html',
    //     controller: 'newGameController'
    // }).
    otherwise({
        redirectTo: '/player'
    });
}]);