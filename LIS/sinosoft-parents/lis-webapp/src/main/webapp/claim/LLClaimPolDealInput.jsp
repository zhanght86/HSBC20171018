<%
//**************************************************************************************************
//Name��LLClaimPolDealInput.jsp
//Function����������
//Author��zl
//Date: 2005-7-20 15:05
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
//==========BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("ClmNo");           //�ⰸ��
//==========END
%>
    <title>��ͬ����</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LLClaimPolDeal.js"></script>
    <%@include file="LLClaimPolDealInit.jsp"%>

</head>

<body  onload="initForm();">
<form name=fm id'f'm target=fraSubmit method=post>
    <br>
    <!--=========================================================================
        ��������Ľ��
        =========================================================================
    -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolCalResult);"></TD>
            <TD class= titleImg> ���������� </TD>
        </TR>
    </Table>
    <Div  id= "divPolCalResult" style= "display:''">
        <Table  class= common>
            <tr>
                <td style="text-align: left" colSpan=1><span id="spanPolCalResultGrid"></span></td>
            </tr>
        </Table>
    <hr class=line>
    </div>

    <table>
        <tr>
            <td><INPUT class=cssButton name="polCalButton" VALUE="��������"  TYPE=button onclick="submitForm()" ></td>
            <td><INPUT class=cssButton name="goBack" value="��  ��" type=button onclick="top.close()"></td>
        </tr>
    </table>

	<input type=hidden name=ClmNo id=ClmNo value=''><!--�ⰸ��-->
    <input type=hidden name=hideOperate id=hideOperate value=''><!--�����Ĳ�������-->

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
