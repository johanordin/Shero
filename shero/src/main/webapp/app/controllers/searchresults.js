angular.module('SHeroApp').controller('SearchResultsCtrl', function($scope) { 
  $scope.itemlist = [
  {name:'surfboard',rating:2, price:20},
  {name:'thing',rating:6, price:50},
  {name:'car',rating:5, price:100},
  {name:'skateboard',rating:1, price:10},
  ]

  $scope.show = false; 

  $scope.expand = function(){
    console.log("show")
    $scope.show = true;

  }
});
