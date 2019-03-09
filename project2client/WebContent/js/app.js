var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/addjob',{controller:'JobCtrl',templateUrl:'views/jobform.html'}) //INSERT
	//V to C $scope.addJob() ,view calling a function addJob()
	//this view can access all $scope variables and $scope functions defined in JobCtrl 
	
	.when('/getalljobs',{controller:'JobCtrl',templateUrl:'views/jobslist.html'}) //SELECT
	//C to V -> JobService.getAllJobs()....$scope.jobs, view is using the variable jobs
	
	.when('/registration',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})//INSERT
	//V to C -> $scope.registration(),view is calling a function registration()
	
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})//SELECT
	//V to C, $scope.login(),view is calling a function login()
	
	.when('/updateuserprofile',{controller:'UserCtrl',templateUrl:'views/updateuserprofile.html'})//SELECT
	//C to V,UserService.getUser()..$scope.user=response.data
	
	.when('/addblog',{controller:'BlogPostCtrl',templateUrl:'views/blogpostform.html'})
	.when('/blogsapproved',{controller:'BlogPostCtrl',templateUrl:'views/blogsapproved.html'})
	.when('/blogswaitingforapproval',{controller:'BlogPostCtrl',templateUrl:'views/blogswaitingforapproval.html'})
	.when('/getblogwaitingforapproval/:blogpostid',{controller:'BlogInDetailCtrl',templateUrl:'views/blogapprovalform.html'})
	.when('/getblogapproved/:blogpostid',{controller:'BlogInDetailCtrl',templateUrl:'views/blogindetail.html'})
	.when('/home',{controller:'NotificationCtrl',templateUrl:'views/home.html'})
	.when('/getnotification/:notificationId',{controller:'NotificationCtrl',templateUrl:'views/notificationindetail.html'})
	.when('/uploadprofilepic',{templateUrl:'views/uploadprofilepic.html'})
	.when('/suggestedusers',{controller:'FriendCtrl',templateUrl:'views/suggesteduserslist.html'})
	.when('/pendingrequests',{controller:'FriendCtrl',templateUrl:'views/pendingrequests.html'})
	.when('/listoffriends',{controller:'FriendCtrl',templateUrl:'views/friendslist.html'})
	.when('/chat',{controller:'ChatCtrl',templateUrl:'views/chat.html'})
	.otherwise({controller:'NotificationCtrl',templateUrl:'views/home.html'})
})
app.run(function($rootScope,UserService,$location,$cookieStore){//when module gets instantiated, get newly created $rootScope, add logout function to the $rootScope objecgt
	alert('app.run function is getting executed...')
	//Adding a function logout() in $rootScope
	//restore the logged in user details from cookieStore
	//$rootScope.user 
	
	if($rootScope.user==undefined)
	   $rootScope.user=$cookieStore.get('userDetails')
		
	$rootScope.logout=function(){
		alert('Logout function in UserCtrl in $rootScope object')
		UserService.logout().then(
				function(response){
					delete $rootScope.user
					$cookieStore.remove('userDetails')
					$location.path('/login')
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	//Get the user details from cookie and assign it to the $rootScope variable 'user'
	
})


