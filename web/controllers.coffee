homeController = ($scope, $http, $location) ->
  $scope.findGeeks = ->
    $http.get('/search', { 'params': { 'keywords': $scope.keywords } } ).success (data) ->
      $scope.geeks = data
      $location.url "?keyword=#{$scope.keywords}"

