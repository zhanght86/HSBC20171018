<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuHealthQChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-06-19 11:10:36
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
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();

	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}

 	String tContNo       = request.getParameter("ContNo");
  String tPrtSeq       = request.getParameter("PrtSeq");
  String tMissionID    = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tPrtNo        = request.getParameter("PrtNo");
  String tProposalContNo =request.getParameter("ContNo");
 	String tCustomerNo   =request.getParameter("CustomerNo");
  String tPEAddress    = request.getParameter("PEAddress");
  String tPEDoctor     = request.getParameter("PEDoctor");
  String tPEDate       = request.getParameter("PEDate");
  String tREPEDate     = request.getParameter("REPEDate");
  String tNote         = request.getParameter("Note");
  String tMasculineFlag= request.getParameter("MasculineFlag");
  
 loggerDebug("UWManuHealthQSave","ContNo : "+tContNo);
  loggerDebug("UWManuHealthQSave","PrtSeq : "+tPrtSeq);
  loggerDebug("UWManuHealthQSave","MissionID : "+tMissionID);
  loggerDebug("UWManuHealthQSave","SubMissionID : "+tSubMissionID);
  loggerDebug("UWManuHealthQSave","PrtNo : "+tPrtNo);
  loggerDebug("UWManuHealthQSave","PEAddress : "+tPEAddress);
  loggerDebug("UWManuHealthQSave","PEDoctor : "+tPEDoctor);
  loggerDebug("UWManuHealthQSave","PEDate : "+tPEDate);
  loggerDebug("UWManuHealthQSave","REPEDate : "+tREPEDate);
  loggerDebug("UWManuHealthQSave","Note : "+tNote);
  loggerDebug("UWManuHealthQSave","MasculineFlag : "+tMasculineFlag);
  
 
  LCPENoticeItemSchema tLCPENoticeItemSchema;
  LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
  String count[] = request.getParameterValues("HealthGridNo");
  if(count != null)
  {
  	String[] tHealthCode   = request.getParameterValues("HealthGrid1");
  	String[] tHealthname   = request.getParameterValues("HealthGrid2");
          String[] tHealthResult = request.getParameterValues("HealthGrid3");
          for(int i = 0;i<count.length;i++)
          {
          	tLCPENoticeItemSchema = new   LCPENoticeItemSchema();
                  tLCPENoticeItemSchema.setContNo(tContNo);
                  tLCPENoticeItemSchema.setPrtSeq(tPrtSeq);
                  tLCPENoticeItemSchema.setProposalContNo(tProposalContNo);
                  tLCPENoticeItemSchema.setPEItemCode(tHealthCode[i]);
                  tLCPENoticeItemSchema.setPEItemName(tHealthname[i]);
                  tLCPENoticeItemSchema.setPEItemResult(tHealthResult[i]);
                  tLCPENoticeItemSet.add(tLCPENoticeItemSchema);
          }
  }
  else
  {
  }

  LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
  LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
  tLCPENoticeSchema.setContNo(tContNo);
  tLCPENoticeSchema.setPrtSeq(tPrtSeq);
  tLCPENoticeSchema.setCustomerNo(tCustomerNo);
  tLCPENoticeSchema.setPEResult(tNote);
  tLCPENoticeSchema.setPEAddress(tPEAddress);
  tLCPENoticeSchema.setPEDoctor(tPEDoctor);
  tLCPENoticeSchema.setPEDate(tPEDate);
  tLCPENoticeSchema.setREPEDate(tREPEDate);
  tLCPENoticeSchema.setMasculineFlag(tMasculineFlag);
  tLCPENoticeSet.add(tLCPENoticeSchema);



 	LCPENoticeResultSchema tLCPENoticeResultSchema ;
 	LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();

	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");

		lineCount = arrCount.length;
		loggerDebug("UWManuHealthQSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tLCPENoticeResultSchema = new LCPENoticeResultSchema();
			tLCPENoticeResultSchema.setContNo(tContNo);
			tLCPENoticeResultSchema.setProposalContNo(tProposalContNo);
			tLCPENoticeResultSchema.setPrtSeq(tPrtSeq);
			tLCPENoticeResultSchema.setCustomerNo(tCustomerNo);	
			if(tDisDesb[i].equals(""))		
			{tLCPENoticeResultSchema.setDisDesb("无");}
		  else
		  { tLCPENoticeResultSchema.setDisDesb(tDisDesb[i]);	}	
		  if(tDisResult[i].equals(""))
		  {tLCPENoticeResultSchema.setDisResult("无");}
		  else
		  {tLCPENoticeResultSchema.setDisResult(tDisResult[i]);}
		  if(tICDCode[i].equals(""))
		  {tLCPENoticeResultSchema.setICDCode("无");}
		  else
		  {tLCPENoticeResultSchema.setICDCode(tICDCode[i]);} 
			loggerDebug("UWManuHealthQSave","tDisDesb="+tDisDesb[i]);
			loggerDebug("UWManuHealthQSave","tDisResult="+tDisResult[i]);
			loggerDebug("UWManuHealthQSave","tICDCode="+tICDCode[i]);		
			tLCPENoticeResultSet.add(tLCPENoticeResultSchema);
		}
	}
	else
	{
	}

	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tG);
/*************************************	
  tVData.add(tLCPENoticeItemSet);
  tVData.add(tLCPENoticeSet);
	tVData.add(tLCPENoticeResultSet);
***************************************/
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
  tTransferData.setNameAndValue("LCPENoticeSet",tLCPENoticeSet);
  tTransferData.setNameAndValue("LCPENoticeResultSet",tLCPENoticeResultSet);
  tTransferData.setNameAndValue("MissionID",tMissionID);
  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
  tTransferData.setNameAndValue("PrtNo",tPrtNo);
  tTransferData.setNameAndValue("ContNo",tContNo);
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
//  tTransferData.setNameAndValue("MakeDate",MakeDate);
 
  tVData.add(tTransferData);

loggerDebug("UWManuHealthQSave","here");
  
	TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();

		loggerDebug("UWManuHealthQSave","this will save the data!!!");
  if(tTbWorkFlowUI.submitData(tVData,"0000001121")){
	
			Content = " 保存成功! ";
			FlagStr = "Succ";
	
	}
	else{
		Content = "保存失败，原因是:" + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}


/*****************************************************
	DisDesbUI tDisDesbUI = new DisDesbUI();
	try{
		loggerDebug("UWManuHealthQSave","this will save the data!!!");
		tDisDesbUI.submitData(tVData,"");
	}
	catch(Exception ex){
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
******************************************************/

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
