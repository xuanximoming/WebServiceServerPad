<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String temperature = request.getParameter("temperature");
String highpressure = request.getParameter("highpressure");
String lowpressure = request.getParameter("lowpressure");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>图表展示</title>
<link href="../css/layout.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="../js/jquery.flot.js"></script>
<script type="text/javascript">
$(function(){
	//var temperature =  [ [ 1167692400000, 37.5 ],[ 1167692500000, 38.5 ],[ 1167692600000, 40.0 ] ];
	//var highpressure = [ [1167692400000, 130 ], [ 1167692500000, 110 ],[ 1167692600000, 115 ], [ 1167692700000, 117 ],[ 1167692800000, 120 ] ];
	//var lowpressure = [ [1167692400000, 80 ], [ 1167692500000, 70 ],[ 1167692600000, 75 ], [ 1167692700000, 80 ],[ 1167692800000, 80 ] ];

	var temperature = <%=temperature%>;
	var highpressure = <%=highpressure%>;
	var lowpressure = <%=lowpressure%>;
	data = [
	    {data:temperature,
	    	label:"体温",
			xaxis:1,
			yaxis:1},
		
		{data:highpressure,
			label:"高压",
			xaxis:1,
			yaxis:2},
		
		{data:lowpressure,
			label:"低压",
			xaxis:1,
			yaxis:2}
	];
	function doPlot(position){
		$.plot($("#placeholder"),
			data,
			{
				xaxes:[{mode:'time',timeformat:'%y/%m/%d %H:%M'}],
				yaxes:[
				       {},
				       {
						alignTicksWithAxis : position == "right" ? 1 : null,
						position : position
				       }],
				legend:{position:'sw'}
			}
		);
	}
	doPlot("right");
});
</script>
</head>
<body>
<div id="placeholder" style="width: 600px; height: 300px;"></div>
</body>
</html>