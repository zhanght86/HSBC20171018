<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDClaimDefiEntryInput.jsp
 //程序功能：理赔信息录入界面
 //创建日期：2009-3-16
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
  GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 String mRiskcode=request.getParameter("riskcode");
 String tPdFlag=request.getParameter("pdflag");
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDClaimDefiEntry.js"></SCRIPT>
 <%@include file="PDClaimDefiEntryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >产品基础信息：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class="common" name="RiskCode" readonly="readonly" id=riskcode>
		</td>
		<td class=title5>申请日期</td>
		<td class=input5>
			<input class="common" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<input class=cssbutton type=button onclick="QueryBaseInfo()" value="查看基础信息">
<br>
<table>
  <tr>
    <td class="titleImg" >产品给付责任信息:</td>
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
<input value="责任赔付定义" type=button  onclick="button194( )" class="cssButton" type="button" >
<input value="累加器配置定义" type=button id=lcalculatorid onclick="button199( )" class="cssButton" type="button" >
</BR></BR>
<input value="问题件回复/查询" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input id=savabutton1 value="问题件录入" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<!--input value="记事本" type=button  onclick="button197( )" class="cssButton" type="button" -->
<br><br>
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<input id=savabutton2 value="理赔信息录入完毕" type=button  onclick="btnClaimDone( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMRisk">
<input type=hidden name=PdFlag id=PdFlag>
<input type=hidden name=MissionID id=MissionID>
<input type=hidden name=SubMissionID id=SubMissionID>
<input type=hidden name=ActivityID id=ActivityID>
<input type=hidden name=IsReadOnly id=IsReadOnly>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
