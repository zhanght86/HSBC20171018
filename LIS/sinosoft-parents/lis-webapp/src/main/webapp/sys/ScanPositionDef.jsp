<html>
<%@ page language="java" contentType="text/html; charset=gbk" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String subType  = request.getParameter("subType");
%>
<head>
	<title>影像件随动定制</title>
	<META   HTTP-EQUIV="pragma"   CONTENT="no-cache">        
 	<META   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache,   must-revalidate">        
	<META   HTTP-EQUIV="expires"   CONTENT="0"> 
  <script src="../common/javascript/jquery.js"></script>
	<script src="../common/javascript/jquery.easyui.min.js"></script>
	<script src="../common/javascript/jquery.Jcrop.min.js"></script>
	<script src="../common/javascript/ajaxupload.3.6.js"></script>
	<script src="ScanPositionDef.js"></script>
	
	<script language="javascript">
			var subType = "<%=subType%>"; 
	</script>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
	<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
</head>

<body>
	
		<div id="ScanTree" region="west" title="影像件元素" split="true">
			<ul id="st"></ul>
			<div id="mb" class="easyui-menu" style="width:120px;">
				<div onclick="searchPst()" iconCls="icon-search">查看随动位置</div>		
				<div onclick="intoAddObj()" iconCls="icon-add">增加元素</div>
				<div onclick="intoEditObj()" iconCls="icon-save">修改元素名称</div>
				<div onclick="delObj()" iconCls="icon-remove">删除元素</div>
			</div>
		</div>
		<div id="ScanPic" title="影像件" region="center" split="true">
			<div id="PicTabs" class="easyui-tabs" fit="true" border="false">
			</div>
		</div>

<input type=hidden  id="x" name="x" />
<input type=hidden  id="y" name="y" />
<input type=hidden  id="x2" name="x2" />
<input type=hidden  id="y2" name="y2" />
<input type=hidden  id="w" name="w" value="0"/>
<input type=hidden  id="h" name="h" value="0"/>
</body>
</html>
