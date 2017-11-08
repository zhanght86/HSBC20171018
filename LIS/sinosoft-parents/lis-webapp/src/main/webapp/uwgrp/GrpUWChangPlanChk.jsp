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
 
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		
	    String tGrpContNo = request.getParameter("GrpContNo");
	    String tMissionID = request.getParameter("MissionID");
	    String tSubMissionID = request.getParameter("SubMissionID");
	    String tRemark = request.getParameter("ChangeIdea");
		tLCGrpContSchema.setRemark(tRemark);
		tLCGrpContSchema.setGrpContNo(tGrpContNo);
		tLCGrpContSet.add(tLCGrpContSchema);
		
		
		 TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("GrpContNo",tGrpContNo);	  
	   tTransferData.setNameAndValue("MissionID",tMissionID );
	   tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
	  
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLCGrpContSet);
		tVData.add(tTransferData);
		 GrpTbWorkFlowUI tGrpTbWorkFlowUI   = new GrpTbWorkFlowUI();
		if (tGrpTbWorkFlowUI.submitData(tVData,"0000002301") == false)
	
					{						
						Content = " 承保计划变更原因录入失败，原因是: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = " 承保计划变更成功！";
						FlagStr = "Succ";
					}
	
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
