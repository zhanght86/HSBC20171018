<%
//Name: LLLClaimQueryInit.jsp
//Function�������ⰸ��ѯ
//Date��2005.06.21
//Author ��quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN�����ղ���
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		  String tAppntNo = request.getParameter("tAppntNo");	////�����˱���
		  String tClmNo = request.getParameter("ClmNo");
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLLClaimQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> �����ⰸ��ѯ�嵥  </td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
 <!--       <input class=cssButton  type=button value=" �� �� " onclick="saveClick()">
        <input class=cssButton  type=button value=" �� ѯ " onclick="queryClick()">        
        <input class=cssButton  type=button value=" �� �� " onclick="top.close()"> -->
        <center>
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
    </div>

    <Div id= "divLLReport2" style= "display:none">
		<table>
			<TR>
				<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
				<TD class= titleImg>������Ϣ</TD>
			</TR>
		</table>
    </Div>

    <Div id= "divLLReport1" style= "display:none">
        <table class= common>
            <TR class= common>
                <TD class= title> �¼��� </TD>
                <TD class= input> <Input class= "readonly" readonly name=AccNo></TD>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class="readonly" readonly  name=RptNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class= common name=RptorName ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˵绰 </TD>
                <TD class= input> <Input class= common name=RptorPhone ></TD>
                <TD class= title> ������ͨѶ��ַ </TD>
                <TD class= input> <Input class= common name=RptorAddress ></TD>
                <TD class= title> ���������¹��˹�ϵ </TD>
                <TD class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('Relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('Relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
            </TR>
            <TR class= common>
                <TD class= title> ������ʽ </TD>
                <TD class= input> <Input class=codeno name="RptMode" ondblclick="return showCodeList('llrptcasetype',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptcasetype',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" readonly=true></TD>
                <TD class= title> ���յص� </TD>
                <TD class= input> <Input class= common name=AccidentSite ></TD>
                <TD class= title> ������������ </TD>
            <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> ��Ͻ���� </TD>
                <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="readonly" readonly name=Operator ></TD>
                <TD class= title> ����״̬ </TD>
                <TD class= input> <Input class= "readonly" readonly name=CaseState></TD>
            </TR>
        </table>

    <!--��������Ϣ-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>

       <table>
            <tr>
                <td><input class=cssButton name=QueryCont2 VALUE="������ѯ"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="�鿴����"  TYPE=button onclick="showQueryInq()"></td>
				<td><INPUT class=cssButton name="ViewReport" VALUE="�鿴�ʱ�"  TYPE=button onclick="showQuerySubmit()"></td>
            </tr>
        </table>
    </Div>



    <!--������,������Ϣ��-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--�����˱���,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--Ͷ���˺�ͬ����-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
