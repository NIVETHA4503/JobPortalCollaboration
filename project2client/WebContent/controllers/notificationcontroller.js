/**
 * NotificationCtrl
 *    /home,   NotificationCtrl function will get executed
 */
app.controller('NotificationCtrl',function($scope,$rootScope,NotificationService,$location,$routeParams){
	//Defining a function
	function getNotificationsNotViewed(){
	NotificationService.getNotificationsNotViewed().then(
			function(response){//in index.html , response.data (Array of notifications)
				$rootScope.notifications=response.data
				$rootScope.notificationCount=$rootScope.notifications.length
			},
			function(response){
				if(response.status==401)
					$location.path('/login')
			})
	}
	//function call -STATEMENT to get Array of notification not viewed by the logged in user
	getNotificationsNotViewed()
    if($routeParams.notificationId!=undefined){//The statements inside the if statement has to get executed,
    	//only if there is notificationid in the path
    	
    	//SELECT * from notification where notificationId=?
    	NotificationService.getNotification($routeParams.notificationId).then(
    			function(response){
    				$scope.notification=response.data//notificationdetails.html
    			},
    			function(response){
    				if(response.status==401)
    					$location.path('/login')
    			})
    			
    	//Update notification set viewed=true where notificationId=?
    	NotificationService.updateNotification($routeParams.notificationId).then(
    			function(response){
    				//getallnotificationsnotviewed, initialize updated notificationcount
    				//select * from notification where viewed=0 and userToBeNotified.email=?
    				//$rootScope.notifications,$rootScope.notificationCount will get updated.
    				getNotificationsNotViewed()
    			},
    			function(response){
    				if(response.status==401)
    					$location.path('/login')
    			})
    }
})




