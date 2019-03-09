/**
 * 
 */
app.controller('JobCtrl',function($scope,JobService,$location){
$scope.show=false;
	
	$scope.addJob=function(){
		
		JobService.addJob($scope.job).then(
		function(response){
			alert('Job details inserted successfully...')
			
			$scope.job={}
			$location.path('/getalljobs')
		},function(response){ 
			
		console.log(response.status)
			if(response.status==400)
				$scope.message="CLIENT ERROR... BAD REQUEST"
				else
			$scope.error=response.data
		})
	}
	
	
	JobService.getAllJobs().then(
			function(response){
				$scope.jobs=response.data
				console.log(response.status)
			},function(response){
				console.log(response.status)
			})
			$scope.showDetails=function(jobID){
//		if($scope.show==false)
//			$scope.show=true;
//		else
//		
//		$scope.show=false;
		$scope.show=!$scope.show
		$scope.jobID=jobID
	}
})



