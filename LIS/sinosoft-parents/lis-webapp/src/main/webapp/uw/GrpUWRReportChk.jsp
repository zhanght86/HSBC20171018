<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealthChk.jsp
//程序功能：承保人工期调体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput=(GlobalInput)session.getValue("GI");	  
  if(tGlobalInput == null) {
	out.println("session has expired");
	return;
  }

  
  	// 投保单列表
	
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	
	LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tProposalContNo = request.getParameter("ContNo");
	
	
	
	
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String thealthcode[] = request.getParameterValues("InvestigateGrid1");
	String thealthname[] = request.getParameterValues("InvestigateGrid2");
	
	
	loggerDebug("GrpUWRReportChk","ContNo:"+tContNo);
	loggerDebug("GrpUWRReportChk","ProposalContNo:"+tProposalContNo);
	
	
	
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}
	loggerDebug("GrpUWRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
		Content = "契调资料信息录入不完整!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpUWRReportChk","111");
	}
	else
	{
		loggerDebug("GrpUWRReportChk","222");
	    //体检资料一
		    tLCGrpContSchema.setGrpContNo(tContNo);
		    tLCGrpContSchema.setProposalGrpContNo(tContNo);	    		
	    	
	    	
	    	
	    	tLCGrpContSet.add(tLCGrpContSchema);

	    	loggerDebug("GrpUWRReportChk","chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals(""))
				{
		  			LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
		  			tLCRReportItemSchema.setContNo(tContNo);
		  			tLCRReportItemSchema.setProposalContNo(tContNo);
					tLCRReportItemSchema.setRReportItemCode( thealthcode[i]);
					tLCRReportItemSchema.setRReportItemName( thealthname[i]);
					   	    
			    	tLCRReportItemSet.add( tLCRReportItemSchema );
			        loggerDebug("GrpUWRReportChk","i:"+i);
	    		    loggerDebug("GrpUWRReportChk","healthcode:"+thealthcode[i]);	    	
			   		flag = true;
				}
			}
			    
		}
		else
		{
			Content = "传输数据失败!";
			flag = false;
		}
	}
	
	loggerDebug("GrpUWRReportChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
	   VData tVData = new VData();
	   
       
	   tVData.add(tGlobalInput);
	   tVData.add(tLCGrpContSet);
	   tVData.add(tLCRReportItemSet);
	  
	        GrpUWRReportUI tGrpUWRReportUI = new GrpUWRReportUI();
		if (tGrpUWRReportUI.submitData(tVData,"INSERT") == false)
		{
			int n = tGrpUWRReportUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 自动核保失败，原因是: " + tGrpUWRReportUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tGrpUWRReportUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 人工核保成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 人工核保失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
		
	} 
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
