<%
//**************************************************************************************************
//Name��LLGrpClaimSimpleInput.jsp
//Function�����װ���������Ϣ
//Author��pd
//Date: 2005-10-21
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
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLGrpClaimSimple.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLGrpClaimSimpleInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
            <td class= titleImg>�������ѯ������</td>
        </tr>
    </table>    

    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> �ⰸ�� </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> ����ͻ��� </TD>
                <TD class= input5><Input class="wid" class=common name=CustomerNo id=CustomerNo ></TD></TR>
                <TR class= common>
                <TD class= title5> ��λ���� </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <TD class= title5> ���屣���� </TD>
                <TD class= input5> <Input class="wid" class= common name=GrpContNo id=GrpContNo ></TD>
            </TR>
            <TR class= common>
                
                <TD class= title5> �������� </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> </TD>
                <TD class= input5></TD>
            </TR>
        </table>
    </DIV>
   <!-- <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="querySelfGrid();">
    <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="ApplyClaim2();">-->
    <a href="javascript:void(0);" class="button" onClick="querySelfGrid();">��    ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="ApplyClaim2();">��    ��</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimRegister);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>
    <Div id= "divSelfLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>        
    </div>
    <br>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimSimpleGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div  id= "divSearch1" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> �ⰸ�� </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo2 id=RptNo2 ></TD>
                <TD class= title5> ����ͻ��� </TD>
                <TD class= input5><Input class="wid" class=common name=CustomerNo2 id=CustomerNo2 ></TD></TR>
                 <TR class= common>
                <TD class= title5> ��λ���� </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName2 id=CustomerName2 ></TD>
                 <TD class= title5> ���屣���� </TD>
                <TD class= input5> <Input class="wid" class= common name=GrpContNo2 id=GrpContNo2 ></TD>
            </TR>
            <TR class= common>
               
                <TD class= title5> �������� </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate2" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> </TD>
                <TD class= input5></TD>
            </TR>
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">--> 
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a><br><br>   
    <br>

    <Div id= "divLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();HighlightByRow()"></td>
            </tr>
        </table>        
    </div>
    <br>
    <!--INPUT class=cssButton id="riskbutton" VALUE="���װ�������" TYPE=button onClick="ApplyClaim();"-->
    <!--<input class=cssButton name=Reportbutton  VALUE="������Ϣ����"  TYPE=button onclick="PrintReportClass();" >
    <input class=cssButton name=Inputbutton  VALUE="��������Ϣ����"  TYPE=button onclick="getin()" >-->
    <!--a href="./simpleclaim.xls">����[���װ�������]����ģ��</a-->
    <%
    //�������������ر���
    %>
   
   <!-- <TR class= common>                            
          <TD class=title><font  color='#ff0000'><b> ʹ�õ��빦�����ȵ���������Ϣ!</b></font> </TD>
		    </TR>     -->  
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    
  <input type=hidden id="MissionID"    name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <input type=hidden id="ActivityID" name= "ActivityID">
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
