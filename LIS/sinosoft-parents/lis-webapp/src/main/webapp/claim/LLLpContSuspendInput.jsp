<%
//Name: LLLpContSuspendInput.jsp
//Function����������ҳ��
//Date��
//Author ��yuejinwei
//Modify : zhoulei
//Modify date : 2005-11-19 14:18
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN�����ղ���==========================================================
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");
		  String tInsuredNo = request.getParameter("InsuredNo");	//�����˿ͻ���
		  String tClmNo = request.getParameter("ClmNo");
	//==========END====================================================================
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLpContSuspend.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLpContSuspendInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLpContSuspend);"></td>
            <td class= titleImg> �ⰸ�޹ر��������嵥 </td>
        </tr>
    </table>
    <Div  id= "divLLLpContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLpContSuspendGrid" ></span></td>
            </tr>
        </table>
    </div>

    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContIn);"></td>
            <td class= titleImg> �ⰸ��ر��������嵥 </td>
        </tr>
    </table>
    <Div  id= "divLLLcContIn" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLpContInGrid" ></span></td>
            </tr>
        </table>
        <input class=cssButton  type=button value=" �� �� " onclick="saveClick()">  <!-- wyc modify -->
        <input class=cssButton  type=button value=" �� ѯ " onclick="queryClick()">
        <input class=cssButton  type=button value=" �� �� " onclick="top.close()">
    </div>

    <!--������,������Ϣ��-->
    <Input type=hidden id="InsuredNo" name="InsuredNo"><!--�����˿ͻ�����,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->
    <Input type=hidden id="ClmNo" name="ClmNo">

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
