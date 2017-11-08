<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDClaimDefiInput.jsp
 //程序功能：责任给付定义界面
 //创建日期：2009-3-16
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
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
 <SCRIPT src="PDClaimDefi.js"></SCRIPT>
 <%@include file="PDClaimDefiInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDClaimDefiSave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >责任给付属性定义</td>
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
<input value="进入算法定义" type=button onclick="button224( )" class="cssButton" type="button" >
<input value="责任给付生存" type=button id="btnClmAlive" name="btnGetAlive" onclick="button225( )" class="cssButton" type="button" >
<input value="责任给付赔付" type=button  onclick="button226( )" class="cssButton" type="button" >
<br><br>
<!--input value="赔付控制定义" type=button  onclick="button227( )" class="cssButton" type="button" >
<input value="赔付费用定义" type=button  onclick="button228( )" class="cssButton" type="button" >
<input value="赔付时期定义" type=button  onclick="button230( )" class="cssButton" type="button" >
<br><br>
<input value="给付账户定义" type=button  onclick="button231( )" class="cssButton" type="button" >
<input value="特殊赔付定义" type=button  onclick="button232( )" class="cssButton" type="button" >
<br><br-->
<input value="返回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>
<input type=hidden name="RiskCode">
<input type=hidden name="GetDutyCode">
<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMDutyGet">
<input type=hidden name=IsReadOnly>
<input type=hidden name=PageNo value=131>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
