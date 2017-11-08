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
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
   
  CErrors tError = null;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  VData tVData = new VData();
  
	String strCustomerNo_NEW = request.getParameter("CustomerNo_NEW");
	String strCustomerNo_OLD = request.getParameter("CustomerNo_OLD");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tCustomerType = request.getParameter("CustomerType");
	String tPrtNo =request.getParameter("PrtNo");
	String tForceFlag = request.getParameter("ForceFlag");
  
    TransferData tTransferData = new TransferData();
    loggerDebug("CustomerMergeSave","新录入的客户号为："+strCustomerNo_OLD);
    loggerDebug("CustomerMergeSave","合并后的客户号为："+strCustomerNo_NEW);
    loggerDebug("CustomerMergeSave","是否强制关联："+tForceFlag);
    tTransferData.setNameAndValue("CustomerNo_NEW", strCustomerNo_NEW);
    tTransferData.setNameAndValue("CustomerNo_OLD", strCustomerNo_OLD);
    tTransferData.setNameAndValue("MissionID", tMissionID);
    tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
    tTransferData.setNameAndValue("CustomerType", tCustomerType);
    tTransferData.setNameAndValue("PrtNo", tPrtNo);
    tTransferData.setNameAndValue("ForceFlag", tForceFlag);
    
   // CustomerUnionUI tCustomerUnionUI = new CustomerUnionUI();
    String busiName="cbcheckCustomerUnionUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);   
		tBusinessDelegate.submitData(tVData, "CUSTOMER|UNION",busiName);
	}
	catch(Exception ex)
	{
	      Content = "保存失败，原因是:" + ex.toString();
	      loggerDebug("CustomerMergeSave","aaaa"+ex.toString());
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
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
	//alert("ccccccccccc");
	//alert("ddddddddddd");
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
	//top.opener.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
