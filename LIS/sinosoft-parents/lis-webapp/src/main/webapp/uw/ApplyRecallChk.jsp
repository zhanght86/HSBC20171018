<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ApplyRecallChk.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	    //输出参数
	    CErrors tError = null;
	    String FlagStr = "Fail";
	    String Content = "";

		GlobalInput tG = new GlobalInput();  
		tG=(GlobalInput)session.getValue("GI");
	
		String tPrtNo = request.getParameter("PrtNo");
		String tContNo = request.getParameter("ContNoH");
		String tContent = request.getParameter("Content");
		//String tApplyType = request.getParameter("ApplyType");
		String tContType = request.getParameter("PolType");
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWWithDReasonCode","");
		tTransferData.setNameAndValue("UWWithDReason","");
		tTransferData.setNameAndValue("Content",tContent);
		tTransferData.setNameAndValue("ContNo",tContNo);	
	
	  	//补充附加险表			    				
		LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();		
		tLCApplyRecallPolSchema.setRemark(tContent);
		tLCApplyRecallPolSchema.setPrtNo(tPrtNo);	
		tLCApplyRecallPolSchema.setApplyType("0"); //撤单	
			
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tTransferData);
		tVData.add( tLCApplyRecallPolSchema);
		tVData.add( tG );
		
		// 数据传输
		if(tContType.equals("1"))
		{
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
		 }
		 else if(tContType.equals("2"))
		 {
		 GrpApplyRecallPolUI tGrpApplyRecallPolUI = new GrpApplyRecallPolUI();
		if (tGrpApplyRecallPolUI.submitData(tVData,"") == false)
		{
			Content = " 保存失败，原因是: " + tGrpApplyRecallPolUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
        else     
		{                          
		    Content = " 保存成功! ";
		    FlagStr = "Succ";
		 }
		 }

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
