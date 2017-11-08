<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：AppntChkCustomerUnionSave.jsp
//程序功能：
//创建日期：2005-04-20 10:49:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strCustomerNo_OLD = request.getParameter("CustomerNo_NEW");
	String strCustomerNo_NEW = request.getParameter("CustomerNo_OLD");
  
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("CustomerNo_NEW", strCustomerNo_OLD);
    tTransferData.setNameAndValue("CustomerNo_OLD", strCustomerNo_NEW);
    
    CustomerUnionUI tCustomerUnionUI = new CustomerUnionUI();
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);   
		tCustomerUnionUI.submitData(tVData, "CUSTOMER|UNION");
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      loggerDebug("ClientMergeSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tCustomerUnionUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		      Content ="保存成功！";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = "保存失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
  %>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
