homeController = ($scope, $http, $location) ->
  $scope.doSearch = ->
    $scope.refresh()

  $scope.refresh = ->
    $location.url "?q=#{$scope.search}";
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json

  $scope.search = $location.search().q || ''
  $scope.refresh()
