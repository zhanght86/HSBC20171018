<%
//**************************************************************************************************
//Name��LLClaimAuditMissInput.jsp
//Function����������������Ϣ
//Author��zl
//Date: 2005-6-20 14:33
//Desc:
//**************************************************************************************************
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
<script>	
	var operator = "<%=tGlobalInput.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGlobalInput.ComCode%>"; //��¼��½����
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <SCRIPT src="LLClaimAuditMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimAuditMissInit.jsp"%>
    <title>��˹��� </title>
</head>
<body  onload="initForm();initElementtype();" >
    <form action="./LLClaimAuditMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <div id = "AuditClaimPool"></div>
    <div>
	     <Input type=hidden name="Operator" >
	    <Input type=hidden name="ComCode" >
	    <Input type=hidden name="ManageCom" >
	    <Input type=hidden name=CurDate  id="CurDate">    
		<input type=hidden id="MissionID" 	 name= "MissionID">
		<input type=hidden id="SubMissionID" name= "SubMissionID">
		<input type=hidden id="ActivityID" name= "ActivityID">
    </div>	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

    <!-- ����ز��� -->
    <!--<table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>-->
 <!--  modify by lzf 2013-05-16 <Div  id= "divSearch" style= "display: 'none'">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> �ⰸ״̬ </TD>
                <TD class= input> <Input class=codeno name=ClmState CodeData="0|3^10|�ѱ���^20|������^30|���" ondblclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('ClmState', [this,ClmStateName],[0,1])" ><Input class=codename name=ClmStateName readonly=true></TD>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name=CustomerNo ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name=CustomerName ></TD>
                <TD class= title> �Ա� </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                <TD class= title> �¹����� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="AccidentDate2" onblur="CheckDate(fm.AccidentDate2);"></TD>
            </TR>
        </table>
    </DIV>
 end by lzf    -->
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimAudit);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>-->
  <!-- modify by lzf  <Div  id= "divLLClaimAudit" style= "display: 'none'" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimAuditGrid" ></span> </TD>
            </TR>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <br>
    <hr>
 end by lzf   --> 
    <!--<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyClaim();">-->
<!-- modify by lzf    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimAuditGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLClaimAuditGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimAuditGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightByRow()"></td>
            </tr>
        </table>        
    </div>
    <br>
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 end by lzf  --><br><br><br><br>
</body>
</html>
