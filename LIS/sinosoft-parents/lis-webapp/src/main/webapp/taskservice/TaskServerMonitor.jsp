
<%
	//�������ƣ�TaskService.jsp
	//�����ܣ�
	//�������ڣ�2004-12-15 
	//������  ��ZhangRong
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<script>
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="TaskServerMonitorInit.jsp"%>
<SCRIPT src="TaskServerMonitor.js"></SCRIPT>

</head>

<body onload="initForm();">
<form action="./TaskServerMonitorChk.jsp" method=post name=fm id=fm
	target="fraSubmit">
<div>
	 <table  class= common>
  		<TR class=common>
  			<td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"  OnClick= "showPage(this,asd);">
  		   <td class=titleImg>�����������������(�޸ĺ�,����������������ſ���Ч)</td>
  		  </td>
  	    </TR>
  	   </table>
</Div>
<Div id="asd" style="display: ''"><!--INPUT VALUE="�鿴������ϸ" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->

	
<table class=common>
	<TR>
	</TR>
	<TR>
		<TD text-align: left colSpan=1><span id="spanServerGrid">
		</span></TD>
	</TR>

</table></Div>
<INPUT VALUE="��������������������Ϣ" class=cssButton TYPE=button name=delbutton onclick="saveServerConfig();"><br>
<table class=common>
	<tr>
	����˵��:
	</tr>
	<tr>
		Debug:0-�������־,1-�����־
	</tr>
	<tr>
		InvalidTime:����ʧЧʱ��,����ת�ɱ��ݽڵ�ִ��
	</tr>
  <tr>
		RunType:0-���ڵ�����,1-�ֽڵ�����
	</tr>
	<tr>
		ServerType:tomcat,weblogic
	</tr>
</table>

<div>
	 <table  class= common>
  		<TR class=common>
  			<td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="xcc();" >
  		   <td class=titleImg>����ڵ��������(�޸ĺ�,����������������ſ���Ч)</td>
  		  </td>
  	    </TR>
  	   </table>
</div>
<Div id="xcc" style="display: ''"><!--INPUT VALUE="�鿴������ϸ" class=cssButton TYPE=button name=delbutton onclick="taskManage();"-->



<table class=common>
	<TR>
	</TR>
	<TR>
		<TD text-align: left colSpan=1><span id="spanTaskServerPlanGrid">
		</span></TD>
	</TR>
</table></Div>
<INPUT VALUE="ˢ�·���ڵ���Ϣ" class=cssButton TYPE=button name=delbutton onclick="refreshTask();">
<INPUT VALUE="�������ڵ�������Ϣ" class=cssButton TYPE=button name=delbutton onclick="saveTaskServerConfig();">

<table style="display:none" class=common>
	<TR>
	</TR>
	<TR>
		<TD text-align: left colSpan=1><span id="spanTaskServerConfigGrid">
		</span></TD>
	</TR>
</table>

 <input	type="hidden" name="fmAction" value="">
 <input	type="hidden" name="hiddenServerIP" value="">
 <input	type="hidden" name="hiddenServerPort" value="">
	</form>
	
<span id="spanCode" style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
