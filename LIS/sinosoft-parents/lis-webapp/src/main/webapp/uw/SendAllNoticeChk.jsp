<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWSendPrintChk.jsp
//程序功能：送打印队列
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充	
			
  	//接收信息
  	// 投保单列表	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
  LCRReportPrtSet tLCRReportPrtSet = new LCRReportPrtSet();
  LCSpecSet tLCSpecSet = new LCSpecSet();
	String tProposalNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	
	String tCode = request.getParameter("NoticeType");
	String tRemark = request.getParameter("Content");
	loggerDebug("SendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	
	String tChk[] = request.getParameterValues("InpUWSpecGridChk");
	String tSerialno2[] = request.getParameterValues("UWSpecGrid6");
	String tProposalno2[] = request.getParameterValues("UWSpecGrid5");
	
    //loggerDebug("SendAllNoticeChk","*******************"+tChk.length);
  
  if(tCode.equals("82"))
   {
     for (int j = 0;j < tChk.length;j++)
       {
       	   loggerDebug("SendAllNoticeChk","*******************"+tChk[j]);
           if(tChk[j].equals("1"))           
           {
               LCSpecSchema tLCSpecSchema = new LCSpecSchema();
               loggerDebug("SendAllNoticeChk","oooooooooooooooooo"+tProposalno2[j]);
               loggerDebug("SendAllNoticeChk","oooooooooooooooooo"+tProposalno2[j]);
               tLCSpecSchema.setProposalNo(tProposalno2[j]);
               loggerDebug("SendAllNoticeChk","oooooooooooooooooo"+tSerialno2[j]);
               loggerDebug("SendAllNoticeChk","oooooooooooooooooo"+tSerialno2[j]);
               tLCSpecSchema.setSerialNo(tSerialno2[j]);
               tLCSpecSet.add(tLCSpecSchema);
           }
       }	
	 }
	boolean flag = false;
	
	if(tCode.equals("89")||tCode.equals("BQ88"))
 	{
  
	String tSerialNo[] = request.getParameterValues("QuestionTypeGridNo");
	String tLCRReportPrtCode[] = request.getParameterValues("QuestionTypeGrid1");
  int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}

     	for(int i=0;i<ChkCount;i++)
			{
			 	LCRReportPrtSchema tLCRReportPrtSchema = new LCRReportPrtSchema();
		    tLCRReportPrtSchema.setAskCode(tLCRReportPrtCode[i]);
				tLCRReportPrtSet.add(tLCRReportPrtSchema);
			}
	}
	
	if (!tProposalNo.equals("")&&!tOtherNoType.equals("")&&!tCode.equals(""))
	{
 	    tLOPRTManagerSchema.setOtherNo( tProposalNo);
	    tLOPRTManagerSchema.setOtherNoType(tOtherNoType);
	    tLOPRTManagerSchema.setCode(tCode);
	    tLOPRTManagerSchema.setRemark(tRemark);
	    loggerDebug("SendAllNoticeChk","============EdorNo:" + tEdorNo);
	    loggerDebug("SendAllNoticeChk","============EdorType:" + tEdorType);
	    if(!tEdorType.equals("") && !tEdorNo.equals("")){
	       tLOPRTManagerSchema.setStandbyFlag1(tEdorNo);
	       tLOPRTManagerSchema.setStandbyFlag2(tEdorType);
	    }	    
	    
	    flag = true;
	}
	else
	{
	    FlagStr = "Fail";
	    Content = "号码传输失败!";
	}
	
try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("MissionID",tMissionID);
    mTransferData.setNameAndValue("SubMissionID",tSubMissionID); 
    
    if(tCode.equals("89")||tCode.equals("BQ88"))
    {
    mTransferData.setNameAndValue("LCRReportPrtSet",tLCRReportPrtSet);
    }
    
    if(tCode.equals("82"))
    {
    mTransferData.setNameAndValue("LCSpecSet",tLCSpecSet);
    }
   
    tVData.add(mTransferData);
		// 数据传输
		//TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
		 String busiName="tbTbWorkFlowUI";
         BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"0000001270",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 操作失败,原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();		    
		    if (!tError.needDealError())
		    {                     
		    	Content = " 操作成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                              
		    {
		    	Content = " 操作失败,原因是:";
		    	int n = tError.getErrorCount();
    			if (n > 0)
    			{
			      for(int i = 0;i < n;i++)
			      {			        
			        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
			      }
			}
		    	FlagStr = "Fail";
		    }
		}
	}
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+".提示：异常终止!";
}
%>       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>
