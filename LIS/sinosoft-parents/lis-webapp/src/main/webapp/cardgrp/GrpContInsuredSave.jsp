<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GrpContInsuredSave.jsp
//�����ܣ�
//�������ڣ�2002-06-27 08:49:52
//������  ��CrtHtml���򴴽�
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

<%
  //������Ϣ������У�鴦��
  //�������
  LLAccountSchema tLLAccountSchema=new LLAccountSchema(); //add by wanglei
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  TransferData tTransferData = new TransferData(); 
  ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("GrpContInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("GrpContInsuredSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("GrpContInsuredSave","aaaa");
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    String fmAction=request.getParameter("fmAction");
    loggerDebug("GrpContInsuredSave","fmAction:"+fmAction); 

/*        
  String tLimit="";
  String CustomerNo="";
*/ 
  //tongmeng 2009-03-20 add
  //֧�ָ�����Ч�յ�ָ��.
  //���������Ч����ֵ,��ôʹ�ø��˵���Ч��.
  String tContCValiDate = request.getParameter("ContCValiDate");
   if(tContCValiDate!=null&&!tContCValiDate.equals("")&&!tContCValiDate.equals("null"))
  {
	   tLCContSchema.setCValiDate(tContCValiDate);
	 
  }
  tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
  tLCContSchema.setContNo(request.getParameter("ContNo")); 
  tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
  tLCContSchema.setManageCom(request.getParameter("ManageCom"));
  loggerDebug("GrpContInsuredSave","�������: "+request.getParameter("ExecuteCom"));
  if("".equals(request.getParameter("ExecuteCom"))){
	  tLCContSchema.setExecuteCom(request.getParameter("ManageCom"));
  }
  else{
  tLCContSchema.setExecuteCom(request.getParameter("ExecuteCom"));
  }
	  
  //tLCContSchema.setPolType(request.getParameter("PolTypeFlag")); //���������
  //tLCContSchema.setPeoples(request.getParameter("InsuredAppAge")); //������������
  
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","GrpContNo=="+request.getParameter("GrpContNo"));
  loggerDebug("GrpContInsuredSave","ProposalContNo=="+request.getParameter("ProposalContNo"));
  loggerDebug("GrpContInsuredSave","PrtNo=="+request.getParameter("PrtNo"));
  loggerDebug("GrpContInsuredSave","ManageCom=="+request.getParameter("ManageCom"));
  loggerDebug("GrpContInsuredSave","RelationToMainInsuredCode=="+request.getParameter("RelationToMainInsured"));
  loggerDebug("GrpContInsuredSave","RelationToAppntCode=="+request.getParameter("RelationToAppnt"));
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  
  
  tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"));
  tmLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"));
  tmLCInsuredSchema.setContNo(request.getParameter("ContNo"));
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
  tmLCInsuredSchema.setWorkNo(request.getParameter("WorkNo"));
  tmLCInsuredSchema.setCertifyCode(request.getParameter("CertifyCode")); //�������add by liuqh
  tmLCInsuredSchema.setStartCode(request.getParameter("StartCode"));
  tmLCInsuredSchema.setEndCode(request.getParameter("EndCode"));
  tmLCInsuredSchema.setInsuredStat("1"); //add by liuqh
  //add by wanglei start.....
  tLLAccountSchema.setGrpContNo(request.getParameter("GrpContNo"));
  tLLAccountSchema.setInsuredNo(request.getParameter("InsuredNo"));
  tLLAccountSchema.setName(request.getParameter("Name"));
  tLLAccountSchema.setBankCode(request.getParameter("BankCode"));
  tLLAccountSchema.setAccount(request.getParameter("BankAccNo"));
  //end......
  
  loggerDebug("GrpContInsuredSave","����֤�ţ�"+request.getParameter("WorkNo"));
  //����������ʱ���ü�ְ�����֣��洢ԭ��������ͬ��
  tmLCInsuredSchema.setPluralityType(request.getParameter("vContNo"));
  loggerDebug("GrpContInsuredSave","%%%%%%%%%%%%%%%%%=="+request.getParameter("vContNo"));
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","InsuredNo=="+request.getParameter("InsuredNo"));
  loggerDebug("GrpContInsuredSave","RelationToMainInsured=="+request.getParameter("RelationToMainInsuredCode"));
  loggerDebug("GrpContInsuredSave","RelationToAppnt=="+request.getParameter("RelationToAppntCode"));
  loggerDebug("GrpContInsuredSave","ContNo=="+request.getParameter("ContNo"));
  loggerDebug("GrpContInsuredSave","################LCCont#######################");
  loggerDebug("GrpContInsuredSave","################LCCont#######################");



          
  tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
  tLDPersonSchema.setName(request.getParameter("Name"));
  tLDPersonSchema.setSex(request.getParameter("Sex"));      
  tLDPersonSchema.setBirthday(request.getParameter("Birthday"));
  tLDPersonSchema.setIDType(request.getParameter("IDType"));
  String tIDType = request.getParameter("IDType");
  if(!tIDType.equals("9")){
	  //��֤������ ����Ҫ¼��֤������ ��ʱ���set��LDPerson���� �״��ܹ�����ɹ� ���������ѯ���α������ٴ���� �ᱨ��;
     tLDPersonSchema.setIDNo(request.getParameter("IDNo"));
  }
          if(tLDPersonSchema.getIDType().equals("0")&&tLDPersonSchema.getIDNo()!=null&&tLDPersonSchema.getIDNo().length()==15){
            tLDPersonSchema.setIDNo(PubFun.TransID(tLDPersonSchema.getIDNo()));
            
        }
        if(tLDPersonSchema.getIDNo()!=null&&!tLDPersonSchema.getIDNo().equals("")){
        tLDPersonSchema.setIDNo(tLDPersonSchema.getIDNo().toUpperCase()); //add by wanglei ��֤���������ĸͳһ�ɴ�д��
        }
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
  if(request.getParameter("OccupationType")==null || request.getParameter("OccupationType").equals(""))
  {
  tLDPersonSchema.setOccupationType("1");
  }else{
  tLDPersonSchema.setOccupationType(request.getParameter("OccupationType"));
  }
  tLDPersonSchema.setOccupationCode(request.getParameter("OccupationCode"));
  tLDPersonSchema.setWorkType(request.getParameter("WorkType"));
  //Ϊ�������޸�
  tLDPersonSchema.setPluralityType(request.getParameter("vContNo"));
  tLDPersonSchema.setDeathDate(request.getParameter("DeathDate"));
  tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
  tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlag"));
  tLDPersonSchema.setProterty(request.getParameter("Proterty"));
  tLDPersonSchema.setRemark(request.getParameter("Remark"));
  tLDPersonSchema.setState(request.getParameter("State"));
 	
 	loggerDebug("GrpContInsuredSave","##########11LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","InsuredAddressNo=="+request.getParameter("InsuredAddressNo"));
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	
 	
 	tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
  if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals("")){
    loggerDebug("GrpContInsuredSave","abc");
    tLCAddressSchema.setAddressNo(request.getParameter("InsuredAddressNo"));  
  }
  tLCAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
  tLCAddressSchema.setZipCode(request.getParameter("ZipCode"));
  tLCAddressSchema.setPhone(request.getParameter("Phone"));
  tLCAddressSchema.setFax(request.getParameter("Fax"));          
  tLCAddressSchema.setMobile(request.getParameter("Mobile"));        
  tLCAddressSchema.setEMail(request.getParameter("EMail"));
  loggerDebug("GrpContInsuredSave","�ʼ���"+request.getParameter("EMail"));
  loggerDebug("GrpContInsuredSave","�ʼ���"+request.getParameter("Mobile"));
  tLCAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));        
  tLCAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
  tLCAddressSchema.setHomePhone(request.getParameter("HomePhone"));
  tLCAddressSchema.setHomeFax(request.getParameter("HomeFax"));        
  tLCAddressSchema.setCompanyPhone(request.getParameter("GrpPhone"));
  tLCAddressSchema.setCompanyAddress(request.getParameter("GrpAddress"));
  tLCAddressSchema.setCompanyZipCode(request.getParameter("GrpZipCode"));
  tLCAddressSchema.setCompanyFax(request.getParameter("GrpFax"));        
  tLCAddressSchema.setGrpName(request.getParameter("GrpName"));        
  
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","getAddressNo()=="+tLCAddressSchema.getAddressNo());
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("GrpContInsuredSave","##########LCAddressSchema###########");
  
  String SavePolType="";
  String BQFlag=request.getParameter("BQFlag");
  loggerDebug("GrpContInsuredSave","======================BQFlag======="+BQFlag);
  if(BQFlag==null) SavePolType="0";
  else if(BQFlag.equals("")) SavePolType="0";
  else  SavePolType=BQFlag;  
  loggerDebug("GrpContInsuredSave","======================SavePolType======="+SavePolType);
  loggerDebug("GrpContInsuredSave","======================edortype======="+request.getParameter("EdorType"));
  loggerDebug("GrpContInsuredSave","======================edortypecal======="+request.getParameter("EdorTypeCal"));
  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
  tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
  tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //��ͥ����� 
  tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //�ŵ������˵����
  tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //���������
  tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //������������
  tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //������������
  tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //�ڲ��ͻ���
  tTransferData.setNameAndValue("BQFlag",BQFlag); //��ȫ��־
  tTransferData.setNameAndValue("EdorType",request.getParameter("EdorType")); //��ȫ��Ŀ
  tTransferData.setNameAndValue("EdorTypeCal",request.getParameter("EdorTypeCal")); //��ȫ�㷨
  tTransferData.setNameAndValue("EdorValiDate",request.getParameter("EdorValiDate")); //��ȫ�㷨
          
  loggerDebug("GrpContInsuredSave","ContType"+request.getParameter("ContType"));
  loggerDebug("GrpContInsuredSave","fmAction=="+fmAction);

  if (fmAction.equals("UPDATE||CONTINSURED")||fmAction.equals("DELETE||CONTINSURED"))
  {
    String tRadio[] = request.getParameterValues("InpInsuredGridSel");
    String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
    loggerDebug("GrpContInsuredSave","**************2"+tRadio);
    loggerDebug("GrpContInsuredSave","**************1"+tInsuredNo);
    
    if (tRadio!=null)
    {
      for (int index=0; index< tRadio.length;index++)
      {
        loggerDebug("GrpContInsuredSave","**************3");
      
        if(tRadio[index].equals("1"))
        {
          tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
          tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
          tOLDLCInsuredDB.setInsuredNo(tInsuredNo[index]);
        }
        if (tOLDLCInsuredDB.getInsuredNo()==null)
        {
          tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
          tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
          tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
        }
      }
    }
    else
    {
      loggerDebug("GrpContInsuredSave","**************4");
      tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
      tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
      loggerDebug("GrpContInsuredSave","tInsuredNo"+tInsuredNo[0]);
      tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
    }
  }
  
  String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");        //��д����
	//String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //��֪�ͻ�����
	//String tImpartCustomerNo[] = request.getParameterValues("ImpartGrid6");     //��֪�ͻ�����
	//loggerDebug("GrpContInsuredSave","#######bbbbb==="+request.getParameterValues("ImpartGridNo").length);	
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
		tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
	}
	loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
	loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
	loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
	loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
  loggerDebug("GrpContInsuredSave","end set schema ��֪��Ϣ..."+ImpartCount);
  loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
  loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
  loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
  loggerDebug("GrpContInsuredSave","$$$$$$$$$$$$$$$$$$$$$$$$$");
/*********************************
  String tImpartDetailNum[] = request.getParameterValues("ImpartDetailGridNo");
	String tImpartDetailVer[] = request.getParameterValues("ImpartDetailGrid1");            //��֪���
	String tImpartDetailCode[] = request.getParameterValues("ImpartDetailGrid2");           //��֪����
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid3");  
	
************************************/
/*********************************
	String tImpartDetailContent[] = request.getParameterValues("ImpartDetailGrid3");        //��֪����
	String tImpartDetailStartDate[] = request.getParameterValues("ImpartDetailGrid4");      
	String tImpartDetailEndDate[] = request.getParameterValues("ImpartDetailGrid5");
	String tImpartDetailProver[] = request.getParameterValues("ImpartDetailGrid6");    
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid7");  
	String tImpartDetailIsProved[] = request.getParameterValues("ImpartDetailGrid8");  		  
**********************************/      		        		
/*********************************
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
************************************/
/************************************		
		tLCCustomerImpartDetailSchema.setStartDate(tImpartDetailStartDate[i]);
		tLCCustomerImpartDetailSchema.setEndDate(tImpartDetailEndDate[i]);
		tLCCustomerImpartDetailSchema.setProver(tImpartDetailProver[i]);
		tLCCustomerImpartDetailSchema.setCurrCondition(tImpartDetailCurrCondition[i]);								
		tLCCustomerImpartDetailSchema.setIsProved(tImpartDetailIsProved[i]);					
		tLCCustomerImpartDetailSet.add(tLCCustomerImpartDetailSchema);
	}
	
  loggerDebug("GrpContInsuredSave","end set schema ��֪��ϸ��Ϣ..."+ImpartDetailCount);             
************************************/
  try
  {
    // ׼���������� VData
    loggerDebug("GrpContInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLLAccountSchema);
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
//    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);

    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    if (tContInsuredUI.submitData(tVData,fmAction))
    {
  	  if (fmAction.equals("INSERT||CONTINSURED"))
	    {
	      loggerDebug("GrpContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tContInsuredUI.getResult();
	      loggerDebug("GrpContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      loggerDebug("GrpContInsuredSave","test");
	    
	      if( mLCInsuredSchema.getInsuredNo() == null ) 
	      {
		          loggerDebug("GrpContInsuredSave","null");
	      }
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      loggerDebug("GrpContInsuredSave","jsp"+strCustomerNo);
	      String RelationToMainInsured=mLCInsuredSchema.getRelationToMainInsured();
	      if(RelationToMainInsured==null)
	      {
	        RelationToMainInsured="";
	      }
	      if( strCustomerNo==null)
	      {
	      	strCustomerNo = "123";
	      	loggerDebug("GrpContInsuredSave","null");
	      }
	    
	      // loggerDebug("GrpContInsuredSave","-----:"+mLAAgentSchema.getAgentCode());
%>
<SCRIPT language="javascript">
  parent.fraInterface.fm.all("InsuredNo").value="<%=strCustomerNo%>";
	parent.fraInterface.InsuredGrid.addOne("InsuredGrid");    	            	 
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,1,"<%=StrTool.unicodeToGBK(mLCInsuredSchema.getInsuredNo())%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,2,"<%=StrTool.unicodeToGBK(mLCInsuredSchema.getName())%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,3,"<%=mLCInsuredSchema.getSex()%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,4,"<%=mLCInsuredSchema.getBirthday()%>");                     
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,5,"<%=RelationToMainInsured%>"); 
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,6,"<%=mLCInsuredSchema.getSequenceNo()%>");                                   
  parent.fraInterface.fm.all("ContNo").value="<%=strContNo%>"; 
  parent.fraInterface.fm.all("InsuredAddressNo").value="<%=strAddressNo%>"; 
                  
</SCRIPT>
<%
	    }
	    if (fmAction.equals("UPDATE||CONTINSURED"))
	    {
	      loggerDebug("GrpContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tContInsuredUI.getResult();
	      loggerDebug("GrpContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      loggerDebug("GrpContInsuredSave","jsp"+strAddressNo);
%>
<SCRIPT language="javascript">
   parent.fraInterface.fm.all("InsuredNo").value="<%=strCustomerNo%>";
   parent.fraInterface.fm.all("InsuredAddressNo").value="<%=strAddressNo%>";  
   if (parent.fraInterface.fm.all("FamilyType").value=="1"){  
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,2,"<%=mLCInsuredSchema.getName()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,3,"<%=mLCInsuredSchema.getSex()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,4,"<%=mLCInsuredSchema.getBirthday()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,5,"<%=mLCInsuredSchema.getRelationToMainInsured()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,6,"<%=mLCInsuredSchema.getSequenceNo()%>");                               
   }
</SCRIPT>
<%
	      }
	      if (fmAction.equals("DELETE||CONTINSURED"))
	      {
	  	    
%>
<SCRIPT language="javascript">
  parent.fraInterface.fm.all("InsuredNo").value=""; 
	if (parent.fraInterface.fm.all("FamilyType").value=="1"){  
  //ɾ����ѡ�еĿͻ�
    parent.fraInterface.InsuredGrid.delRadioTrueLine("InsuredGrid");
  }
  else {
    parent.fraInterface.InsuredGrid.clearData();
  }
  if (parent.fraInterface.fm.all("ContType").value=="2"&&parent.fraInterface.fm.all("RelationToMainInsured").value=="00"){
    parent.fraInterface.fm.all("ContNo").value="";
    parent.fraInterface.fm.all("ProposalContNo").value="";
  }
  parent.fraInterface.PolGrid.clearData();
  parent.fraInterface.emptyInsured();
  
  
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
    tError = tContInsuredUI.mErrors;
    if (!tError.needDealError())
    {                          
      Content ="����ɹ���";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
    loggerDebug("GrpContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //ҳ����Ч��

  loggerDebug("GrpContInsuredSave","####################################");
  loggerDebug("GrpContInsuredSave","####################################");
  loggerDebug("GrpContInsuredSave","####################################");
  loggerDebug("GrpContInsuredSave","####################################");
  loggerDebug("GrpContInsuredSave","####################################");
  loggerDebug("GrpContInsuredSave","####################################");
%>                                       
<html>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>

</html>

