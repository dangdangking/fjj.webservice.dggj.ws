/*(function(){
    angular.module("mainApp",['ngRoute', 'infinite-scroll', 'ngSanitize', 'monospaced.elastic','ui.grid']);
})();*/

var app = angular.module('mainApp', ['oitozero.ngSweetAlert']);
app.config(function($httpProvider){
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
    $httpProvider.defaults.transformRequest = function(obj) {
        var str = [];
        for(var p in obj)
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        return str.join("&");
    };
});
app.controller('mainCtrl',function($scope, $http, SweetAlert,$interval) {
	$scope.data = {};
	$scope.data.lasttime = "";
	$scope.data.freshinterval = 10;
	$scope.init = function(){
		$("#disalertdiv").alert('close');
	}
	//$scope.init();
	$scope.getCurrentTime = function(){
		var dt = new Date();
		var h = dt.getHours();
		var m = dt.getMinutes();
		var s = dt.getSeconds();
		$scope.data.freshtime = h + ":" + m + ":" + s;
	}
	$scope.getCurrentTime();
	
	
	
	$scope.loadData = function() {
		$http({
            method: "post",
            url: "/FjjService/servlet/manage/query"
        })
        .success(function(revObj) {
            if (revObj.code != '00000') {
            	SweetAlert.swal(revObj.msg,"","error");
            }
            $scope.data.tablelist = revObj.data;
        })
        .error(function(revObj) {
            alert(JSON.stringify(revObj));
        });
	};
	
	$scope.addinterval = function(){
		$scope.data.freshinterval = $scope.data.freshinterval + 10;
	}
	$scope.minerinterval = function(){
		if($scope.data.freshinterval > 10)	$scope.data.freshinterval = $scope.data.freshinterval - 10;
	}
	
	$scope.cleanData = function() {
		SweetAlert.swal({
			  title: "你确定要执行清理吗?",
			  text: "本次清理将会清理： 封发邮件信息表，扫描登记表!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "是的，清理!",
			  closeOnConfirm: false
			},
			function(){
				$http({
		            method: "post",
		            url: "/FjjService/servlet/manage/cleanData"
		        })
		        .success(function(revObj) {
		            if (revObj.code != '00000') {
		            	SweetAlert.swal(revObj.msg,"","error");
		            }
		            SweetAlert.swal("清理成功！","su","info");
		        })
		        .error(function(revObj) {
		            alert(JSON.stringify(revObj));
		        });
			});
	};
	
	$scope.cleanHis = function() {
		SweetAlert.swal({
			  title: "你确定要执行清理吗?",
			  text: "本次清理将会清理所有历史表!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "是的，清理!",
			  closeOnConfirm: false
			},
			function(){
				$http({
		            method: "post",
		            url: "/FjjService/servlet/manage/cleanHis"
		        })
		        .success(function(revObj) {
		            if (revObj.code != '00000') {
		            	SweetAlert.swal(revObj.msg,"","error");
		            }
		            SweetAlert.swal("清理成功！","su","info");
		        })
		        .error(function(revObj) {
		            alert(JSON.stringify(revObj));
		        });
			});
		
	};
	
	$scope.doOne = function(m) {
		params = {
    			method:m
    		}
		$http({
            method: "post",
            url: "/datatool/manage/doOne",
            data:params
        })
        .success(function(revObj) {
            if (revObj.code != '00000') {
            	SweetAlert.swal(revObj.msg,"","error");
            }
            $scope.data.tablelist = revObj.data;
        })
        .error(function(revObj) {
            alert(JSON.stringify(revObj));
        });
	};
	
	$scope.loadData();
	//
	$interval(function(){
		if($scope.data.lasttime!="")$scope.data.lasttime --;
        //$("#alertdiv").alert();
        if($scope.data.lasttime <= 0){
        	$scope.data.lasttime = "";
        	$scope.loadData();
        	$scope.getCurrentTime();
    		$scope.data.lasttime = $scope.data.freshinterval;
        }
    },1000);
	
} );

