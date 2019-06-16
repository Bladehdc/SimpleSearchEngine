<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	long times =  Long.parseLong(request.getAttribute("times").toString());
	int totalnum =  Integer.parseInt(request.getAttribute("totalnum").toString());
	int totalpage = Integer.parseInt(request.getAttribute("totalpage").toString());;
	int pageid =  Integer.parseInt(request.getAttribute("page").toString());
	String[] Allresult=(String[]) request.getAttribute("result");
	String query=(String) request.getAttribute("search");
%>
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="/css/result.css"></link>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>搜索结果</title>
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	</head>
	<body>
		<div id="head" class="header">
			<div class="mysearch">
				<p class="imgs">
				</p>
				<form id="searchform" name="searchForm" action="search" method="post">
					<input class="searchinput" id="input" autocomplete="off" name="searchString">
					<div class="addon" id="inputblock">
						<ul>
							<li>123</li>
						</ul>
					</div>
			 		<input type="submit" class="searchbutton">
			 	</form>
			</div>
		</div>
		<div class="showresult">
			<p>为您找到<%=totalnum%>个结果,耗时<%=times%>毫秒</p>
		</div>
		<%if(totalnum == 0){%>
            <p class="sorry">很抱歉，没有<%=query %>的查询结果。</p>
        <%} else {%>
   	 		<% 
   	 			int temp = (totalnum>10)? 10 : totalnum;
   	 			for(int i=0;i<temp;i++){
	 				out.println(Allresult[i]);
   	 			}
   	 		%>
		<%}%>
		<%if(totalnum > 10){%>
			<div class="pages">
				<% if(pageid > 1){%><a class="asr"><span class="pc nextpage">上一页</span></a><%} %>
				<% 
					for(int i=0;i<10;i++){
						if(pageid == i+1){
							out.println("<a class=\"asr\"><span class=\"strong\">"+(i+1)+"</span></a>");
						}else{
							out.println("<a href=\"search?page="+ (i+1) +"\" class=\"asr\"><span class=\"pc\">"+(i+1)+"</span></a>");
						}
					}
				%>
				<% if(pageid < totalpage){%><a class="asr"><span class="pc nextpage">下一页</span></a><%} %>
			</div>
		<%} %>
		<div class="bottom"></div>
		<script>
			var t = document.getElementById("inputblock");
			document.getElementById("input").addEventListener("input",function(e){
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
			document.getElementById("input").addEventListener("click",function(e){
				t.style.display = "none";
			})
		</script>
	</body>
</html>