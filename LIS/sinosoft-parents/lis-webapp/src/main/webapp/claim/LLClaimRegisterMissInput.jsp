<%
//**************************************************************************************************
//Name：LLClaimRegisterMissInput.jsp
//Function：立案工作队列信息
//Author：zl
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
	var operator = "<%=tGlobalInput.Operator%>";   //记录操作员
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGlobalInput.ComCode%>"; //记录登陆机构
</script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimRegisterMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimRegisterMissInit.jsp"%>
    <title>立案登记 </title>
</head>
<body  onload="initForm();initElementtype();" >
    <form action="./LLClaimRegisterMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <div id="NoScanClaimPool"></div>
    <!--<p>
        <INPUT class=cssButton VALUE="新增立案" TYPE=button onclick="newReport();">
    </p>-->
    <a href="javascript:void(0);" class="button" onClick="newReport();">新增立案</a>
    <div>
	    <Input type=hidden name="Operator" id="Operator" >
	    <Input type=hidden name="ComCode" id="ComCode" >
	    <Input type=hidden name="ManageCom" id="ManageCom" >
	    <Input type=hidden name=CurDate  id="CurDate">
		<input type=hidden id="MissionID" 	 name= "MissionID">
		<input type=hidden id="SubMissionID" name= "SubMissionID">
		<input type=hidden id="ActivityID" name= "ActivityID">
	</div>
	</form>
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <!-- 共享池部分 -->
   <!--  <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> 立案号 </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> 客户号 </TD>
                <TD class= input> <Input class= common name=CustomerNo ></TD>
                <TD class= title> 客户姓名 </TD>
                <TD class= input> <Input class= common name=CustomerName ></TD>
            </TR>
            <TR class= common>
                
                <TD class= title> 客户性别 </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                <TD class= title> 事故日期 </TD>
                <TD class= input> <input class="coolDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);" ></TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">
    <br>
    <hr>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimRegisterGrid);"></td>
                <td class= titleImg> 工作队列 </td>
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
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightByRow()"></td>
            </tr>
        </table>
    </div>
    <br>
    <hr>
    <table  class= common >
        <tr  class=common>
            <td><INPUT class=cssButton VALUE="新增立案" TYPE=button onclick="newReport();"></td>
        </tr>
    </table>
    <%
    //保存数据用隐藏表单区
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
   --><br><br><br><br>
</body>
</html>
