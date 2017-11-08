<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�InsuredUWInfoChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2005-01-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
    <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	
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
		tLCInsuredSchema.setInsuredStat("1");    //1-��ͣ 0-δ��ͣ
		
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLCInsuredSchema);
		
		//StopInsuredUI tStopInsuredUI = new StopInsuredUI();
		   String busiName="cbcheckgrpStopInsuredUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tBusinessDelegate.submitData(tVData,"",busiName);
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
  	loggerDebug("InsuredUWInfoChk","===============------"+tSugUWFlag);
 		tLCUWMasterSchema.setPolNo(tPolNo);
 		tLCUWMasterSchema.setProposalNo(tPolNo);
 		tLCUWMasterSchema.setPassFlag(tPassFlag);
 		tLCUWMasterSchema.setUWIdea(tUWIdea);
 		tLCUWMasterSchema.setSugPassFlag(tSugUWFlag);
		TransferData mTransferData = new TransferData();
		if(tPassFlag.equals("L"))
		{
        	mTransferData.setNameAndValue("AddPrem",tPropValue);
        }
        else if(tPassFlag.equals("E"))
        {
        	mTransferData.setNameAndValue("SpecReason",tPropValue);
        }
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tLCUWMasterSchema);
		tVData.add(mTransferData);
		
		//ManuUWRiskSaveUI tManuUWRiskSaveUI = new ManuUWRiskSaveUI();
		   String busiName="cbcheckgrpManuUWRiskSaveUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tBusinessDelegate.submitData(tVData,"",busiName);
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}	
	}
	else if(Flag.equals("Insured"))
	{
		
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	
 		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");
 		String tUWIndIdea=request.getParameter("UWIndIdea");
  	String tIndPassFlag=request.getParameter("uwindstate");
  	String tSugUWIndIdea=request.getParameter("SugIndUWIdea");
  	String tSugIndPassFlag=request.getParameter("SugIndUWFlag");
  	
 		tLCIndUWMasterSchema.setContNo(tContNo);
 		tLCIndUWMasterSchema.setPassFlag(tIndPassFlag);
 		tLCIndUWMasterSchema.setUWIdea(tUWIndIdea);
 		tLCIndUWMasterSchema.setSugPassFlag(tSugIndPassFlag);
 		tLCIndUWMasterSchema.setSugUWIdea(tSugUWIndIdea);
 		 
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
		tTransferData.setNameAndValue("LCIndUWMasterSchema",tLCIndUWMasterSchema); 		 
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		//LCInsuredUWUI tLCInsuredUWUI = new LCInsuredUWUI();
		   String busiName="cbcheckgrpLCInsuredUWUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try{
			loggerDebug("InsuredUWInfoChk","this will save the data!!!");
			tBusinessDelegate.submitData(tVData,"submit",busiName);
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
