<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：DirectContSave.jsp
//程序功能：直销险种录入合同及投保人信息保存
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//输出参数
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	//输入参数
	VData tVData = new VData();
	LCContSchema tLCContSchema   = new LCContSchema();
	LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	LCAddressSchema tLCAddressSchema = new LCAddressSchema();
	LCAccountSchema tLCAccountSchema = new LCAccountSchema();
	LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
    
	tAction = request.getParameter( "fmAction" );
    loggerDebug("DirectContSave","动作是:"+tAction);
	if( tAction.equals( "DELETE" ))
	{
		tLCContSchema.setContNo(request.getParameter("ContNo"));
		tLCContSchema.setManageCom(request.getParameter("ManageCom"));	    
	}
	else
	{  
		//首先 调用锁表类，并向[系统保单操作轨迹表]写入信息
		LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
		tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
		tLDSysTraceSchema.setCreatePos("承保录单");
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
		//合同信息
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
		//如果话务员代码不存在，则LCCont表的业务员字段存外务员信息，否则存话务员信息
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
		
		//录入首期账号
	    tLCContSchema.setNewPayMode(request.getParameter("NewPayMode"));
	    tLCContSchema.setNewBankCode(request.getParameter("NewBankCode"));
	    tLCContSchema.setNewBankAccNo(request.getParameter("NewBankAccNo"));
	    tLCContSchema.setNewAccName(request.getParameter("NewAccName"));		
	    //录入续期账号
	    tLCContSchema.setPayLocation(request.getParameter("SecPayMode"));
	    tLCContSchema.setBankCode(request.getParameter("SecBankCode"));
	    tLCContSchema.setBankAccNo(request.getParameter("SecBankAccNo"));
	    tLCContSchema.setAccName(request.getParameter("SecAccName"));
		
		//投保人信息(包括基本信息、职业信息、地址信息等)
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
			
		//投保人地址信息
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
		
		//多业务员信息（主要是佣金的分配信息）
		//如果界面上录入了外务员，那么则存储外务员信息。
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
		//如果界面上录入了话务员，那么则存储话务员信息。
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
		//以下准备投保人告知信息
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
	
	// 准备传输数据 VData                                                           
	tVData.add( tLCContSchema );  
	tVData.add( tLCAddressSchema );   
	tVData.add( tLCAppntSchema );   
	tVData.add( tLCAccountSchema );    
//	tVData.add(tLCCustomerImpartSet);       
	tVData.add(tLACommisionDetailSet);       
	tVData.add( tG );   
	
	//传递非SCHEMA信息                                                              
	TransferData tTransferData = new TransferData();                                  
	String SavePolType="";                                                            
	String BQFlag=request.getParameter("BQFlag");                                     
	if(BQFlag==null) SavePolType="0";                                                 
	else if(BQFlag.equals("")) SavePolType="0";                                       
	else  SavePolType=BQFlag;                                                       
                                                          
	tTransferData.setNameAndValue("SavePolType",SavePolType); //保全保存标记，默认为0，标识非保全
	tTransferData.setNameAndValue("GrpNo",request.getParameter("AppntGrpNo"));
	tTransferData.setNameAndValue("GrpName",request.getParameter("AppntGrpName"));     
	loggerDebug("DirectContSave","SavePolType，BQ is 2，other is 0 : " + request.getParameter("BQFlag"));
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
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " 操作成功! ";
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
