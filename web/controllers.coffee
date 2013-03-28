homeController = ($scope) ->
  console.log 'Angular loaded'

  $scope.findGeeks = ->
    if $scope.keywords == 'java'
      $scope.geeks = 'David'
    else if $scope.keywords == 'scala'
      $scope.geeks = 'Martin'
    else
      $scope.geeks = ''
