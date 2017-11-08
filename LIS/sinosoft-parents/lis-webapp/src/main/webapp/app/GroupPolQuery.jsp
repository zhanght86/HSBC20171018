<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
	String tContNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	try
	{
		tContNo = request.getParameter( "ContNo" );
		
		//默认情况下为集体投保单
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "00000000000000000000";
	}
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
	}
%>
<script>
	var contNo = "<%=tContNo%>";  //个人单的查询条件.
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GroupPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupPolQueryInit.jsp"%>
  <title>集体投保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<div class="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            集体保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GrpProposalNo id="GrpProposalNo" >
          </TD>
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class="common wid"n name=PrtNo id="PrtNo" >
          </TD>
          <TD  class= title>
            销售渠道
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl id="SaleChnl" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id="ManageCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
          <TD  class= title>
            业务员
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id="AgentCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            业务员组别
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup id="AgentGroup" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);">
          </TD>     
       
    </table>
          <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="返  回" class=cssButton TYPE=button onclick="returnParent();"> 	
    </div>		  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" TYPE=button class="cssButton90"  onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class= "cssButton91"  onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class= "cssButton92"  onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class= "cssButton93"  onclick="getLastPage();"> 					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
