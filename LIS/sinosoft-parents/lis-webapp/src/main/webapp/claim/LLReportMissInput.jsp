<%
//**************************************************************************************************
//Name��LLReportMissInput.jsp
//Function����������������Ϣ
//Author��zl
//Date: 2005-6-9 15:31
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
    <SCRIPT src="LLReportMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLReportMissInit.jsp"%>
    <title>�����Ǽ� </title>
</head>
<body  onload="initForm();" >
    <form action="./LLReportMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �������˹������в�ѯ</TD>
        </TR>
    </Table>  
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title5> ������ </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> ���������� </TD>
                <TD class= input5> <Input class="wid" class= common name=RptorName id=RptorName ></TD></TR>
                <TR class= common> 
                <TD class= title5> �ͻ����� </TD>       
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <TD class= title5> �¹���������(��ʼ) </TD>
                <TD class= input5> <!--<Input class="multiDatePicker" dateFormat="short" name="RptStartDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RptStartDate id="RptStartDate"><span class="icon"><a onClick="laydate({elem: '#RptStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td> 
            </TR>
            <TR class= common>                  
                         
                <TD class= title5> �¹���������(����) </TD>
                <TD class= input5><!-- <Input class="multiDatePicker" dateFormat="short" name="RptEndDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RptEndDate id="RptEndDate"><span class="icon"><a onClick="laydate({elem: '#RptEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
                <TD class= title5> </TD>
                <TD class= input5> </TD>
            </TR>
         
        </table> 
    </DIV>
   <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="querySelfGrid();"> 
    <!--Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>      
    <Div  id= "divLLReport" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLReportGrid" ></span> </TD>
            </TR>
        </Table>
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
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();"-->

    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLReportGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>      
        </table>
    </Div>
    <Div  id= "divSelfLLReportGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLReportGrid" ></span></td>
            </tr>
        </table>
             
    </div>
    <INPUT class=cssButton VALUE="��������" TYPE=button onclick="newReport();">
    <%
    //�������������ر���
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
