/**
 * 
 */
app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL ="http://localhost:8080/project2middleware"
	
	friendService.getAllSuggestedUsers=function(){
		return $http.get(BASE_URL + "/suggestedusers")
	}
	
	
	friendService.addFriend=function(toId){
		return $http.post(BASE_URL + "/addfriend",toId)
	}
	
	friendService.getPendingRequests=function(){
		return $http.get(BASE_URL + "/pendingrequests")
	}
	
     friendService.acceptRequest=function(pendingRequest){//pendingRequest is Friend object
    	 return $http.put(BASE_URL + "/acceptrequest",pendingRequest)
     }
	
     friendService.deleteRequest=function(pendingRequest){
    	 return $http.put(BASE_URL +"/deleterequest",pendingRequest)
     }
     
     
     friendService.getAllFriends=function(){
     	return $http.get(BASE_URL + "/listoffriends")
     }
	return friendService;
})





