<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//Modify by niuzj,2006-08-23,Ӣ����Ҫ��¼����������Ϣʱ����һ�����Ա��ֶ�
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%
CErrors tError = null;
String FlagStr = "";
String Content = "";
int k = 0; //��־¼����Լ
String FORMATMODOL = "0.00"; //���ѱ�����������ľ�ȷλ��
DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); //����ת������
String ttDutyCode = "";
String tContNo = request.getParameter("ContNo");
//String tPolNo = request.getParameter("PolNo");
ttDutyCode = request.getParameter( "DutyCode" );
String tOperate = "UPDATE||PROPOSAL";
GlobalInput tG = new GlobalInput();
tG = ( GlobalInput )session.getValue( "GI" );
//��ȡ��Ӧ������
String approveFlag = "";
String UWFlag = "";
String approveCode = "";
String approveDate = "";

	LCPremToAccDB tLCPremToAccDB =new LCPremToAccDB();
	LCPremToAccSet tLCPremToAccSet =new LCPremToAccSet();
	LCPremSchema tLCPremSchema = new LCPremSchema();
	LCPremDB tLCPremDB =new LCPremDB();
	LCPremSet tLCPremSet = new LCPremSet();
	LCDutySchema tLCDutySchema =new LCDutySchema();
	LCDutyDB tLCDutyDB =new LCDutyDB();
	LCDutySet tLCDutySet = new LCDutySet();
	LCSpecDB tLCSpecDB = new LCSpecDB();
	LCSpecSet tLCSpecSet = new LCSpecSet();
	LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema(); //Ա����ͬ��Լ
	LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
	LCCustomerImpartDB tLCCustomerImpartDB =new LCCustomerImpartDB();
	LCCustomerImpartSet tLCCustomerImpartSet =new LCCustomerImpartSet();
	LCBnfDB tLCBnfDB = new LCBnfDB();
	LCBnfSet tLCBnfSet =new LCBnfSet();
	LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
	LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
	LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	LCInsuredDB tLCInsuredDB = new LCInsuredDB();
	LCInsuredSet tLCInsuredSet = new LCInsuredSet();
	LCGrpAppntSchema tLCGrpAppntSchema =new LCGrpAppntSchema();
	LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
	LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
	LCAppntSchema tLCAppntSchema =new LCAppntSchema();
	LCAppntDB tLCAppntDB = new LCAppntDB();
	LCAppntSet tLCAppntSet = new LCAppntSet();
	LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	LCGrpPolDB tLCGrpPolDB =new LCGrpPolDB();
	LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPolDB tLCPolDB = new LCPolDB();
	LCPolSet tLCPolSet = new LCPolSet();
	LCContSchema tLCContSchema = new LCContSchema();
	LCContDB tLCContDB = new LCContDB();
	LCContSet tLCContSet = new LCContSet();
	LCGetDB tLCGetDB = new LCGetDB();
	LCGetSet tLCGetSet = new LCGetSet();
	String tChk[] = request.getParameterValues("InpRiskFloatRateGridChk");
	String tPolNo[] = request.getParameterValues("RiskFloatRateGrid5");
	String tGetDutyKind[] = request.getParameterValues("RiskFloatRateGrid6");
	String tNewFloatRate[] = request.getParameterValues("RiskFloatRateGrid4");
	//String tRadio[] = request.getParameterValues("InpObjGridSel"); 
	String tSpecOperate = request.getParameter("SpecOperate");
	//String tGetDutyKind = request.getParameter("GetDutyKind");
	String tInsuredNo = request.getParameter("InsuredNo"); //add ln 2008-12-6 �����˿ͻ���
	loggerDebug("UWModifyFloatRateSave","��Щ������ҪGetDutyKind��"+tGetDutyKind);
	//tongmeng 2009-05-09 ����ɾ��Ա����Լ
	if(tSpecOperate!=null&&!tSpecOperate.equals("DELETE&&YGSPEC"))
	{
	for(int j=0;j<tChk.length;j++)
	{
		if(tChk[j].equals("1"))
		{
			//���б�ѡ��
	try{
		loggerDebug("UWModifyFloatRateSave","ContNo: "+tContNo);
		loggerDebug("UWModifyFloatRateSave","PolNo: "+tPolNo);
		//���ݺ�ͬ�Ų�ѯLCPremToAccSchema��
		tLCPremToAccDB.setPolNo(tPolNo[j]);
	    tLCPremToAccSet = tLCPremToAccDB.query();
		//LCPrem
		tLCPremDB.setPolNo(tPolNo[j]);
		tLCPremSet =tLCPremDB.query();
		LCPremSet tLCPremSet1 =new LCPremSet();
		for(int i=1;i<=tLCPremSet.size();i++){
			tLCPremSchema =new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			tLCPremSchema.setPrem(0);
			//tLCPremSchema.setSumPrem(0);
			//tLCPremSchema.setStandPrem(0);
			tLCPremSet1.add(tLCPremSchema);
		}
		
		//��ͬ����Ա����Լ¼��
		tLCCSpecSchema.setSerialNo(request.getParameter("SerialNo"));
		tLCCSpecSchema.setContNo(request.getParameter("ContNo"));
		tLCCSpecSchema.setGrpContNo("00000000000000000000");
		tLCCSpecSchema.setProposalContNo(request.getParameter("ContNo"));
		tLCCSpecSchema.setOperator(tG.Operator);
		tLCCSpecSchema.setPrtFlag("1");
		tLCCSpecSchema.setNeedPrint("N");
		tLCCSpecSchema.setSpecCode(request.getParameter("SpecCode"));
		tLCCSpecSchema.setSpecType(request.getParameter("SpecType"));
		tLCCSpecSchema.setSpecReason(request.getParameter("SpecReason"));
		//loggerDebug("UWModifyFloatRateSave","��Լԭ��" + request.getParameter("SpecReason"));
		tLCCSpecSchema.setSpecContent(request.getParameter("FloatRateIdea"));
		tLCCUWMasterSchema.setSpecReason(request.getParameter("SpecReason"));
		//LCDuty   FloatRate
		tLCDutyDB.setPolNo(tPolNo[j]);
		tLCDutySet = tLCDutyDB.query();
		if(tLCDutySet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","��ѯLCDuty��ʧ�ܣ�");
		}else{
			tLCDutySchema =tLCDutySet.get(1);
			tLCDutySchema.setFloatRate(tNewFloatRate[j]);
			tLCDutySchema.setPrem(0);
			tLCDutySchema.setCalRule("2");
			
			//tLCDutySchema.setCalRule("0");
			//tLCDutySchema.setSumPrem(0);
			//tLCDutySchema.setStandPrem(0);
		}
		
		//LCSpec 
		tLCSpecDB.setPolNo(tPolNo[j]);
		tLCSpecSet = tLCSpecDB.query();
		//LCCustomerImpart
		tLCCustomerImpartDB.setContNo(tContNo);
		tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		//LCBnf
		tLCBnfDB.setPolNo(tPolNo[j]);
		tLCBnfSet = tLCBnfDB.query();
		//LCInsuredRelated
		tLCInsuredRelatedDB.setPolNo(tPolNo[j]);
		tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
		//LCInsured
		tLCInsuredDB.setContNo(tContNo);
		tLCInsuredSet = tLCInsuredDB.query();
		if(tLCInsuredSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","��ѯLCInsured�����");
		}else{
			tLCInsuredSchema = tLCInsuredSet.get(1);
		}
		//LCGrpAppnt
		tLCGrpAppntDB.setPrtNo(tContNo);
		tLCGrpAppntSet = tLCGrpAppntDB.query();
		if(tLCGrpAppntSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","��ѯLCGrpAppnt�����");
		}else{
			tLCGrpAppntSchema = tLCGrpAppntSet.get(1);
		}
		//LCAppnt
		tLCAppntDB.setContNo(tContNo);
		tLCAppntSet = tLCAppntDB.query();
		if(tLCAppntSet.size()<=0){
			loggerDebug("UWModifyFloatRateSave","��ѯLCAppnt�����");
		}else{
			tLCAppntSchema =tLCAppntSet.get(1);
		}
		//LCGrpPol
		tLCGrpPolDB.setGrpPolNo(tPolNo[j]);
		if(tLCGrpPolDB.getInfo()==true){
			tLCGrpPolSchema = tLCGrpPolDB.getSchema();
		}
		//LCPol
		tLCPolDB.setPolNo(tPolNo[j]);
		if(tLCPolDB.getInfo()==true){
			tLCPolSchema =tLCPolDB.getSchema();
			tLCPolSchema.setFloatRate(tNewFloatRate[j]);
			tLCPolSchema.setPrem(0);
			//tLCPolSchema.setSumPrem(0);
			//tLCPolSchema.setStandPrem(0);
			approveFlag = tLCPolSchema.getApproveFlag();
			UWFlag = tLCPolSchema.getUWFlag();
			approveCode = tLCPolSchema.getApproveCode();
			approveDate = tLCPolSchema.getApproveDate();
			
			
		}
		//LCCont
		tLCContDB.setContNo(tContNo);
		if(tLCContDB.getInfo()==true){
			tLCContSchema = tLCContDB.getSchema();
		}
		
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("SavePolType",tLCPolSchema.getAppFlag());
        tTransferData.setNameAndValue("samePersonFlag", request.getParameter("SamePersonFlag"));
        tTransferData.setNameAndValue("deleteAccNo", "0");
        tTransferData.setNameAndValue("ChangePlanFlag", "0");
        tTransferData.setNameAndValue("GetDutyKind",tGetDutyKind[j]);
		VData tVData = new VData();
		  tVData.addElement(tLCContSchema);
		  tVData.addElement(tLCGrpPolSchema);
		  tVData.addElement(tLCPolSchema);
		  tVData.addElement(tLCAppntSchema);
		  tVData.addElement(tLCGrpAppntSchema);
		  tVData.addElement(tLCInsuredSchema);
		  tVData.addElement(tLCInsuredRelatedSet);
		  tVData.addElement(tLCBnfSet);
		  //tVData.addElement(tLCCustomerImpartSet);
		  //tVData.addElement(tLCCSpecSchema);
		  tVData.addElement(tLCSpecSet);
		  tVData.addElement(tLCDutySet);
		  //tVData.addElement(tLCPremSet1);
	      tVData.addElement(tLCPremToAccSet);
		  tVData.addElement(tG);
		  tVData.addElement(tTransferData);
		  
		  ProposalUI tProposalUI = new ProposalUI();
		  
		  if( !tProposalUI.submitData( tVData, tOperate ) ) {
			    tError = tProposalUI.mErrors;
			    Content += tError.getFirstError();
			    FlagStr = "Fail";
			  }
			  else {
				  if(k==0){
					  
					/*

			approveFlag = (String)tTransferData.getValueByName("ApproveFlag");
			                UWFlag = (String)tTransferData.getValueByName("UWFlag");
			                approveCode = (String)tTransferData.getValueByName("ApproveCode");
			                approveDate = (String)tTransferData.getValueByName("ApproveDate");
			
					*/
					 //tongmeng 2008-12-18 add
					  //�޸�Ͷ����,��Ҫ�ָ���������
					
					  {
						  	LCPolSchema tempLCPolSchema = new LCPolSchema();
						    tLCPolSchema.setPolNo(tPolNo[j]);
						    tLCPolSchema.setApproveFlag(approveFlag);
						    tLCPolSchema.setUWFlag(UWFlag);
						    tLCPolSchema.setApproveCode(approveCode);
						    tLCPolSchema.setApproveDate(approveDate);

						    VData VData3 = new VData();
						    VData3.add(tLCPolSchema);
							 	VData3.add(tG);
							 	QueryPolInfoBL tQueryPolInfoBL = new QueryPolInfoBL();
						    if (!tQueryPolInfoBL.submitData(VData3, "INSERT")) {
						      Content = "����ʧ�ܣ�ԭ����: " + tQueryPolInfoBL.mErrors.getError(0).errorMessage;
						      FlagStr = "Fail";
						      throw new Exception();
						    }
					  }
					
					  
					tVData = new VData();
					tVData.add(tLCContSchema);
					tVData.add(tTransferData);
				  	tVData.add(tLCCSpecSchema);
				  	tVData.add( tLCCUWMasterSchema );
					//tTransferData.setNameAndValue("PolNo",tPolNo);
					//tTransferData.setNameAndValue("Operatetype",tOperatetype);
					//tTransferData.setNameAndValue("Proposalno",tProposalno);
					//tTransferData.setNameAndValue("Serialno",tSerialno);					
				tVData.add( tG );
		
				// ���ݴ���
				UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
				if (!tUWSpecInputUI.submitData(tVData,tSpecOperate))
				  {     
		        
					int n = tUWSpecInputUI.mErrors.getErrorCount();
					Content = " Ա����Լ����ʧ�ܣ�ԭ����: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				   }
//					�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
					if (FlagStr.equals("Fail"))
					{
					    tError = tUWSpecInputUI.mErrors;
					    if (!tError.needDealError())
					    {                          
					    	Content = " Ա����Լ����ɹ�! ";
					    	FlagStr = "Succ";
					    }
					    else                                                                           
					    {
						    FlagStr = "Fail";
					    }
					}
				  }
				if (!FlagStr.equals("Fail"))
				{
			   	 	Content = "����ɹ�!"; 
			    	FlagStr = "Succ";
				}

			    tVData.clear();
			    tVData = tProposalUI.getResult();
			   
			  
			    /////////end add by yaory
			  }  
		  
		  
	      }catch(Exception ex){
		    continue;
	      }
		}
	}
	}
	else
	{
		//ɾ�� 
		loggerDebug("UWModifyFloatRateSave","ContNo: "+tContNo);
		loggerDebug("UWModifyFloatRateSave","PolNo: "+tPolNo);
		VData tVData = new VData();
		tVData.add(tG);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo",tContNo);
		tVData.add(tTransferData);
		// ���ݴ���
		UWSpecInputUI tUWSpecInputUI   = new UWSpecInputUI();
		if (!tUWSpecInputUI.submitData(tVData,tSpecOperate))
		  {     
        
			int n = tUWSpecInputUI.mErrors.getErrorCount();
			Content = " Ա����Լ����ʧ�ܣ�ԭ����: " + tUWSpecInputUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		   }
//			�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			if (FlagStr.equals("Fail"))
			{
			    tError = tUWSpecInputUI.mErrors;
			    if (!tError.needDealError())
			    {                          
			    	Content = " Ա����Լ����ɹ�! ";
			    	FlagStr = "Succ";
			    }
			    else                                                                           
			    {
				    FlagStr = "Fail";
			    }
			}

			if (!FlagStr.equals("Fail"))
			{
		   	 	Content = "����ɹ�!"; 
		    	FlagStr = "Succ";
			}
	}

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>



  

