<%
//程序名称：ProposalApproveModify.jsp
//程序功能：复核不通过修改
//创建日期：2002-11-23 17:06:57
//创建人  ：胡博
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
	var prtNo = "<%=request.getParameter("prtNo")%>";      //个人单的查询条件.
	var type = "<%=request.getParameter("type")%>";        //类型，由ＰｒｏｐｏｓａｌＱｕｅｒｙＤｅｔａｉｌ传入
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="changeSubFloatRate.js"></SCRIPT>
  <%@include file="changeSubFloatRateInit.jsp"%>
  
  <title>保单查询 </title>
</head>

<body onload="initForm();" >
  <form method=post name=fm id=fm target="fraTitle">
    <!-- 保单信息部分 -->
    <table>
    	<tr>
    		<td class= titleImg>
    			<INPUT VALUE="修改投保单" class="cssButton" TYPE=button onclick="modifyClick();">
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      				
  	</div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
