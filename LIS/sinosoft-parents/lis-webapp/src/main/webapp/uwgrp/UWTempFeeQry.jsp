<%
//�������ƣ�UWTempFeeQry.jsp
//�����ܣ��˱������շѲ�ѯ
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
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
				<td class= titleImg>������Ϣ</td>
			</tr>
		</table>	
		<Table  class= common>
			<tr class=common>
				<TD  class= title>Ͷ��������</TD>
				<TD  class= input>
					<Input class=readonly readonly name=PrtNo value="<%=PrtNo%>">
				</TD>
				<!--TD  class= title>���վݺ���</TD>
				<TD  class= input>
					<Input class=readonly readonly name=TempFeeNo>
				</TD-->
				<TD  class= title>Ͷ���˿ͻ���</TD>
				<TD  class= input>
					<Input class= readonly readonly name=CustomerNo value="<%=AppntNo%>">
				</TD>
			</TR>	
				
			<tr class=common>
				<TD  class= title>Ͷ��������</TD>
				<TD  class= input>
					<Input class=readonly readonly name=CustomerName value="">
				</TD>
				<TD  class= title>ҵ��Ա����</TD>
				<TD  class= input>
					<Input class=readonly readonly name=AgentCode>
				</TD>
				<TD  class= title>�����ܽ��</TD>
				<TD  class= input>
					<Input class= readonly readonly name=PayMoney>
				</TD>
			</TR>		
		</table>
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
</Form>
