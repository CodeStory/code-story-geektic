angular.module('code-story-geektic', [])
  .config ($routeProvider, $locationProvider) ->
    $routeProvider
      .when('/', {templateUrl: 'static/[[version]]/home.html', controller: "homeController", reloadOnSearch: false})
      .otherwise {redirectTo: '/'}
    $locationProvider
      .html5Mode true
    return
