<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput(); 
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("claimNo");	//赔案号
    String tCustomerNo = request.getParameter("customerNo");	//赔案号
    String tFlag = request.getParameter("Flag");	//赔案号
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimReportQuery.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimReportQueryInit.jsp"%>
    <title>报案登记 </title>
</head>
<body  onload="initForm();" >
    <form action="./LLClaimRepRegRelaSave.jsp"  method=post name=fm id=fm target="fraSubmit">
    <!-- 报案信息部分 -->
    <!--<Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
           
        </TR>
    </Table> --> 
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title> 客户编码 </TD>       
                <TD class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo ></TD>
                <TD class= title></TD>
                <TD class= input></TD>
                <TD class= title></TD>
                <TD class= input></TD>
            </TR>

         
        </table> 
    </DIV>
    <INPUT style="display:none" class=cssButton VALUE="查  询" TYPE=button onclick="querySelfGrid();">
    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLReportGrid);"></td>
                <td class= titleImg> 报案信息 </td>
            </tr>      
        </table>
    </Div>
    <Div  id= "divSelfLLReportGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLReportGrid" ></span></td>
            </tr>
        </table>
        <!--<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();">                     
        <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">     -->
    </div>
    <br>
    <table style=""  class= common >
        <tr  class=common>                
            <td><INPUT class=cssButton  name=QueryDetail VALUE="详细查询" TYPE=button onclick="QueryRepDetail();"></td>
            <td><INPUT class=cssButton  name=ReportRela VALUE="报案关联" TYPE=button onclick="RepRegrelaSubmit();"></td> 
            <td><INPUT class=cssButton  name=Back VALUE="返    回" TYPE=button onclick="top.close();"></td> 
        </tr>
    </table> 
    <%
    //保存数据用隐藏表单区
    %>  
    <Input type=hidden id="ClmNo" name="ClmNo"><!--立案号-->
	<Input type=hidden id="RptNo" name="RptNo"><!--报案号-->
	<Input type=hidden id="Flag" name="Flag"><!--报案号-->
    <Input type=hidden name="Operator" id="Operator" >
    <Input type=hidden name="ComCode" id="ComCode" >
    <Input type=hidden name="ManageCom" id="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
