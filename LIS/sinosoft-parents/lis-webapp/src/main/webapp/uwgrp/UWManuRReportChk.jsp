<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuRReportChk.jsp
//程序功能：承保人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
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
	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
  LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
  LCRReportItemSchema tLCRReportItemSchema ;
  
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tCustomerName = request.getParameter("CustomerName");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tOperator = request.getParameter("Operator");
	String tFlag = request.getParameter("Flag");
	String tContente = request.getParameter("Contente");
	String tRReportReason = request.getParameter("RReportReason");

	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String tRReportItemCode[] = request.getParameterValues("InvestigateGrid1");
	String tRReportItemName[] = request.getParameterValues("InvestigateGrid2");
		
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("UWManuRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "生调资料信息录入不完整!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{
		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tLCRReportSchema.setContNo(tContNo);
			tLCRReportSchema.setContente(tContente);
			tLCRReportSchema.setName(tCustomerName);
			tLCRReportSchema.setRReportReason(tRReportReason);
			
			//准备公共传输信息
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
			tTransferData.setNameAndValue("CustomerNo",tCustomerNo) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);	
			for(int i=0;i<ChkCount;i++)
			{
				tLCRReportItemSchema = new LCRReportItemSchema();
				
				tLCRReportItemSchema.setRReportItemCode(tRReportItemCode[i]);
				tLCRReportItemSchema.setRReportItemName(tRReportItemName[i]);
				
				tLCRReportItemSet.add(tLCRReportItemSchema);
			}
			
			tTransferData.setNameAndValue("LCRReportItemSet",tLCRReportItemSet);
			loggerDebug("UWManuRReportChk","len="+tLCRReportItemSet.size());
		}
		else
		{
			flag = false;
			Content = "数据不完整!";
		}	
		loggerDebug("UWManuRReportChk","flag:"+flag);
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
				if (!tBusinessDelegate.submitData(tVData,"0000001104",busiName)) //执行承保核保生调工作流节点0000001104
				{
					int n = tBusinessDelegate.getCErrors().getErrorCount();
					loggerDebug("UWManuRReportChk","ErrorCount:"+n ) ;
					Content = " 发承保生调通知书失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 承保人工核保生调成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 承保人工核保生调失败，原因是:" + tError.getFirstError();
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
	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
