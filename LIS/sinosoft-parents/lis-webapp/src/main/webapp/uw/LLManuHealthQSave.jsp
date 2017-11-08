<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuHealthQChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人 :ln   更新日期  :2008-11-7   更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
 	String tPEName   =request.getParameter("PEName");
  String tPEAddress    = request.getParameter("PEAddress");
  String tPEDoctor     = request.getParameter("PEDoctor");
  String tAccName     = request.getParameter("AccName"); //字段改用做陪检人
  String tPEDate       = request.getParameter("PEDate");
  String tREPEDate     = request.getParameter("REPEDate");
  String tNote         = request.getParameter("Note"); //其他信息
  String tMasculineFlag= request.getParameter("MasculineFlag");
  //体检结论
  String tPEResult= request.getParameter("PEResult"); //体检医院最终结论
  String tPEResultDes= request.getParameter("PEResultDes"); //体检医院最终结论详细信息
  String tResult= tPEResult + ":" + tPEResultDes + ":" + tNote;
//  String tManageCom= request.getParameter("ManageCom");
  //其他体检信息
  String tOtherPEItemCode= "Other";
  String tOtherPEItemName= request.getParameter("OtherPEItem");
  String tOtherPEItemRes= request.getParameter("OtherPEItemRes");
  String tOtherPEItemDes= request.getParameter("OtherPEItemDes");
  //既往病史
  String tPastDiseaseRes= request.getParameter("PastDiseaseRes");
  String tPastDiseaseDes= request.getParameter("PastDiseaseDes");
  
  //新增加滴 ActvityID 20130603
  String ActivityID = request.getParameter("ActivityID");
  String ClmNo = request.getParameter("ClmNo");
  String BatNo = request.getParameter("BatNo");
  //end
 loggerDebug("LLManuHealthQSave","ContNo : "+tContNo);
  loggerDebug("LLManuHealthQSave","PrtSeq : "+tPrtSeq);
  loggerDebug("LLManuHealthQSave","MissionID : "+tMissionID);
  loggerDebug("LLManuHealthQSave","SubMissionID : "+tSubMissionID);
  loggerDebug("LLManuHealthQSave","PrtNo : "+tPrtNo);
  loggerDebug("LLManuHealthQSave","PEAddress : "+tPEAddress);
  loggerDebug("LLManuHealthQSave","AccName : "+tAccName);
  loggerDebug("LLManuHealthQSave","PEDate : "+tPEDate);
  loggerDebug("LLManuHealthQSave","Note : "+tNote);
  loggerDebug("LLManuHealthQSave","Result : "+tResult);
  loggerDebug("LLManuHealthQSave","OtherPEItem : "+tOtherPEItemDes);
  loggerDebug("LLManuHealthQSave","OtherPEItemRes : "+tOtherPEItemDes);
  loggerDebug("LLManuHealthQSave","OtherPEItemDes : "+tOtherPEItemDes);
  loggerDebug("LLManuHealthQSave","PastDiseaseRes : "+tPastDiseaseRes);
  loggerDebug("LLManuHealthQSave","PastDiseaseDes : "+tPastDiseaseDes);
  
 //体检项目结果
 /**
  LLUWPENoticeItemSchema tLLUWPENoticeItemSchema;
  LLUWPENoticeItemSet tLLUWPENoticeItemSet = new LLUWPENoticeItemSet();
  LLUWPENoticeItemSet tLLUWPENoticeItemSet3 = new LLUWPENoticeItemSet();
  String count[] = request.getParameterValues("HealthGridNo");
  if(count != null)
  {
  	String[] tHealthCode   = request.getParameterValues("HealthGrid1");
  	String[] tHealthname   = request.getParameterValues("HealthGrid2");
  	      String[] tHealthResult   = request.getParameterValues("HealthGrid3");
          String[] tHealthMark = request.getParameterValues("HealthGrid4");
          for(int i = 0;i<count.length;i++)
          {
          	tLLUWPENoticeItemSchema = new   LLUWPENoticeItemSchema();
          	tLLUWPENoticeItemSchema.setContNo(tContNo);
          	tLLUWPENoticeItemSchema.setPrtSeq(tPrtSeq);
          	tLLUWPENoticeItemSchema.setProposalContNo(tProposalContNo);
          	tLLUWPENoticeItemSchema.setPEItemCode(tHealthCode[i]);
          	tLLUWPENoticeItemSchema.setPEItemName(tHealthname[i]);
          	tLLUWPENoticeItemSchema.setFreePE(tHealthResult[i]);
          	tLLUWPENoticeItemSchema.setPEItemResult(tHealthMark[i]);
          	tLLUWPENoticeItemSchema.add(tLLUWPENoticeItemSchema);
          }
  }
  else
  {
  }
  //add by zhangxing
  String ChkCount[] = request.getParameterValues("OtherHealthGridNo"); 
  if (ChkCount != null)
	{
	      String[] aHealthCode   = request.getParameterValues("OtherHealthGrid1");	    
  	    String[] aHealthname   = request.getParameterValues("OtherHealthGrid2");
  	    String[] aHealthResult = request.getParameterValues("OtherHealthGrid3");
  	    String[] aHealthMark = request.getParameterValues("OtherHealthGrid4");
			  for (int j = 0; j < ChkCount.length; j++)
			  {			     
				    if (!aHealthCode[j].equals(""))
				    {
		  	    LLUWPENoticeItemSchema aLLUWPENoticeItemSchema = new LLUWPENoticeItemSchema();
		  		
					 	aLLUWPENoticeItemSchema.setPEItemCode( aHealthCode[j]);
					 	aLLUWPENoticeItemSchema.setPEItemName( aHealthname[j]);
					 	aLLUWPENoticeItemSchema.setFreePE(aHealthResult[j]);
					 	aLLUWPENoticeItemSchema.setPEItemResult(aHealthMark[j]);
					
			    	tLLUWPENoticeItemSet3.add( aLLUWPENoticeItemSchema );		   			   		
				    }
			 }

 }
  else
  {
  }
**/

	LCPENoticeReplySchema tLCPENoticeReplySchema;
	LCPENoticeReplySet tLCPENoticeReplySet = new LCPENoticeReplySet();
	//必选体检项目保存
	String count[] = request.getParameterValues("HealthGridNo");
	if(count != null)
	{
		String[] tPEItemCode   = request.getParameterValues("HealthGrid1");
		String[] tPEItemName   = request.getParameterValues("HealthGrid2");
		String[] tPEItemDivCode   = request.getParameterValues("HealthGrid3");
		String[] tPEItemDivName   = request.getParameterValues("HealthGrid4");
		String[] tPEItemDivRes   = request.getParameterValues("HealthGrid5");
		String[] tPEItemDivDes   = request.getParameterValues("HealthGrid6");
		String[] tPEItemDivNor   = request.getParameterValues("HealthGrid7");
        
	        for(int i = 0;i<count.length;i++)
	        {
	        	tLCPENoticeReplySchema = new LCPENoticeReplySchema();
	        	tLCPENoticeReplySchema.setContNo(tContNo);
	        	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tLCPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tLCPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tLCPENoticeReplySchema.setPEItemDivName(tPEItemDivName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tLCPENoticeReplySchema.setFreePE("0");
	        	
	        	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
	        }
	}
	//可选体检项目保存
	String count1[] = request.getParameterValues("HealthOtherGridNo");
	if(count1 != null)
	{
		String[] tPEItemCode   = request.getParameterValues("HealthOtherGrid1");
		String[] tPEItemName   = request.getParameterValues("HealthOtherGrid2");
		String[] tPEItemDivCode   = request.getParameterValues("HealthOtherGrid3");
		String[] tPEItemDivName   = request.getParameterValues("HealthOtherGrid4");
		String[] tPEItemDivRes   = request.getParameterValues("HealthOtherGrid5");
		String[] tPEItemDivDes   = request.getParameterValues("HealthOtherGrid6");
		String[] tPEItemDivNor   = request.getParameterValues("HealthOtherGrid7");
        
	        for(int i = 0;i<count1.length;i++)
	        {
	        	tLCPENoticeReplySchema = new LCPENoticeReplySchema();
	        	tLCPENoticeReplySchema.setContNo(tContNo);
	        	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tLCPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tLCPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tLCPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tLCPENoticeReplySchema.setPEItemDivName(tPEItemDivCode[i]);
	        	tLCPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tLCPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tLCPENoticeReplySchema.setFreePE("1");
	        	
	        	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
	        }
	}
	//其他体检项目保存
	  if(!(tOtherPEItemName.equals("null")||tOtherPEItemName.equals("")))
	  {
		  tLCPENoticeReplySchema = new LCPENoticeReplySchema();
      	tLCPENoticeReplySchema.setContNo(tContNo);
      	tLCPENoticeReplySchema.setPrtSeq(tPrtSeq);
      	tLCPENoticeReplySchema.setProposalContNo(tProposalContNo);
      	tLCPENoticeReplySchema.setPEItemCode(tOtherPEItemCode);
      	tLCPENoticeReplySchema.setPEItemName(tOtherPEItemName);
      	tLCPENoticeReplySchema.setPEItemDivCode(tOtherPEItemCode);
      	tLCPENoticeReplySchema.setPEItemDivName(tOtherPEItemName);
      	tLCPENoticeReplySchema.setPEItemDivRes(tOtherPEItemRes);
      	tLCPENoticeReplySchema.setPEItemDivDes(tOtherPEItemDes);
      	tLCPENoticeReplySchema.setPEItemDivNor("");
      	
      	tLCPENoticeReplySet.add(tLCPENoticeReplySchema);
	  }	            
	
//体检最终结论保存
  LLUWPENoticeSchema tLLUWPENoticeSchema = new LLUWPENoticeSchema();
  LLUWPENoticeSet tLLUWPENoticeSet = new LLUWPENoticeSet();
  tLLUWPENoticeSchema.setContNo(tContNo);
  tLLUWPENoticeSchema.setPrtSeq(tPrtSeq);
  tLLUWPENoticeSchema.setCustomerNo(tCustomerNo);
  tLLUWPENoticeSchema.setPEResult(tResult);
  tLLUWPENoticeSchema.setPEAddress(tPEAddress);
  tLLUWPENoticeSchema.setAgentName(tAccName); //暂用于记录陪检人
  tLLUWPENoticeSchema.setREPEDate(tPEDate); //实际体检日期
  tLLUWPENoticeSchema.setREPEDate(tREPEDate);
  tLLUWPENoticeSchema.setMasculineFlag(tMasculineFlag);
  tLLUWPENoticeSchema.setHospitCode(request.getParameter("PEHospital"));
  tLLUWPENoticeSet.add(tLLUWPENoticeSchema);


//既往病史保存
 	LCPENoticeResultSchema tLCPENoticeResultSchema ;
 	LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
/**
	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		//String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");

		lineCount = arrCount.length;
		loggerDebug("LLManuHealthQSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tLCPENoticeResultSchema = new LCPENoticeResultSchema();
			tLCPENoticeResultSchema.setContNo(tContNo);
			tLCPENoticeResultSchema.setProposalContNo(tProposalContNo);
			tLCPENoticeResultSchema.setPrtSeq(tPrtSeq);
			tLCPENoticeResultSchema.setCustomerNo(tCustomerNo);				
		 
		  tLCPENoticeResultSchema.setDisDesb("无");	
		  if(tDisResult[i].equals(""))
		  {tLCPENoticeResultSchema.setDisResult("无");}
		  else
		  {tLCPENoticeResultSchema.setDisResult(tDisResult[i]);}
		  if(tICDCode[i].equals(""))
		  {tLCPENoticeResultSchema.setICDCode("无");}
		  else
		  {tLCPENoticeResultSchema.setICDCode(tICDCode[i]);} 
		
			loggerDebug("LLManuHealthQSave","tDisResult="+tDisResult[i]);
			loggerDebug("LLManuHealthQSave","tICDCode="+tICDCode[i]);		
			tLCPENoticeResultSet.add(tLCPENoticeResultSchema);
		}
	}
	else
	{
	}
**/
	tLCPENoticeResultSchema = new LCPENoticeResultSchema();
	tLCPENoticeResultSchema.setContNo(tContNo);
	tLCPENoticeResultSchema.setProposalContNo(tProposalContNo);
	tLCPENoticeResultSchema.setPrtSeq(tPrtSeq);
	tLCPENoticeResultSchema.setCustomerNo(tCustomerNo);	
	tLCPENoticeResultSchema.setName(tPEName);	
	tLCPENoticeResultSchema.setDisDesb(tPastDiseaseDes);	
	{tLCPENoticeResultSchema.setDisResult(tPastDiseaseRes);}
		
	tLCPENoticeResultSet.add(tLCPENoticeResultSchema);


	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tLCPENoticeReplySet);
	tVData.add(tLLUWPENoticeSet);
	tVData.add(tLCPENoticeResultSet);
	
/*************************************	
  tVData.add(tLCPENoticeItemSet);
  tVData.add(tLCPENoticeSet);
  tVData.add(tLCPENoticeResultSet);
***************************************/
  TransferData tTransferData = new TransferData();
  //tTransferData.setNameAndValue("LCPENoticeItemSet",tLCPENoticeItemSet);
  //add by zhangxing 
 // tTransferData.setNameAndValue("LCPENoticeItemSet3",tLCPENoticeItemSet3);
  //tTransferData.setNameAndValue("LCPENoticeSet",tLCPENoticeSet);
  //tTransferData.setNameAndValue("LCPENoticeResultSet",tLCPENoticeResultSet);
  tTransferData.setNameAndValue("MissionID",tMissionID);
  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
  tTransferData.setNameAndValue("PrtNo",tPrtNo);
  tTransferData.setNameAndValue("ContNo",tContNo);
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
 // tTransferData.setNameAndValue("ManageCom",tManageCom);
//  tTransferData.setNameAndValue("MakeDate",MakeDate);
 tTransferData.setNameAndValue("ActivityID",ActivityID);
 tTransferData.setNameAndValue("ClmNo",ClmNo);
 tTransferData.setNameAndValue("BatNo",BatNo);
 tTransferData.setNameAndValue("CodeType","LP03");
 tTransferData.setNameAndValue("CertifyNo",request.getParameter("PrtSeq"));
 
  tVData.add(tTransferData);
  tVData.add(tG);

loggerDebug("LLManuHealthQSave","here");
//====================20130603=========================
WorkFlowUI tWorkFlowUI = new WorkFlowUI();

loggerDebug("LLManuHealthQSave","this will save the data!!!");
String busiName="tWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
  {    
       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
       { 
			Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = "提交工作流失败";
			FlagStr = "Fail";				
	   }
  }
  else
  {
     	Content = "数据提交成功! ";
      	FlagStr = "Succ";  
  }
/*	TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();

		loggerDebug("LLManuHealthQSave","this will save the data!!!");
  if(tTbWorkFlowUI.submitData(tVData,"0000005534")){
	
			Content = " 保存成功! ";
			FlagStr = "Succ";
	
	}
	else{
		Content = "保存失败，原因是:" + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}

*/
/*****************************************************
	DisDesbUI tDisDesbUI = new DisDesbUI();
	try{
		loggerDebug("LLManuHealthQSave","this will save the data!!!");
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

