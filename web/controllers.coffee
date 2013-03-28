homeController = ($scope, $http) ->
  console.log 'Angular loaded'

  $scope.findGeeks = ->
    $http.get("/search", { 'params': { 'keywords': $scope.keywords } } ).success (data) ->
      $scope.geeks = data


