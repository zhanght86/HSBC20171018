<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--*******************************************************
* �������ƣ�Title.jsp
* �����ܣ�ϵͳ����ҳ��
* ��������ˣ������
* ����������ڣ�2002-08-21
* 				2004-11-3 16:58
*******************************************************-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<!-- ҳ����ʽ  -->
<link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
<script language="JavaScript">
function showHideFrame(){	
	try{
			//alert(parent.fraInterfaceSet.cols);
		if(parent.fraSet.rows== "0,50%,18,*,0"){
			parent.fraSet.rows = "0,*,18,0,0";
			changeImage.src = "../common/images/butCollapse.gif";
		}
		else if(parent.fraSet.rows == "0,*,18,0,0"){
			parent.fraSet.rows = "0,50%,18,*,0";
			changeImage.src = "../common/images/butExpand.gif";
		}
	}
	catch(re){}
}
</script>
</head>
<style type="text/css">
<!--
	body {font-size:9pt;FONT-WEIGHT: bold}
	td {font-size:9pt;FONT-WEIGHT: bold}
-->
</style>
<body text="#000000" leftmargin="0" topmargin="2" marginwidth="0" marginheight="2" bgcolor="#D7E1F6">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" align=center>
		<tr align="center" > 
			<td onclick="showHideFrame()">
				<img name=changeImage src="../common/images/butExpand.gif" >
        		<font style="FONT-SIZE: 9pt;CURSOR: default; COLOR: #000000">��Ļ�л� </font>
        	</td>
    	</tr>
	</table>
</body>
</html>