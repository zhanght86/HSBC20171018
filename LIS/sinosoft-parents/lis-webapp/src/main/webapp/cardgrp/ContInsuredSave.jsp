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
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦����
  //�������
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  TransferData tTransferData = new TransferData();
  
  String busiName="tbContInsuredUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //�������
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //�μ�loginSubmit.jsp
  loggerDebug("ContInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("ContInsuredSave","ҳ��ʧЧ,�����µ�½");   
    FlagStr = "Fail";        
    Content = "ҳ��ʧЧ,�����µ�½";  
  }
  else //ҳ����Ч
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("ContInsuredSave","aaaa");
    //����Ҫִ�еĶ��������ӣ��޸ģ�ɾ��
    String fmAction=request.getParameter("fmAction");
    loggerDebug("ContInsuredSave","fmAction:"+fmAction); 

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
  
  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","GrpContNo=="+request.getParameter("GrpContNo"));
  loggerDebug("ContInsuredSave","ProposalContNo=="+request.getParameter("ProposalContNo"));
  loggerDebug("ContInsuredSave","PrtNo=="+request.getParameter("PrtNo"));
  loggerDebug("ContInsuredSave","ManageCom=="+request.getParameter("ManageCom"));
  loggerDebug("ContInsuredSave","RelationToMainInsuredCode=="+request.getParameter("RelationToMainInsured"));
  loggerDebug("ContInsuredSave","RelationToAppntCode=="+request.getParameter("RelationToAppnt"));
  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","################LCCont#######################");
  
  
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
  


  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","InsuredNo=="+request.getParameter("InsuredNo"));
  loggerDebug("ContInsuredSave","RelationToMainInsured=="+request.getParameter("RelationToMainInsuredCode"));
  loggerDebug("ContInsuredSave","RelationToAppnt=="+request.getParameter("RelationToAppntCode"));
  loggerDebug("ContInsuredSave","ContNo=="+request.getParameter("ContNo"));
  loggerDebug("ContInsuredSave","LicenseType=="+request.getParameter("LicenseType"));
  loggerDebug("ContInsuredSave","################LCCont#######################");
  loggerDebug("ContInsuredSave","################LCCont#######################");



          
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
 	
 	loggerDebug("ContInsuredSave","##########11LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","InsuredAddressNo=="+request.getParameter("InsuredAddressNo"));
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	
 	
 	tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
  if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals("")){
    loggerDebug("ContInsuredSave","abc");
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
  
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","getAddressNo()=="+tLCAddressSchema.getAddressNo());
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
 	loggerDebug("ContInsuredSave","##########LCAddressSchema###########");
  
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
          
  loggerDebug("ContInsuredSave","ContType"+request.getParameter("ContType"));
  loggerDebug("ContInsuredSave","fmAction=="+fmAction);

  if (fmAction.equals("UPDATE||CONTINSURED")||fmAction.equals("DELETE||CONTINSURED"))
  {
    String tRadio[] = request.getParameterValues("InpInsuredGridSel");
    String tInsuredNo[] = request.getParameterValues("InsuredGrid1");
    loggerDebug("ContInsuredSave","**************2"+tRadio);
    loggerDebug("ContInsuredSave","**************1"+tInsuredNo);
    
    if (tRadio!=null)
    {
      for (int index=0; index< tRadio.length;index++)
      {
        loggerDebug("ContInsuredSave","**************3");
      
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
      loggerDebug("ContInsuredSave","**************4");
      tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
      tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
      loggerDebug("ContInsuredSave","tInsuredNo"+tInsuredNo[0]);
      tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
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
	//loggerDebug("ContInsuredSave","#######bbbbb==="+request.getParameterValues("ImpartInsuredGridNo").length);	
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
	
  loggerDebug("ContInsuredSave","end set schema ��֪��Ϣ..."+ImpartCount);
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
	
  loggerDebug("ContInsuredSave","end set schema ��֪��ϸ��Ϣ..."+ImpartDetailCount);             
  try
  {
    // ׼���������� VData
    loggerDebug("ContInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);
    loggerDebug("ContInsuredSave","sadf12312"+tOLDLCInsuredDB.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //ִ�ж�����insert ���Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    if (tBusinessDelegate.submitData(tVData,fmAction,busiName))
    {
  	  if (fmAction.equals("INSERT||CONTINSURED"))
	    {
	      loggerDebug("ContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tBusinessDelegate.getResult();
	      loggerDebug("ContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      loggerDebug("ContInsuredSave","test");
	    
	      if( mLCInsuredSchema.getInsuredNo() == null ) 
	      {
		          loggerDebug("ContInsuredSave","null");
	      }
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      loggerDebug("ContInsuredSave","jsp"+strCustomerNo);
	      String RelationToMainInsured=mLCInsuredSchema.getRelationToMainInsured();
	      if(RelationToMainInsured==null)
	      {
	        RelationToMainInsured="";
	      }
	      if( strCustomerNo==null)
	      {
	      	strCustomerNo = "123";
	      	loggerDebug("ContInsuredSave","null");
	      }
	    
	      // loggerDebug("ContInsuredSave","-----:"+mLAAgentSchema.getAgentCode());
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
	      loggerDebug("ContInsuredSave","------return");
	      	
	      tVData.clear();
	      tVData = tBusinessDelegate.getResult();
	      loggerDebug("ContInsuredSave","-----size:"+tVData.size());
	      
	      LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema(); 
	      mLCInsuredSchema=(LCInsuredSchema)tVData.getObjectByObjectName("LCInsuredSchema",0);
	    
	      String strCustomerNo = mLCInsuredSchema.getInsuredNo(); 
	      String strContNo =  mLCInsuredSchema.getContNo();
	      String strAddressNo= mLCInsuredSchema.getAddressNo();
	      
	      loggerDebug("ContInsuredSave","jsp"+strAddressNo);
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
  //alert(1);
  parent.fraInterface.fm.SamePersonFlag.checked = false;
  parent.fraInterface.isSamePerson();
  parent.fraInterface.fm.all("InsuredNo").value=""; 
  parent.fraInterface.emptyInsured();
  //alert(2);
	if (parent.fraInterface.fm.all("FamilyType").value=="1"){  
  //ɾ����ѡ�еĿͻ�
    parent.fraInterface.InsuredGrid.delRadioTrueLine("InsuredGrid");
  }
  else {
    parent.fraInterface.InsuredGrid.clearData();
  }
  //alert(3);
  if (parent.fraInterface.fm.all("ContType").value=="2"&&parent.fraInterface.fm.all("RelationToMainInsured").value=="00"){
    parent.fraInterface.fm.all("ContNo").value="";
    parent.fraInterface.fm.all("ProposalContNo").value="";
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
    loggerDebug("ContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //ҳ����Ч��

  loggerDebug("ContInsuredSave","####################################");
  loggerDebug("ContInsuredSave","####################################");
  loggerDebug("ContInsuredSave","####################################");
  loggerDebug("ContInsuredSave","####################################");
  loggerDebug("ContInsuredSave","####################################");
  loggerDebug("ContInsuredSave","####################################");
%>                                       
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
	
</script>
</html>

