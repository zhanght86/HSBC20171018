<%
//**************************************************************************************************
//Name��LLClaimRegisterMissInput.jsp
//Function����������������Ϣ
//Author��zl
//Date: 2005-6-13 20:07
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
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
    <SCRIPT src="LLClaimScanRegisterMiss.js"></SCRIPT>
    <!--SCRIPT src="LLClaimPubFun.js"></SCRIPT-->
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimScanRegisterMissInit.jsp"%>
    <title>��ɨ�������Ǽ� </title>
</head>
<body  onload="initForm();initElementtype();" >
    <form action="./LLClaimRegisterMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
   <div id="ScanerClaimPool"></div> 
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
	<input type=hidden id="Clmno" 	 name= "Clmno">	
	<input type=hidden id="MissionID" 	 name= "MissionID">
  	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <!-- ����ز��� -->
   <!--   <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> ������� </TD>
                <TD class= input> <Input class= common name=ApplyNo ></TD>

            </TR>
            <TR class= common>
                <TD class= title> ɨ������ </TD>
                <TD class= input> <Input class= multiDatePicker name=ScanDate ></TD>
                <TD class= title> ɨ����� </TD>
                <TD class= input> <Input class= common name=ScanCom ></TD>

                <TD class= title> ɨ����Ա </TD>
                <TD class= input> <Input class= common name=Scaner ></TD>
            </TR>

        </table>
    </DIV>
    
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <p>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimRegister);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimRegister" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimRegisterGrid" ></span> </TD>
            </TR>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <br>
    <hr>
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="Apply();">
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimRegisterGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLClaimRegisterGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimRegisterGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightByRow()"></td>
            </tr>
        </table>
    </div>
    <br>
    <hr>

    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
	<input type=hidden id="Clmno" 	 name= "Clmno">	
	<input type=hidden id="MissionID" 	 name= "MissionID">
  	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  --><br><br><br><br>
</body>
</html>
