angular.module('SHeroApp')
.service('sharedItemList', function() {
	  var itemList = [];

	  var addItems = function(newObj) {
		  itemList.push(newObj);
	  };

	  var getItems = function(){
	      return itemList;
	  };

	  return {
		  addItems: addItems,
		  getItems: getItems
	  };

});