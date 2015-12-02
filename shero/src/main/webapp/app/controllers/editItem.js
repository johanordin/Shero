angular.module('SHeroApp')
    .controller('EditItemCtrl', function ($scope, $stateParams, $location, ItemsService, UsersService, SessionStorageService) {
    	
    	$scope.isNewitem = false;
    	$scope.itemId = $stateParams.itemId;
    
        var rawItem = SessionStorageService.getUserItemSpecific($scope.itemId);
        $scope.item = ItemsService.getNeededItemInfo(rawItem[0]);
    
        $scope.deleteItem = function () {
            var deleteItemPromise = ItemsService.deleteItem($scope.itemId)
            deleteItemPromise.then(function(response) {
                var getUserPromise = UsersService.getUserById(SessionStorageService.getUserId());
                getUserPromise.then(function(response) {
                    SessionStorageService.updateUserData(response.data);
                    console.log("Item "+$scope.itemId+" deleted");
                    $location.path("/Home");
                });
            });
        }
        
		$scope.disabled = function(date, mode) {
			return true;
		};
		
		// Add alert when a new item is created.
		$scope.$watch( function () { return $location.path(); }, function (path) {
			var itemCreated = "/EditItem/" + $scope.itemId + "/new";
			//console.log("Path: " + path);
			//console.log(itemCreated);
			
			if (path == itemCreated) {
				$scope.isNewitem = true;
			}

		});
     
    });
