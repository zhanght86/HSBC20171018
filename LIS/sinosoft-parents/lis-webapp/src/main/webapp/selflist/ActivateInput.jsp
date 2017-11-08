<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
	<%
//程序名称：ActivateInput.jsp
//程序功能：卡单激活功能
//创建日期：2008-03-05 
//创建人  ：张征
//更新记录：  更新人    更新日期     更新原因/内容
  GlobalInput tG = (GlobalInput)session.getValue("GI");
  String Branch =tG.ComCode;
  %>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ActivateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ActivateInit.jsp"%>
</head>
<body onload="initForm()">
  <form action="./ActivateSave.jsp" method=post name=fm id="fm" target="fraSubmit">
  	<Table class= common>
	     <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
        <td class="titleImg">自助卡单激活</td>
	     </tr>
	  </Table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
   	<Table class= common>
   		<TR class= common>
   			<TD class= title5>卡号</TD>
   			<TD class= input5><Input class= "common wid" name= CertifyNo id="CertifyNo"></TD>

        <TD class= title5>密码</TD>
        <TD class= input5><Input class= "common wid" type=password name= Password id="Password"></TD>
   		</TR>
    </Table>
  </Div>
  </div>
<a href="javascript:void(0)" name="ActivateOper" class=button onclick="submitForm();">激  活</a>
<!-- <input name="ActivateOper" class="cssButton" type="button" value="激  活" onclick="submitForm()"> -->
</form>
 <br> 
 <br> 
 <br> 
 <br> 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
