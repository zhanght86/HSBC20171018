<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDIssueQueryInput.jsp 
 //程序功能：问题件录入
 //创建日期：2009-4-3
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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <SCRIPT src="PDIssueQuery.js"></SCRIPT>
 <%@include file="PDIssueQueryInit.jsp"%>
 <%@include file="buttonshow.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="./PDIssueQuerySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >问题件查询条件：</td>
  </tr>
</table>
<TABLE  class="common" >
	<tr class="common">
		<td class="title5">产品险种代码</td>
		<td class="input5">
			<input class="codeno" name="RiskCode"  style="background: url(../common/images/select--bg_03.png) no-repeat right center;"  ondblclick="return showCodeList('pdriskdefing',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('pdriskdefing',[this,RiskCodeName],[0,1]);"><input class="codename" name="RiskCodeName" >
		</td>  
		<td class="title5">返回岗位</td>
		<td class="input5">
		<input class="codeno" name="BackPost" style="background: url(../common/images/select--bg_03.png) no-repeat right center;"  ondblclick="return showCodeList('pd_issuepost',[this,BackPostName1],[0,1]);" onkeyup="return showCodeListKey('pd_issuepost',[this,BackPostName1],[0,1]);" ><input class="codename" name="BackPostName1" >
		</td>  
	</tr>
	<tr class="common">
		<td class="title5">问题件内容</td>
		<td class="input5">
			<input class="common" name="IssueCont" >
		</td>  
		<td class="title5">问题件状态</td>
		<td class="input5">
			<input class="codeno" name="IssueState" style="background: url(../common/images/select--bg_03.png) no-repeat right center;"  ondblclick="return showCodeList('pd_issuestate',[this,IssueStateName],[0,1]);" onkeyup="return showCodeListKey('pd_issuestate',[this,IssueStateName],[0,1]);"><input class="codename" name="IssueStateName" >
		</td>  
</tr>
</TABLE>
<input value="查  询" type=button  onclick="QueryIssue( )" class="cssButton" type="button" >
<table>
  <tr>
    <td class="titleImg" >问题件列表</td>
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
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR>
<input value="下载附件" type=button  onclick="DownLoadFile( )" class="cssButton" type="button" >
<br>
<table class=common>
	<TR class=common>
		<TD class=title5>回复内容</TD>
    <TD><textarea rows="3" cols="90" name="ReplyContDesc" elementtype=nacessary verify="问题件回复内容|notnull"></textarea>
    </TD>
	</TR>   
</table>
<input value="回  复" id="Replybutton" type=button  onclick="ReplyIssue( )" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="button55( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="SerialNo">
<input type=hidden name="FindFlag">
<input type=hidden name="hiddenRiskCode">
<input type=hidden name="hiddenBackPost">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
