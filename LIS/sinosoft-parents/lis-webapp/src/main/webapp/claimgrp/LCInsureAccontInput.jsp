<%
//**************************************************************************************************
//Name��LCInsureAccontInput.jsp
//Function���ʻ�����
//Author��yuejw
//Date: 2005-7-11   10:13
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
%>
<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LCInsureAccont.js"></SCRIPT>
    <%@include file="LCInsureAccontInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm target="fraSubmit">
    <!--�����˻������-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAccClass);"></TD>
            <TD class= titleImg>�����˻�������Ϣ</TD>
        </TR>
    </table>
    <Div  id= "divLCInsureAccClass" style= "display: ''">
	    <!--�����˻������-->
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLCInsureAccClassGrid" ></span></td>
	        </tr>
	    </table>
  	</Div>
	<hr>

    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAccTrace);"></td>
            <td class=titleImg> �����ʻ���Ǽ������� </td>
        </tr>
    </table>
    <Div id= "divLCInsureAccTrace" style= "display: ''">
	    <!--�����ʻ���Ǽ�������Ϣ-->
	    <table class= common>
	        <tr class= common>
	            <td text-align: left colSpan=1><span id="spanLCInsureAccTraceGrid" ></span></td>
	        </tr>
	    </table>
    </div>

    <%
    //������,������Ϣ��
    %>
    <input type=hidden id="fmtransact" name="fmtransact">
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
