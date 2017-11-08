<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：WithdrawContChk.jsp
//程序功能：撤单
//创建日期：2008-09-28 11:10:36
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
	    //输出参数
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";

		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getValue("GI");
	
		String tPrtNo = request.getParameter("PrtNoH");
		String tContNo = request.getParameter("ContNoH");
		String tContent = request.getParameter("Content");
		String tUWWithDReasonCode = request.getParameter("UWWithDReasonCode");
		String tUWWithDReason = request.getParameter("UWWithDReason");
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWWithDReasonCode",tUWWithDReasonCode);
		tTransferData.setNameAndValue("UWWithDReason",tUWWithDReason);
		tTransferData.setNameAndValue("Content",tContent);
		tTransferData.setNameAndValue("ContNo",tContNo);
	
	
	  	//补充附加险表			    				
		LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();		
		tLCApplyRecallPolSchema.setRemark(tContent);
		tLCApplyRecallPolSchema.setPrtNo(tPrtNo);	
		tLCApplyRecallPolSchema.setApplyType("0"); //撤单	
			
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add( tLCApplyRecallPolSchema);
		tVData.add( tG );
		
		// 数据传输
		ApplyRecallPolUI tApplyRecallPolUI   = new ApplyRecallPolUI();
		if (tApplyRecallPolUI.submitData(tVData,"") == false)
		{
			Content = " 保存失败，原因是: " + tApplyRecallPolUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else     
		{                          
		    Content = " 保存成功! ";
		    FlagStr = "Succ";
		 }

%>                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
