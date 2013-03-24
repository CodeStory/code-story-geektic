homeController = ($scope, $http, $location) ->
  $scope.doSearch = ->
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json
      $scope.latestgeeks = json if json.length > 0
      $location.url "?q=#{$scope.search}";

  $scope.search = $location.search().q || ''
  $scope.doSearch()
