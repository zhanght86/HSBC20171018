<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealthChk.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
	LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tGrpContNo = request.getParameter("GrpContNo");
	String tContNo = request.getParameter("ContNo");
	String tProposalContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");
	String tPrtNo =  request.getParameter("PrtNo");
	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("EDate");
	String tNote = request.getParameter("Note");
//	String tMissionID = request.getParameter("MissionID");
//	String tSubMissionID = request.getParameter("SubMissionID");	
	
        String[] tBodyCheckItems = new String[20]; 
        String  tbodyCheck = request.getParameter("bodyCheck");
	tBodyCheckItems = request.getParameterValues(tbodyCheck);
	String thealthcode[] = new String[20];
	String thealthname[] = new String[20];
        loggerDebug("GrpUWManuHealthChk","size:"+tBodyCheckItems.length);
        if(tBodyCheckItems.length != 0)
        {
        	for(int i = 0;i<tBodyCheckItems.length;i++)
                {
                	thealthcode[i] = tBodyCheckItems[i].substring(0,3);
                        thealthname[i] = tBodyCheckItems[i].substring(3);
                }
        }

	
	loggerDebug("GrpUWManuHealthChk","ContNo:"+tContNo);
	loggerDebug("GrpUWManuHealthChk","hospital:"+tHospital);
	loggerDebug("GrpUWManuHealthChk","note:"+tNote);
	loggerDebug("GrpUWManuHealthChk","ifempty:"+tIfEmpty);
	loggerDebug("GrpUWManuHealthChk","insureno:"+tInsureNo);
	loggerDebug("GrpUWManuHealthChk","EDATE:"+tEDate);
	
	boolean flag = true;
	int ChkCount = tBodyCheckItems.length;
	/*if(tSerialNo != null)
	{		
		ChkCount = tSerialNo.length;
	}*/
	loggerDebug("GrpUWManuHealthChk","count:"+ChkCount);
	if (ChkCount == 0 || tHospital.equals("") || tIfEmpty.equals(""))
	{
		Content = "体检资料信息录入不完整!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("GrpUWManuHealthChk","111");
	}
	else
	{
		loggerDebug("GrpUWManuHealthChk","222");
	    //体检资料一
		    tLCPENoticeSchema.setGrpContNo(tGrpContNo);
		    tLCPENoticeSchema.setContNo(tContNo);
			tLCPENoticeSchema.setProposalContNo(tContNo);
	    	tLCPENoticeSchema.setPEAddress(tHospital);
	    	tLCPENoticeSchema.setPEDate(tEDate);
	    	tLCPENoticeSchema.setPEBeforeCond(tIfEmpty);
	    	tLCPENoticeSchema.setRemark(tNote);
	    	tLCPENoticeSchema.setCustomerNo(tInsureNo);
            tLCPENoticeSet.add(tLCPENoticeSchema);
	    	loggerDebug("GrpUWManuHealthChk","chkcount="+ChkCount);
	    	if (ChkCount > 0)
	    	{
	    		for (int i = 0; i < ChkCount; i++)
			{
				if (!thealthcode[i].equals(""))
				{
		  			LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		  			tLCPENoticeItemSchema.setGrpContNo(tGrpContNo);
		  			tLCPENoticeItemSchema.setContNo(tContNo);
					tLCPENoticeItemSchema.setPEItemCode( thealthcode[i]);
					tLCPENoticeItemSchema.setPEItemName( thealthname[i]);
					tLCPENoticeItemSchema.setFreePE("N");   	    
			    	tLCPENoticeItemSet.add( tLCPENoticeItemSchema );
			        loggerDebug("GrpUWManuHealthChk","i:"+i);
	    		    loggerDebug("GrpUWManuHealthChk","healthcode:"+thealthcode[i]);	    	
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
	
	loggerDebug("GrpUWManuHealthChk","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
	   VData tVData = new VData();
	   

//	   TransferData tTransferData = new TransferData();
//	   tTransferData.setNameAndValue("ContNo",tContNo);
//	   tTransferData.setNameAndValue("PrtNo",tPrtNo);
//	   tTransferData.setNameAndValue("InsureNo",tInsureNo );
//	   //tTransferData.setNameAndValue("MissionID",tMissionID );
//	   //tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
//       tTransferData.setNameAndValue("LCPENoticeSchema",tLCPENoticeSchema);
//       tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
           
           
           tVData.add(tGlobalInput);
	   tVData.add(tLCPENoticeSet);
	   tVData.add(tLCPENoticeItemSet);
	   
	   loggerDebug("GrpUWManuHealthChk","tVData.add()");
	  
	    UWAutoHealthUI tUWAutoHealthUI = new UWAutoHealthUI();
		if (tUWAutoHealthUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWAutoHealthUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 自动核保失败，原因是: " + tUWAutoHealthUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWAutoHealthUI.mErrors;
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
