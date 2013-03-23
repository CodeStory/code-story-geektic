homeController = ($scope, $http, $location) ->
  $scope.doSearch = ->
    $scope.refresh()

  $scope.refresh = ->
    $location.url "?q=#{$scope.search}";
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json
      $scope.latestgeeks = json if json.length > 0 # peut etre amelioré

  $scope.search = $location.search().q || ''
  $scope.refresh()
