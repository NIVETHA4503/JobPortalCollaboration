/**
 *   /getblogwaitingforapproval/:blogpostid
 * BlogInDetailCtrl
 *   select * from blogpost where blogpostid=?
 */
app.controller('BlogInDetailCtrl',function($scope,BlogPostService,$routeParams,$location,$sce){
	var blogPostId=$routeParams.blogpostid //param name is defined in app.config in when statment
	alert('blogpost id is ' + blogPostId)
	
	//Get the result if the query select * from blogpost where blogpostid=?
	//STATMENT - Call a function in Service
	BlogPostService.getBlog(blogPostId).then(
			function(response){
				$scope.blogPost=response.data //single blogpost object
				$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
			},
			function(response){
				if(response.status==401)
					$location.path('/login')
			})
	
	//STATEMENT 
	BlogPostService.hasUserLikedBlogpost(blogPostId).then(
		function(response){
		 if(response.data=='')
			 $scope.isLiked=false  //glyphicon in black
			 else
				 $scope.isLiked=true //glyphicon in blue
	    },
	    function(response){
		if(response.status==401)
			$location.path('/login')
	    })
    //Function for approving a blogpost
		$scope.approveBlogPost=function(blogPost){
		   BlogPostService.approveBlogPost(blogPost).then(
				   function(response){//when the blogpost approval status is updated successfully
					   //redirect the user to blogswaitingforapproval.html
					   $location.path('/blogswaitingforapproval')
				   },
				   function(response){
					   if(response.status==401)
						   $location.path('/login')
				   })
	    }	
			
	//function for rejecting a blogpost
       $scope.rejectBlogPost=function(blogPost){
    	   BlogPostService.rejectBlogPost(blogPost).then(
    			   function(response){
    				   $location.path('/blogswaitingforapproval')
    			   },
    			   function(response){
    				   if(response.status==401)
						   $location.path('/login')
    			   })
       }	  
       
       $scope.updateLikes=function(blogPostId){
    	   BlogPostService.updateLikes(blogPostId).then(
    		function(response){
    			$scope.isLiked=!$scope.isLiked
    			$scope.blogPost=response.data//response.data is blogPost object which has updated likes
    		},	   
    	    function(response){
    			if(response.status==401)
					   $location.path('/login')
    		}
    	   )
       }
       
       $scope.addBlogComment=function(commentTxt,blogPost){//when user clicks button POST COMMENT
    	   //CREATE A BLOGCOMMENT OBJECT AND SET THE VALUE FOR THESE TWO PROPERTIES
    	   $scope.blogComment={}
    	   $scope.blogComment.commentTxt=commentTxt
    	   $scope.blogComment.blogPost=blogPost
    	   console.log($scope.blogComment)
    	   BlogPostService.addBlogComment($scope.blogComment).then(
    			   function(response){
    				   $scope.commentTxt=''  //clear the textarea after posting the comment
    				   $scope.blogComment=response.data //values for all properties
    			   },
    			   function(response){
    				   if(response.status==401)
    					   $location.path('/login')
    			   })
       }
       
       $scope.getAllBlogComments=function(blogPostId){//when user clicks 'show comments'
    	   BlogPostService.getAllBlogComments(blogPostId).then(
    			   function(response){
    				   $scope.blogComments=response.data //Array of blogcomments
    			   },
    			   function(response){
    				   if(response.status==401)
    					   $location.path('/login')
    			   })
       }
})




