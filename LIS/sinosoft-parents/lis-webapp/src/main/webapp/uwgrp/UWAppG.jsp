<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//程序名称：UWAppG.jsp
//程序功能：既往投保信息查询
//创建日期：2004-11-27 11:10:36
//创建人  ： ZhangRong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWAppG.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppGInit.jsp"%>
  <title>既往投保信息 </title>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tInsureNo%>','<%=tContNo%>');" >
  <form method=post name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 既往投保信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 既往投保信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPol1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class="cssButton" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class="cssButton" TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ContNo" value= "">
      <INPUT type= "hidden" name= "InsureNoHide" value= "">
      <hr>
      </hr>					
      <!--INPUT VALUE="保单明细信息" class= common TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="以往核保记录" class= common TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="最终核保信息" class= common TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="体检资料信息" class= common TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="核保分析报告" class= common TYPE=button onclick="showReport();"-->
      <!--hr>
      </hr-->					
	</div>
	<p>
        <INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="parent.close();"> 					
    </P>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
