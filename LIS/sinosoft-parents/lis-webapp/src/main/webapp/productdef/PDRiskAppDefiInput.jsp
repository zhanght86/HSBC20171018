<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDRiskAppDefiInput.jsp
 //程序功能：险种承保定义
 //创建日期：2009-3-12
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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

 <SCRIPT src="PDRiskAppDefi.js"></SCRIPT>
 <%@include file="PDRiskAppDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDRiskAppDefiSave.jsp" method=post name=fm target="fraSubmit">

<table class=common>
	<TR class=common>
		<TD class=title5>险种编码</TD>
		<TD class=input5>
			<input class=common name=RiskCode readonly="readonly">
		</TD>
		<TD class=title5>申请日期</TD>
		<TD class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly">
		</TD>
	</TR>
</Table>		
<table>
  <tr>
    <td class="titleImg" width=85%>险种承保属性定义:</td>
    <td>
    	<div align=right>
    		<input value="保存" name=save type=button onclick="saveData()" class="cssButton" type="button" ><input value="修改" type=button  onclick="update()" class="cssButton" type="button" ><input value="删除" type=button  onclick="del()" class="cssButton" type="button" >
    	</div>
    </td>
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
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<input value="返回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRiskApp">
<input type=hidden name=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
