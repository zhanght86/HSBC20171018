<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�DirectContSave.jsp
//�����ܣ�ֱ������¼���ͬ��Ͷ������Ϣ����
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	//�������
	VData tVData = new VData();
	LCContSchema tLCContSchema   = new LCContSchema();
	LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	LCAddressSchema tLCAddressSchema = new LCAddressSchema();
	LCAccountSchema tLCAccountSchema = new LCAccountSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
    
	tAction = request.getParameter( "fmAction" );
    loggerDebug("DirectContSave","������:"+tAction);
	if( tAction.equals( "DELETE" ))
	{
		tLCContSchema.setContNo(request.getParameter("ContNo"));
		tLCContSchema.setManageCom(request.getParameter("ManageCom"));	    
	}
	else
	{  
		//���� ���������࣬����[ϵͳ���������켣��]д����Ϣ
		LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
		tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
		tLDSysTraceSchema.setCreatePos("�б�¼��");
		tLDSysTraceSchema.setPolState("1002");
		LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
		inLDSysTraceSet.add(tLDSysTraceSchema);
		VData VData3 = new VData();
		VData3.add(tG);
		VData3.add(inLDSysTraceSet);
		LockTableUI LockTableUI1 = new LockTableUI();
		if (!LockTableUI1.submitData(VData3, "INSERT")) 
		{
		VData rVData = LockTableUI1.getResult();
		loggerDebug("DirectContSave","LockTable Failed! " + (String)rVData.get(0));
		}
		else 
		{
		loggerDebug("DirectContSave","LockTable Succed!");
		}
		//��ͬ��Ϣ
		if( tAction.equals( "UPDATE" ))
		{
			tLCContSchema.setGrpContNo(request.getParameter("GrpContNo")); 
			tLCContSchema.setContNo(request.getParameter("ContNo")); 
			tLCContSchema.setProposalContNo(request.getParameter("ProposalContNo")); 
		}
		if( tAction.equals( "INSERT" ))
		{
			tLCContSchema.setGrpContNo(request.getParameter("GrpContNo")); 
			tLCContSchema.setContNo(request.getParameter("ContNo")); 
			tLCContSchema.setProposalContNo(request.getParameter("ProposalContNo")); 
		}
		tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
		tLCContSchema.setManageCom(request.getParameter("ManageCom"));
		tLCContSchema.setSaleChnl("5");
		tLCContSchema.setGetPolMode(request.getParameter("CSplit"));
		tLCContSchema.setSellType(request.getParameter("SellType"));
		//�������Ա���벻���ڣ���LCCont���ҵ��Ա�ֶδ�����Ա��Ϣ������滰��Ա��Ϣ
		if(request.getParameter("TelephonistCode")==null || request.getParameter("TelephonistCode").equals(""))
        {
			tLCContSchema.setAgentCode(request.getParameter("AgentCode"));
			tLCContSchema.setAgentGroup(request.getParameter("AgentGroup"));		
		}
		else
		{
			tLCContSchema.setAgentCode(request.getParameter("TelephonistCode"));
			tLCContSchema.setAgentGroup(request.getParameter("TelephGroup"));
		}
		tLCContSchema.setAgentCom(request.getParameter("AgentCom"));
		tLCContSchema.setAgentType(request.getParameter("AgentType"));
		tLCContSchema.setPolType("0");
		tLCContSchema.setContType("1");
		tLCContSchema.setPolApplyDate(request.getParameter("PolApplyDate"));        
		tLCContSchema.setForceUWFlag("0");
		tLCContSchema.setRemark(request.getParameter("Remark"));
		
		tLCContSchema.setBankCode(request.getParameter("NewAccName"));
		tLCContSchema.setAccName(request.getParameter("AppntAccName"));
		tLCContSchema.setBankAccNo(request.getParameter("NewBankAccNo"));
		
		//¼�������˺�
	    tLCContSchema.setNewPayMode(request.getParameter("NewPayMode"));
	    tLCContSchema.setNewBankCode(request.getParameter("NewBankCode"));
	    tLCContSchema.setNewBankAccNo(request.getParameter("NewBankAccNo"));
	    tLCContSchema.setNewAccName(request.getParameter("NewAccName"));		
	    //¼�������˺�
	    tLCContSchema.setPayLocation(request.getParameter("SecPayMode"));
	    tLCContSchema.setBankCode(request.getParameter("SecBankCode"));
	    tLCContSchema.setBankAccNo(request.getParameter("SecBankAccNo"));
	    tLCContSchema.setAccName(request.getParameter("SecAccName"));
		
		//Ͷ������Ϣ(����������Ϣ��ְҵ��Ϣ����ַ��Ϣ��)
		tLCAppntSchema.setAppntNo(request.getParameter("AppntNo"));       
		tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              
		tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));               
		tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));          
		tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));            
		tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo")); 
		tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));        
		tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));          
		//tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));                    
		tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));    
		tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));    
		//tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));          
		//tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));
		//tLCAppntSchema.setSmokeFlag(request.getParameter("AppntSmokeFlag"));
		tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace"));
		//tLCAppntSchema.setNationality(request.getParameter("AppntNationality"));         
		tLCAppntSchema.setBankCode(request.getParameter("NewBankCode"));
		tLCAppntSchema.setAccName(request.getParameter("NewAccName"));
		tLCAppntSchema.setBankAccNo(request.getParameter("NewBankAccNo"));
		tLCAppntSchema.setMakeDate(request.getParameter("AppntMakeDate"));
		tLCAppntSchema.setMakeTime(request.getParameter("AppntMakeTime"));
		tLCAppntSchema.setLicenseType(request.getParameter("AppntLicenseType"));
		
		tLCAccountSchema.setBankCode(request.getParameter("NewBankCode"));
		tLCAccountSchema.setAccName(request.getParameter("NewAccName"));
		tLCAccountSchema.setBankAccNo(request.getParameter("NewBankAccNo"));
		tLCAccountSchema.setAccKind("1");
			
		//Ͷ���˵�ַ��Ϣ
		tLCAddressSchema.setCustomerNo(request.getParameter("AppntNo"));
		tLCAddressSchema.setAddressNo(request.getParameter("AppntAddressNo"));                    
		tLCAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));
		tLCAddressSchema.setZipCode(request.getParameter("AppntZipCode"));
		tLCAddressSchema.setPhone(request.getParameter("AppntPhone"));
		tLCAddressSchema.setFax(request.getParameter("AppntFax"));        
		tLCAddressSchema.setMobile(request.getParameter("AppntMobile"));
		tLCAddressSchema.setEMail(request.getParameter("AppntEMail"));
		tLCAddressSchema.setHomeAddress(request.getParameter("AppntHomeAddress"));
		tLCAddressSchema.setHomePhone(request.getParameter("AppntHomePhone"));
		tLCAddressSchema.setHomeFax(request.getParameter("AppntHomeFax"));
		tLCAddressSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode"));        
		tLCAddressSchema.setCompanyPhone(request.getParameter("AppntGrpPhone"));
		tLCAddressSchema.setCompanyAddress(request.getParameter("CompanyAddress"));
		tLCAddressSchema.setCompanyZipCode(request.getParameter("AppntGrpZipCode"));
		tLCAddressSchema.setCompanyFax(request.getParameter("AppntGrpFax"));
		tLCAddressSchema.setProvince(request.getParameter("AppntProvince"));
		tLCAddressSchema.setCity(request.getParameter("AppntCity"));
		tLCAddressSchema.setCounty(request.getParameter("AppntDistrict"));
		tLCAddressSchema.setGrpName(request.getParameter("AppntGrpName"));	
		
		//��ҵ��Ա��Ϣ����Ҫ��Ӷ��ķ�����Ϣ��
		//���������¼��������Ա����ô��洢����Ա��Ϣ��
		if(request.getParameter("AgentCode")==null || request.getParameter("AgentCode").equals(""))
        { }
    	else
    	{
			LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
			tLACommisionDetailSchema.setGrpContNo(request.getParameter("ContNo"));
			tLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
			tLACommisionDetailSchema.setAgentGroup(request.getParameter("AgentGroup"));
			tLACommisionDetailSchema.setBusiRate(request.getParameter("AgentBusiRate"));
			tLACommisionDetailSchema.setPolType("0");
			//tLACommisionDetailSchema.setMakeDate(request.getParameter("AppntMakeDate"));  
			//tLACommisionDetailSchema.setMakeTime(request.getParameter("AppntMakeTime"));  
			tLACommisionDetailSet.add(tLACommisionDetailSchema);
		}
		//���������¼���˻���Ա����ô��洢����Ա��Ϣ��
		if(request.getParameter("TelephonistCode")==null || request.getParameter("TelephonistCode").equals(""))
        { }
		else
		{
			LACommisionDetailSchema mLACommisionDetailSchema = new LACommisionDetailSchema();
			mLACommisionDetailSchema.setGrpContNo(request.getParameter("ContNo"));
			mLACommisionDetailSchema.setAgentCode(request.getParameter("TelephonistCode"));
			mLACommisionDetailSchema.setAgentGroup(request.getParameter("TelephGroup"));
			mLACommisionDetailSchema.setBusiRate(request.getParameter("TeleBusiRate"));
			mLACommisionDetailSchema.setPolType("0");
			//mLACommisionDetailSchema.setMakeDate(request.getParameter("MakeDate"));  
			//mLACommisionDetailSchema.setMakeTime(request.getParameter("MakeTime"));  
			tLACommisionDetailSet.add(mLACommisionDetailSchema);			
		}
		//����׼��Ͷ���˸�֪��Ϣ
//		LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
//		tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
//		tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
//		tLCCustomerImpartSchema.setCustomerNoType("2");
//		tLCCustomerImpartSchema.setImpartVer(tAgentImpartVer[i]) ;
//		tLCCustomerImpartSchema.setImpartCode(tAgentImpartCode[i]);
//		tLCCustomerImpartSchema.setImpartContent(tAgentImpartContent[i]);
//		tLCCustomerImpartSchema.setImpartParamModle(tAgentImpartParamModle[i]);
//		tLCCustomerImpartSchema.setPatchNo("0");
//		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
	}
	
	// ׼���������� VData                                                           
	tVData.add( tLCContSchema );  
	tVData.add( tLCAddressSchema );   
	tVData.add( tLCAppntSchema );   
	tVData.add( tLCAccountSchema );    
//	tVData.add(tLCCustomerImpartSet);       
	tVData.add(tLACommisionDetailSet);       
	tVData.add( tG );   
	
	//���ݷ�SCHEMA��Ϣ                                                              
	TransferData tTransferData = new TransferData();                                  
	String SavePolType="";                                                            
	String BQFlag=request.getParameter("BQFlag");                                     
	if(BQFlag==null) SavePolType="0";                                                 
	else if(BQFlag.equals("")) SavePolType="0";                                       
	else  SavePolType=BQFlag;                                                       
                                                          
	tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ
	tTransferData.setNameAndValue("GrpNo",request.getParameter("AppntGrpNo"));
	tTransferData.setNameAndValue("GrpName",request.getParameter("AppntGrpName"));     
	loggerDebug("DirectContSave","SavePolType��BQ is 2��other is 0 : " + request.getParameter("BQFlag"));
	tVData.addElement(tTransferData);  
	
	if( tAction.equals( "INSERT" )) tOperate = "INSERT||CONT";                  
	else if( tAction.equals( "UPDATE" )) tOperate = "UPDATE||CONT";                  
	else if( tAction.equals( "DELETE" )) tOperate = "DELETE||CONT";  
	else {}	
	
	loggerDebug("DirectContSave","OK~");                                                                                
	//ContUI tContUI = new ContUI();  
	String busiName="tbContUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	loggerDebug("DirectContSave","before submit");                             
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )                       
	{                                                                               
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";
	}
	loggerDebug("DirectContSave",FlagStr);
    loggerDebug("DirectContSave",Content);
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>
