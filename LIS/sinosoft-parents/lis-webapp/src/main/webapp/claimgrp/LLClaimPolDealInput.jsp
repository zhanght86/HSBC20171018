<%
//**************************************************************************************************
//Name：LLClaimPolDealInput.jsp
//Function：保单结算
//Author：zl
//Date: 2005-7-20 15:05
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
<head>
<%
//==========BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("ClmNo");           //赔案号
//==========END
%>
    <title>合同处理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LLClaimPolDeal.js"></script>
    <%@include file="LLClaimPolDealInit.jsp"%>

</head>

<body  onload="initForm();">
<form name=fm target=fraSubmit method=post>
    <br>
    <!--=========================================================================
        保单结算的结果
        =========================================================================
    -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolCalResult);"></TD>
            <TD class= titleImg> 保单结算结果 </TD>
        </TR>
    </Table>
    <Div  id= "divPolCalResult" style= "display:''">
        <Table  class= common>
            <tr>
                <td text-align: left colSpan=1><span id="spanPolCalResultGrid"></span></td>
            </tr>
        </Table>
    <hr>
    </div>

    <table>
        <tr>
            <td><INPUT class=cssButton name="polCalButton" VALUE="保单结算"  TYPE=button onclick="submitForm()" ></td>
            <td><INPUT class=cssButton name="goBack" value="返  回" type=button onclick="top.close()"></td>
        </tr>
    </table>

	<input type=hidden name=ClmNo value=''><!--赔案号-->
    <input type=hidden name=hideOperate value=''><!--隐常的操作符号-->

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
