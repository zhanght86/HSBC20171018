<%
//�������ƣ�LLSubReportDescInput.jsp
//�����ܣ��¹�������Ϣ¼��
//�������ڣ�2005-05-10
//������  ��zhoulei
//���¼�¼��
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
//==========BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tCustomerNo = request.getParameter("custNo"); //�����˱���
	  String tStartPhase = request.getParameter("startPhase"); //����׶�

//==========END
%>
    <title>�¹�������Ϣ¼��</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLClaimDesc.js"></script>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimDescInit.jsp"%>

</head>
<body  onload="initForm()">
<form name=fm id=fm target=fraSubmit method=post>
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReportDesc1);"></TD>
            <TD class= titleImg>�¹�������Ϣ</TD>
        </TR>
    </table>
    <Div id= "divLLSubReportDesc1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLSubReportDescGrid" ></span></td>
            </tr>
        </table>
    </Div>
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReportDesc2);"></TD>
            <TD class= titleImg>������Ϣ</TD>
        </TR>
    </table>
    <Div id= "divLLSubReportDesc2" style= "display: ''" class="maxbox1">
        <!--TABLE class=common>
            <tr class=common>
                <td class= title> �������� </td>
                <td class= input><Input type="input" class="common" name="DescType"></td>
                <td class= title></td>
                <td class= input></td>
                <td class= title></td>
                <td class= input></td>
            </tr>
        </TABLE-->
        <Table class= common>
            <tr class= common>
                <td class= title> �������� </td>
            </tr>
            <tr class= common>
                <td colspan="6" style="padding-left:16px"> <textarea name="Describe" id="Describe" cols="196" rows="4" witdh=25% class="common"></textarea></td>
            </tr>
        </TABLE>
        <Table style="display:none">
            <tr>
                <td><Input class=cssButton name="addClick"value="��  ��" type=button onclick="saveClick()"></td>
                <td><Input class=cssButton value="��  ��" type=button onclick="top.close()"></td>
            </tr>
        </TABLE>
    </Div>
    <a href="javascript:void(0);" name="addClick" class="button" onClick="saveClick();">��    ��</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
    <%
    //���ر���������Ϣ��
    %>
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="WhoNo" name="WhoNo"><!--�����˴���-->
    <Input type=hidden id="StartPhase" name="StartPhase"><!--����׶�-->

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
