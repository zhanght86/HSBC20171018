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
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
	String Flag = request.getParameter("flag");
	loggerDebug("BQInsuredUWInfoChk","@@@Flag:"+Flag);
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
		
		StopInsuredUI tStopInsuredUI = new StopInsuredUI();
		
		try{
			loggerDebug("BQInsuredUWInfoChk","this will save the data!!!");
			tStopInsuredUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tStopInsuredUI.mErrors;
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
  	LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
  		String tEdorNo=request.getParameter("EdorNo");
  		String tEdorType=request.getParameter("EdorType");
 		String tPolNo=request.getParameter("PolNo");
 		String tUWIdea=request.getParameter("UWIdea");
 		String tPropValue = request.getParameter("PropValue");
	  	String tPassFlag=request.getParameter("uwstate");
	  	String tSugUWFlag = request.getParameter("SugUWFlag");
	  	String tamnt = request.getParameter("amnt");
	  	loggerDebug("BQInsuredUWInfoChk","===============------"+tamnt);
	  	tLPUWMasterSchema.setEdorNo(tEdorNo);
	  	tLPUWMasterSchema.setEdorType(tEdorType);
 		tLPUWMasterSchema.setPolNo(tPolNo);
 		tLPUWMasterSchema.setProposalNo(tPolNo);
 		tLPUWMasterSchema.setPassFlag(tPassFlag);
 		tLPUWMasterSchema.setUWIdea(tUWIdea);
 		tLPUWMasterSchema.setSugPassFlag(tSugUWFlag);
 		tLPUWMasterSchema.setChangePolReason(tamnt);
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
		tVData.add(tLPUWMasterSchema);
		tVData.add(mTransferData);
		
		BQUWRiskSaveUI tBQUWRiskSaveUI = new BQUWRiskSaveUI();
		
		try{
			loggerDebug("BQInsuredUWInfoChk","this will save the data!!!");
			tBQUWRiskSaveUI.submitData(tVData,"");
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBQUWRiskSaveUI.mErrors;
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
		
  	LPIndUWMasterSchema tLPIndUWMasterSchema = new LPIndUWMasterSchema();
  	
  		String tEdorNo=request.getParameter("EdorNo");
  		String tEdorType=request.getParameter("EdorType");
 		String tContNo=request.getParameter("ContNo");
 		String tInsuredNo = request.getParameter("InsuredNo");
 		String tUWIndIdea=request.getParameter("UWIdea");
  //	String tIndPassFlag=request.getParameter("uwindstate");
  //	String tSugUWIndIdea=request.getParameter("SugIndUWIdea");
  //	String tSugIndPassFlag=request.getParameter("SugIndUWFlag");
  		tLPIndUWMasterSchema.setEdorNo(tEdorNo);
 		tLPIndUWMasterSchema.setContNo(tContNo);
 		tLPIndUWMasterSchema.setInsuredNo(tInsuredNo);
 	//	tLPIndUWMasterSchema.setPassFlag(tIndPassFlag);
 		tLPIndUWMasterSchema.setUWIdea(tUWIndIdea);
 	//	tLPIndUWMasterSchema.setSugPassFlag(tSugIndPassFlag);
 	//	tLPIndUWMasterSchema.setSugUWIdea(tSugUWIndIdea);
 		 
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorType",tEdorType);
		tTransferData.setNameAndValue("EdorNo",tEdorNo);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
		tTransferData.setNameAndValue("LPIndUWMasterSchema",tLPIndUWMasterSchema); 		 
		// ׼���������� VData
		VData tVData = new VData();
		FlagStr="";
  	
		tVData.add(tG);
		tVData.add(tTransferData);
		
		BQInsuredUWUI tBQInsuredUWUI = new BQInsuredUWUI();
		
		try{
			loggerDebug("BQInsuredUWInfoChk","this will save the data!!!");
			tBQInsuredUWUI.submitData(tVData,"submit");
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBQInsuredUWUI.mErrors;
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
