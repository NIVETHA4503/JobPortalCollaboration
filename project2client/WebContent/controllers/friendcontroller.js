/**
 * 
 */
app.controller('FriendCtrl',function($scope,$location,FriendService){
	//STATEMENT
	function getAllSuggestedUsers(){
	FriendService.getAllSuggestedUsers().then(
			function(response){
			  $scope.suggestedUsers=response.data //Array of User objects
			},
			function(response){
				if(response.status==401)
					$location.path('/login')
			})
	}
	
	function getPendingRequests(){
		FriendService.getPendingRequests().then(
				function(response){
					$scope.pendingRequests=response.data //Array of Friend Object[{fromId,toId,status,friendId},{}]
				},
				
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	getAllSuggestedUsers()
	getPendingRequests()
	$scope.addFriend=function(toId){//toId is suggestedUser in suggesteduserslist.html,toId is user object
		FriendService.addFriend(toId).then(
				function(response){
					getAllSuggestedUsers()//S in (A - (B U C))
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	
	$scope.acceptRequest=function(pendingRequest){//pendingRequest has fromId,toId,status,friendId
		FriendService.acceptRequest(pendingRequest).then(
				function(response){
					//get pending requests
					getPendingRequests()//from Friend where toId_email=? and status='P'
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	$scope.deleteRequest=function(pendingRequest){
		FriendService.deleteRequest(pendingRequest).then(
				function(response){
					//get pending requests
					getPendingRequests()//from Friend where toId_email=? and status='P'
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	function getAllFriends(){
    	FriendService.getAllFriends().then(
    			function(response){
    				//response.data is Array of User object
    				$scope.friends=response.data
    			},function(response){
    				if(response.status==401)
    					$location.path('/login')
    			})
    }
	
	getAllFriends()
})


