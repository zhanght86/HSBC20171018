<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDIssueInputInput.jsp
 //程序功能：问题件录入
 //创建日期：2009-4-2
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

 <script src="PDCommonJS.js"></script>
 <SCRIPT src="PDIssueInput.js"></SCRIPT>
 <%@include file="PDIssueInputInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDIssueInputSave.jsp" method=post name=fm target="fraSubmit" >

<table>
  <tr>
    <td class="titleImg" >问题件信息录入：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>返回岗位</td>
		<td class=input5>
			<input class="codeno" name="BackPost"  ondblclick="return showCodeList('pd_issuepost',[this,BackPostName],[0,1]);" onkeyup="return showCodeListKey('pd_issuepost',[this,BackPostName],[0,1]);" ><input class="codename" name="BackPostName" elementtype=nacessary verify="返回岗位|notnull">
		</td>
	</tr>
</table>
<table class=common>
	<TR class=common>
		<TD class=title5>问题件内容</TD>
    <TD><textarea rows="3" cols="90" name="IssueContDesc" elementtype=nacessary verify="问题件内容|notnull"></textarea>
    </TD>
	</TR>   
</table>
<input type=hidden name="operator">
<input type=hidden name="PostCode">
<input type=hidden name="IssueType">
<input type=hidden name="Filepath2">
<input type=hidden name="Filename2">
<input type=hidden name="SerialNo">

<input type=hidden name="MissionID">
<input type=hidden name="SubMissionID">
<input type=hidden name="ActivityID">

<input type=hidden name="NeedTransferFlag">
<input type=hidden name="RequDate">

</form>
<form action="./UpLodeSave.jsp" method=post name=fmImport target="fraSubmit" ENCTYPE="multipart/form-data">
<div id="divDiskInput" style="display:''">
  <table class = common >    	
	  <TR>
	    <TD  width='10%' style="font:9pt" class= common>
选择导入的文件：
	    </TD>     
	    <TD  width='80%' class= common>
	      <INPUT type="file"　width="50%" name=FileName class= common >
	     	<INPUT VALUE="上载附件" type="button" class=cssButton onclick="Upload();">
	     	<INPUT VALUE="取消附件" type="button" class=cssButton onclick="CancleUpload();">
	    </TD>
	   </TR>
  </table>
</div>
<BR>
<input value="保存" type=button  onclick="ins( )" class="cssButton" type="button" >
<input value="删除" type=button  onclick="del()" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >已保存问题件</td>
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
<input value="确认" type=button  onclick="DealIssue( )" class="cssButton" type="button" >
<input value="返回" type=button  onclick="top.close()"  class="cssButton" type="button" >
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
