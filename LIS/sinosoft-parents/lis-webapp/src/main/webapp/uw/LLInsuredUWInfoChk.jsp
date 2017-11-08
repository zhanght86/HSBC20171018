<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：InsuredUWInfoChk.jsp
//程序功能：人工核保体检资料查询
//创建日期：2005-01-19 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	String tBatNo = request.getParameter("BatNo");
	String tClmNo = request.getParameter("ClmNo");
	loggerDebug("LLInsuredUWInfoChk","@@@Flag:"+Flag);
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
	
//	if(Flag.equals("StopInsured"))
//	{
//		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
//		
//		tLCInsuredSchema.setContNo(request.getParameter("ContNo"));
//		tLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
//		tLCInsuredSchema.setInsuredStat("1");    //1-暂停 0-未暂停
//		
//		VData tVData = new VData();
//		FlagStr="";
  	
//		tVData.add(tG);
//		tVData.add(tLCInsuredSchema);
		
//		StopInsuredUI tStopInsuredUI = new StopInsuredUI();
		
//		try{
//			loggerDebug("LLInsuredUWInfoChk","this will save the data!!!");
//			tStopInsuredUI.submitData(tVData,"");
//		}
//		catch(Exception ex){
//			Content = "保存失败，原因是:" + ex.toString();
//			FlagStr = "Fail";
//		}
		
//		if (!FlagStr.equals("Fail")){
//			tError = tStopInsuredUI.mErrors;
//			if (!tError.needDealError()){
//				Content = " 保存成功! ";
//				FlagStr = "Succ";
//			}
//			else{
//				Content = " 保存失败，原因是:" + tError.getFirstError();
//				FlagStr = "Fail";
//			}
//		}		
//	}
//	else
	if(Flag.equals("risk")){
	  	LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
	  	
	 	String tPolNo=request.getParameter("PolNo");
	 	String tUWIdea=request.getParameter("UWIdea");
	 	String tPropValue = request.getParameter("PropValue");
	  	String tPassFlag=request.getParameter("uwstate");
	  	String tSugUWFlag = request.getParameter("SugUWFlag");
	  	String tamnt = request.getParameter("amnt");
	  	loggerDebug("LLInsuredUWInfoChk","===============------"+tamnt);
	  	tLLUWMasterSchema.setPolNo(tPolNo);
	  	tLLUWMasterSchema.setProposalNo(tPolNo);
	  	tLLUWMasterSchema.setPassFlag(tPassFlag);
	  	tLLUWMasterSchema.setUWIdea(tUWIdea);
	  	tLLUWMasterSchema.setSugPassFlag(tSugUWFlag);
	  	tLLUWMasterSchema.setChangePolReason(tamnt);
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("BatNo",tBatNo);
		mTransferData.setNameAndValue("ClmNo",tClmNo);
		if(tPassFlag.equals("L"))
		{
        	mTransferData.setNameAndValue("AddPrem",tPropValue);
        }
        else if(tPassFlag.equals("E"))
        {
        	mTransferData.setNameAndValue("SpecReason",tPropValue);
        }
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLLUWMasterSchema);
		tVData.add(mTransferData);
		
		/*LLManuUWRiskSaveUI tLLManuUWRiskSaveUI = new LLManuUWRiskSaveUI();
		
		try{
			loggerDebug("LLInsuredUWInfoChk","this will save the data!!!");
			tLLManuUWRiskSaveUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tLLManuUWRiskSaveUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}	*/
		String busiName="LLManuUWRiskSaveUI";
		  String mDescType="保存";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			loggerDebug("LLInsuredUWInfoChk","this will save the data!!!");
			  if(!tBusinessDelegate.submitData(tVData,"",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"失败";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"成功! ";
			      	FlagStr = "Succ";  
			  }
	}
	else if(Flag.equals("Insured"))
	{
		
		LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
 	
		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");
 		String tUWIndIdea=request.getParameter("UWIdea");
		String tIndPassFlag=request.getParameter("uwindstate");
		String tSugUWIndIdea=request.getParameter("SugIndUWIdea");
  		String tSugIndPassFlag=request.getParameter("SugIndUWFlag");
  	
  		tLLCUWMasterSchema.setContNo(tContNo);
  		tLLCUWMasterSchema.setInsuredNo(tInsuredNo);
  		tLLCUWMasterSchema.setPassFlag(tIndPassFlag);
  		tLLCUWMasterSchema.setChangePolReason(tUWIndIdea);
  		tLLCUWMasterSchema.setSugPassFlag(tSugIndPassFlag);
  		tLLCUWMasterSchema.setUpReportContent(tSugUWIndIdea);
 		 
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
		tTransferData.setNameAndValue("LLCUWMasterSchema",tLLCUWMasterSchema); 		 
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		/*LLUWIdeaUI tLLUWIdeaUI = new LLUWIdeaUI();
		
		try{
			loggerDebug("LLInsuredUWInfoChk","this will save the data!!!");
			tLLUWIdeaUI.submitData(tVData,"submit");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tLLUWIdeaUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}*/
		String busiName="LLUWIdeaUI";
		  String mDescType="保存";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			loggerDebug("LLInsuredUWInfoChk","this will save the data!!!");
			  if(!tBusinessDelegate.submitData(tVData,"submit",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"失败";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"成功! ";
			      	FlagStr = "Succ";  
			  }
	}
%>                      
<html>
<script language="javascript">
	
	if('<%=Flag%>'!='Insured')
	{
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	}
	else
		{
			parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
			}
</script>
</html>
