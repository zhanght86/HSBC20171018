<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuHealthQChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  String tGrpContNo = "000000000000";
  String tPrtNo = request.getParameter("ContNo");
 	String tContNo = request.getParameter("ContNo");
 	String tPrtSeq=request.getParameter("PrtSeq");

 	String tProposalContNo =request.getParameter("ContNo");
 	
 	String tReplyContente = request.getParameter("ReplyContente");
 	String tRReportFee = request.getParameter("RReportFee");
 	loggerDebug("RReportQuerySave","ReplyContente = :"+tReplyContente);
 	
 	String tMissionID = request.getParameter("MissionID");

 	String tSubMissionID = request.getParameter("SubMissionID");
  
 	
 	
 	
 	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
 	LCRReportSet tLCRReportSet = new LCRReportSet();
 	tLCRReportSchema.setReplyContente(tReplyContente);
 	tLCRReportSchema.setRReportFee(tRReportFee);
 	tLCRReportSchema.setProposalContNo(tContNo);
 	tLCRReportSchema.setPrtSeq(tPrtSeq);

 	
 	TransferData mTransferData = new TransferData();
 	
 	mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
 	
 	mTransferData.setNameAndValue("PrtNo",tPrtNo);
 	mTransferData.setNameAndValue("ContNo",tContNo);
 	mTransferData.setNameAndValue("PrtSeq",tPrtSeq);
 
 	
 	mTransferData.setNameAndValue("MissionID",tMissionID);
 	mTransferData.setNameAndValue("SubMissionID",tSubMissionID);


		
		// 准备传输数据 VData
		VData tVData = new VData();
		
		FlagStr="";
  	
		tVData.add(tG);

		tVData.add(mTransferData);
	
		
		String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

     // TbWorkFlowUI  tTbWorkFlowUI = new TbWorkFlowUI();
		
		try{
			loggerDebug("RReportQuerySave","this will save the data!!!");

			tBusinessDelegate.submitData(tVData,"0000001120",busiName);
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
		    	}
		}

	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
