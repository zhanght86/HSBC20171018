<%
//**************************************************************************************************
//Name：LCInsureAccontInput.jsp
//Function：帐户处理
//Author：yuejw
//Date: 2005-7-11   10:13
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//赔案号
%>
<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LCInsureAccont.js"></SCRIPT>
    <%@include file="LCInsureAccontInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!--保险账户分类表-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAccClass);"></TD>
            <TD class= titleImg>保险账户分类信息</TD>
        </TR>
    </table>
    <Div  id= "divLCInsureAccClass" style= "display: ''">
	    <!--保险账户分类表-->
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLCInsureAccClassGrid" ></span></td>
	        </tr>
	    </table>
  	</Div>

    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAccTrace);"></td>
            <td class=titleImg> 保险帐户表记价履历表 </td>
        </tr>
    </table>
    <Div id= "divLCInsureAccTrace" style= "display: ''">
	    <!--保险帐户表记价履历信息-->
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLCInsureAccTraceGrid" ></span></td>
	        </tr>
	    </table>
    </div>

    <%
    //隐藏区,保存信息用
    %>
    <input type=hidden id="fmtransact" name="fmtransact">
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
