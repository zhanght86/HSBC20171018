<html>
<%
//程序名称：WFEdorNoscanAppInput.jsp
//程序功能：保全工作流-团单保全无扫描申请
//创建日期：2005-08-15 15:13:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>

<%
    //添加页面控件的初始化。
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  
  <SCRIPT src="WFGrpEdorNoscanApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFGrpEdorNoscanAppInit.jsp"%>

  <title>团单保全无扫描申请</title>

</head>

<body  onload="initForm();" >
  <form action="./WFGrpEdorNoscanAppSave.jsp" method=post name=fm id=fm target="fraSubmit">

    <table class= common border=0 width=100%>
        <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
    		</td>
            <td class= titleImg>请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
    <div class="maxbox">
        <table  class= common >
            <TR  class= common>
                <td class=title5>保全受理号</td>
                <td class= input5><Input class="wid" class="common" name=EdorAcceptNo_ser id=EdorAcceptNo_ser></td>
                <TD class= title5>号码类型</TD>
                <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=OtherNoType id=OtherNoType ondblclick="showCodeList('gedornotype',[this,OtherNoName],[0,1])" onMouseDown="showCodeList('gedornotype',[this,OtherNoName],[0,1])" onkeyup="showCodeListKey('gedornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName id=OtherNoName readonly></td></TR>
                <TR  class= common>
                <TD class= title5>团体保单号</TD>
                <td class= input5><Input class="wid" class="common" name=OtherNo id=OtherNo></td>
                 <td class=title5>申请人</td>
                <td class=input5><Input class="wid" type="input" class="common" name=EdorAppName id=EdorAppName></td>
            </TR>
            <TR  class= common>
               
                <td class=title5>申请方式</td>
                <td class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=AppType id=AppType ondblclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onMouseDown="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName id=AppTypeName readonly></td>
                <TD class= title5>录入日期</TD>
                <TD class= input5>
                <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR  class= common>
                <TD class=title5>管理机构</TD>
                <TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom_ser  id=ManageCom_ser ondblclick="showCodeList('station',[this,ManageComName],[0,1])" onMouseDown="showCodeList('station',[this,ManageComName],[0,1])" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1])" readonly=true><input class=codename name=ManageComName id=ManageComName readonly></TD>
                <TD class= title5>  </TD>
                <TD class= title5>  </TD>
            </TR>
        </table>
    </div>
    </Div>
        <!--<INPUT VALUE=" 查 询 " class = cssButton TYPE=button onclick="easyQueryClickSelf();">
        <INPUT class=cssButton id="riskbutton" VALUE=" 申 请 " TYPE=button onClick="applyMission();">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClickSelf();">查    询</a>
        <a href="javascript:void(0);" id="riskbutton" class="button" onClick="applyMission();">申    请</a>
    

    <table>
        <tr>
            <td class=common>
                <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divSelfGrid);">
            </td>
            <td class= titleImg>
                 我的任务
            </td>
        </tr>
    </table>
    <div id="divSelfGrid" style="display:''" align="center">
        <table class="common">
            <tr class="common">
                <td><span id="spanSelfGrid"></span></td>
            </tr>
        </table>
        <!--<input type="button" class="cssButton" value="首  页" onclick="turnPage2.firstPage()">
        <input type="button" class="cssButton" value="上一页" onclick="turnPage2.previousPage()">
        <input type="button" class="cssButton" value="下一页" onclick="turnPage2.nextPage()">
        <input type="button" class="cssButton" value="尾  页" onclick="turnPage2.lastPage()">-->
    </div>

    <!--<INPUT class=cssButton TYPE=button VALUE=" 保全受理 " onclick="GoToBusiDeal()">
    <INPUT class=cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">保全受理</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>

        <!-- 隐藏域部分 -->
            <input type="hidden" name="EdorAcceptNo">
            <input type="hidden" name="MissionID">
            <input type="hidden" name="SubMissionID">
            <input type="hidden" name="ActivityID">

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	<br><br><br><br>
</body>
</html>
