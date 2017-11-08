
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
  <%@page import="com.sinosoft.lis.bq.*"%>
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
  	LPCSpecSchema tLPCSpecSchema = new LPCSpecSchema();
  	LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();
  	TransferData tTransferData = new TransferData();
  	String tContNo = request.getParameter("ContNo");
	
    String tNeedPrintFlag = request.getParameter("NeedPrintFlag"); //2008-11-27 ln add
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	//String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalContNo = request.getParameter("proposalContNo");
	String tSerialno = request.getParameter("serialno");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tSpecType = request.getParameter("HealthSpecTemp");
	String tSpecCode = request.getParameter("SpecTemp");
	String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	loggerDebug("BQChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("BQChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("BQChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("BQChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("BQChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("BQChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("BQChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("BQChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("BQChangeRiskPlanChk","SpecCode:"+tSpecCode);
	if (tContNo == "" || (tRemark == "" ) ){
		Content = "请录入续保特别约定信息或续保备注信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else{     
	    if(tContNo != null &&  tMissionID != null && tSubMissionID != null){
		   //准备特约信息
		   	tLPCSpecSchema.setContNo(tContNo); 
		   	tLPCSpecSchema.setCustomerNo(tInsuredNo);
		   	tLPCSpecSchema.setProposalContNo(tProposalContNo);
		   	tLPCSpecSchema.setSpecContent(tRemark);
		   	tLPCSpecSchema.setSpecType(tSpecType);
		   	tLPCSpecSchema.setSpecCode(tSpecCode);
		   	tLPCSpecSchema.setNeedPrint(tNeedPrintFlag);
		   	tLPCSpecSchema.setPrtFlag("1");
			//tongmeng 2007-12-17 add
			//记录特约原因
			tLPCSpecSchema.setSpecReason(tSpecReason);	
			            
		}else{
			Content = "传输数据失败!";
			flag = false;
		}
	}
try{
  	if (flag == true){
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLPCSpecSchema);
		tVData.add( tLPIndUWMasterSchema );
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		tTransferData.setNameAndValue("EdorAcceptNo",tEdorAcceptNo);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("EdorType",tEdorType);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// 数据传输
		BQSpecUI tBQSpecUI   = new BQSpecUI();
		if (!tBQSpecUI.submitData(tVData,"")){     
		        
			int n = tBQSpecUI.mErrors.getErrorCount();
			Content = " 核保特约失败，原因是: " + tBQSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail"){
		    tError = tBQSpecUI.mErrors;
		    if (!tError.needDealError()){                          
		    	Content = " 续保核保特约成功! ";
		    	FlagStr = "Succ";
		    }
		    else{
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e){
	e.printStackTrace();
	Content = Content.trim()+"提示：异常终止!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

