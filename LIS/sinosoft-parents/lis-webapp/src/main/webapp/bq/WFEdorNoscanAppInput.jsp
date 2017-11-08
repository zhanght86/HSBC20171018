<html>
<%
//程序名称：WFEdorNoscanAppInput.jsp
//程序功能：保全工作流-保全无扫描申请
//创建日期：2005-04-27 15:13:22
//创建人  ：zhangtao
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
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="WFEdorNoscanApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFEdorNoscanAppInit.jsp"%>

  <title>保全无扫描申请</title>
  

</head>
		<%
		    GlobalInput tGlobalInput = new GlobalInput(); 
		    tGlobalInput = (GlobalInput)session.getValue("GI");
	    %>
	 <script>	
		var operator = "<%=tGlobalInput.Operator%>";   //记录操作员
		var manageCom = "<%=tGlobalInput.ManageCom%>"; //记录登陆机构
		var comcode = "<%=tGlobalInput.ComCode%>"; //记录登陆机构
	</script>
<body  onload="initForm();" >
  <form action="./WFEdorNoscanAppSave.jsp" method=post name="fm" id="fm" target="fraSubmit">
<!-- 
    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>请输入查询条件：</td>
        </tr>
    </table>
    
   
    <Div  id= "divSearch" style= "display: ''">
        <table  class= common >
            <TR  class= common>
                <td class=title>保全受理号</td>
                <td class=input><Input class="common" name=EdorAcceptNo_ser></td>
                <TD class=title>号码类型</TD>
                <td class=input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this,OtherNoName],[0,1])" onkeyup="showCodeListKey('edornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly></td>
                <TD class=title>客户/保单号</TD>
                <td class=input><Input class="common" name=OtherNo></td>
            </TR>
            <TR  class= common>
                <td class=title>申请人</td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>
                <td class=title>申请方式</td>
                <td class=input><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly></td>
                <TD class=title>录入日期</TD>
                <TD class=input><Input class="multiDatePicker" name=MakeDate ></TD>
            </TR>
            <TR  class= common>
                <TD class=title>管理机构</TD>
                <TD class=input><Input class="codeno" name=ManageCom_ser ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly></TD>
                <TD class=title>  </TD>
                <TD class=input>  </TD>
                <TD class=title>  </TD>
                <TD class=input>  </TD>
            </TR>
        </table>
    </div>
        <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickSelf();">
        <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="applyMission();">
    <br>
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSelfGrid)">
                </td>
                <td class="titleImg">我的任务</td>
           </tr>
        </table>
        
        <div id="divSelfGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageSelfGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageSelfGrid.firstPage();HighlightByRow()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageSelfGrid.previousPage();HighlightByRow()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageSelfGrid.nextPage();HighlightByRow()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageSelfGrid.lastPage();HighlightByRow()">
            </div>
        </div>
         -->
    <div id="NoScanAppInputPool"></div>
    <br>
        <!--<input type="button" class="cssButton" value=" 保全受理 " onclick="goToBusiDeal()">
        <input type="button" class="cssButton" value="记事本查看" onclick="showNotePad()">-->
        <a href="javascript:void(0);" class="button" onClick="goToBusiDeal();">保全受理</a>
        <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>

        <!-- 隐藏域部分 -->
            <input type="hidden" name="EdorAcceptNo" id="EdorAcceptNo">
            <input type="hidden" name="MissionID" id="MissionID">
            <input type="hidden" name="SubMissionID" id="SubMissionID">
            <input type="hidden" name="ActivityID" id="ActivityID">
            <input type="hidden" name="ICFlag" id="ICFlag">
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
