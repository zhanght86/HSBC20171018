<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
LPrtDefinitionSchema tLPrtDefinitionSchema=new LPrtDefinitionSchema();
String tBusiName = "LPrtDefinitionUI";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getValue("GI");
CErrors tError =null;
String transact ="";
String FlagStr ="";
String Content ="";
String tPrintID = "";
transact=request.getParameter("fmtransact");
tLPrtDefinitionSchema.setPrintID(request.getParameter("PrintID"));
tLPrtDefinitionSchema.setPrintName(request.getParameter("PrintName"));
tLPrtDefinitionSchema.setPrintObject(request.getParameter("PrintObject"));
tLPrtDefinitionSchema.setPrintType(request.getParameter("PrintType"));
tLPrtDefinitionSchema.setLanguageType(request.getParameter("LanguageType"));
tLPrtDefinitionSchema.setState("0");
VData tVData=new VData();
tVData.addElement(tG);
tVData.addElement(tLPrtDefinitionSchema);
if(!tBusinessDelegate.submitData(tVData,transact,tBusiName))
{
	if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
	{
		Content = "保存失败，原因是" + tBusinessDelegate.getCErrors().getFirstError();
		FlagStr = "Fail";
	}
	else
	{
		Content = "保存失败";
		FlagStr = "Fail";
	}	
}
else
{
	if(transact.equals("DELETE||MAIN"))
	{
		Content = " 删除成功!";
		FlagStr = "Fail";
	}
	else
	{
	    if(transact.equals("INSERT||MAIN"))
	  	{
	    	Content = " 保存成功! ";
	  	}
	  	if(transact.equals("UPDATE||MAIN"))
	  	{
	  		Content = " 修改成功! ";
	  	}
	  	FlagStr = "Success";
	  	tPrintID = (String)tBusinessDelegate.getResult().get(0);
	}
   	
}
%>
<html>
  <script language="javascript">
     parent.fraInterface.fm.all("PrintID").value = '<%=tPrintID%>';
     parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
  </script>    
</html>
