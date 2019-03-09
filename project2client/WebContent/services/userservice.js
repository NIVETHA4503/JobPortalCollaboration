/**
 * 
 */
app.factory('UserService',function($http){
	var userService={}
	userService.userRegistration=function(user)
	{
		return	$http.post("http://localhost:8080/project2middleware/registration",user)
	}
	userService.login=function(user){
	     return $http.post("http://localhost:8080/project2middleware/login",user)
		}
	userService.logout=function(){
		return $http.put("http://localhost:8080/project2middleware/logout")
	}
	
	userService.getUser=function(){
		return $http.get("http://localhost:8080/project2middleware/getuser")
	}
	
	userService.updateUserProfile=function(user){
		return $http.put("http://localhost:8080/project2middleware/updateuser",user)
	}
	
	
	
	
	return userService;
})


