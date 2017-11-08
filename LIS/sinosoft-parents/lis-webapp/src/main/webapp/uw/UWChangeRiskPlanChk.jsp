
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecChk.jsp
//程序功能：人工核保特约承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
//modify by zhangxing
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
   } 
    //接收信息
  	LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	TransferData tTransferData = new TransferData();
  String tContNo = request.getParameter("ContNo");
	
    String tNeedPrintFlag = request.getParameter("NeedPrintFlag"); //2008-11-27 ln add
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	//String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalContNo = request.getParameter("proposalContNo");
	String tSerialno = request.getParameter("serialno");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tSpecType = request.getParameter("HealthSpecTemp");
	String tSpecCode = request.getParameter("SpecTemp");
	loggerDebug("UWChangeRiskPlanChk","PrtNo:"+tPrtNo);
	loggerDebug("UWChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("UWChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("UWChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("UWChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("UWChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("UWChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("UWChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("UWChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("UWChangeRiskPlanChk","SpecCode:"+tSpecCode);
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "请录入续保特别约定信息或续保备注信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null && tMissionID != null && tSubMissionID != null)
	      {
		   //准备特约信息
		   	tLCCSpecSchema.setContNo(tContNo); 
		   	//tLCSpecSchema.setSpecType("1");
		   	tLCCSpecSchema.setProposalContNo(tProposalContNo);
		   	tLCCSpecSchema.setSpecContent(tRemark);
		   	tLCCSpecSchema.setSpecType(tSpecType);
		   	tLCCSpecSchema.setSpecCode(tSpecCode);
		   	tLCCSpecSchema.setNeedPrint(tNeedPrintFlag);

		   //准备特约原因
		tLCIndUWMasterSchema.setSpecReason(tSpecReason);
		//tongmeng 2007-12-17 add
		//记录特约原因
		tLCCSpecSchema.setSpecReason(tSpecReason);	
    tLCCSpecSchema.setCustomerNo(tInsuredNo);            
		   } // End of if
		  else
		  {
			Content = "传输数据失败!";
			flag = false;
		  }
	}
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLCCSpecSchema);
		tVData.add( tLCIndUWMasterSchema );
		TransferData mTransferData = new TransferData();
		//tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// 数据传输
		UWSpecUI tUWSpecUI   = new UWSpecUI();
		if (!tUWSpecUI.submitData(tVData,""))
		  {     
		        
			int n = tUWSpecUI.mErrors.getErrorCount();
			Content = " 核保特约失败，原因是: " + tUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWSpecUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 续保核保特约成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"提示：异常终止!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

