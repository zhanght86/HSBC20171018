<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ProposalSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
	//�������
	CErrors tError = null;
	String FlagStr = "Succ";
	String Content = "";
	String PrtNo = request.getParameter("PrtNo");
	//09-12-17  ����tCheckMultFlag �������ķ���¼��Ĳ�������  ��ֱ�ӷ�����ʾ����������������   1-������   2-���������践�ش�����ʾ
	String tCheckMultFlag="1";
    loggerDebug("WbProposalSave","Start Save"+request.getParameter("PrtNo")+"...");
    
	VData tResult = new VData();
    GlobalInput tG = new GlobalInput();	
    TransferData tTransferData = new TransferData();
	tG = ( GlobalInput )session.getValue( "GI" );
	loggerDebug("WbProposalSave","tG: "+tG);
	LCPolSchema tLCPolSchema = new LCPolSchema(); 
	SSRS tRiskInfoSSRS = null;
	String tProposalNo="";
	LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();       
    VData InsuredResults = new VData();
    
    String tsamePersonFlag = ""; //Ͷ�����뱻�����Ƿ�ͬһ��
	try
	{
		String fmAction = request.getParameter("fmAction");
	 	loggerDebug("WbProposalSave","fmAction:"+fmAction);
	 	String tDealType=request.getParameter("DealType");
	 	loggerDebug("WbProposalSave","tDealType:"+tDealType);
	 	if(fmAction.equals("INSERT"))
	 	{
	 		int tInsuredNum = Integer.parseInt(request.getParameter("InsuredNum"));
			String tMainRiskNum[] = new String[3];
			loggerDebug("WbProposalSave","��������Ŀ��"+tInsuredNum+"...");
			for(int i=1;i<=tInsuredNum ; i++)
			{
				tMainRiskNum[i-1] = request.getParameter("MainRiskNum"+i);
				loggerDebug("WbProposalSave","��"+i+"������������Ŀ��"+tMainRiskNum[i-1]+"...");
			}	    
		    
		    
		  //��������
		  tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
		  tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
		  tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
	      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
		  tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
		  tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
		  tLCPolSchema.setRemark(request.getParameter("Remark"));
		  tLCPolSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
		  tLCPolSchema.setCValiDate(request.getParameter("PolApplyDate"));
		  tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));
		  tLCPolSchema.setPayIntv(request.getParameter("PayIntv"));
		  tLCPolSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
		  tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));
		  loggerDebug("WbProposalSave","tLCPolSchema:"+tLCPolSchema.encode());
		  
		  //����
		  tTransferData.setNameAndValue("NewPayMode", StrTool.cTrim(request.getParameter("NewPayMode"))); //
		  tTransferData.setNameAndValue("NewBankCode", StrTool.cTrim(request.getParameter("NewBankCode"))); //
	      tTransferData.setNameAndValue("NewAccName", StrTool.cTrim(request.getParameter("NewAccName"))); //
	      tTransferData.setNameAndValue("NewBankAccNo", StrTool.cTrim(request.getParameter("NewBankAccNo"))); //
	      //����
	      tTransferData.setNameAndValue("PayLocation", StrTool.cTrim(request.getParameter("PayLocation"))); //
	 	  tTransferData.setNameAndValue("BankCode", StrTool.cTrim(request.getParameter("BankCode"))); //
	      tTransferData.setNameAndValue("AccName", StrTool.cTrim(request.getParameter("AccName"))); //
	      tTransferData.setNameAndValue("BankAccNo", StrTool.cTrim(request.getParameter("BankAccNo"))); //
	      tTransferData.setNameAndValue("OutPayFlag", StrTool.cTrim(request.getParameter("OutPayFlag"))); //
	      tTransferData.setNameAndValue("GetPolMode", StrTool.cTrim(request.getParameter("GetPolMode"))); //
	      tTransferData.setNameAndValue("ChiefBankCode","");
	      tTransferData.setNameAndValue("AgentName",StrTool.cTrim(request.getParameter("AgentName")));
	      tTransferData.setNameAndValue("SignName",StrTool.cTrim(request.getParameter("SignName")));	
	      tTransferData.setNameAndValue("SignName",StrTool.cTrim(request.getParameter("SignName")));
	      //����[��������] -2010-03-19 - hanbin 
	      tTransferData.setNameAndValue("FirstTrialDate",StrTool.cTrim(request.getParameter("FirstTrialDate")));
	      tTransferData.setNameAndValue("XQremindflag",StrTool.cTrim(request.getParameter("XQremindFlag")));
	 	  tTransferData.setNameAndValue("DealType",tDealType);	 	  
	 	  
	    //Ͷ������Ϣ
	 	  tLCAppntIndSchema.setCustomerNo(request.getParameter("AppntCustomerNo"));  //�ͻ���
		  tLCAppntIndSchema.setName(request.getParameter("AppntName"));              //���� 
		  tLCAppntIndSchema.setSex(request.getParameter("AppntSex"));                //�Ա�
		  tLCAppntIndSchema.setBirthday(request.getParameter("AppntBirthday"));      //��������                                                                            //���䣬���ύ 
		  tLCAppntIndSchema.setIDType(request.getParameter("AppntIDType"));          //֤������
		  tLCAppntIndSchema.setIDNo(request.getParameter("AppntIDNo"));              //֤������ 
		  tLCAppntIndSchema.setNativePlace(request.getParameter("AppntNativePlace")); //����
		  tLCAppntIndSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));  //�������ڵ�
		  tLCAppntIndSchema.setMarriage(request.getParameter("AppntMarriage"));      //����״��
		  //tLCAppntIndSchema.setNationality(request.getParameter("AppntNationality")); //����
		  tLCAppntIndSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured")); //�뱻�����˹�ϵ
		  
		  if(tLCAppntIndSchema.getRelationToInsured().equals("00"))
			  tsamePersonFlag = "1";
		  
		  tLCAppntIndSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));         //��ϵ��ַ
		  tLCAppntIndSchema.setZipCode(request.getParameter("AppntZipCode"));        //��������
		  tLCAppntIndSchema.setPhone(request.getParameter("AppntPhone"));            //��ѡ�طõ绰 
		  tLCAppntIndSchema.setPhone2(request.getParameter("AppntPhone2"));          //������ϵ�绰
		  //tLCAppntIndSchema.setMobile(request.getParameter("AppntMobile"));          //�ƶ��绰
		  tLCAppntIndSchema.setEMail(request.getParameter("AppntEMail"));            //��������
		  tLCAppntIndSchema.setHomeAddress(request.getParameter("AppntHomeAddress")); //סַ
		  tLCAppntIndSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode")); //סַ��������
		  tLCAppntIndSchema.setGrpName(request.getParameter("AppntGrpName"));        //������λ
		  tLCAppntIndSchema.setWorkType(request.getParameter("AppntWorkType"));      //ְҵ�����֣�
		  tLCAppntIndSchema.setPluralityType(request.getParameter("AppntPluralityType"));        //��ְ�����֣�
		  tLCAppntIndSchema.setOccupationType(request.getParameter("AppntOccupationType"));      //ְҵ���
		  tLCAppntIndSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));      //ְҵ����
		  tTransferData.setNameAndValue("AppntSocialInsuFlag",StrTool.cTrim(request.getParameter("AppntSocialInsuFlag")));
	 	  tTransferData.setNameAndValue("AppntIDEndDate",StrTool.cTrim(request.getParameter("AppntIDEndDate")));
		  loggerDebug("WbProposalSave","tLCAppntIndSchema:"+tLCAppntIndSchema.encode());
		  
		//��֪��Ϣ******************************************************************************
		/**
		//***************************************************************************************/	  		
		  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
		  String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
		  String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
		  String tImpartContent1[] = request.getParameterValues("ImpartGrid3");        //��֪����
		  String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //��д����
		  String tCustomerNoType[] = request.getParameterValues("ImpartGrid5");       //��֪�ͻ�����
		  String tCustomerToInsured[] = request.getParameterValues("ImpartGrid6");           //�ͻ��ڲ����루�����ˣ�
		  String tImpartFlag[] = request.getParameterValues("ImpartGrid7"); //��֪�Ƿ���Ч����Ϊ��Ч��
		  
		  int ImpartCount = 0;
		  if (tImpartNum != null) ImpartCount = tImpartNum.length;
		  
		  for (int i = 0; i < ImpartCount; i++)
		  {
		  	if(!tCustomerNoType[i].trim().equals("0")&&!tCustomerNoType[i].trim().equals("1")
	        		&& !tCustomerNoType[i].trim().equals("2"))
	        {
	          loggerDebug("WbProposalSave","***���赼�롣������");
	          continue;
	        }
		  
		  	LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		  	loggerDebug("WbProposalSave","***LCCustomerImpart->IMPARTFLAG "+tImpartFlag[i]);	        
		  	
//	  		if( tImpartCustomerNoType[i].trim().equals( "A" ))
//	  			tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "AppntCustomerNo" ));
//	  		if( tImpartCustomerNoType[i].trim().equals( "I" ))
//	  			tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "CustomerNo" ));
	    
		  	tLCCustomerImpartSchema.setCustomerNoType( tCustomerNoType[i] );
		  	tLCCustomerImpartSchema.setCustomerNo(tCustomerToInsured[i]);
		  	tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		  	tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		  	tLCCustomerImpartSchema.setImpartContent(tImpartContent1[i]);
		  	tLCCustomerImpartSchema.setImpartParamModle(tImpartContent[i]);	  	
		  	loggerDebug("WbProposalSave","tLCCustomerImpartSchema:"+tLCCustomerImpartSchema.encode());
		  	
		  	tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		  }
		  
		  /**
		  //***************************************************************************************
		  //***************************************************************************************/
		  
		   //***************************************************************************************
		  //***************************************************************************************/
		  tResult.add(tTransferData);
	      tResult.add(tLCPolSchema);
	      tResult.add(tLCAppntIndSchema);
	      tResult.add(tLCCustomerImpartSet);
		  
		  //***************************************************************************************
		  //***************************************************************************************/
		  
		  //***************************************************************************************
		  //***************************************************************************************/
		  //�������������Ϣ	
		  LCInsuredSchema tLCInsuredSchema ;
		  TransferData tTransferData1 ;
		  LCPolSet tLCPolSet1;
		  VData tRiskBasicInfoMainSet;
		  LCInsuredSet tLCInsuredSet ;
		  VData tTransferDataSet1 ;   //��������У����Ϣ����2
		  VData tTransferDataSet ;   //��������У����Ϣ����1
		  VData  tInsuredRelaSet ;
		  for (int i = 0; i < tInsuredNum; i++)
		  {
			  int m =i+1;
			  tLCInsuredSchema = new LCInsuredSchema();                 				  
	  		  tTransferData1 = new TransferData();
	  		  tTransferDataSet1 = new VData();
	  		  tTransferDataSet = new VData();
	  		  tLCInsuredSet = new LCInsuredSet(); 	
	  		  tRiskBasicInfoMainSet = new VData();
	  		  tLCPolSet1 = new LCPolSet();
	  		  tInsuredRelaSet = new VData();
	  		  
		  		if (((!"".equals(request.getParameter("RelationToAppnt1"))
						 && "00".equals(request.getParameter("RelationToAppnt1")) && tInsuredNum>1) //���ڶ౻����
						 ||(!tsamePersonFlag.equals("") && tsamePersonFlag.equals("1") && tInsuredNum==1) //���ڷǶ౻����
						 || (request.getParameter("SamePersonFlag1") != null)) && m==1)
		        {
		        	 //��������˺�Ͷ���˵Ĺ�ϵΪ���ˣ������Ͷ���˺ͱ�����Ϊͬһ����--���ڶ౻��������
		             tTransferData1.setNameAndValue("samePersonFlag", "1"); //������ͬͶ���˱�־	             
		        }
		  		else
		  			tTransferData1.setNameAndValue("samePersonFlag", "0");
		    
			  	tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"+m));  //�ͻ���
				  tLCInsuredSchema.setName(request.getParameter("Name"+m));              //���� 
				  tLCInsuredSchema.setSex(request.getParameter("Sex"+m));                //�Ա�
				  tLCInsuredSchema.setBirthday(request.getParameter("Birthday"+m));      //�������� 
				  tLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"+m));
	              tLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"+m));
				  tLCInsuredSchema.setIDType(request.getParameter("IDType"+m));          //֤������
				  tLCInsuredSchema.setIDNo(request.getParameter("IDNo"+m));              //֤������ 
				  tLCInsuredSchema.setIDExpDate(request.getParameter("IDEndDate"+m));              //֤������
				  tLCInsuredSchema.setNativePlace(request.getParameter("NativePlace"+m)); //����
				  tLCInsuredSchema.setRgtAddress(request.getParameter("RgtAddress"+m));  //�������ڵ�
				  tLCInsuredSchema.setMarriage(request.getParameter("Marriage"+m));      //����״��
				  tLCInsuredSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"+m));
	              
	              tTransferData1.setNameAndValue("HomeAddress", StrTool.cTrim(request.getParameter("HomeAddress"+m))); //
	              tTransferData1.setNameAndValue("HomeZipCode", StrTool.cTrim(request.getParameter("HomeZipCode"+m))); //
	              tTransferData1.setNameAndValue("Phone", StrTool.cTrim(request.getParameter("Phone"+m))); //
	              tTransferData1.setNameAndValue("Phone2", StrTool.cTrim(request.getParameter("Phone2"+m))); //
	              tTransferData1.setNameAndValue("GrpName", StrTool.cTrim(request.getParameter("GrpName"+m))); //
	              tTransferData1.setNameAndValue("EMail", StrTool.cTrim(request.getParameter("EMail"+m)));
				  //tLCInsuredSchema.setPostalAddress(request.getParameter("PostalAddress"));         //��ϵ��ַ
				  //tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));        //�������� 
				  //tLCInsuredSchema.setHomeAddress(request.getParameter("HomeAddress")); //סַ
				  //tLCInsuredSchema.setHomeZipCode(request.getParameter("HomeZipCode")); //סַ��������
				  //tLCInsuredSchema.setPhone(request.getParameter("Phone"));            //��ϵ�绰��1�� 
				  //tLCInsuredSchema.setPhone2(request.getParameter("Phone2"));          //��ϵ�绰��2��
				  //tLCInsuredSchema.setGrpName(request.getParameter("GrpName"));        //������λ	
				  
			  	  tLCInsuredSchema.setWorkType(request.getParameter("WorkType"+m));      //ְҵ�����֣�
				  tLCInsuredSchema.setPluralityType(request.getParameter("PluralityType"+m));         //��ְ�����֣�
				  tLCInsuredSchema.setOccupationType(request.getParameter("OccupationType"+m));       //ְҵ���
				  tLCInsuredSchema.setOccupationCode(request.getParameter("OccupationCode"+m));       //ְҵ����
				  if( tInsuredNum == 1 )
					  tLCInsuredSchema.setSequenceNo("-1"); //�Ƕ౻����
				  else
				 	  tLCInsuredSchema.setSequenceNo(request.getParameter("SequenceNo"+m));
			  loggerDebug("WbProposalSave","tLCInsuredSchema:"+tLCInsuredSchema.encode());
				  
			  tTransferDataSet.add(tTransferData1);  		  
	          tLCInsuredSet.add(tLCInsuredSchema);
	          
	        //���汣�ս���𡢺�������ʽ��Ϣ
	        String tDealTypeNum[] = request.getParameterValues("DealType"+m+"GridNo");
			  String tInsuredNo[] = request.getParameterValues("DealType"+m+"Grid1");            //
			  String tMainRiskNo[] = request.getParameterValues("DealType"+m+"Grid2");           //
			  String tGetYear[] = request.getParameterValues("DealType"+m+"Grid3");        //
			  String tGetYearFlag[] = request.getParameterValues("DealType"+m+"Grid4");        //
			  String tGetYears[] = request.getParameterValues("DealType"+m+"Grid5");       //
			  String tGetDutyKind[] = request.getParameterValues("DealType"+m+"Grid6");           //
			  String tLiveGetMode[] = request.getParameterValues("DealType"+m+"Grid7"); //
			  String tBonusGetMode[] = request.getParameterValues("DealType"+m+"Grid8");
			  
			  int DealTypeCount = 0;
			  if (tDealTypeNum != null) DealTypeCount = tDealTypeNum.length;
			  
			  for (int j = 0; j < DealTypeCount; j++)
			  {
				  tTransferData1 = new TransferData();
				  LCPolSchema tLCPolSchema1 = new LCPolSchema();			  
		//      		tLCPolSchema1.setSchema(tLCPolSchema); //���뵥�����汣�ս���𡢺�������ʽ��Ϣ
		      		tLCPolSchema1.setInsuredNo(tMainRiskNo[j]); //���������                   		      		
		      	    try
		      	    {
		      	    	tLCPolSchema1.setGetYear(StrTool.cTrim(tGetYear[j]));
		      	    }
		      	    catch(Exception ex)
		      	    {
		      	        loggerDebug("WbProposalSave","****tLCPolSchema.setGetYear("+StrTool.cTrim(tGetYear[j])+") �����쳣");
		      	    }
		      	    tLCPolSchema1.setGetYearFlag(StrTool.cTrim(tGetYearFlag[j]));
		      	    
		      	    //��ҪУ�����Ϣ
		      	    String sGetYears = tGetYears[j];
		      	    if(StrTool.cTrim(sGetYears).indexOf("��")!=-1) //���������λ�����ȡ
		      	    {
		      	    	sGetYears = sGetYears.substring(0,sGetYears.indexOf("��"));
		      	    }
		      	    else
		      	    {
		      	    	sGetYears = StrTool.cTrim(sGetYears);
		      	    }
		      	    loggerDebug("WbProposalSave","***sGetYears: "+sGetYears);
		
		      	    tTransferData1.setNameAndValue("GetYears",sGetYears);
		      	    tTransferData1.setNameAndValue("GetDutyKind",StrTool.cTrim(tGetDutyKind[j]));
		      	    
		      	    tLCPolSchema1.setLiveGetMode(StrTool.cTrim(tLiveGetMode[j]));
		      	    tLCPolSchema1.setBonusGetMode(StrTool.cTrim(tBonusGetMode[j]));                       	    
		      	    tTransferDataSet1.add(tTransferData1);
		      	    tLCPolSet1.add(tLCPolSchema1);
			  }
			  
			//������Ϣ
			for (int j = 0; j < Integer.parseInt(tMainRiskNum[i]); j++)
			{
				int n =j+1;
				VData tRiskBasicInfoSet = new VData();
				  String tRiskNum[]   = request.getParameterValues("Risk"+m+n+"GridNo");
				  String tRiskCode[]   = request.getParameterValues("Risk"+m+n+"Grid3");
				  String tRiskName[]   = request.getParameterValues("Risk"+m+n+"Grid4");
				  String tRiskAmnt[]  = request.getParameterValues("Risk"+m+n+"Grid5");
				  String tRiskMult[]     = request.getParameterValues("Risk"+m+n+"Grid6");	 			  
				  String tRiskInsuYear[]   = request.getParameterValues("Risk"+m+n+"Grid7");
				  String tRiskInsuYearFlag[] = request.getParameterValues("Risk"+m+n+"Grid8");
				  String tRiskPayEndYear[]     = request.getParameterValues("Risk"+m+n+"Grid9");
				  String tRiskPayEndYearFlag[] = request.getParameterValues("Risk"+m+n+"Grid10");
				  String tRiskPrem[]   = request.getParameterValues("Risk"+m+n+"Grid11");
				  String tRiskAddPrem[]   = request.getParameterValues("Risk"+m+n+"Grid12");
				  String tRiskInputPrem[]   = request.getParameterValues("Risk"+m+n+"Grid13");
				  
				  int RiskCount = 0;
				  if (tRiskNum != null) 
				  		RiskCount = tRiskNum.length;
				  
				  for (int k = 0; k < RiskCount; k++)
				  {
				  	RiskBasicInfo tRiskBasicInfo = new RiskBasicInfo();
				  	tRiskBasicInfo.setRiskNo(request.getParameter("MainRiskNo"+m+n));
				  	tRiskBasicInfo.setRiskCode(tRiskCode[k]);
				  	tRiskBasicInfo.setRiskName(tRiskName[k]);
				  	tRiskBasicInfo.setAmnt(tRiskAmnt[k]);
				  	tRiskBasicInfo.setPrem(tRiskPrem[k]);
				  	
				  	tRiskBasicInfo.setAddPrem(tRiskAddPrem[k]);
				  	tRiskBasicInfo.setInputPrem(tRiskInputPrem[k]);
				  	try{
				  		tRiskBasicInfo.setMult(tRiskMult[k]);
				  	}catch(Exception e){
				  		e.printStackTrace();
				  		Content = "��������¼��������";
				  		FlagStr="Fail";
				  		tCheckMultFlag = "2";
				  		
				  	}
				  	//���ڿ��������⴦������������141��ͷ����141601��
				  	if(tRiskCode[k]!=null && tRiskCode[k].startsWith("141"))
				  	{
				  	  tRiskBasicInfo.setPayEndYear(1);
				  	  tRiskBasicInfo.setPayEndYearFlag("Y");
				  	  tRiskBasicInfo.setInsuYear(1);
				  	  tRiskBasicInfo.setInsuYearFlag("Y");
				  	}
				  else
				  	{
				  	  tRiskBasicInfo.setPayEndYear(tRiskPayEndYear[k]);
				  	  tRiskBasicInfo.setPayEndYearFlag(tRiskPayEndYearFlag[k]);
				  	  tRiskBasicInfo.setInsuYear(tRiskInsuYear[k]);
				  	  tRiskBasicInfo.setInsuYearFlag(tRiskInsuYearFlag[k]);
				  	}
				  	loggerDebug("WbProposalSave","tRiskBasicInfo:"+tRiskBasicInfo.encode());
				  	
				  	tRiskBasicInfoSet.add(tRiskBasicInfo);
				  }
				  tRiskBasicInfoMainSet.add(tRiskBasicInfoSet);
				  
			}
			
			//��������Ϣ
			LCBnfSet tLCBnfSet = new LCBnfSet();
			
			String tBnfNum[] = request.getParameterValues("Bnf"+m+"GridNo");
			  String tBInsuredNo[] = request.getParameterValues("Bnf"+m+"Grid1");            //
			  String tBMainRiskNo[] = request.getParameterValues("Bnf"+m+"Grid2");           //
			  String tBnfType[] = request.getParameterValues("Bnf"+m+"Grid3");        //
			  String tName[] = request.getParameterValues("Bnf"+m+"Grid4");        //
			  String tIDType[] = request.getParameterValues("Bnf"+m+"Grid5");       //
			  String tIDNo[] = request.getParameterValues("Bnf"+m+"Grid6");           //
			  String tBnfRelationToInsured[] = request.getParameterValues("Bnf"+m+"Grid7"); //
			  String tBnfLot[] = request.getParameterValues("Bnf"+m+"Grid8");
			  String tBnfGrade[] = request.getParameterValues("Bnf"+m+"Grid9"); //
			  String tAddress[] = request.getParameterValues("Bnf"+m+"Grid10");
			  String tIDExpDate[] = request.getParameterValues("Bnf"+m+"Grid11");
			  int BnfCount = 0;
			  if (tBnfNum != null) BnfCount = tBnfNum.length;
			  
			  for (int j = 0; j < BnfCount; j++)
			  {
				  if( tName[j] == null || tName[j].equals( "" )) break;
					
				  	LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				  	tLCBnfSchema.setBnfType(tBnfType[j]);
				  	tLCBnfSchema.setName(tName[j]);
				  	tLCBnfSchema.setIDType(tIDType[j]);
				  	tLCBnfSchema.setIDNo(tIDNo[j]);
				  	tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[j]);
				  	tLCBnfSchema.setBnfLot(tBnfLot[j]);
				  	tLCBnfSchema.setBnfGrade(tBnfGrade[j]);
				  	tLCBnfSchema.setPostalAddress(tAddress[j]);
				  	tLCBnfSchema.setBnfNo(tBMainRiskNo[j]);
				  	tLCBnfSchema.setIDExpDate(tIDExpDate[j]);
				  	
				  	loggerDebug("WbProposalSave","tLCBnfSchema:"+tLCBnfSchema.encode());
				  	
				  	tLCBnfSet.add(tLCBnfSchema);
			  }	
			  
			  tInsuredRelaSet.add(tTransferDataSet);
	          tInsuredRelaSet.add(tTransferDataSet1);
	          tInsuredRelaSet.add(tLCInsuredSet);
	          tInsuredRelaSet.add(tLCBnfSet);
	          tInsuredRelaSet.add(tLCPolSet1);                    
	          tInsuredRelaSet.add(tRiskBasicInfoMainSet);
			  
	          InsuredResults.add(tInsuredRelaSet);  
	          
	        //---------------------print------------------------------------------//
	          if(tLCInsuredSet !=null)
	          {
	            loggerDebug("WbProposalSave","***LCInsuredSet: "+tLCInsuredSet.encode());
	          }

	          if(tLCBnfSet !=null)
	          {
	            loggerDebug("WbProposalSave","***LCBnfSet: "+tLCBnfSet.encode());
	          }
	          
	          if(tLCPolSet1 !=null)
	          {
	            loggerDebug("WbProposalSave","***LCPolSet1������ȴ���ʽ��Ϣ��: "+tLCPolSet1.encode());
	          }          
	          //-------------------------------------------------------------------//
			
		  } 	  
		  
		  tResult.add(InsuredResults);
		  tResult.add(tG); 
	 	}
		//�����������Ҫ��ŵ���BL
		if("1".equals(tCheckMultFlag)){
 	  //*************************
 	  //*******************************************************
 	  //******************************************************************************************* 	  
 	  
 	  String BussNoType = request.getParameter("BussNoType");
 	  loggerDebug("WbProposalSave","BussNoType:"+BussNoType);	  	  
 	 PubConcurrencyLock mPubLock = new PubConcurrencyLock();
 	 try{
	     //���벢�����ƣ�ͬһ��������ͬһʱ��ֻ�ܴ���һ��	     
	     if (!mPubLock.lock(PrtNo, "LC0031")) {
	    	 FlagStr="Fail";
	 	  	if(mPubLock.mErrors.needDealError())
	 	  	{
	 	  		Content="Ͷ������ӡˢ�ţ�"+PrtNo+")���ڴ����У����Ժ����ԣ�";
	 	  	}
	 	  	loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		 }
	     else{
	     		if(BussNoType==null || BussNoType.equals(""))
	     	     	  loggerDebug("WbProposalSave","BussNoType���ݴ������");
	     	 	  else if(BussNoType.equals("TB"))
	     	 	  {
	     	 		 BPODealInputDataBL tBPODealInputDataBL = new BPODealInputDataBL(); 
	     	 		 if(fmAction.equals("INSERT"))
	     	 	 	  {
	     	 	 	  	loggerDebug("WbProposalSave","fmAction:"+fmAction);
	     	 	 	 	ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "��Ͷ�����Ѿ����棬�������ٴα��棬�����½���¼����棡";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tBPODealInputDataBL.DealOneRightPol(tResult,1);
		     	 	 	  	if(!aaa)
		     	 	 	  	{
		     	 	 	  		FlagStr="Fail";
		     	 	 	  		if(tBPODealInputDataBL.mErrors.needDealError())
		     	 	 	  		{
		     	 	 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
		     	 	 	  		}
		     	 	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	 	 	  		
		     	 	 	  		loggerDebug("WbProposalSave","***��ʼ�����Ѿ����������..." );
		     	 	 	  	  	if(!tBPODealInputDataBL.redoInputedPol(PrtNo))
			     	 	        {
			     	 	          loggerDebug("WbProposalSave","***�����Ѿ����������ʧ��"+ PrtNo +" : "+tBPODealInputDataBL.mErrors.getLastError());
			     	 	        }
			     	 	        else
			     	 	      	{
			     	 	      	 loggerDebug("WbProposalSave","***�����Ѿ�������������..." );
			     	 	      	}
		     	 	 	  	 }
		     	 	 	  	 loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	 	 	  	
	     	 	 	  } 
	     	 		 else if(fmAction.equals("DELETE"))
	     	 	 	  { 
	     	 			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "���쳣���Ѿ����棬����ɾ����";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tBPODealInputDataBL.updateBPOState(PrtNo,tDealType,"2",tG); 	 	        
	     		 	 	  	if(!aaa)
	     		 	 	  	{
	     		 	 	  		FlagStr="Fail";
	     		 	 	  		if(tBPODealInputDataBL.mErrors.needDealError())
	     		 	 	  		{
	     		 	 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
	     		 	 	  		}
	     		 	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
	     		 	 	  	}	
	     		 	 	   loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);	
	    		     	}	     	 	 	        
	     	 	 	  }
	     	 		 else if(fmAction.equals("DELETECONT"))
	     		 	  {
	     	 			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist==null || tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "��Ͷ������δ���棬������ɾ��¼����Ϣ��";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		ExeSQL tExe1 = new ExeSQL();
		    		     	String tExist1=tExe1.getOneValue("select 1 from lwmission where missionprop1='"+PrtNo+"' and activityid in ('0000001090','0000001091')");
		    		     	loggerDebug("WbProposalSave","tExist1:"+tExist1);
		    		     	if(tExist1==null || tExist1.equals(""))
		    		     	{		     		
		    			 	   Content = "��Ͷ�����Ѿ�������ϣ�������ɾ��¼����Ϣ��";
		    			 	   FlagStr="Fail";
		    		     	}
		    		     	else
		    		     	{
		    		     		boolean aaa=tBPODealInputDataBL.redoInputedCont(PrtNo,tDealType,"0",tG);
			     		 	  	if(!aaa)
			     		 	  	{
			     		 	  		FlagStr="Fail";
			     		 	  		if(tBPODealInputDataBL.mErrors.needDealError())
			     		 	  		{
			     		 	  			Content=tBPODealInputDataBL.mErrors.getFirstError();
			     		 	  		}
			     		 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
			     		 	  	}
			     		 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    		     	} 		    		     		
	    		     	}	     		 	    
	     		 	  }
	     	 	  }
	     	 	  else if(BussNoType.equals("JM"))
	     	 	  {
	     	 		 JMBPODealInputDataBL tJMBPODealInputDataBL = new JMBPODealInputDataBL();
	     	  		 if(fmAction.equals("INSERT"))
	     	  	 	  {	     	  			
	     	  	 	  	loggerDebug("WbProposalSave","fmAction:"+fmAction);
		     	  	 	ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "��Ͷ�����Ѿ����棬�������ٴα��棬�����½���¼����棡";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tJMBPODealInputDataBL.DealOneRightPol(tResult,1);
		     	  	 	  	if(!aaa)
		     	  	 	  	{
		     	  	 	  		FlagStr="Fail";
		     	  	 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
		     	  	 	  		{
		     	  	 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
		     	  	 	  		}
		     	  	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	  	 	  		
		     	  	 	  		loggerDebug("WbProposalSave","***��ʼ�����Ѿ����������..." );
		     	  	 	  	  if(!tJMBPODealInputDataBL.redoInputedPol(PrtNo))
			     	  	        {
			     	  	          loggerDebug("WbProposalSave","***�����Ѿ����������ʧ��"+ PrtNo +" : "+tJMBPODealInputDataBL.mErrors.getLastError());
			     	  	        }
			     	  	       else
			     	  	      	{
			     	  	      	 loggerDebug("WbProposalSave","***�����Ѿ�������������..." );
			     	  	      	}
		     	  	 	  	 }
		     	  	 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	  	 	  	
	     	  	 	  } 
	     	  		 else if(fmAction.equals("DELETE"))
	     	  	 	  {
	     	  			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist!=null && !tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "���쳣���Ѿ����棬����ɾ����";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		boolean aaa=tJMBPODealInputDataBL.updateBPOState(PrtNo,tDealType,"2",tG);
		     	  	 	  	if(!aaa)
		     	  	 	  	{
		     	  	 	  		FlagStr="Fail";
		     	  	 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
		     	  	 	  		{
		     	  	 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
		     	  	 	  		}
		     	  	 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
		     	  	 	  	}
		     	  	 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
	    		     	}	     	  	 	    
	     	  	 	  }
	     	  		else if(fmAction.equals("DELETECONT"))
	     		 	  {
	     	  			ExeSQL tExe = new ExeSQL();
	    		     	String tExist=tExe.getOneValue("select 1 from lccont where prtno='"+PrtNo+"' ");
	    		     	loggerDebug("WbProposalSave","tExist:"+tExist);
	    		     	if(tExist==null || tExist.equals(""))
	    		     	{		     		
	    			 	   Content = "��Ͷ������δ���棬������ɾ��¼����Ϣ��";
	    			 	   FlagStr="Fail";
	    		     	}
	    		     	else
	    		     	{
	    		     		ExeSQL tExe1 = new ExeSQL();
		    		     	String tExist1=tExe1.getOneValue("select 1 from lwmission where missionprop1='"+PrtNo+"' and activityid in ('0000001090','0000001091')");
		    		     	loggerDebug("WbProposalSave","tExist1:"+tExist1);
		    		     	if(tExist1==null || tExist1.equals(""))
		    		     	{		     		
		    			 	   Content = "��Ͷ�����Ѿ�������ϣ�������ɾ��¼����Ϣ��";
		    			 	   FlagStr="Fail";
		    		     	}
		    		     	else
		    		     	{
		    		     		boolean aaa=tJMBPODealInputDataBL.redoInputedCont(PrtNo,tDealType,"0",tG);
			     		 	  	if(!aaa)
			     		 	  	{
			     		 	  		FlagStr="Fail";
			     		 	  		if(tJMBPODealInputDataBL.mErrors.needDealError())
			     		 	  		{
			     		 	  			Content=tJMBPODealInputDataBL.mErrors.getFirstError();
			     		 	  		}
			     		 	  		loggerDebug("WbProposalSave","FailFailFailFailFailFail" );
			     		 	  	}
			     		 	  	loggerDebug("WbProposalSave","aaaaaaaaaaaaaaaaaa"+aaa);
		    		     	}	    		     		
	    		     	} 	     		 	    
	     		 	  }
	     	  	  } 
	     }
 	 }
 	catch(Exception e)
	{
 		FlagStr="Fail";
 	    Content = "����ʱ�����쳣:e.toString()";
	}
	finally
	{
		mPubLock.unLock();
	}
	}
	  if(FlagStr.equals("Succ")){
    
	  } // end of if
	  //FlagStr="Succ";
	  loggerDebug("WbProposalSave",Content);
	  
	  //�滻��������Ϣ�������ַ�
	  Content = Content.replace('\n', ' ');
	  
	  while (Content.indexOf("\"") != -1) 
	  {
      		Content = Content.replace('\"', ' ');
      		loggerDebug("WbProposalSave",Content);
	  }
    
    
    	loggerDebug("WbProposalSave",Content);
    	loggerDebug("WbProposalSave",FlagStr); 
    	
    	ExeSQL tExe = new ExeSQL();
    	tProposalNo=tExe.getOneValue("select contno from lccont where prtno='"+PrtNo+"' ");
    	if(tProposalNo==null)
    	{
    		tProposalNo="";
    	}
    	loggerDebug("WbProposalSave",tProposalNo);
    	
     }
     catch(Exception ex)
     {
      FlagStr="Fail";
      Content = "����ʱ�����쳣��";
     }
    %>    

                  
<html>
<script language="javascript">
	try 
	{
		parent.fraInterface.fm.all('ProposalNo').value='<%=tProposalNo%>';
		parent.fraInterface.fm.all('save').disabled=true;
		parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
	}	catch(ex) { 
		//alert("after Save but happen err:" + ex);
	}
</script>
 
</html>

