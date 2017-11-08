<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuRReportChk.jsp
//程序功能：承保人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人：ln    更新日期：2008-10-23   更新原因/内容：根据新核保要求进行修改
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.xbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
	RnewRReportSchema tRnewRReportSchema = new RnewRReportSchema();
  RnewRReportItemSet tRnewRReportItemSet = new RnewRReportItemSet();
  RnewRReportItemSchema tRnewRReportItemSchema ;
  
	TransferData tTransferData = new TransferData();
	String tContNo = request.getParameter("ContNo");
	String tPrtNo = request.getParameter("PrtNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	String tCustomerName = request.getParameter("CustomerName");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tOperator = request.getParameter("Operator");
	String tFlag = request.getParameter("Flag");
	//String tRemark = request.getParameter("Remark");
	String tContente = request.getParameter("Contente");
	String tRReportReason = request.getParameter("RReportReason");

 	//tongmeng 2007-12-19 modify
 	//修改为MS的生调流程 
 	/*
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String tRReportItemCode[] = request.getParameterValues("InvestigateGrid1");
	String tRReportItemName[] = request.getParameterValues("InvestigateGrid2");
	String tRemark[] = request.getParameterValues("InvestigateGrid3");
		*/
	boolean flag = true;
	/*
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("RnewManuRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "生调资料信息录入不完整!";
		FlagStr = "Fail";
		flag = false;
	}
	else*/
	//{
		if (!tContNo.equals("")&& !tMissionID.equals(""))
		{
			tRnewRReportSchema.setContNo(tContNo);
			tRnewRReportSchema.setContente(tContente);
			//tRnewRReportSchema.setRemark(tRemark); //add ln 2008-10-23 --生调特殊要求
			tRnewRReportSchema.setName(tCustomerName);
			tRnewRReportSchema.setRReportReason(tRReportReason);
			
			//准备公共传输信息
			tTransferData.setNameAndValue("ContNo",tContNo);
			tTransferData.setNameAndValue("PrtNo",tPrtNo) ;
			tTransferData.setNameAndValue("CustomerNo",tCustomerNo) ;
			tTransferData.setNameAndValue("MissionID",tMissionID);	
			tTransferData.setNameAndValue("SubMissionID",tSubMissionID);	
			tTransferData.setNameAndValue("RnewRReportSchema",tRnewRReportSchema);	
			
			/*for(int i=0;i<ChkCount;i++)
			{
				tRnewRReportItemSchema = new RnewRReportItemSchema();
				
				tRnewRReportItemSchema.setRReportItemCode(tRReportItemCode[i]);
				tRnewRReportItemSchema.setRReportItemName(tRReportItemName[i]);
				tRnewRReportItemSchema.setRemark(tRemark[i]);
				
				tRnewRReportItemSet.add(tRnewRReportItemSchema);
			}
			
			tTransferData.setNameAndValue("RnewRReportItemSet",tRnewRReportItemSet);
			loggerDebug("RnewManuRReportChk","len="+tRnewRReportItemSet.size());*/
		}
		else
		{
			flag = false;
			Content = "数据不完整!";
		}	
		loggerDebug("RnewManuRReportChk","flag:"+flag);
		try
		{
		  	if (flag == true)
		  	{
				// 准备传输数据 VData
				VData tVData = new VData();
				tVData.add( tTransferData);
				tVData.add( tG );
				
				// 数据传输
				 String busiName="RnewRReportChkUI";
        		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        		if(tBusinessDelegate.submitData(tVData,"",busiName)==false){
        			int n = tBusinessDelegate.getCErrors().getErrorCount();
        			for (int i = 0; i < n; i++)
    					Content = " 生调录入失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
    					FlagStr = "Fail";
        		}
				/*
				RnewRReportChkUI tRnewRReportChkUI   = new RnewRReportChkUI();
				if (tRnewRReportChkUI.submitData(tVData,"") == false)
				{
					int n = tRnewRReportChkUI.mErrors.getErrorCount();
					for (int i = 0; i < n; i++)
					Content = " 生调录入失败，原因是: " + tRnewRReportChkUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}*/
				//如果在Catch中发现异常，则不从错误类中提取错误信息
				if (FlagStr == "Fail")
				{
				    tError = tBusinessDelegate.getCErrors();
				    if (!tError.needDealError())
				    {                          
				    	Content = " 生调录入成功! ";
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	Content = " 生调录入失败，原因是:" + tError.getFirstError();
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
//	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
