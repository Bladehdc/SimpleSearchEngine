<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/css/mainpage.css"></link>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>搜索</title>
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	</head>
	<body>
		<div class="wrapper">
			<div class="mylogo">
				<img src="/image/7.png">
			</div>
			<form id="searchform" name="searchForm" action="search" method="post">
				<div class="mysearch">
					<input class="searchinput" id="inputblock" autocomplete="off" name="searchString">
					<input type="submit" class="searchbutton">
				</div>
				<!-- 搜索提示 -->
				<div class="engine-list" id="adlist">
					<ul>
						<li>123</li>
						<li>123</li>
						<li>123</li>
						<li>123</li>
					</ul>
				</div>
			</form>
			<div class="info">
				假的搜索引擎。
			</div>
		</div>
		<script>
			var t = document.getElementById("adlist");
			document.getElementById("inputblock").addEventListener("input",function(e){
				$.ajax({
					type: 'post',
					url: 'search',
					contentType: 'application/x-www-form-urlencoded; charset=utf-8',
					data: "type=s",
					success: function (data) {
						console.log(data);
						t.style.display = "block";
			        }, error: function (data) {
			        	console.log("补全失败！");
			    	}
			    })
			})
			document.getElementById("inputblock").addEventListener("click",function(e){
				t.style.display = "none";
			})
		</script>
	</body>
</html>