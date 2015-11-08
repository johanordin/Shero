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
//to show the rating as stars
angular.module('SHeroApp').filter('range', function() {
  return function(input, total) {
    total = parseInt(total);

    for (var i=0; i<total; i++) {
      input.push(i);
    }

    return input;
  };

  $('#rent').click(function () {
    if($('button span').hasClass('glyphicon-chevron-down'))
    {
      $('#rent').html('Details'); 
    }
    else
    {      
      $('#rent').html('Rent'); 
    }
  });
});