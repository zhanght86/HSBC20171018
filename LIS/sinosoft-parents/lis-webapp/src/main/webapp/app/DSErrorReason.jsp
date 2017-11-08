<%
//程序名称：AbnormityErrAndRecordErr.jsp
//程序功能：异常件错误原因查询以及记录差错功能
//创建日期：2007-08-01 14:32:57
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
	String tPrtNo=(String)request.getParameter("prtNo");
	//tPrtNo="";
	loggerDebug("DSErrorReason","页面获得的印刷号为"+tPrtNo);
	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var prtNo="<%=tPrtNo%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="DSErrorReason.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="DSErrorReasonInit.jsp"%>
  
  <title>异常件错误原因查询以及记录差错功能</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
   <table class=common border=0 width=100%>
    	<tr>
		    <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
			<td class=titleImg align= center>异常件错误原因</td>
		</tr>
	</table>
	<hr class=line>
  	<Div  id= "divLCPol1" style= "display:  ">
      	<table  class=common>
       		<tr  class=common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
       <Div  id= "divPage1" align=center style= "display:   ">
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
  </Div> 
  	</div>	

    <table class=common border=0 width=100%>
    	<tr>
		    <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
			<td class=titleImg align= center>差错记录</td>
		</tr>
	</table>
	<hr class=line>
  	<Div  id= "divLCPol" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanErrGrid" >
  					</span> 
  			  	</td>
  			</tr>
  			<tr class=common>
  			    <td>
  			       <INPUT  VALUE="保存" class=cssButton TYPE=button onclick="submitForm();">
  			    </td>
  		   </tr> 
    	</table>    
    	<Div  id= "divPage" align=center style= "display:   ">
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage1.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage1.lastPage();">     
  </Div> 					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
