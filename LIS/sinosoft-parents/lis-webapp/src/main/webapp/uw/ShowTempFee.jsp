<%
//程序名称：
//程序功能：
//创建日期：2007-01-04
//创建人  ：路明
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%> 
<%request.setCharacterEncoding("GBK");%>
<%
  
	String PrtNo = "";
	String AppntNo = "";
	String AppntName = "";
	String ContType = "";
	String RiskCode= "";
	
	PrtNo = request.getParameter("Prtno");
	AppntNo = request.getParameter("AppntNo");
	AppntName = new String (request.getParameter("AppntName").getBytes("8859_1"),"GBK");  
	ContType = request.getParameter("ContType");
	if (ContType == null || ContType.equals("null") || "".equals(ContType))
	    ContType = "1";
	loggerDebug("ShowTempFee","ContType=" + ContType);
	loggerDebug("ShowTempFee","aasfas="+AppntName);
	loggerDebug("ShowTempFee","PrtNo===================="+PrtNo);
%>
<script>
	var PrtNo = "<%=PrtNo%>";  

</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src="ShowTempFee.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="ShowTempFeeInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form method=post name=fm id="fm" target="fraSubmit">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class= titleImg>基本信息</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''">	
		<Table  class= common>
			<tr class=common>
				<TD  class= title>投保单号码</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=PrtNo id="PrtNo" value="<%=PrtNo%>">
				</TD>
				<!--TD  class= title>暂收据号码</TD>
				<TD  class= input>
					<Input class=readonly readonly name=TempFeeNo>
				</TD-->
				<TD  class= title>投保单位客户号</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=CustomerNo id="CustomerNo" value="<%=AppntNo%>">
				</TD>
			</TR>	
				
			<tr class=common>
				<TD  class= title>投保单位</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=CustomerName id="CustomerName" value="<%=AppntName%>">
				</TD>
				<TD  class= title>业务员代码</TD>
				<TD  class= input>
				<Input class="readonly wid" readonly name=AgentCode id="AgentCode">
				</TD>
				<TD  class= title>交费总金额</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=PayMoney id="PayMoney">
				</TD>
			</TR>		
		</Table>
		</div>
		</div>
		<br>
  		<Div  id= "divTempFeeSave" style= "display:''">
  			<!--暂交费表部分 -->  
    		<Table  class= common>
				<tr>
    	 			<td text-align: left colSpan=1>
	 					<span id="spanTempToGrid" ></span> 
					</td>
       			</tr>
				<tr>
    	 			<td text-align: left colSpan=1>
	 					<span id="spanTempClassToGrid" ></span> 
					</td>
       			</tr>
       		</table>
    	</Div>
    <input type=hidden name=ContType id="ContType" value="<%=ContType%>">
</Form>
<br>
<br>
<br>
<br>
