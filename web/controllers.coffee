homeController = ($scope, $http, $location) ->
  $scope.findGeeks = ->
    parameters = keywords: $scope.keywords

    $http.get('/search', params: parameters).success (data) ->
      $scope.geeks = data
      $location.url "?keyword=#{$scope.keywords}"

