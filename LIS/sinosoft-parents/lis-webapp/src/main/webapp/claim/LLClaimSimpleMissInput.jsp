<%
//**************************************************************************************************
//Name：LLClaimSimpleMissInput.jsp
//Function：简易案件工作队列信息
//Author：zl
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
     <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
    <SCRIPT src="LLClaimSimpleMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimSimpleMissInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<form method=post name=fm id=fm target="fraSubmit">
<div id ="SimpleClaimPool"></div>
<div>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >   
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
</div>	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
    <!-- 共享池部分 -->
<!-- lzf    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> 赔案号 </TD>
                <TD class= input> <Input class= common name=RptNo ></TD>
                <TD class= title> 赔案状态 </TD>
                <TD class= input> <Input class=codeno name=ClmState CodeData="0|3^10|已报案^20|已立案^30|审核" ondblclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('ClmState', [this,ClmStateName],[0,1])" ><Input class=codename name=ClmStateName readonly=true></TD>
                <TD class= title> 客户编码 </TD>
                <TD class= input> <Input class= common name=CustomerNo ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 客户姓名 </TD>
                <TD class= input> <Input class= common name=CustomerName ></TD>
                <TD class= title> 性别 </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                <TD class= title> 立案日期 </TD>
                <TD class= input> <input class="multiDatePicker" dateFormat="short" name="RgtDate" onblur="CheckDate(fm.RgtDate);"></TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimSimple);"></TD>
            <TD class= titleImg> 共享池 </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimSimple" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimSimpleGrid" ></span> </TD>
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
    <hr>
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyClaim();">
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimSimpleGrid);"></td>
                <td class= titleImg> 工作队列 </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLClaimSimpleGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>        
    </div>
    <br>
    <%
    //保存数据用隐藏表单区
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
  end lzf --> <br><br><br><br>
</body>
</html>
