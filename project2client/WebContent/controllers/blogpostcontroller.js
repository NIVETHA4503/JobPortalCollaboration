/**
 * 
 */
app.controller('BlogPostCtrl',function($scope,BlogPostService,$location,$rootScope){
	//Add a blog
	//How to get blogpost object from the view?
	//View to Controller
	//Call a function from view
	$scope.addBlogPost=function(blogPost){ //ADDING A FUNCTION IN $scope
		//pass the blogpost to BlogService.addBlogPost()
		BlogPostService.addBlogPost(blogPost).then(function(response){
			alert('BlogPost added successfully and it is waiting for approval')
			$location.path('/home')
		},function(response){
			//401 or 500(if 401, login.html,if 500, display error message in blogpostform.html
			if(response.status==401)
				$location.path('/login')
			$scope.error=response.data
			
		})
	}
	
	//Get All blogs Approved - STATEMENT
	if($rootScope.user!=undefined)
	BlogPostService.getBlogsApproved().then(
			function(response){
			$scope.blogsApproved=response.data //blogsapproved.html 
			//list of blogs approved,select * from blogpost where approved=1
			},
			function(response){
				if(response.status==401)
					$location.path('/login')
			})
	
	//Get blogs waiting for approval - STATEMENT
	if($rootScope.user.role=='ADMIN')
	BlogPostService.getBlogsWaitingForApproval().then(
			function(response){
				$scope.blogsWaitingForApproval=response.data //blogswaitingforapproval.html
				//list of blogs not approved, select * from blogpost where approved=0
			},
			function(response){
				if(response.status==401 && response.data.errorCode==5)//Not Authenticated
					$location.path('/login')
				//if user is authenticated but not authorized
					//Display the error message in the same view
				$scope.error=response.data
			})
			
			$scope.addBoldTags=function(){
		$scope.blogPost.blogContent=$scope.blogPost.blogContent + "<b></b>"
	}
})



//9 - adding a function in $scope