<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDTestDeployInput.jsp
 //程序功能：产品测试与发布
 //创建日期：2009-3-18
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
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
    <td class="titleImg" >请输入测试中险种代码：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class="common" name="RiskCode" >
		</td>
		<td class=title5>申请日期</td>
		<TD class=input><input class="coolDatePicker" dateFormat="short"
			name="RequDate" readonly id="RequDate"
			onClick="laydate({elem:'#RequDate'});"><span class="icon"><a
			onClick="laydate({elem: '#RequDate'});"><img
			src="../common/laydate/skins/default/icon.png" /></a></span></TD>
	</tr>
</table><input value="查  询" type=button  onclick="query()" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >测试中的产品：</td>
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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<input value="记事本" onclick="button406( )" class="cssButton" type="hidden" >
<!--  <input value="问题件回复/查询" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input value="测试问题件录入" type=button  onclick="IssueInput( )" class="cssButton" type="button" >-->
<br><br>
<div style="display: none">
<table>
  <tr>
    <td class="titleImg" >测试要素</td>
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
<INPUT CLASS=cssbutton VALUE="保  存" TYPE=button onclick="saveTestState();"> 
</div>
<div style="display:none;">
	<table>
	  <tr>
	    <td class="titleImg" >选择发布的平台</td>
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
发布平台
	      </td>
	      <!-- 
	       <td class= input>
	       		<Input class="codeno" name=pd_release  verify="发布平台|NOTNUlL" 
	      			ondblclick="return showCodeList('pd_release',[this,pd_releasename],[0,1]);" 
	      			onkeyup="return showCodeListKey('pd_release',[this,pd_releasename],[0,1]);"
	      			onfocus="return selectpd_release(this.value)"><input class=codename 
	      			name=pd_releasename readonly="readonly"><font size=1 color='#ff0000'><b>*</b></font>
	       </td> 
	    
	      <td class= title>
	      		<div id="pd_noyStrdiv" style="display:none;">
	      		是否需要PD表数据
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
	       		<Input class="codeno" name=pd_release  verify="发布平台|NOTNUlL" 
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
		<td class=title5>发布原因：</td>
		<td>
			<textarea name='deployReason' rows="5" cols="80"></textarea>
		</td>
	</tr>
</table>
</BR></BR>
<input value="产品发布" type=button  onclick="btnDeploy( )" class="cssButton" type="button" >
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
