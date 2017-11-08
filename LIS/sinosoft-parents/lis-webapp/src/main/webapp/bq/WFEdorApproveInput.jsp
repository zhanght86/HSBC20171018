<html>
<%
//程序名称：WFEdorApproveInput.jsp
//程序功能：保全工作流-保全复核
//创建日期：2005-04-30 11:49:22
//创建人  ：sinosoft
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
    var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFEdorApprove.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFEdorApproveInit.jsp"%>

  <title>保全复核</title>

</head>

<body  onload="initForm();" >
  <form action="./WFEdorApproveSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<div id="EdorApproveInputPool"></div>
   <!--<table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
        <table  class= common >
            <TR  class= common>
                <td class=title> 保全受理号 </td>
                <td class= input><Input class="common" name=EdorAcceptNo></td>
                <TD class= title> 号码类型 </TD>
                <td class= input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);"onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName readonly=true ></td>
                <TD class= title> 客户/保单号 </TD>
                <td class= input><Input class="common" name=OtherNo></td>
            </TR>
            <TR  class= common>
                <td class=title> 申请人 </td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>
                <td class=title> 申请方式 </td>
                <td class= input ><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);"onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName readonly=true></td>
                <TD class= title> 录入日期 </TD>
                <TD class= input><Input class= "multiDatePicker" dateFormat="short" name=MakeDate ></TD>
            </TR>
            <TR  class= common>
                <TD class=title> 管理机构 </TD>
                <TD class=input><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>
                <TD class= title>最低复核级别</TD>
				<TD class=input><input type="text" class="codeno" name="EdorPopedom" verify="个单权限级别|Code:EdorPopedom" ondblclick="showCodeList('EdorPopedom',[this,EdorPopedomName],[0,1])" onkeyup="showCodeListKey('EdorPopedom',[this,EdorPopedomName],[0,1])"><input type="text" class="codename" name="EdorPopedomName" readonly>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
                <TD class= title>  </TD>
            </TR>
        </table>
    </div>

    <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickAll();">

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divAllGrid);">
            </td>
            <td class= titleImg>
                 共享工作池
            </td>
        </tr>
    </table>
    <div id="divAllGrid" style="display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAllGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageAllGrid" align="center" style="display:'none'">
            <input type="button" class="cssButton" value="首  页" onclick="turnPageAllGrid.firstPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="上一页" onclick="turnPageAllGrid.previousPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="下一页" onclick="turnPageAllGrid.nextPage();HighlightAllRow();">
            <input type="button" class="cssButton" value="尾  页" onclick="turnPageAllGrid.lastPage();HighlightAllRow();">
        </div>
    </div>

    <br>
        <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission();">
    <br>

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>
                 我的任务
            </td>
        </tr>
    </table>
    <div  id="divSelfGrid" style="display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
        <div id="divTurnPageSelfGrid" align="center" style="display:'none'">
            <input type="button" class="cssButton" value="首  页" onclick="turnPageSelfGrid.firstPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="上一页" onclick="turnPageSelfGrid.previousPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="下一页" onclick="turnPageSelfGrid.nextPage();HighlightSelfRow();">
            <input type="button" class="cssButton" value="尾  页" onclick="turnPageSelfGrid.lastPage();HighlightSelfRow();">
        </div>
    </div>  -->
    <br>
    <!--<INPUT class= cssButton TYPE=button VALUE=" 保全复核 " onclick="GoToBusiDeal()">
    <INPUT class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">保全复核</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>

        <!-- 隐藏域部分 -->
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID">
        <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
