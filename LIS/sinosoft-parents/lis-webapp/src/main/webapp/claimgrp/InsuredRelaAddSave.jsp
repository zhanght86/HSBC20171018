<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GrpFillInsuredSave.jsp
//程序功能：
//创建日期：2006-04-13 14:49:52
//创建人  ：Chenrong程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  //接收信息，并作校验处理。
  //输入参数
  LLAccountSchema tLLAccountSchema=new LLAccountSchema();
  LCContSchema tLCContSchema = new LCContSchema();
  LDPersonSchema tLDPersonSchema   = new LDPersonSchema();
  LCInsuredDB tOLDLCInsuredDB=new LCInsuredDB();
  LCAddressSchema tLCAddressSchema = new LCAddressSchema();
  LCInsuredSchema tmLCInsuredSchema =new LCInsuredSchema();
  LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
  LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
  LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
  TransferData tTransferData = new TransferData(); 
  //InsuredRelaAddUI tInsuredRelaAddUI  = new InsuredRelaAddUI();
  String busiName="grpInsuredRelaAddUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  //输出参数
  String FlagStr = "";
  String Content = "";
   
  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
  loggerDebug("InsuredRelaAddSave","tGI"+tGI);
  if(tGI==null)
  {
    loggerDebug("InsuredRelaAddSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  else //页面有效
  {
    CErrors tError = null;
    String tBmCert = "";
    loggerDebug("InsuredRelaAddSave","aaaa");
    //后面要执行的动作：添加，修改，删除
    String fmAction=request.getParameter("fmAction");
    loggerDebug("InsuredRelaAddSave","fmAction:"+fmAction); 

       
    tLCContSchema.setGrpContNo(request.getParameter("BPNo"));  
    tLCContSchema.setContNo(request.getParameter("ContNo")); 
    tLCContSchema.setPrtNo(request.getParameter("PrtNo"));
    tLCContSchema.setManageCom(request.getParameter("ManageCom"));
  
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
    loggerDebug("InsuredRelaAddSave","GrpContNo=="+request.getParameter("BPNo"));
    loggerDebug("InsuredRelaAddSave","ContNo=="+request.getParameter("ContNo"));
    loggerDebug("InsuredRelaAddSave","ProposalContNo=="+request.getParameter("ProposalContNo"));
    loggerDebug("InsuredRelaAddSave","PrtNo=="+request.getParameter("PrtNo"));
    loggerDebug("InsuredRelaAddSave","ManageCom=="+request.getParameter("ManageCom"));
    loggerDebug("InsuredRelaAddSave","RelationToMainInsuredCode=="+request.getParameter("RelationToMainInsured"));
    loggerDebug("InsuredRelaAddSave","RelationToAppntCode=="+request.getParameter("RelationToAppnt"));
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
  
  
    tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured2"));
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
    
    tLCInsuredRelatedSchema.setMainCustomerNo(request.getParameter("MainInsuredNo"));
    tLCInsuredRelatedSchema.setBirthday(request.getParameter("Birthday"));
    tLCInsuredRelatedSchema.setName(request.getParameter("Name"));
    tLCInsuredRelatedSchema.setSex(request.getParameter("Sex"));
    tLCInsuredRelatedSchema.setIDType(request.getParameter("IDType"));
    tLCInsuredRelatedSchema.setIDNo(request.getParameter("IDNo"));
    tLCInsuredRelatedSchema.setPolNo(request.getParameter("PolNo"));
    tLCInsuredRelatedSchema.setRelationToInsured(request.getParameter("RelationToMainInsured2"));
    
    
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
    loggerDebug("InsuredRelaAddSave","InsuredNo=="+request.getParameter("InsuredNo"));
    loggerDebug("InsuredRelaAddSave","RelationToMainInsured=="+request.getParameter("RelationToMainInsuredCode"));
    loggerDebug("InsuredRelaAddSave","RelationToAppnt=="+request.getParameter("RelationToAppntCode"));
    loggerDebug("InsuredRelaAddSave","OldContNo=="+request.getParameter("OldContNo"));
    loggerDebug("InsuredRelaAddSave","LicenseType=="+request.getParameter("LicenseType"));
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
    loggerDebug("InsuredRelaAddSave","################LCCont#######################");
          
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
    
    //标记为无名单补名单；记录原来ContNo值！
    tLDPersonSchema.setPluralityType(request.getParameter("OldContNo"));
    
    tLDPersonSchema.setDeathDate(request.getParameter("DeathDate"));
    tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
    tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlag"));
    tLDPersonSchema.setProterty(request.getParameter("Proterty"));
    tLDPersonSchema.setRemark(request.getParameter("Remark"));
    tLDPersonSchema.setState(request.getParameter("State"));
    tLDPersonSchema.setLicenseType(request.getParameter("LicenseType"));
    
    loggerDebug("InsuredRelaAddSave","##########11LCAddressSchema###########");
    loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
    loggerDebug("InsuredRelaAddSave","InsuredAddressNo=="+request.getParameter("InsuredAddressNo"));
    loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
    loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
 	
 	
 	
 	/*tLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
    if(request.getParameter("InsuredAddressNo")!=null && !request.getParameter("InsuredAddressNo").equals("")){
        loggerDebug("InsuredRelaAddSave","abc");
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
    tLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));*/
  
 	loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
 	loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
 	//loggerDebug("InsuredRelaAddSave","getAddressNo()=="+tLCAddressSchema.getAddressNo());
 	loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
 	loggerDebug("InsuredRelaAddSave","##########LCAddressSchema###########");
 	
 	/*tLLAccountSchema.setGrpContNo(request.getParameter("GrpContNo"));
 	tLLAccountSchema.setInsuredNo(request.getParameter("InsuredNo"));
 	tLLAccountSchema.setName(request.getParameter("Name"));
 	tLLAccountSchema.setBankCode(request.getParameter("BankCode"));
 	tLLAccountSchema.setAccount(request.getParameter("BankAccNo"));*/
  
    String SavePolType="";
    String BQFlag=request.getParameter("BQFlag");
  
    if(BQFlag==null) 
    SavePolType="0";
    else if(BQFlag.equals("")) 
    SavePolType="0";
    else  
    SavePolType=BQFlag;   
          
    loggerDebug("InsuredRelaAddSave","ContType"+request.getParameter("ContType"));
    loggerDebug("InsuredRelaAddSave","fmAction=="+fmAction);
    loggerDebug("InsuredRelaAddSave","Begin->告知");
    
    /*String tImpartNum[] = request.getParameterValues("ImpartInsuredGridNo");
	String tImpartVer[] = request.getParameterValues("ImpartInsuredGrid1");            //告知版别
	String tImpartCode[] = request.getParameterValues("ImpartInsuredGrid2");           //告知编码
	String tImpartContent[] = request.getParameterValues("ImpartInsuredGrid3");        //告知内容
	String tImpartParamModle[] = request.getParameterValues("ImpartInsuredGrid4");        //填写内容
	String tIncome = "";
	 tIncome = request.getParameter("Income"); 
	String tIncomeWay = "";
	 tIncomeWay = request.getParameter("IncomeWay");  
	String tImpartParaModle1=tIncome+","+ tIncomeWay; 
	
	loggerDebug("InsuredRelaAddSave","Begin->tIncome=" + tIncome);
	loggerDebug("InsuredRelaAddSave","Begin->tIncomeWay=" + tIncomeWay);
	
	if((tIncome == null || tIncome.equals("")) && (tIncomeWay == null || tIncomeWay.equals("")))
	{
	    //loggerDebug("InsuredRelaAddSave","b");	   
	}
	else
	{
	    //loggerDebug("InsuredRelaAddSave","aaaaab");
	    LCCustomerImpartSchema ttLCCustomerImpartSchema = new LCCustomerImpartSchema(); 
		ttLCCustomerImpartSchema.setCustomerNoType("1");
		ttLCCustomerImpartSchema.setImpartCode("001");
		ttLCCustomerImpartSchema.setImpartContent("您每年固定收入    万元  主要收入来源： （序号）被选项：①工薪②个体③私营④房屋出租⑤证券投资⑥银行利息⑦其他");
		ttLCCustomerImpartSchema.setImpartParamModle(tImpartParaModle1);
		ttLCCustomerImpartSchema.setImpartVer("01");
		ttLCCustomerImpartSchema.setPatchNo("0");
		tLCCustomerImpartSet.add(ttLCCustomerImpartSchema);
	}

	//loggerDebug("InsuredRelaAddSave","Begin->告知2");

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
	
    loggerDebug("InsuredRelaAddSave","end set schema 告知信息..."+ImpartCount);
    String tImpartDetailNum[] = request.getParameterValues("ImpartDetailGridNo");
	String tImpartDetailVer[] = request.getParameterValues("ImpartDetailGrid1");            //告知版别
	String tImpartDetailCode[] = request.getParameterValues("ImpartDetailGrid2");           //告知编码
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
	*/
    //loggerDebug("InsuredRelaAddSave","end set schema 告知明细信息..."+ImpartDetailCount); 
  
    tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全  
    tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo")); 
    tTransferData.setNameAndValue("FamilyType",request.getParameter("FamilyType")); //家庭单标记 
    tTransferData.setNameAndValue("ContType",request.getParameter("ContType")); //团单，个人单标记
    tTransferData.setNameAndValue("PolTypeFlag",request.getParameter("PolTypeFlag")); //无名单标记
    tTransferData.setNameAndValue("InsuredPeoples",request.getParameter("InsuredPeoples")); //被保险人人数
    tTransferData.setNameAndValue("InsuredAppAge",request.getParameter("InsuredAppAge")); //被保险人年龄
    tTransferData.setNameAndValue("SequenceNo",request.getParameter("SequenceNo")); //内部客户号
    tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo")); //内部客户号
    tTransferData.setNameAndValue("MainInsuredNo",request.getParameter("MainInsuredNo")); //内部客户号
    tTransferData.setNameAndValue("ContNo2",request.getParameter("OldContNo")); //内部客户号
    tTransferData.setNameAndValue("RiskSortValue",request.getParameter("RiskSortValue")); //是否校验连带被保险人人数标志
              
  try
  {
    // 准备传输数据 VData
    loggerDebug("InsuredRelaAddSave","tLDPersonSchema2"+tLDPersonSchema);
    VData tVData = new VData();
    tVData.add(tLCContSchema);
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLCCustomerImpartDetailSet);             
    tVData.add(tmLCInsuredSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);
    tVData.add(tLLAccountSchema);
    tVData.add(tLCInsuredRelatedSchema);
    
    loggerDebug("InsuredRelaAddSave","sadf12312"+tOLDLCInsuredDB.getInsuredNo());
    
    tVData.add(tTransferData);
    tVData.add(tGI);
    
     
    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
   // if (tInsuredRelaAddUI.submitData(tVData,"INSERT||CONTINSURED"))
//    {
//  	  if (fmAction.equals("INSERT||CONTINSURED"))
//	    {
//  		  
//	    }
//	  }
if(!tBusinessDelegate.submitData(tVData,"INSERT||CONTINSURED",busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "处理失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "处理失败";
		FlagStr = "Fail";				
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
    //tError = tInsuredRelaAddUI.mErrors;
    tError =  tBusinessDelegate.getCErrors();
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
    loggerDebug("InsuredRelaAddSave","FlagStr:"+FlagStr+"Content:"+Content);
  
  } //页面有效区
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


