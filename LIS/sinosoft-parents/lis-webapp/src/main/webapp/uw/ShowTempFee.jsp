<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2007-01-04
//������  ��·��
//���¼�¼��  ������    ��������     ����ԭ��/����
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
				<td class= titleImg>������Ϣ</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''">	
		<Table  class= common>
			<tr class=common>
				<TD  class= title>Ͷ��������</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=PrtNo id="PrtNo" value="<%=PrtNo%>">
				</TD>
				<!--TD  class= title>���վݺ���</TD>
				<TD  class= input>
					<Input class=readonly readonly name=TempFeeNo>
				</TD-->
				<TD  class= title>Ͷ����λ�ͻ���</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=CustomerNo id="CustomerNo" value="<%=AppntNo%>">
				</TD>
			</TR>	
				
			<tr class=common>
				<TD  class= title>Ͷ����λ</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=CustomerName id="CustomerName" value="<%=AppntName%>">
				</TD>
				<TD  class= title>ҵ��Ա����</TD>
				<TD  class= input>
				<Input class="readonly wid" readonly name=AgentCode id="AgentCode">
				</TD>
				<TD  class= title>�����ܽ��</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=PayMoney id="PayMoney">
				</TD>
			</TR>		
		</Table>
		</div>
		</div>
		<br>
  		<Div  id= "divTempFeeSave" style= "display:''">
  			<!--�ݽ��ѱ��� -->  
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
