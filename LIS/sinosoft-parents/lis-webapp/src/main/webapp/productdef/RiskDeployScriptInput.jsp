<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>

<%
 //程序名称：PDRiskDefiInput.jsp
 //程序功能：产品基础信息录入
 //创建日期：2009-3-12
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
 GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 String operator=tGI.Operator;
%>

<head>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <SCRIPT src="RiskDeployScript.js"></SCRIPT>
 <%@include file="RiskDeployScriptInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();">
<form action="./RiskDeployScriptSave.jsp" method=post name=fm target="fraSubmit">
<table>
  <tr>
    <td class="titleImg" >发布查询信息：</td>


</table>
<table class=common>
	<tr class=common>
       <td class="title">险种编码</td>
 		<td class=input5><input class="common" name="RiskCode"></td>
		<td class="title">发布类型</td>
		<td class=input5>
			<input class="codeNo" name="DeployType" ondblclick="return showCodeList('pd_deploytype',[this,DeployTypeS],[0,1]);"
			onkeyup="return showCodeListKey('pd_deploytype',[this,DeployTypeS],[0,1]);"><input class="codename" name="DeployTypeS">
		</td>
		<td class="title">脚本名称</td>
 		<td class=input5>
 			<input class="common" name="DeployName">
 		</td>
  </tr>
  <tr class=common> 
         <td class="title">发布环境</td>
 		<td class=input5><input class="codeNo" name="DeployEnv"  
			ondblclick="return showCodeList('pd_release',[this,DeployEnvS],[0,1]);"
			onkeyup="return showCodeListKey('pd_release',[this,DeployEnvS],[0,1]);"><input
			class="codename" name="DeployEnvS"></td>
		<td class="title" >发布日期起期</td>
		<td>
			<nobr>
			<input class="multiDatePicker" dateFormat="short" name="DeployDateL">
			</nobr>
		</td>
		<td class="title" >发布日期止期</td>
		<td>
			<nobr>
			<input class="multiDatePicker" dateFormat="short" name="DeployDateR">
			</nobr>
		</td>
  </tr>
</table>

<BR>
<div style="float: right;text-align: right;">
<input value="查  询" type=button  onclick="query();" class="cssButton" type="button" >
<input value="重  置" type=reset   class="cssButton" >
</div>
<BR>
<table>
</table>
<table>
  <tr>
    <td class="titleImg" >发布信息清单：</td>


</table>
<div>
<table  class= common>
   <tr>
      <td>
     <span id="spanMullineGrid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
<BR/><BR/>
<div align=left>
<input value="脚本下载" type=button  onclick="save();" class="cssButton" type="button" >
<input value="脚本删除" type=button  onclick="del();" class="cssButton" type="button" >
</div>
<input type=hidden name="operator">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
