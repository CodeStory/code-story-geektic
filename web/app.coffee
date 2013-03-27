angular.module('code-story-geektic', [])
  .config ['$routeProvider', ($routeProvider) ->
    $routeProvider
      .when('/home', {templateUrl: 'static/[[version]]/home.html', controller: homeController})
      .otherwise {redirectTo: '/home'}
    return
  ]
