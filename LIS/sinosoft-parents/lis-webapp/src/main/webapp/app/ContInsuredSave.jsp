<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ContInsuredSave.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>

<%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
//  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCInsuredSchema tOLCLCInsuredSchema   = new LCInsuredSchema();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  
  /*由于下面调用的ContInsuredBL类在2008-08-21上午10：57分提交了第124版程序，在GetInputData中要获取LLAccountSchema，但是Save没有提供，导致爆出null的错误，这里补充上
   *2008-08-25 zhangzheng 
   */
   LLAccountSchema mLLAccountSchema=new LLAccountSchema();
  
  TransferData tTransferData = new TransferData(); 
  //ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  String busiName="tbContInsuredUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("ContInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("ContInsuredSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("ContInsuredSave","aaaa");
    //后面要执行的动作：添加，修改，删除
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
  //tLCContSchema.setPolType(request.getParameter("PolTypeFlag")); //无名单标记
  //tLCContSchema.setPeoples(request.getParameter("InsuredAppAge")); //被保险人年龄
  
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
  tmLCInsuredSchema.setRgtAddress(request.getParameter("InsuredPlace"));
  tmLCInsuredSchema.setIDExpDate(request.getParameter("IDPeriodOfValidity"));
  tmLCInsuredSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"));
  tmLCInsuredSchema.setLanguage(request.getParameter("InsuredLanguage"));
  
  


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
  tLDPersonSchema.setName(request.getParameter("InsuredLastName")+request.getParameter("InsuredFirstName"));//request.getParameter("Name"));
  tLDPersonSchema.setSex(request.getParameter("Sex"));      
  tLDPersonSchema.setBirthday(request.getParameter("Birthday"));
  String IDType = request.getParameter("IDType");
  String IDNo = request.getParameter("IDNo");
  if(IDType.equals("")){
	  IDType=null;
  }
  if(IDNo.equals("")){
	  IDNo=null;
  }
  tLDPersonSchema.setIDType(IDType);
  tLDPersonSchema.setIDNo(IDNo);
  tLDPersonSchema.setPassword(request.getParameter("Password"));
  tLDPersonSchema.setNativePlace(request.getParameter("NativePlace"));
  tLDPersonSchema.setNationality(request.getParameter("Nationality"));
  tLDPersonSchema.setRgtAddress(request.getParameter("InsuredPlace"));
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
  //tLDPersonSchema.setRemark(request.getParameter("Remark"));
  tLDPersonSchema.setState(request.getParameter("State"));
  tLDPersonSchema.setLicenseType(request.getParameter("LicenseType"));
  tLDPersonSchema.setIDExpDate(request.getParameter("IDPeriodOfValidity"));
  tLDPersonSchema.setSocialInsuFlag(request.getParameter("SocialInsuFlag"));
  tLDPersonSchema.setLanguage(request.getParameter("InsuredLanguage"));
  //增加姓、名、英文姓名、拼音姓名
  tLDPersonSchema.setLastName(request.getParameter("InsuredLastName"));
  tLDPersonSchema.setFirstName(request.getParameter("InsuredFirstName"));
  tLDPersonSchema.setLastNameEn(request.getParameter("InsuredLastNameEn"));
  tLDPersonSchema.setFirstNameEn(request.getParameter("InsuredFirstNameEn"));
  tLDPersonSchema.setNameEn(request.getParameter("InsuredFirstNameEn")+request.getParameter("InsuredLastNameEn"));
  tLDPersonSchema.setLastNamePY(request.getParameter("InsuredLastNamePY"));
  tLDPersonSchema.setFirstNamePY(request.getParameter("InsuredFirstNamePY"));
 	
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
  tLCAddressSchema.setPhone(request.getParameter("Phone"));
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
  
  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全  
  tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
  tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //家庭单标记 
  tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //团单，个人单标记
  tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //无名单标记
  tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //被保险人人数
  tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //被保险人年龄
  tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //内部客户号
          
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
        loggerDebug("ContInsuredSave","**************3"+tRadio[index]);
      
        if(tRadio[index].equals("1"))
        {
        	tOLCLCInsuredSchema.setContNo(request.getParameter("ContNo"));
        	tOLCLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
        	tOLCLCInsuredSchema.setInsuredNo(tInsuredNo[index]);
          loggerDebug("ContInsuredSave",tInsuredNo[index]);
        }
        if (tOLCLCInsuredSchema.getInsuredNo()==null)
        {
        	tOLCLCInsuredSchema.setContNo(request.getParameter("ContNo"));
        	tOLCLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
        	tOLCLCInsuredSchema.setInsuredNo(tInsuredNo[0]);
          loggerDebug("ContInsuredSave",tInsuredNo[0]);
        }
      }
    }
    else
    {
      loggerDebug("ContInsuredSave","**************4");
      tOLCLCInsuredSchema.setContNo(request.getParameter("ContNo"));
      tOLCLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
      loggerDebug("ContInsuredSave","tInsuredNo"+tInsuredNo[0]);
      tOLCLCInsuredSchema.setInsuredNo(tInsuredNo[0]);
    }
  }
  
  String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //填写内容
	/*
	String tIncome = request.getParameter("Income"); 
	String tIncomeWay = request.getParameter("IncomeWay");  
	String tImpartParaModle1=tIncome+","+ tIncomeWay;  
	//loggerDebug("ContInsuredSave","#######bbbbb==="+request.getParameterValues("ImpartInsuredGridNo").length);	
	
	  //add by zhangxing
	  String tStature = request.getParameter("Stature");
	  String tAvoirdupois = request.getParameter("Avoirdupois");
	  String tImpartParaModle2 = tStature+","+tAvoirdupois;
	  loggerDebug("ContInsuredSave","tStature=="+tStature);
	  loggerDebug("ContInsuredSave","tAvoirdupois=="+tAvoirdupois);
	if(!tIncome.equals("")&&!tIncomeWay.equals(""))
	{
		LCCustomerImpartSchema ttLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		ttLCCustomerImpartSchema.setCustomerNoType("1");
		ttLCCustomerImpartSchema.setImpartCode("001");
		ttLCCustomerImpartSchema.setImpartContent("您每年固定收入    万元  主要收入来源： （序号）被选项：①工薪②个体③私营④房屋出租⑤证券投资⑥银行利息⑦其他");
		ttLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle1);
		ttLCCustomerImpartSchema.setImpartVer("01");
		ttLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(ttLCCustomerImpartSchema);
	}
	
	 if(!tStature.equals("")&&!tAvoirdupois.equals(""))
	  {
	    LCCustomerImpartSchema aLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		  
		  aLCCustomerImpartSchema.setCustomerNoType("1");
		  aLCCustomerImpartSchema.setImpartCode("000");
		  aLCCustomerImpartSchema.setImpartContent("身高________cm（厘米）  体重________Kg （公斤）");
		  aLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle2);
		  aLCCustomerImpartSchema.setImpartVer("02");		 
		  aLCCustomerImpartSchema.setPatchNo("0");
		  tLCCustomerImpartSet.add(aLCCustomerImpartSchema);
	  }
	 */
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
	
  loggerDebug("ContInsuredSave","end set schema 告知信息..."+ImpartCount);
  String tImpartDetailNum[] = request.getParameterValues("ImpartDetailGridNo");
	String tImpartDetailVer[] = request.getParameterValues("ImpartDetailGrid1");            //告知版别
	String tImpartDetailCode[] = request.getParameterValues("ImpartDetailGrid2");           //告知编码
	String tImpartDetailCurrCondition[] = request.getParameterValues("ImpartDetailGrid3");  
	
/*********************************
	String tImpartDetailContent[] = request.getParameterValues("ImpartDetailGrid3");        //告知内容
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
	
  loggerDebug("ContInsuredSave","end set schema 告知明细信息..."+ImpartDetailCount);             
  try
  {
    // 准备传输数据 VData
    loggerDebug("ContInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLCLCInsuredSchema);
    tVData.add(mLLAccountSchema); // add by zhangzheng 2008-08-25
    loggerDebug("ContInsuredSave","sadf12312"+tOLCLCInsuredSchema.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
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
  parent.fraInterface.document.all("InsuredNo").value="<%=strCustomerNo%>";
	parent.fraInterface.InsuredGrid.addOne("InsuredGrid");    	            	 
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
   
   parent.fraInterface.document.all("InsuredNo").value="<%=strCustomerNo%>";
   parent.fraInterface.document.all("InsuredAddressNo").value="<%=strAddressNo%>"; 
   //alert(parent.fraInterface.document.all("FamilyType").value);
   //if (parent.fraInterface.document.all("FamilyType").value=="1"){  
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,1,"<%=mLCInsuredSchema.getInsuredNo()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,2,"<%=mLCInsuredSchema.getName()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,3,"<%=mLCInsuredSchema.getSex()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,4,"<%=mLCInsuredSchema.getBirthday()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,5,"<%=mLCInsuredSchema.getRelationToMainInsured()%>");
     parent.fraInterface.InsuredGrid.setRowColData(parent.fraInterface.InsuredGrid.getSelNo()-1,6,"<%=mLCInsuredSchema.getSequenceNo()%>");                               
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
  //删除被选中的客户
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
    Content = "操作失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {                          
      Content ="操作成功！";
    	FlagStr = "Succ";
    }
    else                                                                           
    {
    	Content = "操作失败，原因是:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
    loggerDebug("ContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //页面有效区

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


