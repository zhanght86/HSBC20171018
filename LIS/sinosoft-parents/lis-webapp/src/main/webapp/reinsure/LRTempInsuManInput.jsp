<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�LRTempInsuManInput.jsp
//�����ܣ������ٷֹ���
//�������ڣ�2007-10-09 11:10:36
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src="LRTempInsuManInput.js"></SCRIPT>
<%@include file="LRTempInsuManInit.jsp"%>
<title>�ٱ��ظ�</title>
</head>
<body  onload="initElementtype();initForm();" >
<form method=post name=fm target="fraSubmit" action= "" >
<%@include file="../common/jsp/InputButton.jsp"%>
	
	<table id= "divIndInfo" style= "display: ''">
		<tr>
	    	<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpTempInsuList);">
			</td>
			<td class= titleImg>�ٷ������б�</td>
		</tr>
	</table>
	<Div  id= "divGrpTempInsuList" style= "display: ''">
		<Table  class= common>
			<TR  class= common8>
				<TD  class= title58>
				<input type="radio" name="StateRadio"  value="1" onclick="" checked >�ٷִ���
			</TR>
		</Table>
  </Div> 
  
  <!--���ձ�����Ϣ-->
  <Div  id='divIndTempInsuList' style= "display: ''">
  	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndTempListGrid);">
				</td>
				<td class= titleImg>
���ձ�����Ϣ
				</td>
			</tr>
  	</table>
  	<Div  id='divIndTempToalListGrid' style= "display: ''">
  		<table  class= common>
  		 	<tr  class= common>
  		 		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndTempToalListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
		</Div>
	</Div>
	<hr><br>
	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIndTempListGrid);">
				</td>
				<td class= titleImg>
���ձ�����ϸ��Ϣ
				</td>
			</tr>
  	</table>
  	<Div  id='divIndTempListGrid' style= "display: ''">
  		<table  class= common>
  		 	<tr  class= common>
  		 		<td style="text-align:left;" colSpan=1 >
						<span id="spanIndTempListGrid" >
						</span> 
					</td> 
				</tr> 
			</table> 
		</Div>
	</Div>
	
  <Div  id='divButton2' style= "display: ''">
  	<Table class= common>
  		<TR>
  			<TD class= title5>
  			</TD>
  			<TD class="title5">
  			</TD>
  			<td class="input5"></td>
  		</TR>
  	</Table>
  	<hr><br>
  	<Div  id= "divButton3" style= "display: ''"> 
  		<INPUT VALUE="�� ʱ �� ��" class=cssButton TYPE=button onclick="TempCessButton();"> 
  		&nbsp;&nbsp;
  		<INPUT VALUE="�� �� �� ��" class=cssButton TYPE=button onclick="AuditEnd();"> 
  	</Div>
  	</Div>
	</Div>
  <br>
  <Div  id= "divTempConlusionList" style= "display:none;"> 
  	<table>
			<tr>
		    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRelaList);">
				</td>
				<td class= titleImg>
�ٷֽ����б�
				</td>
			</tr>
  	</table>
	<Div id="divText1" style="display:none;">
		OpeFlag			<input type="text" name="OpeFlag">
		ContType		<input type="text" name="ContType">
		DeTailFlag	<input type="text" name="DeTailFlag">
	</Div>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
