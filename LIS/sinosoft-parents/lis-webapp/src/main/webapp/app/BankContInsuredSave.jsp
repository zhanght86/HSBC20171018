<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�ContInsuredSave.jsp
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

<%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
//  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCInsuredSchema tOLDLCInsuredSchema=new LCInsuredSchema();
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
  loggerDebug("BankContInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("BankContInsuredSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("BankContInsuredSave","aaaa");
    //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
    String fmAction=request.getParameter("fmAction");
    loggerDebug("BankContInsuredSave","fmAction:"+fmAction); 

/*        
  String tLimit="";
  String CustomerNo="";
*/ 
        
  tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));  
  tLCContSchema.setContNo(request.getParameter("ContNo")); 
  tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
  tLCContSchema.setManageCom(request.getParameter("ManageCom"));
  //tLCContSchema.setPolType(request.getParameter("PolTypeFlag")); //���������
  //tLCContSchema.setPeoples(request.getParameter("InsuredAppAge")); //������������
  
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","GrpContNo=="+request.getParameter("GrpContNo"));
  loggerDebug("BankContInsuredSave","ProposalContNo=="+request.getParameter("ProposalContNo"));
  loggerDebug("BankContInsuredSave","PrtNo=="+request.getParameter("PrtNo"));
  loggerDebug("BankContInsuredSave","ManageCom=="+request.getParameter("ManageCom"));
  loggerDebug("BankContInsuredSave","RelationToMainInsuredCode=="+request.getParameter("RelationToMainInsured"));
  loggerDebug("BankContInsuredSave","RelationToAppntCode=="+request.getParameter("RelationToAppnt"));
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  
  
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
  tmLCInsuredSchema.setIDExpDate(request.getParameter("IDPeriodOfValidity"));


  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","InsuredNo=="+request.getParameter("InsuredNo"));
  loggerDebug("BankContInsuredSave","RelationToMainInsured=="+request.getParameter("RelationToMainInsuredCode"));
  loggerDebug("BankContInsuredSave","RelationToAppnt=="+request.getParameter("RelationToAppntCode"));
  loggerDebug("BankContInsuredSave","ContNo=="+request.getParameter("ContNo"));
  loggerDebug("BankContInsuredSave","LicenseType=="+request.getParameter("LicenseType"));
  loggerDebug("BankContInsuredSave","################LCCont#######################");
  loggerDebug("BankContInsuredSave","################LCCont#######################");



          
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
  tLDPersonSchema.setPluralityType(request.getParameter("PluralityType"));
  tLDPersonSchema.setDeathDate(request.getParameter("DeathDate"));
  tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
  tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlag"));
  tLDPersonSchema.setProterty(request.getParameter("Proterty"));
  tLDPersonSchema.setRemark(request.getParameter("Remark"));
  tLDPersonSchema.setState(request.getParameter("State"));
  tLDPersonSchema.setLicenseType(request.getParameter("LicenseType"));
  tLDPersonSchema.setIDExpDate(request.getParameter("IDPeriodOfValidity"));
 	
 	loggerDebug("BankContInsuredSave","##########11LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","InsuredAddressNo=="+request.getParameter("InsuredAddressNo"));
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	
 	
 	tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
  if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals("")){
    loggerDebug("BankContInsuredSave","abc");
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
  tLCAddressSchema.setCompanyPhone(request.getParameter("CompanyPhone"));
  tLCAddressSchema.setCompanyAddress(request.getParameter("GrpAddress"));
  tLCAddressSchema.setCompanyZipCode(request.getParameter("GrpZipCode"));
  tLCAddressSchema.setCompanyFax(request.getParameter("GrpFax"));        
  tLCAddressSchema.setGrpName(request.getParameter("GrpName"));        
  tLCAddressSchema.setProvince(request.getParameter("InsuredProvince"));
  tLCAddressSchema.setCity(request.getParameter("InsuredCity"));
  tLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));
  
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","getAddressNo()=="+tLCAddressSchema.getAddressNo());
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("BankContInsuredSave","##########LCAddressSchema###########");
  
  String SavePolType="";
  String BQFlag=request.getParameter("BQFlag");
  
  if(BQFlag==null) SavePolType="0";
  else if(BQFlag.equals("")) SavePolType="0";
  else  SavePolType=BQFlag;  
  
  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //��ȫ�����ǣ�Ĭ��Ϊ0����ʶ�Ǳ�ȫ  
  tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
  tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //��ͥ����� 
  tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //�ŵ������˵����
  tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //���������
  tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //������������
  tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //������������
  tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //�ڲ��ͻ���
  tTransferData.setNameAndValue("LoadFlag",request.getParameter("LoadFlag")); //����loadflag���ж��ںδ����в�����---yeshu,20071224        
  loggerDebug("BankContInsuredSave","ContType"+request.getParameter("ContType"));
  loggerDebug("BankContInsuredSave","fmAction=="+fmAction);

  if (fmAction.equals("UPDATE||CONTINSURED")||fmAction.equals("DELETE||CONTINSURED"))
  {
    String tRadio[] = request.getParameterValues("InpInsuredGridSel");
    String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
    loggerDebug("BankContInsuredSave","**************2"+tRadio);
    loggerDebug("BankContInsuredSave","**************1"+tInsuredNo);
    
    if (tRadio!=null)
    {
      for (int index=0; index< tRadio.length;index++)
      {
        loggerDebug("BankContInsuredSave","**************3");
      
        if(tRadio[index].equals("1"))
        {
          tOLDLCInsuredSchema.setContNo(request.getParameter("ContNo"));
          tOLDLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
          tOLDLCInsuredSchema.setInsuredNo(tInsuredNo[index]);
        }
        if (tOLDLCInsuredSchema.getInsuredNo()==null)
        {
          tOLDLCInsuredSchema.setContNo(request.getParameter("ContNo"));
          tOLDLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
          tOLDLCInsuredSchema.setInsuredNo(tInsuredNo[0]);
        }
      }
    }
    else
    {
      loggerDebug("BankContInsuredSave","**************4");
      tOLDLCInsuredSchema.setContNo(request.getParameter("ContNo"));
      tOLDLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
      loggerDebug("BankContInsuredSave","tInsuredNo"+tInsuredNo[0]);
      tOLDLCInsuredSchema.setInsuredNo(tInsuredNo[0]);
    }
  }
  
  String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //��֪���
	String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //��֪����
	String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //��֪����
	String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //��д����
	String tIncome = request.getParameter("Income"); 
	String tIncomeWay = request.getParameter("IncomeWay");  
	String tImpartParaModle1=tIncome+","+ tIncomeWay;  
	//loggerDebug("BankContInsuredSave","#######bbbbb==="+request.getParameterValues("ImpartInsuredGridNo").length);	
	if(!tIncome.equals("")&&!tIncomeWay.equals(""))
	{
		LCCustomerImpartSchema ttLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		ttLCCustomerImpartSchema.setCustomerNoType("1");
		ttLCCustomerImpartSchema.setImpartCode("001");
		ttLCCustomerImpartSchema.setImpartContent("��ÿ��̶�����    ��Ԫ  ��Ҫ������Դ�� ����ţ���ѡ��ٹ�н�ڸ����˽Ӫ�ܷ��ݳ����֤ȯͶ�ʢ�������Ϣ������");
		ttLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle1);
		ttLCCustomerImpartSchema.setImpartVer("01");
		ttLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(ttLCCustomerImpartSchema);
	}
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
	
  loggerDebug("BankContInsuredSave","end set schema ��֪��Ϣ..."+ImpartCount);
  String tImpartDetailNum[] = request.getParameterValues("ImpartDetailGridNo");
	String tImpartDetailVer[] = request.getParameterValues("ImpartDetailGrid1");            //��֪���
	String tImpartDetailCode[] = request.getParameterValues("ImpartDetailGrid2");           //��֪����
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid3");  
	
/*********************************
	String tImpartDetailContent[] = request.getParameterValues("ImpartDetailGrid3");        //��֪����
	String tImpartDetailStartDate[] = request.getParameterValues("ImpartDetailGrid4");      
	String tImpartDetailEndDate[] = request.getParameterValues("ImpartDetailGrid5");
	String tImpartDetailProver[] = request.getParameterValues("ImpartDetailGrid6");    
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid7");  
	String tImpartDetailIsProved[] = request.getParameterValues("ImpartDetailGrid8");  		  
**********************************/      		        		
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
/************************************		
		tLCCustomerImpartDetailSchema.setStartDate(tImpartDetailStartDate[i]);
		tLCCustomerImpartDetailSchema.setEndDate(tImpartDetailEndDate[i]);
		tLCCustomerImpartDetailSchema.setProver(tImpartDetailProver[i]);
		tLCCustomerImpartDetailSchema.setCurrCondition(tImpartDetailCurrCondition[i]);								
		tLCCustomerImpartDetailSchema.setIsProved(tImpartDetailIsProved[i]);					
		tLCCustomerImpartDetailSet.add(tLCCustomerImpartDetailSchema);
************************************/
	}
	
  loggerDebug("BankContInsuredSave","end set schema ��֪��ϸ��Ϣ..."+ImpartDetailCount);             
  try
  {
    // ׼���������� VData
    loggerDebug("BankContInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    LLAccountSchema tLLAccountSchema = new LLAccountSchema(); //Ϊ�գ���������
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredSchema);
    tVData.add(tLLAccountSchema);
    loggerDebug("BankContInsuredSave","sadf12312"+tOLDLCInsuredSchema.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
    {
  	  if (fmAction.equals("INSERT||CONTINSURED"))
	    {
	      loggerDebug("BankContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tBusinessDelegate.getResult();
	      loggerDebug("BankContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      loggerDebug("BankContInsuredSave","test");
	    
	      if( mLCInsuredSchema.getInsuredNo() == null ) 
	      {
		          loggerDebug("BankContInsuredSave","null");
	      }
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      loggerDebug("BankContInsuredSave","jsp"+strCustomerNo);
	      String RelationToMainInsured=mLCInsuredSchema.getRelationToMainInsured();
	      if(RelationToMainInsured==null)
	      {
	        RelationToMainInsured="";
	      }
	      if( strCustomerNo==null)
	      {
	      	strCustomerNo = "123";
	      	loggerDebug("BankContInsuredSave","null");
	      }
	    
	      // loggerDebug("BankContInsuredSave","-----:"+mLAAgentSchema.getAgentCode());
%>
<SCRIPT language="javascript">
  parent.fraInterface.document.all("InsuredNo").value="<%=strCustomerNo%>";
  //parent.fraInterface.InsuredGrid.addOne("InsuredGrid");    
  parent.fraInterface.getInsuredInfo();	            	 
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,1,"<%=StrTool.unicodeToGBK(mLCInsuredSchema.getInsuredNo())%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,2,"<%=StrTool.unicodeToGBK(mLCInsuredSchema.getName())%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,3,"<%=mLCInsuredSchema.getSex()%>");
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,4,"<%=mLCInsuredSchema.getBirthday()%>");                     
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,5,"<%=RelationToMainInsured%>"); 
  parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.mulLineCount-1,6,"<%=mLCInsuredSchema.getSequenceNo()%>");                                   
  parent.fraInterface.document.all("ContNo").value="<%=strContNo%>"; 
  parent.fraInterface.document.all("InsuredAddressNo").value="<%=strAddressNo%>"; 
                  
</SCRIPT>
<%
	    }
	    if (fmAction.equals("UPDATE||CONTINSURED"))
	    {
	      loggerDebug("BankContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tBusinessDelegate.getResult();
	      loggerDebug("BankContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      
	      loggerDebug("BankContInsuredSave","jsp"+strAddressNo);
%>
<SCRIPT language="javascript">
   
    parent.fraInterface.fm.SamePersonFlag.checked = false;
  	parent.fraInterface.isSamePerson();
  	//parent.fraInterface.emptyInsured();
    
  	
  	parent.fraInterface.document.all("InsuredNo").value="<%=strCustomerNo%>"; 
  	//
   //if (parent.fraInterface.document.all("FamilyType").value=="1"){  
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,2,"<%=mLCInsuredSchema.getName()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,3,"<%=mLCInsuredSchema.getSex()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,4,"<%=mLCInsuredSchema.getBirthday()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,5,"<%=mLCInsuredSchema.getRelationToMainInsured()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,6,"<%=mLCInsuredSchema.getSequenceNo()%>");
     //alert("parent.fraInterface.document.all(InsuredAddressNo).value*"+parent.fraInterface.document.all("InsuredAddressNo").value);
     //alert("parent.fraInterface.document.all(InsuredNo).value=*"+parent.fraInterface.document.all("InsuredNo").value);
     parent.fraInterface.getInsuredInfo();
     parent.fraInterface.document.all("InsuredAddressNo").value="<%=strAddressNo%>";
     parent.fraInterface.getdetailaddress2();                               
   //}
</SCRIPT>
<%
	      }
	      if (fmAction.equals("DELETE||CONTINSURED"))
	      {
	  	    
%>
<SCRIPT language="javascript">
  //alert(1);
  parent.fraInterface.fm.SamePersonFlag.checked = false;
  parent.fraInterface.isSamePerson();
  parent.fraInterface.document.all("InsuredNo").value=""; 
  parent.fraInterface.emptyInsured();
  //alert(2);
	if (parent.fraInterface.document.all("FamilyType").value=="1"){  
  //ɾ����ѡ�еĿͻ�
    parent.fraInterface.InsuredGrid.delRadioTrueLine("InsuredGrid");
  }
  else {
    parent.fraInterface.InsuredGrid.clearData();
  }
  //alert(3);
  if (parent.fraInterface.document.all("ContType").value=="2"&&parent.fraInterface.document.all("RelationToMainInsured").value=="00"){
    parent.fraInterface.document.all("ContNo").value="";
    parent.fraInterface.document.all("ProposalContNo").value="";
  }
  //alert(4);
  parent.fraInterface.PolGrid.clearData();
  //alert(5);
  parent.fraInterface.emptyInsured();
  //alert(6);
  parent.fraInterface.getInsuredInfo();
  
      
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
    loggerDebug("BankContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //ҳ����Ч��

  loggerDebug("BankContInsuredSave","####################################");
  loggerDebug("BankContInsuredSave","####################################");
  loggerDebug("BankContInsuredSave","####################################");
  loggerDebug("BankContInsuredSave","####################################");
  loggerDebug("BankContInsuredSave","####################################");
  loggerDebug("BankContInsuredSave","####################################");
%>                                       
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>


