<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSub.jsp
//程序功能：核保轨迹查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="UWSub.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWSubInit.jsp"%>
  <title>以往核保记录 </title>
</head>
<body  onload="initForm('<%=tContNo%>', '<%=tPolNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWSubQuery.jsp">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 以往核保记录部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 以往核保记录
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCUWSub1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWSubGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class="cssButton" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class="cssButton" TYPE=button onclick="getLastPage();"> 
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">				
      <INPUT type= "hidden" name= "PolNo" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
	</div>
	<p>
        <INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="parent.close();"> 					
    </P>
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
