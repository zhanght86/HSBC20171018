<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuRReportChk.jsp
//程序功能：承保人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：zhangxing
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
  <%@page import="com.sinosoft.workflowengine.*"%>
   <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput(); 
	tG=(GlobalInput)session.getValue("GI");  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  	//接收信息
  	// 投保单列表
	LCReinsureReportSchema tLCReinsureReportSchema = new LCReinsureReportSchema(); 
 
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tReportRemark = request.getParameter("ReportRemark");

		
	boolean flag = true;
	
	

		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tLCReinsureReportSchema.setContNo(tContNo);			
			tLCReinsureReportSchema.setReportRemark(tReportRemark);
			
			//准备公共传输信息
			tTransferData.setNameAndValue("ContNo",tContNo);
		
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("LCReinsureReportSchema",tLCReinsureReportSchema);	
					
			
		}
		else
		{
			flag = false;
			Content = "数据不完整!";
		}	
		loggerDebug("UWManuUpReportChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// 数据传输
				String busiName="tbTbWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			//	TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
				if (!tBusinessDelegate.submitData(tVData,"0000001134",busiName)) //执行承保核保生调工作流节点0000001134
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("UWManuUpReportChk","ErrorCount:"+n ) ;
					Content = " 新契约再保呈报失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 新契约再保呈报成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 新契约再保呈报失败，原因是:" + tError.getFirstError();
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
