
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
  	LCSpecSchema tLCSpecSchema = new LCSpecSchema();
  	LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
  	TransferData tTransferData = new TransferData();
        String tContNo = request.getParameter("ContNo");
	
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalno = request.getParameter("proposalno");
	String tSerialno = request.getParameter("serialno");
	
	
	loggerDebug("UWGrpManuSpecChk","PrtNo:"+tPrtNo);
	loggerDebug("UWGrpManuSpecChk","ContNo:"+tContNo);
	loggerDebug("UWGrpManuSpecChk","remark:"+tRemark);
	loggerDebug("UWGrpManuSpecChk","PolNo:"+tPolNo);
	loggerDebug("UWGrpManuSpecChk","operate:"+tProposalno);
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "请录入承保特别约定信息或承保备注信息!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null )
	      {
		   //准备特约信息
		   	tLCSpecSchema.setContNo(tContNo); 
		   	//tLCSpecSchema.setSpecType("1");
		   	
		   	tLCSpecSchema.setSpecContent(tRemark);
		   	tLCSpecSchema.setSpecType("1");
		   	tLCSpecSchema.setSpecCode("1");
		   //准备特约原因
		tLCUWMasterSchema.setSpecReason(tSpecReason);
				   	
                
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
		tVData.add(tLCSpecSchema);
		tVData.add( tLCUWMasterSchema );
		TransferData mTransferData = new TransferData();
		tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("Proposalno",tProposalno);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tVData.add(tTransferData);
		tVData.add(tPolNo);
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

