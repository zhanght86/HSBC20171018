<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
LPrtTempleteSchema tLPrtTempleteSchema=new LPrtTempleteSchema();
String tBusiName = "LPrtTemApproveUI";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
GlobalInput tG = new GlobalInput(); 
TransferData tTransferData = new TransferData();
tG=(GlobalInput)session.getValue("GI");
CErrors tError =null;
String transact ="";
String FlagStr ="";
String Content ="";
//Ӧ�ø�·��
String tPath = application.getRealPath("").replace('\\','/');
if(!tPath.endsWith("/"))
{
	tPath += "/";
}
tTransferData.setNameAndValue("Path",tPath);
transact=request.getParameter("fmtransact");
tLPrtTempleteSchema.setTempleteID(request.getParameter("TempleteID"));
tLPrtTempleteSchema.setState(request.getParameter("State1"));
tLPrtTempleteSchema.setRemark(request.getParameter("Remark"));
VData tVData=new VData();
tVData.addElement(tG);
tVData.addElement(tTransferData);
tVData.addElement(tLPrtTempleteSchema);

if(!tBusinessDelegate.submitData(tVData,transact,tBusiName))
{
	if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
	{
		Content = "ģ������ʧ�ܣ�ԭ����" + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else
	{
		Content = "ģ������ʧ�ܣ�";
		FlagStr = "Fail";
	}	
}
else
{
	Content = "ģ�������ɹ���";
	FlagStr = "Success";  
}		
%>
<html>
<script language="javascript">
     parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script> 
</html>
