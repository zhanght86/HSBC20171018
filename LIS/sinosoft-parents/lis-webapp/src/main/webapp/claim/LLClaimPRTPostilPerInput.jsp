<%
//Name��LLClaimPRTPostilPerInput.jsp
//Function���������֪ͨ���ӡ
//Author��
//Date: 2010-4-13 11:27
//Desc:
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPRTPostilPerInput.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPRTPostilPerInit.jsp"%>
    <title> �������֪ͨ���ӡ��ѯ </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm id=fm  action="./LLClaimPRTPostilPerSave.jsp" target="fraSubmit">
    <!-- ����ز��� -->
    <table class= common border=0 width=100%>
        <tr>
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
            <td class=titleImg align= center>�������ѯ����(������Ҫһ������)��</td>
        </tr>
    </table>
    <Div  id= "divSearch" class=maxbox1 style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class="title5"> �ⰸ�� </TD>
                <TD class="input5"> <Input class="common wid" name=RptNo id=RptNo ></TD>
                <TD class="title5"> �����˱��� </TD>
                <TD class="input5"> <Input class="common wid" name=CustomerNo id=CustomerNo ></TD>
              </TR>

            <TR class= common>
                <TD class="title5"> �������� </TD>
                <TD class="input5"> <input class="coolDatePicker" dateFormat="short" name="RgtDate" onblur="CheckDate(fm.RgtDate);" onClick="laydate({elem: '#RgtDate'});" id="RgtDate"><span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
                <TD class="title5"> ��������</TD>
				<TD class="input5"> <Input class=codeno name="CalManageCom" id=CalManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);">
				<input class=codename name="ManageComName" id=ManageComName readonly=true></TD> 	
            </TR>            
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class=titleImg> ����ӡ�б� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimQuery" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLClaimQueryGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <!--<table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>-->
    </Div>
    <hr class=line>
    <table>
        <tr>
            <td><INPUT class=cssButton VALUE="��    ӡ" TYPE=button onclick="showPrint();"></td>
        </tr>
    </table>
  <input type=hidden id="fmtransact" name="fmtransact">   
	<input type=hidden id="prtType" name="prtType" value="PCT020" > 
	<input type=hidden id="ClmNo" name="ClmNo"> 
	<input type=hidden id="PrtSeq" name="PrtSeq"> 
	<input type=hidden id="dCustomerNo" name="dCustomerNo"  >  
	<input type=hidden id="mComCode" name="mComCode" value="<%=tGlobalInput.ComCode%>" > 
	<input type=hidden id="mOperator" name="mOperator" value="<%=tGlobalInput.Operator%>" 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
