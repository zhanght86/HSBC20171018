<html> 
<% 
//程序名称：CashValuePrintInput.jsp
//程序功能：现金价值表打印 
//创建日期：2005-09-27 16:49:22
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>    

<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  
  <SCRIPT src="./CashValuePrint.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="CashValuePrintInit.jsp"%>
  
  
</head>
<body  onload="initForm();" >
  <form action="./CashValuePrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <br>
  <div class="maxbox1" >    
  <TABLE class=common>
    <TR  class= common> 
      <TD class = title5 > 保单号 </TD>
      <td class= input5><Input class="common wid" name=ContNo id=ContNo></td>
	  <TD  class= title5>  </TD>
	  <TD  class= input5></TD>
    </TR>
  </TABLE> 
  </div>
  <BR>
  
   <!--<INPUT class=cssButton VALUE="打印现金价值表"  TYPE=button onClick="printCash();">
   <INPUT class=cssButton VALUE="   保单查询   "  TYPE=button onClick="contQuery();">-->
   <a href="javascript:void(0);" class="button"onClick="printCash();">打印现金价值表</a>
<a href="javascript:void(0);" class="button"onClick="contQuery();">保单查询 </a>

   
       

	<input type=hidden id="fmtransact" name="fmtransact">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
