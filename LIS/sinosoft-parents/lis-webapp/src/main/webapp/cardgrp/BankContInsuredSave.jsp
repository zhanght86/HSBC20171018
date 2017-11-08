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
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.service.*" %>
<%
  //接收信息，并作校验处理。
  //输入参数
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
 // ContInsuredUI tContInsuredUI   = new ContInsuredUI();
  //输出参数
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("BankContInsuredSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("BankContInsuredSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("BankContInsuredSave","aaaa");
    //后面要执行的动作：添加，修改，删除
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
  //tLCContSchema.setPolType(request.getParameter("PolTypeFlag")); //无名单标记
  //tLCContSchema.setPeoples(request.getParameter("InsuredAppAge")); //被保险人年龄
  
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
  tLCAddressSchema.setCompanyPhone(request.getParameter("GrpPhone"));
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
  
  
  tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全  
  tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
  tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //家庭单标记 
  tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //团单，个人单标记
  tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //无名单标记
  tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //被保险人人数
  tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //被保险人年龄
  tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //内部客户号
          
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
      loggerDebug("BankContInsuredSave","**************4");
      tOLDLCInsuredDB.setContNo(request.getParameter("ContNo"));
      tOLDLCInsuredDB.setGrpContNo(request.getParameter("GrpContNo"));
      loggerDebug("BankContInsuredSave","tInsuredNo"+tInsuredNo[0]);
      tOLDLCInsuredDB.setInsuredNo(tInsuredNo[0]);
    }
  }
  
  String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //填写内容
	String tIncome = request.getParameter("Income"); 
	String tIncomeWay = request.getParameter("IncomeWay");  
	String tImpartParaModle1=tIncome+","+ tIncomeWay;  
	//loggerDebug("BankContInsuredSave","#######bbbbb==="+request.getParameterValues("ImpartInsuredGridNo").length);	
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
	
  loggerDebug("BankContInsuredSave","end set schema 告知信息..."+ImpartCount);
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
	
  loggerDebug("BankContInsuredSave","end set schema 告知明细信息..."+ImpartDetailCount);             
  try
  {
    // 准备传输数据 VData
    loggerDebug("BankContInsuredSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);
    loggerDebug("BankContInsuredSave","sadf12312"+tOLDLCInsuredDB.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
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
  //删除被选中的客户
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
    loggerDebug("BankContInsuredSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //页面有效区

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


