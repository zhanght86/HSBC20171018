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
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	loggerDebug("InsuredUWInfoChk","@@@Flag:"+Flag);
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
	
	if(Flag.equals("StopInsured"))
	{
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		
		tLCInsuredSchema.setContNo(request.getParameter("ContNo"));
		tLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
		tLCInsuredSchema.setInsuredStat("1");    //1-暂停 0-未暂停
		
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLCInsuredSchema);
		
		StopInsuredUI tStopInsuredUI = new StopInsuredUI();
		
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tStopInsuredUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tStopInsuredUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}		
	}
	else if(Flag.equals("risk"))
	{
  	LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
  	
 		String tPolNo=request.getParameter("PolNo");
 		String tUWIdea=request.getParameter("UWIdea");
 		String tPropValue = request.getParameter("PropValue");
  	String tPassFlag=request.getParameter("uwstate");
  	String tSugUWFlag = request.getParameter("SugUWFlag");
  	String tamnt = request.getParameter("amnt");
  	loggerDebug("InsuredUWInfoChk","===============------"+tamnt);
 		tLCUWMasterSchema.setPolNo(tPolNo);
 		tLCUWMasterSchema.setProposalNo(tPolNo);
 		tLCUWMasterSchema.setPassFlag(tPassFlag);
 		tLCUWMasterSchema.setUWIdea(tUWIdea);
 		tLCUWMasterSchema.setSugPassFlag(tSugUWFlag);
 		tLCUWMasterSchema.setChangePolReason(tamnt);
		TransferData mTransferData = new TransferData();
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
		tVData.add(tLCUWMasterSchema);
		tVData.add(mTransferData);
		
		ManuUWRiskSaveUI tManuUWRiskSaveUI = new ManuUWRiskSaveUI();
		
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tManuUWRiskSaveUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tManuUWRiskSaveUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}	
	}
	else if(Flag.equals("Insured"))
	{
		
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	
 		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");
 		String tUWIndIdea=request.getParameter("UWIdea");
  //	String tIndPassFlag=request.getParameter("uwindstate");
  //	String tSugUWIndIdea=request.getParameter("SugIndUWIdea");
  //	String tSugIndPassFlag=request.getParameter("SugIndUWFlag");
  	
 		tLCIndUWMasterSchema.setContNo(tContNo);
 		tLCIndUWMasterSchema.setInsuredNo(tInsuredNo);
 	//	tLCIndUWMasterSchema.setPassFlag(tIndPassFlag);
 		tLCIndUWMasterSchema.setUWIdea(tUWIndIdea);
 	//	tLCIndUWMasterSchema.setSugPassFlag(tSugIndPassFlag);
 	//	tLCIndUWMasterSchema.setSugUWIdea(tSugUWIndIdea);
 		 
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
		tTransferData.setNameAndValue("LCIndUWMasterSchema",tLCIndUWMasterSchema); 		 
		// 准备传输数据 VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		LCInsuredUWUI tLCInsuredUWUI = new LCInsuredUWUI();
		
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tLCInsuredUWUI.submitData(tVData,"submit");
		}
		catch(Exception ex){
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tLCInsuredUWUI.mErrors;
			if (!tError.needDealError()){
				Content = " 保存成功! ";
				FlagStr = "Succ";
			}
			else{
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
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
