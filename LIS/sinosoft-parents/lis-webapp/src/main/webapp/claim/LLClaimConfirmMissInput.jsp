<%
//**************************************************************************************************
//Name��LLClaimConfirmMissInput.jsp
//Function����������������Ϣ
//Author��zl
//Date: 2005-6-20 17:58
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
    <SCRIPT src="LLClaimConfirmMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimConfirmMissInit.jsp"%>
    <title>�������� </title>
</head>
<body  onload="initForm();initElementtype();" >
    <form action="./LLClaimConfirmMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <div id ="ConfirmClaimPool"></div>
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
    <!-- 
    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>  -->
<!-- lzf    <Div  id= "divSearch1" style= "display: none">
        <table class= common>
        <table class= common> 
		        <TR class= common>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> �ⰸ״̬ </TD>
                <TD class= input> <Input class=codeno name=ClmState CodeData="0|3^10|�ѱ���^20|������^30|���^40|����" onDblClick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('ClmState', [this,ClmStateName],[0,1])" ><Input class=codename name=ClmStateName readonly=true></TD>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name=CustomerNo ></TD>
            </TR>
            <TR class= common>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= common name=CustomerName ></TD>
                <TD class= title> �Ա� </TD>
                <TD class= input> <Input class=codeno name=customerSex onDblClick="return showCodeList('sex',[this,SexName],[0,1]);" onKeyUp="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                <TD class= title> �¹����� </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="AccidentDate2" onBlur="CheckDate(fm.AccidentDate2);"></TD>
            </TR>
        </table>
        </table>
 	   </DIV>
	       <Div  id= "divSearch2" style= "display: none">
        <table class= common>
        <table class= common>
            <TR class= common>
                <TD class= title> ������ </TD>
                <TD class= input><Input class= "readonly" readonly></TD>
                <TD class= title> �ⰸ״̬ </TD>
                <TD class= input><Input class= "readonly" readonly></TD>
                <TD class= title> �ͻ����� </TD>
                <TD class= input><Input class= "readonly" readonly></TD>
            </TR>
            <TR class= common>
                <TD class= title> �ͻ����� </TD>
                <TD class= input> <Input class= "readonly" readonly></TD>
                <TD class= title> �Ա� </TD>
                <TD class= input> <Input class= "readonly" readonly></TD>
                <TD class= title> �¹����� </TD>
                <TD class= input> <Input class= "readonly" readonly></TD>
            </TR>
	    </table>
        </table>
		</div>
    </DIV>
    end lzf --> 
    <!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onClick="queryGrid();"> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimConfirm);"></TD>
            <TD class= titleImg> ����� </TD> 
        </TR>
    </Table>-->
<!-- lzf    <Div  id= "divLLClaimConfirm" style= "display: none" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimConfirmGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <br>
    <hr>
 end lzf    -->
    <!-- <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyClaim();"> -->
<!-- lzf    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimConfirmGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLClaimConfirmGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimConfirmGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onClick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onClick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onClick="turnPage2.lastPage();HighlightByRow()"></td>
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
  end lzf -->
</body>
</html>
