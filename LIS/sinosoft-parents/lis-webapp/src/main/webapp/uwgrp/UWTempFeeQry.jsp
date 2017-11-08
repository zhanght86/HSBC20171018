<%
//程序名称：UWTempFeeQry.jsp
//程序功能：核保财务收费查询
//创建日期：2002-07-12 
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%> 
<%
  
	String PrtNo = "";
	String AppntNo = "";
	String AppntName = "";
	PrtNo = request.getParameter("PrtNo");
	
	AppntNo = request.getParameter("AppntNo");
	AppntName = StrTool.GBKToUnicode(request.getParameter("AppntName")); 
	loggerDebug("UWTempFeeQry","aasfas="+AppntName);
%>
<script>
	var CustomerNo = "<%=AppntNo%>";  
	
</script>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>   
<SCRIPT src="UWTempFeeQry.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="UWTempFeeQryInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form method=post name=fm target="fraSubmit">
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>基本信息</td>
			</tr>
		</table>	
		<Table  class= common>
			<tr class=common>
				<TD  class= title>投保单号码</TD>
				<TD  class= input>
					<Input class=readonly readonly name=PrtNo value="<%=PrtNo%>">
				</TD>
				<!--TD  class= title>暂收据号码</TD>
				<TD  class= input>
					<Input class=readonly readonly name=TempFeeNo>
				</TD-->
				<TD  class= title>投保人客户号</TD>
				<TD  class= input>
					<Input class= readonly readonly name=CustomerNo value="<%=AppntNo%>">
				</TD>
			</TR>	
				
			<tr class=common>
				<TD  class= title>投保人姓名</TD>
				<TD  class= input>
					<Input class=readonly readonly name=CustomerName value="">
				</TD>
				<TD  class= title>业务员代码</TD>
				<TD  class= input>
					<Input class=readonly readonly name=AgentCode>
				</TD>
				<TD  class= title>交费总金额</TD>
				<TD  class= input>
					<Input class= readonly readonly name=PayMoney>
				</TD>
			</TR>		
		</table>
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
</Form>
