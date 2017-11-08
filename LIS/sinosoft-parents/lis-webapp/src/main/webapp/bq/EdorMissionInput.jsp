<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：EdorMissionInput.jsp
//程序功能：保全操作轨迹查询
//创建日期：2005-11-24 19:10:36
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
    String tMissionID = "";
    tMissionID = request.getParameter("MissionID");

%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<script>
    var MissionID = "<%=tMissionID%>";    //工作流任务ID
    var operator = "<%=tGI.Operator%>";   //记录操作员
    var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="EdorMission.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EdorMissionInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
      <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMission);">
            </td>
            <td class= titleImg>
                 保全操作轨迹
            </td>
            </tr>
        </table>
        <Div  id= "divEdorMission" >
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1 >
                    <span id="spanEdorMissionGrid">
                    </span>
                </td>
            </tr>
        </table>
      </Div>

<br>
    <!--<INPUT VALUE=" 返 回 " class=cssButton TYPE=button onclick="returnParent();">-->
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
