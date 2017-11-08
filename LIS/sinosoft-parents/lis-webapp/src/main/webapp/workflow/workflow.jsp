<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%
	String action = request.getParameter("action");
	String flowId = request.getParameter("flowId");
	String version = request.getParameter("Version");
	String mainMissionId = request.getParameter("mainMissionId");
	GlobalInput _gi = (GlobalInput)session.getAttribute("GI");
%>
<!doctype html>
<html>
<head>
<meta charset="GBK">
<title>WorkFlow</title>
<style type="text/css">
button.cssButton{
	margin-left:5px; 
	border:none;
    FONT-SIZE: 14px;
	font-family:"????";
    CURSOR: hand;
    COLOR: #FFF;
    background:url(../images/2432_04.png) repeat-x #1faeff; 
	border-radius: 3px;
	padding:2; 
	height:27px; 
	line-height:20px; 
	margin-top:10px; 
	margin-bottom:10px;
	margin-right:10px; 
	padding:0 10px;
	-webkit-box-shadow: 0px 2px 0px #0e73b6, 0px 4px 4px #b5b5b5;
	-moz-box-shadow: 0px 2px 0px #0e73b6, 0px 4px 4px #b5b5b5;
	box-shadow: 0px 2px 0px #0e73b6, 0px 4px 4px #b5b5b5;
	}
body{overflow: hidden;  -moz-user-select: none; }
#divTip {
	display: block;
	position: absolute;
	height: 100%;
	width: 100%;
	padding-top: 10%;
	z-index: 1001;
}
.bg{
	padding:6px 10px;
	background:#ededed;
	border-radius:8px;
	margin-right:20px;
	display:inline-block;
}
.bd{
	border-right:1px solid #1faeff;
	display:inline-block;
	margin-right:20px;
}
</style> 
</head>
<body onload="init(event)">
	<div id="buttondiv">
	    <div class="bd">
	    <div class="bg" style="padding:6px 10px;background:#ededed;border-radius:8px;margin-right:20px;display:inline-block;">
			<button id="create" class="cssButton" onClick="create(event)">新建</button>&nbsp;
			<button id="save" class="cssButton" onClick="save(event)">保存</button>&nbsp;
			<button id="empty" class="cssButton" onClick="empty(event)">清空</button>&nbsp;
		</div>
		</div>
		<div class="bd">
		<div class="bg">
			<button id="start" class="cssButton" onClick="add(event,'start')">开始</button>&nbsp;
			<button id="node" class="cssButton" onClick="add(event,'')">过程</button>&nbsp;
			<button id="end" class="cssButton" onClick="add(event,'end')">结束</button>&nbsp;
			<button id="link" class="cssButton" onClick="link(event)">联线</button>&nbsp;
			<button id="move" class="cssButton" onClick="move(event)">移动</button>&nbsp;
		</div>
		</div>
		<div class="bd">
		<div class="bg">
			<button id="prop" class="cssButton" onClick="property(event)">属性</button>&nbsp;
			<button id="del" class="cssButton" onClick="del(event)">删除</button>&nbsp;
		</div>
		</div>
		<div class="bg">
			<button id="write" class="cssButton" onClick="exp(event)">导出图片</button>&nbsp;
			<button id="read" class="cssButton" onClick="imp(event)">导入XML</button><input type="file" id="file" style="display:none"/>
		</div>
		<br /><br />
	</div>
	<div id="treediv" style="height: 600px; width: 200px; float: left;">
		<iframe id="tree" src="./wftree.jsp?height=580" width="200" height="600"></iframe>
	</div>
	<div id="svgdiv" style="height: 600px; width: 960px; float: left;">
		<iframe id="svg" src="wf.svg" width="960" height="600"></iframe>
	</div>
	<div id="divTip"  style="border:0px;text-align:center;padding-top:80px;display:none;cursor:Default">
	   <table style="width:50%;margin:auto;border:0;cellpadding:3;cellspacing:1;background: #ff7300; position:static;filter:progid:DXImageTransform.Microsoft.DropShadow(color=#666666,offX=4,offY=4,positives=true)">
	     <tr>
	       <td align="left"><font color="#FFFFFF">温馨提示：</font></td>
	       <td align="right"><span onClick="document.getElementById('divTip').style.display='none'" title="关闭" style="cursor:pointer">关闭</span></td>
	     </tr>
	     <tr>
	       <td colspan="2" width="100%" bgcolor="#FFFFFF" height="150" align="center"><span id="Tip"></span></td>
	     </tr>
	   </table>
	</div>
	<div id="divHis"  style="display: none; position:absolute; slategray;cursor:Default" >
		<table style="border:0;cellpadding:3;cellspacing:1;background: #ff7300; position:static;filter:progid:DXImageTransform.Microsoft.DropShadow(color=#666666,offX=4,offY=4,positives=true)">
			<tr>
				<td><font color="#FFFFFF">操作轨迹：</font></td>
				<td align="right"><span class="navPoint" onClick="document.getElementById('divHis').style.display='none'" title="关闭" style="cursor:pointer">关闭</span></td>
			</tr>
			<tr>
				<td colspan="2" width="100%" bgcolor="#FFFFFF" align="center"><span id="His"></span></td>
			</tr>
		</table>
	</div> 
	<script src="./canvg.js"></script>
	<script src="./canvas2image.js"></script>
	<script src="./workflow.js"></script>
<script>
var action = '<%=action%>';
var flowId="<%=flowId%>";
var flowVersion="<%=version%>";
var Operator = '<%=_gi.Operator %>';
var ManageCom = '<%=_gi.ManageCom %>';
var mainMissionId = '<%=mainMissionId%>';
</script>
</body>
</html>
