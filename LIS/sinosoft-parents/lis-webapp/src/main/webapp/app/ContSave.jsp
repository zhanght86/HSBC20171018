<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽� 
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
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

	//�������
	VData tVData = new VData();
	LCContSchema tLCContSchema   = new LCContSchema();
	LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	LCAddressSchema tLCAddressSchema = new LCAddressSchema();
    LCAccountSchema tLCAccountSchema = new LCAccountSchema();
    LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
    LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
    String AppntName = request.getParameter("AppntLastName")+request.getParameter("AppntFirstName");
    String NameEn = request.getParameter("AppntFirstNameEn")+request.getParameter("AppntLastNameEn");  
  
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");


	tAction = request.getParameter( "fmAction" );
    loggerDebug("ContSave","������:"+tAction);
	if( tAction.equals( "DELETE" ))
	{
    tLCContSchema.setContNo(request.getParameter("ContNo"));
    tLCContSchema.setManageCom(request.getParameter("ManageCom"));	    
	}
	else
  	{        
  	LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
    tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
    tLDSysTraceSchema.setCreatePos("�б�¼��");
    tLDSysTraceSchema.setPolState("1002");
    LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
    inLDSysTraceSet.add(tLDSysTraceSchema);
    VData VData3 = new VData();
    VData3.add(tG);
    VData3.add(inLDSysTraceSet);
        
    //LockTableUI LockTableUI1 = new LockTableUI();
    String busiName="pubfunLockTableUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName)) 
    {
      VData rVData = tBusinessDelegate.getResult();
      loggerDebug("ContSave","LockTable Failed! " + (String)rVData.get(0));
    }
    else 
    {
      loggerDebug("ContSave","LockTable Succed!");
    }
    
    //��ͬ��Ϣ
    if( tAction.equals( "UPDATE" ))
    {
      tLCContSchema.setGrpContNo(request.getParameter("GrpContNo")); 
      loggerDebug("ContSave","�����ͬ����:"+request.getParameter("GrpContNo"));
      loggerDebug("ContSave","�����ͬ����:"+request.getParameter("ContNo"));
      loggerDebug("ContSave","�����ͬ����:"+request.getParameter("MakeDate"));
      //��������Ϣ�޸�  Modifydate:2008-07-11
      tLCContSchema.setContNo(request.getParameter("ContNo")); 
	  tLCContSchema.setProposalContNo(request.getParameter("ProposalContNo"));
	  tLCContSchema.setAppntName(AppntName);//request.getParameter("AppntName"));
	  tLCContSchema.setAppntSex(request.getParameter("AppntSex"));
	  tLCContSchema.setAppntBirthday(request.getParameter("AppntBirthday"));
	  tLCContSchema.setAppntIDType(request.getParameter("AppntIDType"));
	  tLCContSchema.setAppntNo(request.getParameter("AppntIDNo"));
	  }
    if( tAction.equals( "INSERT" ))
    {
      tLCContSchema.setGrpContNo(request.getParameter("GrpContNo")); 
      loggerDebug("ContSave","�����ͬ����:"+request.getParameter("GrpContNo"));
      loggerDebug("ContSave","�����ͬ����:"+request.getParameter("ContNo"));
      tLCContSchema.setContNo(request.getParameter("ContNo")); 
	    tLCContSchema.setProposalContNo(request.getParameter("ProposalContNo")); 
	  }
    tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
    tLCContSchema.setManageCom(request.getParameter("ManageCom"));
    //��������Ϊ���� 02
    //tongmeng 2008-06-10 ��������ʹ�ý��洫������
    String tSaleChnl = request.getParameter("SaleChnl");
    loggerDebug("ContSave","tSaleChnl:"+tSaleChnl);
    tLCContSchema.setSaleChnl(tSaleChnl);
    tLCContSchema.setOutPayFlag(request.getParameter("OutPayFlag"));
    
    //���۷�ʽ��
    loggerDebug("ContSave","%%%%%%%%selltype=="+request.getParameter("SellType"));
    tLCContSchema.setSellType(request.getParameter("SellType"));
    tLCContSchema.setAgentCode(request.getParameter("AgentCode"));
    tLCContSchema.setAgentCode1(request.getParameter("AgentCode1"));
    tLCContSchema.setAgentGroup(request.getParameter("AgentGroup"));
    tLCContSchema.setRemark(request.getParameter("Remark"));
    tLCContSchema.setAgentCom(request.getParameter("AgentCom"));
    tLCContSchema.setAgentType(request.getParameter("AgentType"));
    tLCContSchema.setPolType("0");
    tLCContSchema.setContType("1");
    tLCContSchema.setBankCode(request.getParameter("AppntBankCode"));
    tLCContSchema.setAccName(request.getParameter("AppntAccName"));
    tLCContSchema.setBankAccNo(request.getParameter("AppntBankAccNo"));
    tLCContSchema.setPolApplyDate(request.getParameter("PolAppntDate")); //Ͷ������   
    tLCContSchema.setCValiDate(request.getParameter("PolAppntDate"));
    tLCContSchema.setForceUWFlag("0");
    //¼�������˺�
    tLCContSchema.setPayMode(request.getParameter("SecPayMode"));
    loggerDebug("SecPayMode","SecPayMode:"+request.getParameter("SecPayMode"));
    tLCContSchema.setPayLocation(request.getParameter("SecPayMode"));
    tLCContSchema.setBankCode(request.getParameter("SecAppntBankCode"));
    tLCContSchema.setBankAccNo(request.getParameter("SecAppntBankAccNo"));
    tLCContSchema.setAccName(request.getParameter("SecAppntAccName"));
    /*************************************/
    //¼�������˺�
    tLCContSchema.setNewPayMode(request.getParameter("PayMode"));
    loggerDebug("PayMode","PayMode:"+request.getParameter("PayMode"));
    tLCContSchema.setNewBankCode(request.getParameter("AppntBankCode"));
    tLCContSchema.setNewBankAccNo(request.getParameter("AppntBankAccNo"));
    tLCContSchema.setNewAccName(request.getParameter("AppntAccName"));
    //08-07-11����룬�Զ�������־���Զ��潻��־���������ͷ�ʽ
    tLCContSchema.setRnewFlag(request.getParameter("RnewFlag"));
    tLCContSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
    tLCContSchema.setGetPolMode(request.getParameter("GetPolMode"));
    //tongmeng 2009-04-15 add
    //���ӳ���ǩ��
    tLCContSchema.setSignName(request.getParameter("SignName"));
    //���ӡ��������ڡ� 2010-03-18 - hanbin
    tLCContSchema.setFirstTrialDate(request.getParameter("FirstTrialDate"));
    tLCContSchema.setXQremindflag(request.getParameter("XQremindFlag"));
    /*************************************/
    
		//��¼Ͷ����ϵ
		String tRelationToInsured = request.getParameter("RelationToInsured");
		loggerDebug("ContSave","Ͷ����ϵ:"+tRelationToInsured);
		tLCAppntSchema.setRelationToInsured(tRelationToInsured);

	  //Ͷ������Ϣ
	  tLCAppntSchema.setAppntNo(request.getParameter("AppntNo"));       
    tLCAppntSchema.setAppntName(AppntName);//request.getParameter("AppntName"));              
    tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));               
    tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));          
    tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));            
    tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo"));              
         
       
    tLCAppntSchema.setRgtAddress(request.getParameter("AppntPlace"));        
    tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));          
    tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));                    
    tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));    
    tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));    
    tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));          
    tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));
    tLCAppntSchema.setSmokeFlag(request.getParameter("AppntSmokeFlag"));
    tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace"));
    tLCAppntSchema.setNationality(request.getParameter("AppntNationality"));         
    tLCAppntSchema.setBankCode(request.getParameter("AppntBankCode"));
    tLCAppntSchema.setAccName(request.getParameter("AppntAccName"));
    tLCAppntSchema.setBankAccNo(request.getParameter("AppntBankAccNo"));
    tLCAppntSchema.setMakeDate(request.getParameter("AppntMakeDate"));
    tLCAppntSchema.setMakeTime(request.getParameter("AppntMakeTime"));
    tLCAppntSchema.setLicenseType(request.getParameter("AppntLicenseType"));
    tLCAppntSchema.setSocialInsuFlag(request.getParameter("AppSocialInsuFlag"));
    tLCAppntSchema.setIDExpDate(request.getParameter("AppIDPeriodOfValidity"));
    //tongmeng 2008-06-10 add
    //����ְҵ�ͼ�ְ
    tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
    tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));
    
    //�����ա�����Ӣ��������ƴ������
    tLCAppntSchema.setLastName(request.getParameter("AppntLastName"));
    tLCAppntSchema.setFirstName(request.getParameter("AppntFirstName"));
    tLCAppntSchema.setLastNameEn(request.getParameter("AppntLastNameEn"));
    tLCAppntSchema.setFirstNameEn(request.getParameter("AppntFirstNameEn"));
    tLCAppntSchema.setNameEn(NameEn);
    tLCAppntSchema.setLastNamePY(request.getParameter("AppntLastNamePY"));
    tLCAppntSchema.setFirstNamePY(request.getParameter("AppntFirstNamePY"));
    
    //��������
    tLCAppntSchema.setLanguage(request.getParameter("AppntLanguage"));
    
    
    tLCAccountSchema.setBankCode(request.getParameter("AppntBankCode"));
    tLCAccountSchema.setAccName(request.getParameter("AppntAccName"));
    tLCAccountSchema.setBankAccNo(request.getParameter("AppntBankAccNo"));
    tLCAccountSchema.setAccKind("1");
    
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
    //tLCAddressSchema.setCompanyAddress(request.getParameter("AppntGrpName"));
    //tLCAddressSchema.setPhone(request.getParameter("AppntGrpPhone"));
    tLCAddressSchema.setCompanyAddress(request.getParameter("CompanyAddress"));
    tLCAddressSchema.setCompanyZipCode(request.getParameter("AppntGrpZipCode"));
    tLCAddressSchema.setCompanyFax(request.getParameter("AppntGrpFax"));
    tLCAddressSchema.setProvince(request.getParameter("AppntProvince"));
    tLCAddressSchema.setCity(request.getParameter("AppntCity"));
    tLCAddressSchema.setCounty(request.getParameter("AppntDistrict"));
    tLCAddressSchema.setGrpName(request.getParameter("AppntGrpName"));
    
    double mainBusiRate = 1; //��ҵ��ԱӶ�����
      
    
    
    //����ж�ҵ��Ա���������Ҫ¼���ҵ��Ա��Ϣ��
    String multiAgentFlag = request.getParameter("multiagentflag");
    loggerDebug("ContSave","#######multiAgentFlag=="+multiAgentFlag);
    if(multiAgentFlag!=null && multiAgentFlag.equals("true")){
      //��ȡ����ҵ��Ա����Ϣ
      String mainAgentCode = request.getParameter("AgentCode");
      //ȡ������ҵ��Ա����Ϣ
      String tMultAgentNum[] = request.getParameterValues("MultiAgentGridNo"); 
      String tMultAgentCode[] = request.getParameterValues("MultiAgentGrid1"); 
      String tMultBusiRate[] = request.getParameterValues("MultiAgentGrid5"); 
      String tMultAgentGroup[] = request.getParameterValues("MultiAgentGrid6"); 
      
      int agentCount = 0;
      String polType = "0";
      double tBusiRate = 0;
      //���ڿ���С������
      DecimalFormat df = new DecimalFormat("0.00");
      
      if(tMultAgentNum!=null){
        agentCount = tMultAgentNum.length;
      }
      
      for(int i=0; i<agentCount; i++){

        LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
        tLACommisionDetailSchema.setGrpContNo(request.getParameter("ContNo"));
        tLACommisionDetailSchema.setAgentCode(tMultAgentCode[i]);
        tLACommisionDetailSchema.setBusiRate(tMultBusiRate[i]);
        tLACommisionDetailSchema.setAgentGroup(tMultAgentGroup[i]);
        tLACommisionDetailSchema.setPolType("0");
        tLACommisionDetailSchema.setMakeDate(request.getParameter("AppntMakeDate"));  
        tLACommisionDetailSchema.setMakeTime(request.getParameter("AppntMakeTime"));  

        tBusiRate = tBusiRate + Double.parseDouble(tMultBusiRate[i]);
        
        tLACommisionDetailSet.add(tLACommisionDetailSchema);

      }
      
      loggerDebug("ContSave","--tBusiRate==" + tBusiRate);
      
      //��ҵ��Ա��Ӷ�����Ϊ����ҵ��Ա��Ӷ������룱�Ĳ�ֵ������ò�ֵΪ�������������ʾ
      //���о��ȵ���
      mainBusiRate = Double.parseDouble(df.format(1 - tBusiRate));
      
      
      
      loggerDebug("ContSave","--mainBusiRate==" + mainBusiRate);

      
    
    }
    else{
      mainBusiRate = 1;
    }  
      
    //������ҵ��Ա����Ϣ
    LACommisionDetailSchema mLACommisionDetailSchema = new LACommisionDetailSchema();
    mLACommisionDetailSchema.setGrpContNo(request.getParameter("ContNo"));
    mLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
    mLACommisionDetailSchema.setAgentGroup(request.getParameter("AgentGroup"));
    mLACommisionDetailSchema.setBusiRate(mainBusiRate);
    mLACommisionDetailSchema.setPolType("0");
    mLACommisionDetailSchema.setRelationShip(request.getParameter("RelationShip"));
    mLACommisionDetailSchema.setMakeDate(request.getParameter("MakeDate"));  
    mLACommisionDetailSchema.setMakeTime(request.getParameter("MakeTime"));  
    tLACommisionDetailSet.add(mLACommisionDetailSchema);
      
        
    String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	  String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
	  String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
	  String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
	  String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //��д����
	
	  /*
	  String tIncome = request.getParameter("Income0"); 
	  String tIncomeWay = request.getParameter("IncomeWay0");  
	  String tImpartParaModle1=tIncome+","+ tIncomeWay;
	  loggerDebug("ContSave","tIncome"+tIncome);
	  loggerDebug("ContSave","tIncomeWay"+tIncomeWay);
	  //add by zhangxing
	  String tStature = request.getParameter("AppntStature");
	  String tAvoirdupois = request.getParameter("AppntAvoirdupois");
	  String tImpartParaModle2 = tStature+","+tAvoirdupois;
	  if(!tIncome.equals("")&&!tIncomeWay.equals(""))
	  {
		  LCCustomerImpartSchema ttLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		  ttLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		  ttLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		  ttLCCustomerImpartSchema.setCustomerNoType("0");
		  ttLCCustomerImpartSchema.setImpartCode("001");
		  ttLCCustomerImpartSchema.setImpartContent("��ÿ��̶�����    ��Ԫ  ��Ҫ������Դ�� ����ţ���ѡ��ٹ�н�ڸ����˽Ӫ�ܷ��ݳ����֤ȯͶ�ʢ�������Ϣ������");
		  ttLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle1);
		  ttLCCustomerImpartSchema.setImpartVer("01");
		  loggerDebug("ContSave","#######bbbbb==="+tImpartParaModle1);
		  ttLCCustomerImpartSchema.setPatchNo("0");
		  tLCCustomerImpartSet.add(ttLCCustomerImpartSchema);
	  }
	  if(!tStature.equals("")&&!tAvoirdupois.equals(""))
	  {
	    LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		  aLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
		  aLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
		  aLCCustomerImpartSchema.setCustomerNoType("0");
		  aLCCustomerImpartSchema.setImpartCode("000");
		  aLCCustomerImpartSchema.setImpartContent("���________cm�����ף�  ����________Kg �����");
		  aLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle2);
		  aLCCustomerImpartSchema.setImpartVer("02");		 
		  aLCCustomerImpartSchema.setPatchNo("0");
		  tLCCustomerImpartSet.add(aLCCustomerImpartSchema);
	  }
	  */
		//String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����
		//String tImpartCustomerNo[] = request.getParameterValues("ImpartGrid6");     //��֪�ͻ�����
			
		int ImpartCount = 0;
		if(tImpartNum != null) ImpartCount = tImpartNum.length;
	      
		for(int i = 0; i < ImpartCount; i++){
    
			LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
      tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
      tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
			//tLCCustomerImpartSchema.setCustomerNo(tLDPersonSchema.getCustomerNo());
			tLCCustomerImpartSchema.setCustomerNoType("0");
			tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
			tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
			tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
			tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
			tLCCustomerImpartSchema.setPatchNo("0");
			tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		}
			
    loggerDebug("ContSave","end set schema ��֪��Ϣ..."+ImpartCount);
    
    String tAgentImpartNum[] = request.getParameterValues("AgentImpartGridNo");
	  String tAgentImpartVer[] = request.getParameterValues("AgentImpartGrid1");            //��֪���
	  String tAgentImpartCode[] = request.getParameterValues("AgentImpartGrid2");           //��֪����
	  String tAgentImpartContent[] = request.getParameterValues("AgentImpartGrid3");        //��֪����
	  String tAgentImpartParamModle[] = request.getParameterValues("AgentImpartGrid4");        //��д����
		//String tAgentImpartCustomerNoType[] = request.getParameterValues("AgentImpartGrid5"); //��֪�ͻ�����
		//String tAgentImpartCustomerNo[] = request.getParameterValues("AgentImpartGrid6");     //��֪�ͻ�����
			
		int AgentImpartCount = 0;
		if(tAgentImpartNum != null) AgentImpartCount = tAgentImpartNum.length;
	      
		for(int i = 0; i < AgentImpartCount; i++){
		loggerDebug("ContSave","tAgentImpartParamModle[i]:"+tAgentImpartParamModle[i].length());
      if(tAgentImpartParamModle[i].length()==0)
      {
      continue;
      }
			LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
                        tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ContNo"));
                        tLCCustomerImpartSchema.setContNo(request.getParameter("ContNo"));
			//tLCCustomerImpartSchema.setCustomerNo(tLDPersonSchema.getCustomerNo());
			tLCCustomerImpartSchema.setCustomerNoType("2");
			tLCCustomerImpartSchema.setImpartCode(tAgentImpartCode[i]);
			tLCCustomerImpartSchema.setImpartContent(tAgentImpartContent[i]);
			tLCCustomerImpartSchema.setImpartParamModle(tAgentImpartParamModle[i]);
			tLCCustomerImpartSchema.setImpartVer(tAgentImpartVer[i]) ;
			tLCCustomerImpartSchema.setPatchNo("0");
			tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		}
			
    loggerDebug("ContSave","end set schema ��֪��Ϣ..."+AgentImpartCount);    
	                                                                                
	} // end of else                                                                 
  loggerDebug("ContSave","end setSchema:");                                             
	// ׼���������� VData                                                           
	tVData.add( tLCContSchema );  
	tVData.add( tLCAddressSchema );   
	tVData.add( tLCAppntSchema );   
	tVData.add( tLCAccountSchema );    
	tVData.add(tLCCustomerImpartSet);       
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
  loggerDebug("ContSave","SavePolType��BQ is 2��other is 0 : " + request.getParameter("BQFlag"));
  tVData.addElement(tTransferData);                                                 
                                                                                    
	if( tAction.equals( "INSERT" )) tOperate = "INSERT||CONT";                  
	if( tAction.equals( "UPDATE" )) tOperate = "UPDATE||CONT";                  
	if( tAction.equals( "DELETE" )) tOperate = "DELETE||CONT";                  
  
  loggerDebug("ContSave","OK~");                                                                                
	//ContUI tContUI = new ContUI();         
	loggerDebug("ContSave","before submit");     
	String busiName="tbContUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )                       
	{                                                                               
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		
	}
	else
	{
		Content = " �����ɹ�! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();

		// ��ʾ
		// ������Ϣ
		LCContSchema mLCContSchema = new LCContSchema(); 
    LCAddressSchema mLCAddressSchema = new LCAddressSchema();		
		mLCContSchema.setSchema(( LCContSchema )tVData.getObjectByObjectName( "LCContSchema", 0 ));
		mLCAddressSchema.setSchema(( LCAddressSchema )tVData.getObjectByObjectName( "LCAddressSchema", 0 ));		
		%>
    	<script language="javascript">
    	 	parent.fraInterface.document.all("ContNo").value = "<%=mLCContSchema.getContNo()%>";
    	 	//alert("contNo==="+parent.fraInterface.document.all("ContNo").value);
    	 	parent.fraInterface.document.all("ProposalContNo").value = "<%=mLCContSchema.getProposalContNo()%>";   
    	 	parent.fraInterface.document.all("AppntNo").value = "<%=mLCContSchema.getAppntNo()%>";  
    	 	parent.fraInterface.document.all("GrpContNo").value = "<%=mLCContSchema.getGrpContNo()%>";    	 	
	    	parent.fraInterface.document.all("AppntMakeDate").value = "<%=mLCContSchema.getMakeDate()%>";
	    	parent.fraInterface.document.all("AppntMakeTime").value = "<%=mLCContSchema.getMakeTime()%>";
	    	parent.fraInterface.document.all("AppntAddressNo").value = "<%=mLCAddressSchema.getAddressNo()%>";    	        
    	</script>
		<%		
	}
  loggerDebug("ContSave","Content:"+Content);	
        if( tAction.equals( "DELETE" )){  
%>   
               
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit5("<%=FlagStr%>","<%=Content%>");
</script>
</html>

<%}
  else{
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>
</html>


<%  
  
}
%>
