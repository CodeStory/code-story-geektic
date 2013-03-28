angular.module('code-story-geektic', [])
  .config ($routeProvider, $locationProvider) ->
    $routeProvider
      .when('/', {templateUrl: 'static/[[version]]/home.html', controller: "homeController", reloadOnSearch: false})
      .when('/map', {templateUrl: 'static/[[version]]/map.html', controller: "mapController"})
      .otherwise {redirectTo: '/'}
    $locationProvider
      .html5Mode true
    return
