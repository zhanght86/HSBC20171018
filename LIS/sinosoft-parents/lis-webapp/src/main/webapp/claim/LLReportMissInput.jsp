<%
//**************************************************************************************************
//Name：LLReportMissInput.jsp
//Function：报案工作队列信息
//Author：zl
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
    <title>报案登记 </title>
</head>
<body  onload="initForm();" >
    <form action="./LLReportMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 报案信息部分 -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> 报案个人工作队列查询</TD>
        </TR>
    </Table>  
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title5> 报案号 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> 报案人姓名 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptorName id=RptorName ></TD></TR>
                <TR class= common> 
                <TD class= title5> 客户姓名 </TD>       
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <TD class= title5> 事故日期区间(起始) </TD>
                <TD class= input5> <!--<Input class="multiDatePicker" dateFormat="short" name="RptStartDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RptStartDate id="RptStartDate"><span class="icon"><a onClick="laydate({elem: '#RptStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td> 
            </TR>
            <TR class= common>                  
                         
                <TD class= title5> 事故日期区间(结束) </TD>
                <TD class= input5><!-- <Input class="multiDatePicker" dateFormat="short" name="RptEndDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RptEndDate id="RptEndDate"><span class="icon"><a onClick="laydate({elem: '#RptEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
                <TD class= title5> </TD>
                <TD class= input5> </TD>
            </TR>
         
        </table> 
    </DIV>
   <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="querySelfGrid();"> 
    <!--Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport);"></TD>
            <TD class= titleImg> 共享池 </TD>
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
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>    
    </Div>
    
   <br>
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"-->

    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLReportGrid);"></td>
                <td class= titleImg> 工作队列 </td>
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
    <INPUT class=cssButton VALUE="新增报案" TYPE=button onclick="newReport();">
    <%
    //保存数据用隐藏表单区
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
