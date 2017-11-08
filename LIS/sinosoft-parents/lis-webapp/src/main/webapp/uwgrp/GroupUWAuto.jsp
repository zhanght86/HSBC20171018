<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：GroupUWAuto.jsp
//程序功能：集体自动核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
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
  <SCRIPT src="GroupUWAuto.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupUWAutoInit.jsp"%>
  <title>自动核保 </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWAutoChk.jsp" method=post name=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>请输入查询集体条件：</td>
	</tr>
    </table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            集体投保单号
          </TD>
          <TD  class= input>
            <Input class= common name=ProposalGrpContNo >
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);">
            <input class=codename name=ManageComName readonly=true>
          </TD>
          <TD  class= title>
            投保单位客户号
          </TD>
          <TD  class= input>
            <Input class= common name=GrpNo >
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            投保单位名称
          </TD>
          <TD  class= input>
            <Input class= common name=Name >
          </TD>
           <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this,AgentCodeName],[0,1]);" onkeyup="return showCodeListKey('AgentCode',[this,AgentCodeName],[0,1]);">
            <input class=codename name=AgentCodeName readonly=true>
          </TD>
          <TD  class= title>
            印刷号
          </TD>
          <TD  class= input>
            <Input class=common name=PrtNo>
          </TD>
        </TR>
       
    </table>
          <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClick();"> 
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
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ProposalGrpContNoHidden  value= "">
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
      <INPUT VALUE="首  页"  class =  cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class = cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页"  class =  cssButton TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
      <INPUT VALUE="自动核保" class = cssButton TYPE=button onclick="GrpUWAutoMakeSure();"> 
      <!--INPUT VALUE="设置特殊标志" class = cssButton TYPE=button onclick="SetSpecialFlag();"--> 
  	</p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
