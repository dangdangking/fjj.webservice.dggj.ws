<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html ng-app="mainApp">
<head>
 <meta charset="UTF-8">
<meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>数据传输监控</title>
</head>
<script src="/FjjService/js/jquery-1.11.1.min.js"></script>
<script src="/FjjService/js/angular.min.js"></script>
<script src="/FjjService/js/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<link href="/FjjService/js/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="/FjjService/js/bootstrap-3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
<script src="/FjjService/js/sweet-alert/SweetAlert.min.js"></script>
<script src="/FjjService/js/sweet-alert/sweet-alert.min.js"></script>
<link rel="stylesheet" type="text/css" href="/FjjService/js/sweet-alert/sweet-alert.css"/>


<script src="/FjjService/jsp/fenleiapp.js"></script>
<body style="overflow-y:scroll;">
<div ng-controller="mainCtrl" style="margin-top : 40px;margin-left:50px;margin-right:50px">
	<div id="alertdiv" class="alert alert-success" >数据刷新成功<font size=8 color=blue>{{data.freshtime}}</font>。下次刷新还有...........<font size=10 color=purple>{{data.lasttime}}</font>秒</div>

	<div >
  每隔<button type="button" class="btn btn-default btn-xs" ng-click="minerinterval()" style="margin-left:3px">
  <span class="glyphicon  glyphicon-minus" aria-hidden="true"></span> 
</button>
<font size=5 color=purple>{{data.freshinterval}}</font>秒
<button type="button" class="btn btn-default btn-xs" ng-click="addinterval()">
  <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 
</button>
刷新一次
  </ul>

</div>


	
   <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true" >
   		<div class="panel panel-primary" ng-repeat=" tb in data.tablelist" ><!-- ng-repeat=" tb in data.tablelist" -->
   			<span class="panel-title" >
				<h3 style="margin-left:10px">{{tb.TABLEDESC}} <span class="label label-default">{{tb.TABLENAME}}</span></h3>
			    <span class="label label-primary">最后执行时间： {{tb.LASTACTDT}}</span>
			    <span class="label label-primary">暂停时间： {{tb.DELAYTIME}} 秒</span>
			    <button type="button" class="btn btn-info" ng-click="doOne(tb.DEALFUNCTION)" ng-hide="tb.DEALFUNCTION=='no'">立即执行</button></h3>
			</span>
			<div class="panel-body" style="font-size:24px">
			    <span class="label label-primary">总记录数：{{tb.TOTAL}}</span>
			    <span class="label label-success">已上传：{{tb.DONE}}</span>
			    <span class="label label-info">未上传：{{tb.TODO}}</span>
			    <span class="label label-danger">失败：{{tb.ERR}}</span>
			    <span class="label label-warning" ng-show="tb.CLEAN_FLAG=='1'">历史表记录数：{{tb.HISNUM}}</span>
			     
			</div>
   		</div>
   </div>
 		
</div>
</body>
</html>