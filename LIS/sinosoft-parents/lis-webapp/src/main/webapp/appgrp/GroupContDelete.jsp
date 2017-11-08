<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
  //个人下个人
	//String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupContDelete.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupContDeleteInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单查询条件 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		    <td class= titleImg align= center>请输入查询条件：</td>
	    </tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
        <TR  class= common>
          <TD  class= title5>投保单号</TD>
          <TD  class= input5>
            <Input class="common wid" name=QGrpProposalNo id="QGrpProposalNo">
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>  
		    </TR>
        <TR  class= common>
          <TD  class= title5>
            <!-- 保单状态 -->
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" type = "hidden" readonly name=QState id="QState" value= "0" CodeData= "0|^0|未签单^1|已签单" onclick="showCodeListEx('State',[this,''],[1,0],'', '', '', 1);" ondblClick="showCodeListEx('State',[this,''],[1,0],'', '', '', 1);" onkeyup="showCodeListKeyEx('State',[this,''],[1,0],'', '', '', 1);">
          </TD>     
        </TR> 
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="querygrp();">查  询</a>
       <!-- <INPUT VALUE="查  询" Class="cssButton" TYPE=button onclick="querygrp();"> -->
       <INPUT type= "hidden" name= "Operator" id="Operator" value= "">
       <INPUT type= "hidden" name= "GrpContNo" id="GrpContNo" value= "">
       <INPUT type= "hidden" name= "PrtNo" id="PrtNo" value= "">
    <!-- 查询未过集体单（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 集体投保单查询结果
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      	<table  class= common>
      		<INPUT VALUE="首  页" Class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      		<INPUT VALUE="上一页" Class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      		<INPUT VALUE="下一页" Class="cssButton92" TYPE=button onclick="getNextPage();"> 
      		<INPUT VALUE="尾  页" Class="cssButton93" TYPE=button onclick="getLastPage();">    	
    	</table>
	</Div>
  <a href="javascript:void(0)" class=button onclick="deleteGrpCont();">团单整单删除</a>
  <a href="javascript:void(0)" class=button onclick="deleteCont();">删除被保险人</a>
  <!-- <INPUT VALUE="团单整单删除" Class="cssButton" TYPE=button onclick="deleteGrpCont();"> 
  <INPUT VALUE="删除被保险人" Class="cssButton" TYPE=button onclick="deleteCont();"> --> 
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <script>changecolor(); </script>
</body>
</html>
