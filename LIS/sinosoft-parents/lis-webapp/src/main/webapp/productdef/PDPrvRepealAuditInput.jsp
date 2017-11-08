<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDPrvRepealAuditInput.jsp
 //程序功能：条款废止审核
 //创建日期：
 //创建人  ：  caosg
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
 
 <SCRIPT src="PDPrvRepealAudit.js"></SCRIPT>
 <%@include file="PDPrvRepealAuditInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PD_LMDutyGetClmCalSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >请输入待审核险种代码：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>条款编码</td>
		<td class=input5>
			<input class="codeno" name="RiskCode" ondblclick="return showCodeList('pdriskaudit',[this,RiskName],[0,1]);" onkeyup="return showCodeListKey('pdriskaudit',[this,RiskName],[0,1]);" ><input class="codename" name="RiskName" readonly="readonly" >
		</td>
		<td class=title5>申请日期</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" >
		</td>	
	</tr>
</table>
<input value="查询" type=button  onclick="queryAudit()" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >待审核的条款：</td>
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
<input value="开始审核" type=button  onclick="beginAudit( )" class="cssButton" type="button" >
<br><br>


<input type=hidden name="operator">
<input type=hidden name="tableName" value="">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
