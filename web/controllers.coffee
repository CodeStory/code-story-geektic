homeController = ($scope, $http) ->
  $scope.test = ->
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json
