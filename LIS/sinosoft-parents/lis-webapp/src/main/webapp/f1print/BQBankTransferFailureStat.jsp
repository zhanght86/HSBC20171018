<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BQBankTransferFailureStat.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>保全收付费转账失败清单统计表</title>
</head>
<body >
  <form action="./BQBankTransferFailureStatF1PSave.jsp" method=post name=fm id=fm target="f1print">
    <table>
    	<tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>保全收付费转账失败清单统计</td>
    	</tr>
    </table>
      <Div  id= "divLCPol1" style= "display: ''" class="maxbox1">
    	<table  class= common>
      	  <TR  class= common>
            <TD  class= title5>管理机构</TD>
            <TD  class= input5>
              <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class='code' name=ManageCom id=ManageCom readonly verify="管理机构|NOTNULL" ondblclick="return showCodeList('station',[this],null,null,null,null,null,250);" 
              onclick="return showCodeList('station',[this],null,null,null,null,null,250);" 
            				onkeyup="return showCodeListKey('station',[this],null,null,null,null,null,250);">
            </TD>
             <TD  class= title5>开始日期</TD>
            <TD  class= input5><!--<Input class= "coolDatePicker" dateFormat="short" name=StartDate verify="起始日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
	  </TR>
          <TR  class= common>
           
            <TD  class= title5>结束日期</TD>
            <TD  class= input5><!--<Input class= "coolDatePicker" dateFormat="short" name=EndDate verify="结束日期|NOTNULL">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
            <TD  class= title5></TD>
             <TD  class= input5></TD> 
          </TR>
        </table>
	<br>
        <!--<INPUT VALUE="打印报表" class= common TYPE=button name=btnSubmit onclick="submitForm();"> -->
       
      </div> <a href="javascript:void(0);" name=btnSubmit class="button" onClick="submitForm();">打印报表</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
