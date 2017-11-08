<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ViewErrLogBonusAssign.jsp
//程序功能：分红处理
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<%	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="ViewErrLogBonusAssign.js"></SCRIPT>
  	<%@include file="ViewErrLogBonusAssignInit.jsp"%>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>查询条件</title>
</head>
<body onload="initErrLogBonusGrid()">
  <form method=post name=fm id=fm target="fraSubmit" action= "">
    <table class=common>
		<td class=common><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img></td>
		<td class=titleImg>请输入查询条件：</td>
	</table>
	<div class=maxbox1>
    <table  class= common align=center>
        <TR  class= common>
            <TD  class= title5>保单号</TD>
            <TD  class= input5><Input class="common wid" name=ContNo id=ContNo></TD>
			<TD  class= title5>领取方式	</TD>
	        <TD  class= input5><Input class="common wid" name=GetMode id=GetMode ></TD>                    
        </TR>
        <TR  class= common>
            <TD  class= title5>入机时间</TD>
            <TD  class= input5><Input class="common wid" name=MakeDate id=MakeDate></TD>   
			<TD  class= title5></TD>
			<TD  class= input5></TD>
        </TR>
    </table>
	</div>
  <p>
   <INPUT VALUE="查    询" class=cssButton TYPE=button onclick="easyQueryClick();"> 	
  </p>
 
  	<Div  id= "divErrLogBonus" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanErrLogBonusGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页"  class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页"  class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页"  class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>  
  
 </form>
 
 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
