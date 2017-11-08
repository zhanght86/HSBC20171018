<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalDutyInit.jsp"%>
  <title>保单责任信息 </title>
</head>
<body  onload="initForm('<%=tPolNo%>');" >
  <form method=post name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 责任信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 责任信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCDuty1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- 保费项信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPrem1);">
    		</td>
    		<td class= titleImg>
    			 保费项信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPrem1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPremGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- 领取项信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGet1);">
    		</td>
    		<td class= titleImg>
    			 领取项信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCGet1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanGetGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
          <INPUT VALUE="返回" TYPE=button onclick="window.close();"> 					
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>

</html>
