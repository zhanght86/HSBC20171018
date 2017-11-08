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
  <%@page import="com.sinosoft.workflow.xb.*"%>
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
  String tPEHospital    = request.getParameter("PEHospital"); //2009-01-04 ln add 体检医院代码
  String tPEAddress    = request.getParameter("PEAddress");
 // String tPEDoctor     = request.getParameter("PEDoctor");
  String tAccName     = request.getParameter("AccName"); //字段改用做陪检人
  String tPEDate       = request.getParameter("PEDate");
 // String tREPEDate     = request.getParameter("REPEDate");
  String tNote         = request.getParameter("Note"); //其他信息
 // String tMasculineFlag= request.getParameter("MasculineFlag");
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
  
 loggerDebug("RnewManuHealthQSave","ContNo : "+tContNo);
  loggerDebug("RnewManuHealthQSave","PrtSeq : "+tPrtSeq);
  loggerDebug("RnewManuHealthQSave","MissionID : "+tMissionID);
  loggerDebug("RnewManuHealthQSave","SubMissionID : "+tSubMissionID);
  loggerDebug("RnewManuHealthQSave","PrtNo : "+tPrtNo);
  loggerDebug("RnewManuHealthQSave","PEAddress : "+tPEAddress);
  loggerDebug("RnewManuHealthQSave","AccName : "+tAccName);
  loggerDebug("RnewManuHealthQSave","PEDate : "+tPEDate);
  loggerDebug("RnewManuHealthQSave","Note : "+tNote);
  loggerDebug("RnewManuHealthQSave","Result : "+tResult);
  loggerDebug("RnewManuHealthQSave","OtherPEItem : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","OtherPEItemRes : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","OtherPEItemDes : "+tOtherPEItemDes);
  loggerDebug("RnewManuHealthQSave","PastDiseaseRes : "+tPastDiseaseRes);
  loggerDebug("RnewManuHealthQSave","PastDiseaseDes : "+tPastDiseaseDes);
  
 //体检项目结果
 /**
  RnewPENoticeItemSchema tRnewPENoticeItemSchema;
  RnewPENoticeItemSet tRnewPENoticeItemSet = new RnewPENoticeItemSet();
  RnewPENoticeItemSet tRnewPENoticeItemSet3 = new RnewPENoticeItemSet();
  String count[] = request.getParameterValues("HealthGridNo");
  if(count != null)
  {
  	String[] tHealthCode   = request.getParameterValues("HealthGrid1");
  	String[] tHealthname   = request.getParameterValues("HealthGrid2");
  	      String[] tHealthResult   = request.getParameterValues("HealthGrid3");
          String[] tHealthMark = request.getParameterValues("HealthGrid4");
          for(int i = 0;i<count.length;i++)
          {
          	      tRnewPENoticeItemSchema = new   RnewPENoticeItemSchema();
                  tRnewPENoticeItemSchema.setContNo(tContNo);
                  tRnewPENoticeItemSchema.setPrtSeq(tPrtSeq);
                  tRnewPENoticeItemSchema.setProposalContNo(tProposalContNo);
                  tRnewPENoticeItemSchema.setPEItemCode(tHealthCode[i]);
                  tRnewPENoticeItemSchema.setPEItemName(tHealthname[i]);
                  tRnewPENoticeItemSchema.setFreePE(tHealthResult[i]);
                  tRnewPENoticeItemSchema.setPEItemResult(tHealthMark[i]);
                  tRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);
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
		  	    RnewPENoticeItemSchema aRnewPENoticeItemSchema = new RnewPENoticeItemSchema();
		  		
					 	aRnewPENoticeItemSchema.setPEItemCode( aHealthCode[j]);
					 	aRnewPENoticeItemSchema.setPEItemName( aHealthname[j]);
					 	aRnewPENoticeItemSchema.setFreePE(aHealthResult[j]);
					 	aRnewPENoticeItemSchema.setPEItemResult(aHealthMark[j]);
					
			    	tRnewPENoticeItemSet3.add( aRnewPENoticeItemSchema );		   			   		
				    }
			 }

 }
  else
  {
  }
**/

	RnewPENoticeReplySchema tRnewPENoticeReplySchema;
	RnewPENoticeReplySet tRnewPENoticeReplySet = new RnewPENoticeReplySet();
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
	        	tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
	        	tRnewPENoticeReplySchema.setContNo(tContNo);
	        	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tRnewPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivName(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tRnewPENoticeReplySchema.setFreePE("0");
	        	
	        	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
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
	        	tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
	        	tRnewPENoticeReplySchema.setContNo(tContNo);
	        	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
	        	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
	        	tRnewPENoticeReplySchema.setPEItemCode(tPEItemCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemName(tPEItemName[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivCode(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivName(tPEItemDivCode[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivRes(tPEItemDivRes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivDes(tPEItemDivDes[i]);
	        	tRnewPENoticeReplySchema.setPEItemDivNor(tPEItemDivNor[i]);
	        	tRnewPENoticeReplySchema.setFreePE("1");
	        	
	        	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
	        }
	}
	//其他体检项目保存
	  if(!(tOtherPEItemName.equals("null")||tOtherPEItemName.equals("")))
	  {
		  tRnewPENoticeReplySchema = new RnewPENoticeReplySchema();
      	tRnewPENoticeReplySchema.setContNo(tContNo);
      	tRnewPENoticeReplySchema.setPrtSeq(tPrtSeq);
      	tRnewPENoticeReplySchema.setProposalContNo(tProposalContNo);
      	tRnewPENoticeReplySchema.setPEItemCode(tOtherPEItemCode);
      	tRnewPENoticeReplySchema.setPEItemName(tOtherPEItemName);
      	tRnewPENoticeReplySchema.setPEItemDivCode(tOtherPEItemCode);
      	tRnewPENoticeReplySchema.setPEItemDivName(tOtherPEItemName);
      	tRnewPENoticeReplySchema.setPEItemDivRes(tOtherPEItemRes);
      	tRnewPENoticeReplySchema.setPEItemDivDes(tOtherPEItemDes);
      	tRnewPENoticeReplySchema.setPEItemDivNor("");
      	
      	tRnewPENoticeReplySet.add(tRnewPENoticeReplySchema);
	  }	            
	
//体检最终结论保存
  RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();
  RnewPENoticeSet tRnewPENoticeSet = new RnewPENoticeSet();
  tRnewPENoticeSchema.setContNo(tContNo);
  tRnewPENoticeSchema.setPrtSeq(tPrtSeq);
  tRnewPENoticeSchema.setCustomerNo(tCustomerNo);
  tRnewPENoticeSchema.setPEResult(tResult);
  tRnewPENoticeSchema.setHospitCode(tPEHospital); //2009-01-04 ln add 体检医院代码
  tRnewPENoticeSchema.setPEAddress(tPEAddress);
  tRnewPENoticeSchema.setAgentName(tAccName); //暂用于记录陪检人
  tRnewPENoticeSchema.setREPEDate(tPEDate); //实际体检日期
 // tRnewPENoticeSchema.setREPEDate(tREPEDate);
 // tRnewPENoticeSchema.setMasculineFlag(tMasculineFlag);
  tRnewPENoticeSet.add(tRnewPENoticeSchema);


//既往病史保存
 	RnewPENoticeResultSchema tRnewPENoticeResultSchema ;
 	RnewPENoticeResultSet tRnewPENoticeResultSet = new RnewPENoticeResultSet();
/**
	int lineCount = 0;
	String arrCount[] = request.getParameterValues("DisDesbGridNo");

	if(arrCount!=null)
	{
		//String tDisDesb[]=request.getParameterValues("DisDesbGrid1");
		String tDisResult[]=request.getParameterValues("DisDesbGrid2");
		String tICDCode[]=request.getParameterValues("DisDesbGrid3");

		lineCount = arrCount.length;
		loggerDebug("RnewManuHealthQSave","lineCount="+lineCount);
		for(int i = 0;i<lineCount;i++)
		{
			tRnewPENoticeResultSchema = new RnewPENoticeResultSchema();
			tRnewPENoticeResultSchema.setContNo(tContNo);
			tRnewPENoticeResultSchema.setProposalContNo(tProposalContNo);
			tRnewPENoticeResultSchema.setPrtSeq(tPrtSeq);
			tRnewPENoticeResultSchema.setCustomerNo(tCustomerNo);				
		 
		  tRnewPENoticeResultSchema.setDisDesb("无");	
		  if(tDisResult[i].equals(""))
		  {tRnewPENoticeResultSchema.setDisResult("无");}
		  else
		  {tRnewPENoticeResultSchema.setDisResult(tDisResult[i]);}
		  if(tICDCode[i].equals(""))
		  {tRnewPENoticeResultSchema.setICDCode("无");}
		  else
		  {tRnewPENoticeResultSchema.setICDCode(tICDCode[i]);} 
		
			loggerDebug("RnewManuHealthQSave","tDisResult="+tDisResult[i]);
			loggerDebug("RnewManuHealthQSave","tICDCode="+tICDCode[i]);		
			tRnewPENoticeResultSet.add(tRnewPENoticeResultSchema);
		}
	}
	else
	{
	}
**/
	tRnewPENoticeResultSchema = new RnewPENoticeResultSchema();
	tRnewPENoticeResultSchema.setContNo(tContNo);
	tRnewPENoticeResultSchema.setProposalContNo(tProposalContNo);
	tRnewPENoticeResultSchema.setPrtSeq(tPrtSeq);
	tRnewPENoticeResultSchema.setCustomerNo(tCustomerNo);	
	tRnewPENoticeResultSchema.setName(tPEName);	
	tRnewPENoticeResultSchema.setDisDesb(tPastDiseaseDes);	
	{tRnewPENoticeResultSchema.setDisResult(tPastDiseaseRes);}
		
	tRnewPENoticeResultSet.add(tRnewPENoticeResultSchema);


	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(tRnewPENoticeReplySet);
	tVData.add(tRnewPENoticeSet);
	tVData.add(tRnewPENoticeResultSet);
	
/*************************************	
  tVData.add(tRnewPENoticeItemSet);
  tVData.add(tRnewPENoticeSet);
  tVData.add(tRnewPENoticeResultSet);
***************************************/
  TransferData tTransferData = new TransferData();
  //tTransferData.setNameAndValue("RnewPENoticeItemSet",tRnewPENoticeItemSet);
  //add by zhangxing 
 // tTransferData.setNameAndValue("RnewPENoticeItemSet3",tRnewPENoticeItemSet3);
  //tTransferData.setNameAndValue("RnewPENoticeSet",tRnewPENoticeSet);
  //tTransferData.setNameAndValue("RnewPENoticeResultSet",tRnewPENoticeResultSet);
  tTransferData.setNameAndValue("MissionID",tMissionID);
  tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
  tTransferData.setNameAndValue("PrtNo",tPrtNo);
  tTransferData.setNameAndValue("ContNo",tContNo);
  tTransferData.setNameAndValue("PrtSeq",tPrtSeq);
  tTransferData.setNameAndValue("CustomerNo",tCustomerNo);
 // tTransferData.setNameAndValue("ManageCom",tManageCom);
//  tTransferData.setNameAndValue("MakeDate",MakeDate);
  tTransferData.setNameAndValue("BusiType", "1004");
  ExeSQL yExeSQL = new ExeSQL();
	SSRS ySSRS = new SSRS();
  String sqlstr="select activityid from lwactivity where functionid='10047013'";
	ySSRS = yExeSQL.execSQL(sqlstr);
	String xActivityID="";
	if(ySSRS.MaxRow==0)
	{}
	else
	{
		 xActivityID = ySSRS.GetText(1,1);
	}
  tTransferData.setNameAndValue("ActivityID", xActivityID);
  //工作流该做，回到续保核保初始节点调用类20130923
  RnewCommonDataPut tRnewCommonDataPut = new RnewCommonDataPut();
  if(!tRnewCommonDataPut.submitData(tTransferData))
  {
	  Content = "保存失败，原因是:" + tRnewCommonDataPut.getErrors().getFirstError();
      FlagStr = "Fail";
  }
  tTransferData=tRnewCommonDataPut.getTransferData();
  tVData.add(tTransferData);
  tVData.add(tG);

loggerDebug("RnewManuHealthQSave","here");

String busiName="WorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
System.out.println("开始执行：tBusinessDelegate");
if(tBusinessDelegate.submitData(tVData,"execute",busiName)){
	Content = " 保存成功! ";
	FlagStr = "Succ";
}else{
	Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	FlagStr = "Fail";
}

	//RnewWorkFlowUI tRnewWorkFlowUI = new RnewWorkFlowUI();

		loggerDebug("RnewManuHealthQSave","this will save the data!!!");
		/*
  if(tRnewWorkFlowUI.submitData(tVData,"0000007013")){
	
			Content = " 保存成功! ";
			FlagStr = "Succ";
	
	}
	else{
		Content = "保存失败，原因是:" + tRnewWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
*/

/*****************************************************
	DisDesbUI tDisDesbUI = new DisDesbUI();
	try{
		loggerDebug("RnewManuHealthQSave","this will save the data!!!");
		tDisDesbUI.submitData(tVData,"");
	}
	catch(Exception ex){
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}
******************************************************/

%>
<%@page import="com.sinosoft.lis.xb.RnewCommonDataPut"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

