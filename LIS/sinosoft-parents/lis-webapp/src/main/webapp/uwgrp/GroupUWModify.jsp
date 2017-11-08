<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：GroupUWAuto.jsp
//程序功能：集体核保订正
//创建日期：2004-12-08 11:10:36
//创建人  ：HEYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人	
	String tContNo = "";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupUWModify.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupUWModifyInit.jsp"%>
  <title>自动核保 </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWModifyChk.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		    <td class= titleImg align= center>请输入查询集体条件：</td>
	    </tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>投保单号</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalGrpContNo id="ProposalGrpContNo">
          </TD>
          <TD  class= title5>管理机构</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom" value="<%=tGI.ManageCom%>" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>投保单位客户号</TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpNo id="GrpNo">
          </TD>
          <TD  class= title5>投保单位名称</TD>
          <TD  class= input5>
            <Input class= "common wid" name=Name id="Name">
          </TD>        
        </TR>     
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <!-- <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClick();">  -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrp1);">
    		</td>
    		<td class= titleImg>
    			 集体投保单信息
    		</td>
    	</tr>
    	<tr>
    	    <INPUT  type= "hidden" class= Common name= GrpContNo id="GrpContNo"  value= "">
          <INPUT  type= "hidden" class= Common name= MissionID  id="MissionID" value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id="SubMissionID"  value= "">
    	</tr>
    </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页"  class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页"  class = cssButton91  TYPE=button onclick="getPreviousPage();"> 			
      <INPUT VALUE="下一页"  class = cssButton92  TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页"  class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
      <a href="javascript:void(0)" class=button onclick="GrpUWModify();">核保订正</a>
      <!-- <INPUT VALUE="核保订正" class = cssButton TYPE=button onclick="GrpUWModify();">  -->
      <!--INPUT VALUE="设置特殊标志" class = cssButton TYPE=button onclick="SetSpecialFlag();"--> 
  	</p>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
