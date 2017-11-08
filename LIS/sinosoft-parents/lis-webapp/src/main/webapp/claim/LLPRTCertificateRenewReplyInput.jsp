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
    <SCRIPT src="LLPRTCertificateRenewReply.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLPRTCertificateRenewReplyInit.jsp"%>
    <title>补充材料回销 </title>
</head>
<body  onload="initForm();" >
    <form  method=post name=fm target="fraSubmit">
    <!-- 报案信息部分 -->
 	<Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> 请输入查询条件： </TD>
        </TR>
    </Table>
    <Div id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title5> 立案号 </TD>       
                <TD class= input5> <Input class="wid" class= common name=Rgtno ></TD>
               <TD class= title5> 初审机构</TD>       
                <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="IssueBackCom" id="IssueBackCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name="ManageComName" id="ManageComName" readonly=true></TD> 	
            </TR>
            <TR class= common>    
                <TD class= title5> 起始日期 </TD>       
                <TD class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#IssueBackStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=IssueBackStartDate id="IssueBackStartDate"><span class="icon"><a onClick="laydate({elem: '#IssueBackStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
               <TD class= title5> 结束日期</TD>       
                <TD class= input5>  <Input class="coolDatePicker" onClick="laydate({elem: '#IssueBackEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=IssueBackEndDate id="IssueBackEndDate"><span class="icon"><a onClick="laydate({elem: '#IssueBackEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            </TR>
         
        </table> 
    </DIV>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();"> -->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">查    询</a>

    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLRegisterIssueGrid);"></td>
                <td class= titleImg> 问题件信息: 请单选进入，进行回销处理 </td>
            </tr>      
        </table>
    </Div>
    <Div  id= "divLLRegisterIssueGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLRegisterIssueGrid" ></span></td>
            </tr>
        </table>
        <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">     
    </div>

   
       

    <!--问题件退回原因,问题件退回确认日期,问题件退回人,问题件回销日期,问题件回销结论,问题件回销人-->
  
    <%
    //保存数据用隐藏表单区
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
