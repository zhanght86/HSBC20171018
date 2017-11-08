<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String tContNo = "";
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
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ContQuery.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ContQueryInit.jsp"%>
  <title>投保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGroupPol1);">
		    </td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<div class=maxbox1>
    <table  class=common>
      	<TR  class=common>
          <TD  class= title>
            投保单号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=GrpProposalNo  id=GrpProposalNo>
          </TD>
          <TD  class= title>
            印刷号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name=PrtNo id=PrtNo>
          </TD>
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            代理人编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id=AgentCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            代理人组别
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup id=AgentGroup 
			style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentGroup',[this]);" onkeyup="return showCodeListKey('AgentGroup',[this]);">
          </TD>       
        </TR>
    </table>
	</div>
          <INPUT VALUE="查  询" TYPE=button class=cssButton onclick="easyQueryClick();"> 
          <INPUT VALUE="返  回" TYPE=button class=cssButton onclick="returnParent();"> 					
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
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" TYPE=button class=cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" TYPE=button class=cssButton91 onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" TYPE=button class=cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" TYPE=button class=cssButton93 onclick="getLastPage();"> 					
  	</div>
	<br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
