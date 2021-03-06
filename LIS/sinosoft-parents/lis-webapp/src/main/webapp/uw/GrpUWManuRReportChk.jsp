<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人  zhangxing  更新日期  2006-05-30   更新原因/内容
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
	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
	LCRReportSet tLCRReportSet = new LCRReportSet();
	
	LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
	
	String tGrpContNo = request.getParameter("GrpContNo");
	String tContNo = request.getParameter("ContNo");
	String tProposalContNo = request.getParameter("ProposalNoHide");
	String tInsureNo = request.getParameter("CustomerNo");
	String tCustomerName = request.getParameter("CustomerName");
	String tRReportReason  = request.getParameter("RReportReason");
	String tContente = request.getParameter("Contente");
	loggerDebug("GrpUWManuRReportChk","InsureNo="+tInsureNo);
	
  String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	
	
	String tSerialNo[] = request.getParameterValues("InvestigateGridNo");
	String thealthcode[] = request.getParameterValues("InvestigateGrid1");
	String thealthname[] = request.getParameterValues("InvestigateGrid2");
	String thealthremark[] = request.getParameterValues("InvestigateGrid3");
	
	loggerDebug("GrpUWManuRReportChk","GrpContNo:"+tGrpContNo);
	loggerDebug("GrpUWManuRReportChk","ContNo:"+tContNo);
	loggerDebug("GrpUWManuRReportChk","ProposalContNo:"+tProposalContNo);
	loggerDebug("GrpUWManuRReportChk","tInsureNo:"+tInsureNo);
  loggerDebug("GrpUWManuRReportChk","EdorNo:"+tEdorNo);
  loggerDebug("GrpUWManuRReportChk","EdorType:"+tEdorType);	
	
	boolean flag = true;
	int ChkCount = 0;
	if(tSerialNo != null)
	{		
		  ChkCount = tSerialNo.length;
	}
	loggerDebug("GrpUWManuRReportChk","count:"+ChkCount);
	if (ChkCount == 0 )
	{
			Content = "契调资料信息录入不完整!";
			FlagStr = "Fail";
			flag = false;
			loggerDebug("GrpUWManuRReportChk","111");
	}
	else
	{
			loggerDebug("GrpUWManuRReportChk","222");
		    //体检资料一
			tLCRReportSchema.setGrpContNo(tGrpContNo);
			tLCRReportSchema.setContNo(tContNo);
		  tLCRReportSchema.setProposalContNo(tContNo);	    		
		 	tLCRReportSchema.setCustomerNo(tInsureNo);
		 	tLCRReportSchema.setName(tCustomerName);
		 	tLCRReportSchema.setRReportReason(tRReportReason);		 
	    tLCRReportSchema.setRemark(tContente);
	    loggerDebug("GrpUWManuRReportChk","---------------------------------");
	    loggerDebug("GrpUWManuRReportChk",tLCRReportSchema.getRReportReason());
	    loggerDebug("GrpUWManuRReportChk",tLCRReportSchema.getRemark());
	    loggerDebug("GrpUWManuRReportChk","---------------------------------");
		 	loggerDebug("GrpUWManuRReportChk","chkcount="+ChkCount);
	   	if (ChkCount > 0)
	   	{
	    		for (int i = 0; i < ChkCount; i++)
			    {
				      if (!thealthcode[i].equals(""))
				      {
					  			LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();
					  			tLCRReportItemSchema.setGrpContNo(tGrpContNo);
					  			tLCRReportItemSchema.setContNo(tContNo);
					  			tLCRReportItemSchema.setProposalContNo(tGrpContNo);
								  tLCRReportItemSchema.setRReportItemCode( thealthcode[i]);
							  	tLCRReportItemSchema.setRReportItemName( thealthname[i]);
								  tLCRReportItemSchema.setRemark(thealthremark[i]); 
						    	tLCRReportItemSet.add( tLCRReportItemSchema );
						      loggerDebug("GrpUWManuRReportChk","i:"+i);
				    		  loggerDebug("GrpUWManuRReportChk","healthcode:"+thealthcode[i]);	    	
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
	
	loggerDebug("GrpUWManuRReportChk","flag:"+flag);
  if (flag == true)
  {
		// 准备传输数据 VData
	   VData tVData = new VData();
	   
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("EdorNo", tEdorNo);
    tTransferData.setNameAndValue("EdorType",tEdorType);
    
    tVData.add(tTransferData);	   
       
	   tVData.add(tGlobalInput);
	   tVData.add(tLCRReportSchema);
	   tVData.add(tLCRReportItemSet);
	  
	   UWRReportUI tUWRReportUI = new UWRReportUI();
		 if (tUWRReportUI.submitData(tVData,"INSERT") == false)
		 {
				 int n = tUWRReportUI.mErrors.getErrorCount();
				 for (int i = 0; i < n; i++)
				 Content = " 自动核保失败，原因是: " + tUWRReportUI.mErrors.getError(0).errorMessage;
				 FlagStr = "Fail";
		 }
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWRReportUI.mErrors;
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
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
