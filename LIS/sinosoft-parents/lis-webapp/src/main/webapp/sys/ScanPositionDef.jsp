<html>
<%@ page language="java" contentType="text/html; charset=gbk" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	String subType  = request.getParameter("subType");
%>
<head>
	<title>Ӱ����涯����</title>
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
	
		<div id="ScanTree" region="west" title="Ӱ���Ԫ��" split="true">
			<ul id="st"></ul>
			<div id="mb" class="easyui-menu" style="width:120px;">
				<div onclick="searchPst()" iconCls="icon-search">�鿴�涯λ��</div>		
				<div onclick="intoAddObj()" iconCls="icon-add">����Ԫ��</div>
				<div onclick="intoEditObj()" iconCls="icon-save">�޸�Ԫ������</div>
				<div onclick="delObj()" iconCls="icon-remove">ɾ��Ԫ��</div>
			</div>
		</div>
		<div id="ScanPic" title="Ӱ���" region="center" split="true">
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
