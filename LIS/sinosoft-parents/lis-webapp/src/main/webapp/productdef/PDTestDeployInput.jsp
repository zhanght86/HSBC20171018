<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //�������ƣ�PDTestDeployInput.jsp
 //�����ܣ���Ʒ�����뷢��
 //�������ڣ�2009-3-18
 //������  ��
 //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <script src="PDCommonJS.js"></script> 
 <SCRIPT src="PDTestDeploy.js"></SCRIPT>
 <%@include file="PDTestDeployInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDTestDeploySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >��������������ִ��룺</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>��Ʒ���ִ���</td>
		<td class=input5>
			<input class="common" name="RiskCode" >
		</td>
		<td class=title5>��������</td>
		<TD class=input><input class="coolDatePicker" dateFormat="short"
			name="RequDate" readonly id="RequDate"
			onClick="laydate({elem:'#RequDate'});"><span class="icon"><a
			onClick="laydate({elem: '#RequDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</tr>
</table><input value="��  ѯ" type=button  onclick="query()" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >�����еĲ�Ʒ��</td>
  </tr>
</table>



<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ҳ" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="��һҳ" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="β  ҳ" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<input value="���±�" onclick="button406( )" class="cssButton" type="hidden" >
<!--  <input value="������ظ�/��ѯ" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input value="���������¼��" type=button  onclick="IssueInput( )" class="cssButton" type="button" >-->
<br><br>
<div style="display: none">
<table>
  <tr>
    <td class="titleImg" >����Ҫ��</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline11Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="��  ��" TYPE=button onclick="saveTestState();"> 
</div>
<div style="display:none;">
	<table>
	  <tr>
	    <td class="titleImg" >ѡ�񷢲���ƽ̨</td>
	  </tr>
	</table>
	<table  class= common>
	   <tr  class= common>
	      <td style="text-align:left;" colSpan=1>
	     <span id="spanMulline10Grid" >
	     </span> 
	      </td>
	   </tr>
	</table>
</div>


<table  class= common>
	   <tr  class= common>
	      <td class= title>
����ƽ̨
	      </td>
	      <!-- 
	       <td class= input>
	       		<Input class="codeno" name=pd_release  verify="����ƽ̨|NOTNUlL" 
	      			ondblclick="return showCodeList('pd_release',[this,pd_releasename],[0,1]);" 
	      			onkeyup="return showCodeListKey('pd_release',[this,pd_releasename],[0,1]);"
	      			onfocus="return selectpd_release(this.value)"><input class=codename 
	      			name=pd_releasename readonly="readonly"><font size=1 color='#ff0000'><b>*</b></font>
	       </td> 
	    
	      <td class= title>
	      		<div id="pd_noyStrdiv" style="display:none;">
	      		�Ƿ���ҪPD������
	      		<div>
	      </td>
	      <td class= input>
	     		<div id="pd_noydiv" style="display:none;">
      	   		<Input class="codeno" name=pd_noy
      			ondblclick="return showCodeList('yesorno',[this,pd_noyname],[0,1]);" 
      			onkeyup="return showCodeListKey('yesorno',[this,pd_noyname],[0,1]);"><input class=codename 
      			name=pd_noyname readonly="readonly">
      			<div>
	      </td>
	      
	       -->
	       <td class= input>
	       		<Input class="codeno" name=pd_release  verify="����ƽ̨|NOTNUlL" 
	      			ondblclick="return showCodeList('pd_release',[this,fm.pd_releasename],[0,1]);" 
	      			onkeyup="return showCodeListKey('pd_release',[this,fm.pd_releasename],[0,1]);"
	      			onfocus="return showDeployReason()"><input class=codename 
	      			name=pd_releasename readonly="readonly"><font size=1 color='#ff0000'><b>*</b></font>
	       </td>  
	       <td class= title>
	       </td>
	       <td>
	       </td>  
	   </tr>
</table>



<table class = common style = '' id= 'deployReasonID'>
	<tr class = common>
		<td class=title5>����ԭ��</td>
		<td>
			<textarea name='deployReason' rows="5" cols="80"></textarea>
		</td>
	</tr>
</table>
</BR></BR>
<input value="��Ʒ����" type=button  onclick="btnDeploy( )" class="cssButton" type="button" >
<br><br>
<input type= hidden name ="pd_noy">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
<input type=hidden name=RiskName>
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name='ReleasePlatform'>
<input type=hidden name='SysType'>
<input type=hidden name='EnvType'>
<input type=hidden name='IsDeleteCoreDataBeforeInsert' value='1'>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
