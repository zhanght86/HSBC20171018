<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：BackPolQuery.jsp
//程序功能：撤单申请查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
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
</script>
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<title>问题件查询 </title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="BackPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BackPolQueryInit.jsp"%>
  
</head>
<body  onload="initForm('<%=tProposalNo%>');" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 撤单申请内容：
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanBackPolGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    </div>
      
  <table width="121%" height="37%">
    <TR  class= common> 
      <TD width="100%" height="5%"  class= title> 申请说明 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea id="Content" name="Content" cols="135" rows="10" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--读取信息-->
    <input type= "hidden" id="Flag" name= "Flag" value="">
    <input type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
    <input type= "hidden" id="Type" name= "Type" value="">
  </p>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
