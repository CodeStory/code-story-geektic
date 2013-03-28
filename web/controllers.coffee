homeController = ($scope, $http, $location) ->
  $scope.doSearch = ->
    $http.get("/search?q=#{$scope.search}").success (json) ->
      $scope.geeks = json
      $scope.latestgeeks = json if json.length > 0
      $location.search "q", $scope.search;

  $scope.search = $location.search().q || ''
  $scope.doSearch()

mapController = ->
  console.log("oh yeah")
  map = new Mappy.api.map.Map({container:"#map"})
  map.setCenter(new Mappy.api.geo.Coordinates(1.44295, 43.6044), 7)
  return
