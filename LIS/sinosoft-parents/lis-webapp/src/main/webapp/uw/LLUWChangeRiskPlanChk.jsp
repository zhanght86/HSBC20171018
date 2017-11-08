
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
  	LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
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
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	loggerDebug("LLUWChangeRiskPlanChk","PrtNo:"+tPrtNo);
	loggerDebug("LLUWChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("LLUWChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("LLUWChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("LLUWChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("LLUWChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("LLUWChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("LLUWChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("LLUWChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("LLUWChangeRiskPlanChk","SpecCode:"+tSpecCode);
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
		   	tLLUWSpecMasterSchema.setContNo(tContNo); 
		   	tLLUWSpecMasterSchema.setClmNo(tClmNo);
		   	//tLCSpecSchema.setSpecType("1");
		   	tLLUWSpecMasterSchema.setProposalContNo(tProposalContNo);
		   	tLLUWSpecMasterSchema.setSpecContent(tRemark);
		   	tLLUWSpecMasterSchema.setClmNo(tClmNo);
		   	tLLUWSpecMasterSchema.setBatNo(tBatNo);
		   	tLLUWSpecMasterSchema.setSpecType(tSpecType);
		   	tLLUWSpecMasterSchema.setSpecCode(tSpecCode);
		   	tLLUWSpecMasterSchema.setSerialNo(tSerialno);
		   	tLLUWSpecMasterSchema.setNeedPrint(tNeedPrintFlag);

		   //准备特约原因
		tLCIndUWMasterSchema.setSpecReason(tSpecReason);
		//tongmeng 2007-12-17 add
		//记录特约原因
		tLLUWSpecMasterSchema.setSpecReason(tSpecReason);	
		tLLUWSpecMasterSchema.setCustomerNo(tInsuredNo);            
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
		tVData.add(tLLUWSpecMasterSchema);
		tVData.add( tLCIndUWMasterSchema );
		TransferData mTransferData = new TransferData();
		//tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("SerialNo",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// 数据传输
		/*LLUWSpecUI tLLUWSpecUI   = new LLUWSpecUI();
		if (!tLLUWSpecUI.submitData(tVData,""))
		  {     
		        
			int n = tLLUWSpecUI.mErrors.getErrorCount();
			Content = " 核保特约失败，原因是: " + tLLUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tLLUWSpecUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 续保核保特约成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}*/
		String busiName="LLUWSpecUI";
		  String mDescType="保存";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,"",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"失败";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"成功! ";
			      	FlagStr = "Succ";  
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

