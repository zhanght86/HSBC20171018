<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//程序名称：UWApp.jsp
//程序功能：既往投保信息查询
//创建日期：2002-06-19 11:10:36
//创建人  ： WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppInit.jsp"%>
  <title>既往投保信息 </title>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tCustomerNo%>','<%=tType%>');" >
  <form method=post name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 既往投保信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCont);">
    		</td>
    		<td class= titleImg>
    			 既往合同信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCCont" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanContGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">		
	</div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 既往险种保单信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="首  页" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ProposalNoHide2" value= "">
      <INPUT type= "hidden" name= "InsureNoHide" value= "">
      <INPUT type= "hidden" name= "AppntNoHide" value= "">
      <INPUT type= "hidden" name= "ContNoHide" value= "">
      <!--hr>
      </hr>					
      <INPUT VALUE="保单明细信息" class= cssButton TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="以往核保记录" class= cssButton TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="最终核保信息" class= cssButton TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="体检资料信息" class= cssButton TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="核保分析报告" class= cssButton TYPE=button onclick="showReport();">
      <hr>
      </hr!-->					
	</div>
	<p>
        <INPUT VALUE="返  回" class= cssButton TYPE=button onclick="parent.close();"> 					
    </P>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
