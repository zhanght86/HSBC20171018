<%
//程序名称：SpecialProposalScanQuery.jsp
//程序功能：特殊投保单录入
//创建日期：2007-07-23 16:05:57
//创建人  ：张征
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var	ManageCom = "";  //保单的管理机构
  var MissionID = "";
  var SubMissionID = "";
  var ActivityID = "";
  var SubType = "";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="SpecialProposalScanQuery.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="SpecialProposalScanQueryInit.jsp"%>
  
  <title>投保单扫描件查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <div class="maxbox1">
    <table class=common>
        <tr class= common>
            <TD class=title5>印刷号</TD>
            <TD class=input5><Input class="common wid"  name=QueryPrtNo id="QueryPrtNo"></TD>
            <TD class=title5></TD>
            <TD class=input5></TD>
        </tr>
    </table>
    </div>
    <div>
    <a href="javascript:void(0)" class=button onclick="ClickQuery();">查  询</a>
    <a href="javascript:void(0)" class=button onclick="scanApplyClick();">申请投保单</a>
    </div>
    <br>
    <!-- <Input class=cssButton  VALUE="查询" TYPE=button onclick="ClickQuery()">
    <INPUT class= cssButton VALUE="申请投保单" TYPE=button onclick="scanApplyClick();"> -->
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					  <span id="spanPolGrid" >
  					  </span> 
  			  	</td>
  			  </tr>
    	  </table>
    	
      <Div  id= "divPage" align=center style= "display: 'none' ">
        <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	 				
  	</Div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo" id="GrpPolNo">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
