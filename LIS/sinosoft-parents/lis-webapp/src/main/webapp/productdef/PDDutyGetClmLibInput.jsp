<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDDutyGetClmLibInput.jsp
 //程序功能：给付赔付库
 //创建日期：2009-3-17
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
  <SCRIPT src="DictionaryUtilities.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDDutyGetClmLib.js"></SCRIPT>
 <%@include file="PDDutyGetClmLibInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyGetClmLibSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >给付赔付库查询条件：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>
赔付库代码
		</td>
		<td class=input5>
			<input class=common name="GetDutyCode2">
		</td>
		<td class=title5>
赔付库名称
		</td>
		<td class=input5>
			<input class=common name="GetDutyName">
		</td>
		<td class=title5>
赔付库责任类型
		</td>
		<td class=input5>
			<input class="code" name="GetDutyKind2" ondblclick="return showCodeList('llgetdutykind',[this],[0]);" onkeyup="return showCodeListKey('llgetdutykind',[this],[0]);" >
		</td>
	</tr>
	<tr class=common>
		<td class=title5>
关联险种	
		</td>
		<td class=input5>
			<input class="code" name="RiskCode" ondblclick="return showCodeList('pdriskdeployed',[this],[0]);" onkeyup="return showCodeListKey('pdriskdeployed',[this],[0]);" >
		</td>
		<td class=title5>
关联给付赔付		
		</td>
		<td class=input5>
			<input class="code" name="GetDutyCode" ondblclick="return showCodeList('pdGetDutyCode',[this],[0],null,fm.RiskCode.value,'riskcode');" onkeyup="return showCodeListKey('pdGetDutyCode',[this],[0],null,fm.RiskCode.value,'riskcode');" >
		</td>
		<td class=title5>
关联赔付责任类型
		</td>
		<td class=input5>
			<input class="code" name="GetDutyKind" ondblclick="return showCodeList('llgetdutykind',[this],[0]);" onkeyup="return showCodeListKey('llgetdutykind',[this],[0]);" >
		</td>
	</tr>
</table>
<input value="查询" type=button  onclick="query()" class="cssButton" type="button" >
<input value="返回" type=button name=btnReturn onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<table>
  <tr>
    <td class="titleImg" >给付赔付查询结果：</td>
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
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline11GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline11GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline11GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline11GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >给付赔付关联结果：</td>
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
<INPUT CLASS=cssbutton VALUE="首页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<table>
  <tr>
    <td class="titleImg" >给付赔付库属性定义:</TD><TD width=400></TD><TD><input value="保存" type=button name=btnSave onclick="save()" class="cssButton" type="button" ><input name=btnEdit value="修改" type=button  onclick="update()" class="cssButton" type="button" ><input name=btnDele value="删除" type=button  onclick="del()" class="cssButton" type="button" ></td>
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

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMDutyGetClm_Lib">
<input type=hidden name="GetDutyCode2RowOfMulLine">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
