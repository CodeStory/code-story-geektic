homeController = ($scope, $http) ->
  $scope.doSearch = ->
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json
