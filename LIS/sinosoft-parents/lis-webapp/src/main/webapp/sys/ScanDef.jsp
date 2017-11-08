
<html>
<head>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
<link rel="stylesheet" href="../common/css/jquery.Jcrop.css" type="text/css" />
<script src="../common/javascript/jquery.js"></script>
<script src="../common/javascript/jquery.easyui.min.js"></script>
<script src="../common/javascript/jquery.Jcrop.min.js"></script>
<script src="../common/javascript/Signature.js"></script>
<script src="../common/javascript/jquery.rotate.js"></script>
<script src="../common/javascript/ajaxupload.3.6.js"></script>
<SCRIPT src="ScanDef.js"></SCRIPT>
<SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>

</head>

<%
String SubType    = request.getParameter("SubType");
%>
<script>
	var arrPicName = new Array();
	var subType =  "<%=SubType%>"; 
	var params = {
		oper:'2',
		subType:subType
	}
  $.post(
  	"ScanPositionDefInit.jsp?sid="+getTimeForURL(),
  	params,
  	function(data) {
				$.each(data, function(i, n) {
					 var picPath = data[i].picPath;
					 arrPicName.push(picPath);
				}); 
				initEasyScanPage();
				
					
  	},"json" 
	);
	

	
	
	
  
</script>

<body  class="easyui-layout">
<div id="PicView" region="center" title="Ó°Ïñ¼þ" split="false">
	<div id="PicTab" class="easyui-tabs" fit="true" border="false">
	</div>
</div>

	<form name=fm>
		<input type=hidden  id="x" name="x" />
		<input type=hidden  id="y" name="y" />
		<input type=hidden  id="x2" name="x2" />
		<input type=hidden  id="y2" name="y2" />
		<input type=hidden  id="w" name="w" value="0"/>
		<input type=hidden  id="h" name="h" value="0"/>
		<input type=hidden  id="DocCode" name="DocCode" />
	</form>
</body>
</html>



</html>

