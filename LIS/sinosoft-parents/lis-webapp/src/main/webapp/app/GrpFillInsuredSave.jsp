<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpFillInsuredSave.jsp
//�����ܣ�
//�������ڣ�2006-04-13 14:49:52
//������  ��Chenrong���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LLAccountSchema tLLAccountSchema=new LLAccountSchema();
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  TransferData tTransferData = new TransferData(); 
  //ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  String busiName="tbContInsuredUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("GrpFillInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GrpFillInsuredSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("GrpFillInsuredSave","aaaa");
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    String fmAction=request.getParameter("fmAction");
    loggerDebug("GrpFillInsuredSave","fmAction:"+fmAction); 

       
    tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
    //tLCContSchema.setContNo(request.getParameter("ContNo")); 
    tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
    tLCContSchema.setManageCom(request.getParameter("ManageCom"));
  
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
    loggerDebug("GrpFillInsuredSave","GrpContNo=="+request.getParameter("GrpContNo"));
    loggerDebug("GrpFillInsuredSave","ContNo=="+request.getParameter("ContNo"));
    loggerDebug("GrpFillInsuredSave","ProposalContNo=="+request.getParameter("ProposalContNo"));
    loggerDebug("GrpFillInsuredSave","PrtNo=="+request.getParameter("PrtNo"));
    loggerDebug("GrpFillInsuredSave","ManageCom=="+request.getParameter("ManageCom"));
    loggerDebug("GrpFillInsuredSave","RelationToMainInsuredCode=="+request.getParameter("RelationToMainInsured"));
    loggerDebug("GrpFillInsuredSave","RelationToAppntCode=="+request.getParameter("RelationToAppnt"));
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
  
  
    tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"));
    tmLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"));
    //tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
    tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
    tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
    tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
    tmLCInsuredSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
    tmLCInsuredSchema.setInsuredPeoples(request.getParameter("InsuredPeoples"));
    tmLCInsuredSchema.setSalary(request.getParameter("Salary"));
    tmLCInsuredSchema.setBankCode(request.getParameter("BankCode"));
    tmLCInsuredSchema.setBankAccNo(request.getParameter("BankAccNo"));                
    tmLCInsuredSchema.setAccName(request.getParameter("AccName"));
    tmLCInsuredSchema.setLicenseType(request.getParameter("LicenseType"));
    
    
    
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
    loggerDebug("GrpFillInsuredSave","InsuredNo=="+request.getParameter("InsuredNo"));
    loggerDebug("GrpFillInsuredSave","RelationToMainInsured=="+request.getParameter("RelationToMainInsuredCode"));
    loggerDebug("GrpFillInsuredSave","RelationToAppnt=="+request.getParameter("RelationToAppntCode"));
    loggerDebug("GrpFillInsuredSave","OldContNo=="+request.getParameter("OldContNo"));
    loggerDebug("GrpFillInsuredSave","LicenseType=="+request.getParameter("LicenseType"));
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
    loggerDebug("GrpFillInsuredSave","################LCCont#######################");
          
    tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
    tLDPersonSchema.setName(request.getParameter("Name"));
    tLDPersonSchema.setSex(request.getParameter("Sex"));      
    tLDPersonSchema.setBirthday(request.getParameter("Birthday"));
    tLDPersonSchema.setIDType(request.getParameter("IDType"));
    tLDPersonSchema.setIDNo(request.getParameter("IDNo"));
    tLDPersonSchema.setPassword(request.getParameter("Password"));
    tLDPersonSchema.setNativePlace(request.getParameter("NativePlace"));
    tLDPersonSchema.setNationality(request.getParameter("Nationality"));
    tLDPersonSchema.setRgtAddress(request.getParameter("RgtAddress"));
    tLDPersonSchema.setMarriage(request.getParameter("Marriage"));
    tLDPersonSchema.setMarriageDate(request.getParameter("MarriageDate"));
    tLDPersonSchema.setHealth(request.getParameter("Health"));
    tLDPersonSchema.setStature(request.getParameter("Stature"));
    tLDPersonSchema.setAvoirdupois(request.getParameter("Avoirdupois"));
    tLDPersonSchema.setDegree(request.getParameter("Degree"));
    tLDPersonSchema.setCreditGrade(request.getParameter("CreditGrade"));
    tLDPersonSchema.setOthIDType(request.getParameter("OthIDType"));
    tLDPersonSchema.setOthIDNo(request.getParameter("OthIDNo"));
    tLDPersonSchema.setICNo(request.getParameter("ICNo"));
    tLDPersonSchema.setGrpNo(request.getParameter("GrpNo"));
    tLDPersonSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
    tLDPersonSchema.setStartWorkDate(request.getParameter("StartWorkDate"));
    tLDPersonSchema.setPosition(request.getParameter("Position"));
    tLDPersonSchema.setSalary(request.getParameter("Salary"));
    tLDPersonSchema.setOccupationType(request.getParameter("OccupationType"));
    tLDPersonSchema.setOccupationCode(request.getParameter("OccupationCode"));
    tLDPersonSchema.setWorkType(request.getParameter("WorkType"));
    
    //���Ϊ����������������¼ԭ��ContNoֵ��
    tLDPersonSchema.setPluralityType(request.getParameter("OldContNo"));
    
    tLDPersonSchema.setDeathDate(request.getParameter("DeathDate"));
    tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
    tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlag"));
    tLDPersonSchema.setProterty(request.getParameter("Proterty"));
    tLDPersonSchema.setRemark(request.getParameter("Remark"));
    tLDPersonSchema.setState(request.getParameter("State"));
    tLDPersonSchema.setLicenseType(request.getParameter("LicenseType"));
    
    loggerDebug("GrpFillInsuredSave","##########11LCAddressSchema###########");
    loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
    loggerDebug("GrpFillInsuredSave","InsuredAddressNo=="+request.getParameter("InsuredAddressNo"));
    loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
    loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
 	
 	
 	
 	tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
    if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals("")){
        loggerDebug("GrpFillInsuredSave","abc");
        tLCAddressSchema.setAddressNo(request.getParameter("InsuredAddressNo"));  
    }
    tLCAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
    tLCAddressSchema.setZipCode(request.getParameter("ZIPCODE"));
    tLCAddressSchema.setPhone(request.getParameter("Phone"));
    tLCAddressSchema.setFax(request.getParameter("Fax"));          
    tLCAddressSchema.setMobile(request.getParameter("Mobile"));        
    tLCAddressSchema.setEMail(request.getParameter("EMail"));
    
    tLCAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));        
    tLCAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
    tLCAddressSchema.setHomePhone(request.getParameter("HomePhone"));
    tLCAddressSchema.setHomeFax(request.getParameter("HomeFax"));        
    tLCAddressSchema.setCompanyPhone(request.getParameter("GrpPhone"));
    tLCAddressSchema.setCompanyAddress(request.getParameter("GrpAddress"));
    tLCAddressSchema.setCompanyZipCode(request.getParameter("GrpZipCode"));
    tLCAddressSchema.setCompanyFax(request.getParameter("GrpFax"));        
    tLCAddressSchema.setGrpName(request.getParameter("GrpName"));        
    tLCAddressSchema.setProvince(request.getParameter("InsuredProvince"));
    tLCAddressSchema.setCity(request.getParameter("InsuredCity"));
    tLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));
  
 	loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpFillInsuredSave","getAddressNo()=="+tLCAddressSchema.getAddressNo());
 	loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpFillInsuredSave","##########LCAddressSchema###########");
 	
 	tLLAccountSchema.setGrpContNo(request.getParameter("GrpContNo"));
 	tLLAccountSchema.setInsuredNo(request.getParameter("InsuredNo"));
 	tLLAccountSchema.setName(request.getParameter("Name"));
 	tLLAccountSchema.setBankCode(request.getParameter("BankCode"));
 	tLLAccountSchema.setAccount(request.getParameter("BankAccNo"));
  
    String SavePolType="";
    String BQFlag=request.getParameter("BQFlag");
  
    if(BQFlag==null) 
    SavePolType="0";
    else if(BQFlag.equals("")) 
    SavePolType="0";
    else  
    SavePolType=BQFlag;   
          
    loggerDebug("GrpFillInsuredSave","ContType"+request.getParameter("ContType"));
    loggerDebug("GrpFillInsuredSave","fmAction=="+fmAction);
    loggerDebug("GrpFillInsuredSave","Begin->��֪");
    
    String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //��д����
	String tIncome = "";
	 tIncome = request.getParameter("Income"); 
	String tIncomeWay = "";
	 tIncomeWay = request.getParameter("IncomeWay");  
	String tImpartParaModle1=tIncome+","+ tIncomeWay; 
	
	loggerDebug("GrpFillInsuredSave","Begin->tIncome=" + tIncome);
	loggerDebug("GrpFillInsuredSave","Begin->tIncomeWay=" + tIncomeWay);
	
	if((tIncome == null || tIncome.equals("")) && (tIncomeWay == null || tIncomeWay.equals("")))
	{
	    //loggerDebug("GrpFillInsuredSave","b");	   
	}
	else
	{
	    //loggerDebug("GrpFillInsuredSave","aaaaab");
	    LCCustomerImpartSchema ttLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		ttLCCustomerImpartSchema.setCustomerNoType("1");
		ttLCCustomerImpartSchema.setImpartCode("001");
		ttLCCustomerImpartSchema.setImpartContent("��ÿ��̶�����    ��Ԫ  ��Ҫ������Դ�� ����ţ���ѡ��ٹ�н�ڸ����˽Ӫ�ܷ��ݳ����֤ȯͶ�ʢ�������Ϣ������");
		ttLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle1);
		ttLCCustomerImpartSchema.setImpartVer("01");
		ttLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(ttLCCustomerImpartSchema);
	}

	//loggerDebug("GrpFillInsuredSave","Begin->��֪2");

	int ImpartCount = 0;
	if (tImpartNum != null) ImpartCount = tImpartNum.length;
	   
	for (int i = 0; i < ImpartCount; i++)	{
	    LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
        tLCCustomerImpartSchema.setProposalContNo(request.getParameter("ProposalContNo"));
		//tLCCustomerImpartSchema.setCustomerNo(tLDPersonSchema.getCustomerNo());
		tLCCustomerImpartSchema.setCustomerNoType("1");
		tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLCCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]);
  	    tLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
	}
	
	
	String tRiskNum[] = request.getParameterValues("PolGridNo");
	String tRiskCode[] = request.getParameterValues("PolGrid1");           
	String tPrem[] = request.getParameterValues("PolGrid3");           
	String tAmnt[] = request.getParameterValues("PolGrid4");        
	String tPolNo[] = request.getParameterValues("PolGrid5");        
	int RiskCount = 0;
	if (tRiskNum != null) RiskCount = tRiskNum.length;
	   
	double tP=0;
	double tA=0;
	LCPolSet tLCPolSet =new LCPolSet();
	for (int i = 0; i < RiskCount; i++)	{
		LCPolSchema tLCPolSchema =new LCPolSchema();
		tLCPolSchema.setPolNo(tPolNo[i]);
		tLCPolSchema.setRiskCode(tRiskCode[i]);
		loggerDebug("GrpFillInsuredSave","tPrem["+i+"]:"+tPrem[i]);
		if(tPrem[i]==null||"".equals(tPrem[i])){
			tLCPolSchema.setPrem(0);
			//tP =tP+0;
		}
		else{
			tLCPolSchema.setPrem(tPrem[i]);
			tP =tP+Double.parseDouble(tPrem[i]);
		}
		loggerDebug("GrpFillInsuredSave","tAmnt["+i+"]:"+tAmnt[i]);
		if(tAmnt[i]==null||"".equals(tAmnt[i])){
			tLCPolSchema.setAmnt(0);
			//tA =tA+0;
		}
		else{
			tLCPolSchema.setAmnt(tAmnt[i]);
			tA =tA+Double.parseDouble(tAmnt[i]);
		}
		tLCPolSet.add(tLCPolSchema);
	}
	tLCContSchema.setPrem(tP);
	tLCContSchema.setAmnt(tA);
//	tLCContSchema.setSumPrem(tP);
	
	
    loggerDebug("GrpFillInsuredSave","end set schema ��֪��Ϣ..."+ImpartCount);
    String tImpartDetailNum[] = request.getParameterValues("ImpartDetailGridNo");
	String tImpartDetailVer[] = request.getParameterValues("ImpartDetailGrid1");            //��֪���
	String tImpartDetailCode[] = request.getParameterValues("ImpartDetailGrid2");           //��֪����
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid3");  
     		        		
	int ImpartDetailCount = 0;
	if (tImpartDetailNum != null) ImpartDetailCount = tImpartDetailNum.length;
	    
	for (int i = 0; i < ImpartDetailCount; i++)	{
	    LCCustomerImpartDetailSchema tLCCustomerImpartDetailSchema = new LCCustomerImpartDetailSchema();						
		tLCCustomerImpartDetailSchema.setCustomerNoType("1");
		tLCCustomerImpartDetailSchema.setImpartVer(tImpartDetailVer[i]) ;				
		tLCCustomerImpartDetailSchema.setImpartCode(tImpartDetailCode[i]);
		tLCCustomerImpartDetailSchema.setCurrCondition(tImpartDetailCurrCondition[i]);
		tLCCustomerImpartDetailSchema.setImpartDetailContent(tImpartDetailCurrCondition[i]);
		tLCCustomerImpartDetailSchema.setDiseaseContent(tImpartDetailCurrCondition[i]);
	}
	
    loggerDebug("GrpFillInsuredSave","end set schema ��֪��ϸ��Ϣ..."+ImpartDetailCount); 
  
    tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
    tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //��ͥ����� 
    tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //�ŵ������˵����
    tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //���������
    tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //������������
    tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //������������
    tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //�ڲ��ͻ���
              
  try
  {
    // ׼���������� VData
    loggerDebug("GrpFillInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);
    tVData.add(tLLAccountSchema);
    tVData.add(tLCPolSet);
    
    loggerDebug("GrpFillInsuredSave","sadf12312"+tOLDLCInsuredDB.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
    {
  	  if (fmAction.equals("INSERT||CONTINSURED"))
	    {
	      loggerDebug("GrpFillInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tBusinessDelegate.getResult();
	      loggerDebug("GrpFillInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      if( mLCInsuredSchema.getInsuredNo() == null ) 
	      {
		          loggerDebug("GrpFillInsuredSave","null");
	      }
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      loggerDebug("GrpFillInsuredSave","jsp"+strCustomerNo);
	      String RelationToMainInsured=mLCInsuredSchema.getRelationToMainInsured();
	      if(RelationToMainInsured==null)
	      {
	        RelationToMainInsured="";
	      }
	      if( strCustomerNo==null)
	      {
	      	strCustomerNo = "123";
	      	loggerDebug("GrpFillInsuredSave","null");
	      }
	    
	      // loggerDebug("GrpFillInsuredSave","-----:"+mLAAgentSchema.getAgentCode());
%>
<SCRIPT language="javascript">
                              
    parent.fraInterface.document.all("ContNo").value="<%=strContNo%>"; 
    parent.fraInterface.document.all("InsuredAddressNo").value="<%=strAddressNo%>"; 
                  
</SCRIPT>
<%
	    }
	  }
  }
  catch(Exception ex)
  {
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
        Content ="�����ɹ���";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
    loggerDebug("GrpFillInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //ҳ����Ч��
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


