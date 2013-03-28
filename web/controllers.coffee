homeController = ($scope, $http) ->
  $scope.findGeeks = ->
    $http.get('/search', { 'params': { 'keywords': $scope.keywords } } ).success (data) ->
      $scope.geeks = data


