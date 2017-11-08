
<%
//程序名称：PolModifySave.jsp
//程序功能：保单修改
//创建日期：2006-07-18 11:10:36
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //输出参数
  	CErrors tError = null;
  	String FlagStr = "Fail";
  	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}  
	//1-合同信息,不会做修改,不传到后台
	LCContSchema tLCContSchema=new LCContSchema();  
  	tLCContSchema.setContNo(request.getParameter("ContNo"));
  	tLCContSchema.setInsuredName(StrTool.unicodeToGBK(request.getParameter("Name")));  	
  	tLCContSchema.setInsuredIDType(StrTool.unicodeToGBK(request.getParameter("IDType")));
  	tLCContSchema.setInsuredIDNo(StrTool.unicodeToGBK(request.getParameter("IDNo")));
  	tLCContSchema.setAppntName(StrTool.unicodeToGBK(request.getParameter("AppntName")));
  	tLCContSchema.setAppntIDType(StrTool.unicodeToGBK(request.getParameter("AppntIDType")));
  	tLCContSchema.setAppntIDNo(StrTool.unicodeToGBK(request.getParameter("AppntIDNo")));
  	//目前只有一个银行帐号
  	tLCContSchema.setNewBankCode(request.getParameter("NewBankCode"));
	tLCContSchema.setNewAccName(StrTool.unicodeToGBK(request.getParameter("NewAccName")));
  	tLCContSchema.setNewBankAccNo(request.getParameter("NewBankAccNo"));
  	tLCContSchema.setPayLocation(request.getParameter("PayLocation"));
  	tLCContSchema.setBankCode(request.getParameter("BankCode"));
	tLCContSchema.setAccName(StrTool.unicodeToGBK(request.getParameter("AccName")));
  	tLCContSchema.setBankAccNo(request.getParameter("BankAccNo"));

	//2-投保人信息
	LCAppntSchema tLCAppntSchema=new LCAppntSchema();
	tLCAppntSchema.setContNo(request.getParameter("ContNo"));
  	tLCAppntSchema.setAppntName(StrTool.unicodeToGBK(request.getParameter("AppntName")));
  	tLCAppntSchema.setIDType(StrTool.unicodeToGBK(request.getParameter("AppntIDType")));
  	tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo")); 
 	//投保人地址信息需要单独保存
	LCAddressSchema tLCAppntAddressSchema = new LCAddressSchema();
	tLCAppntAddressSchema.setCustomerNo(request.getParameter("AppntNo"));
	tLCAppntAddressSchema.setAddressNo(request.getParameter("AppntAddressNo"));
	tLCAppntAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));
	tLCAppntAddressSchema.setZipCode(request.getParameter("AppntZipCode"));
	tLCAppntAddressSchema.setHomeAddress(request.getParameter("AppntHomeAddress"));
	tLCAppntAddressSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode"));
	tLCAppntAddressSchema.setMobile(request.getParameter("AppntPhone"));
	tLCAppntAddressSchema.setPhone(request.getParameter("AppntPhone2"));
	tLCAppntAddressSchema.setEMail(request.getParameter("AppntEMail"));
	tLCAppntAddressSchema.setGrpName(request.getParameter("AppntGrpName"));
	
	//3-被保人信息
	LCInsuredSchema tLCInsuredSchema=new LCInsuredSchema();
  	tLCInsuredSchema.setContNo(request.getParameter("ContNo"));
  	tLCInsuredSchema.setName(StrTool.unicodeToGBK(request.getParameter("Name")));
  	tLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
  	tLCInsuredSchema.setIDType(request.getParameter("IDType"));
  	tLCInsuredSchema.setIDNo(request.getParameter("IDNo"));
  	//被保人地址信息6.0已经迁移到地址表中,此处需要单独保存
  	LCAddressSchema tLCInsuredAddressSchema = new LCAddressSchema();
  	tLCInsuredAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
  	tLCInsuredAddressSchema.setAddressNo(request.getParameter("AddressNo"));
  	tLCInsuredAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
	tLCInsuredAddressSchema.setZipCode(request.getParameter("ZipCode"));
	tLCInsuredAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));
	tLCInsuredAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
	tLCInsuredAddressSchema.setMobile(request.getParameter("Phone"));
	tLCInsuredAddressSchema.setPhone(request.getParameter("Phone2"));
	tLCInsuredAddressSchema.setEMail(request.getParameter("EMail"));
	tLCInsuredAddressSchema.setGrpName(request.getParameter("GrpName"));  	
  	
	//4-受益人信息
	LCBnfSet tLCBnfSet=new LCBnfSet();
  	String tLCBnfGridNo[] = request.getParameterValues("LCBnfGridNo");  //得到序号列的所有值
    String tLCBnfGrid1 [] = request.getParameterValues("LCBnfGrid1"); //得到第1列的所有值
	String tLCBnfGrid2 [] = request.getParameterValues("LCBnfGrid2"); //得到第2列的所有值
	String tLCBnfGrid3 [] = request.getParameterValues("LCBnfGrid3"); //得到第3列的所有值
	String tLCBnfGrid4 [] = request.getParameterValues("LCBnfGrid4"); //得到第4列的所有值
	String tLCBnfGrid5 [] = request.getParameterValues("LCBnfGrid5"); //得到第5列的所有值
	String tLCBnfGrid6 [] = request.getParameterValues("LCBnfGrid6"); //得到第6列的所有值
	String tLCBnfGrid7 [] = request.getParameterValues("LCBnfGrid7"); //得到第7列的所有值
	String tLCBnfGrid8 [] = request.getParameterValues("LCBnfGrid8"); //得到第8列的所有值
	String tLCBnfGrid10 [] = request.getParameterValues("LCBnfGrid10"); //得到第10列的所有值
	String tLCBnfGrid11 [] = request.getParameterValues("LCBnfGrid11"); //得到第11列的所有值
	String tLCBnfGrid12 [] = request.getParameterValues("LCBnfGrid12"); //得到第12列的所有值
	int  BnfCount ;  //得到接受到的记录数
	if(tLCBnfGridNo==null)
		BnfCount=0;
	else
		BnfCount=tLCBnfGridNo.length;	
	for(int index=0;index< BnfCount;index++)
	{
		//如果是空增加一行而没有数据，跳过
		if(tLCBnfGrid1[index].equals("")||tLCBnfGrid7[index].equals(""))
			continue;
		LCBnfSchema tLCBnfSchema=new LCBnfSchema();
		tLCBnfSchema.setContNo(request.getParameter("ContNo"));
		tLCBnfSchema.setPolNo(request.getParameter("SelPolNo")); //主险保单号
		tLCBnfSchema.setInsuredNo(request.getParameter("InsuredNo"));
		tLCBnfSchema.setBnfType(tLCBnfGrid1[index]);
		tLCBnfSchema.setName(tLCBnfGrid2[index]);
		tLCBnfSchema.setIDType(tLCBnfGrid3[index]);
		tLCBnfSchema.setIDNo(tLCBnfGrid4[index]);
		tLCBnfSchema.setRelationToInsured(tLCBnfGrid5[index]);
		tLCBnfSchema.setBnfLot(tLCBnfGrid6[index]);
		tLCBnfSchema.setBnfGrade(tLCBnfGrid7[index]);
		tLCBnfSchema.setPostalAddress(tLCBnfGrid8[index]);
		tLCBnfSchema.setOperator(tLCBnfGrid10[index]);
		tLCBnfSchema.setMakeDate(tLCBnfGrid11[index]);
		tLCBnfSchema.setMakeTime(tLCBnfGrid12[index]);
		tLCBnfSet.add(tLCBnfSchema);
	}
		
		//5-修改原因
		LCEdorReasonSchema tLCEdorReasonSchema=new LCEdorReasonSchema();
		tLCEdorReasonSchema.setContNo(request.getParameter("ContNo"));	
		tLCEdorReasonSchema.setReason(StrTool.unicodeToGBK(request.getParameter("Reason")));
		
	/*
  	LCPolSchema tLCPolSchema=new LCPolSchema();  
  	tLCPolSchema.setPolNo(request.getParameter("PolNo"));
  	tLCPolSchema.setInsuredName(StrTool.unicodeToGBK(request.getParameter("Name")));  	
  	tLCPolSchema.setAppntName(StrTool.unicodeToGBK(request.getParameter("AppntName")));
  	tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));
  	tLCPolSchema.setBankCode(request.getParameter("BankCode"));
	tLCPolSchema.setAccName(StrTool.unicodeToGBK(request.getParameter("AccName")));
  	tLCPolSchema.setBankAccNo(request.getParameter("BankAccNo"));
  	tLCPolSchema.setCustomGetPolDate(request.getParameter("CustomGetPolDate"));

  	LCBankAuthSchema tLCBankAuthSchema=new LCBankAuthSchema();
  	tLCBankAuthSchema.setPolNo(request.getParameter("PolNo"));
  	tLCBankAuthSchema.setPayGetFlag("0");
  	tLCBankAuthSchema.setBankCode(request.getParameter("BankCode"));
  	tLCBankAuthSchema.setBankAccNo(request.getParameter("BankAccNo"));
  	tLCBankAuthSchema.setAccName(StrTool.unicodeToGBK(request.getParameter("AccName")));

  	LCInsuredSchema tLCInsuredSchema=new LCInsuredSchema();
  	tLCInsuredSchema.setPolNo(request.getParameter("PolNo"));
  	tLCInsuredSchema.setName(StrTool.unicodeToGBK(request.getParameter("Name")));
  	tLCInsuredSchema.setCustomerNo(request.getParameter("CustomerNo"));
  	tLCInsuredSchema.setIDType(request.getParameter("IDType"));
  	tLCInsuredSchema.setIDNo(request.getParameter("IDNo"));
  	tLCInsuredSchema.setNativePlace(StrTool.unicodeToGBK(request.getParameter("NativePlace")));
  	tLCInsuredSchema.setRgtAddress(StrTool.unicodeToGBK(request.getParameter("RgtAddress")));
  	tLCInsuredSchema.setPostalAddress(StrTool.unicodeToGBK(request.getParameter("PostalAddress")));
  	tLCInsuredSchema.setZipCode(request.getParameter("ZipCode"));
  	tLCInsuredSchema.setPhone(request.getParameter("Phone"));
  	tLCInsuredSchema.setMobile(request.getParameter("Mobile"));
  	tLCInsuredSchema.setEMail(request.getParameter("EMail"));
  	tLCInsuredSchema.setMobile(request.getParameter("Mobile"));
  	tLCInsuredSchema.setGrpName(StrTool.unicodeToGBK(request.getParameter("GrpName")));
  	tLCInsuredSchema.setWorkType(StrTool.unicodeToGBK(request.getParameter("WorkType")));
  	tLCInsuredSchema.setPluralityType(StrTool.unicodeToGBK(request.getParameter("PluralityType")));

  	LCAppntIndSchema tLCAppntIndSchema=new LCAppntIndSchema();
	tLCAppntIndSchema.setPolNo(request.getParameter("PolNo"));
  	tLCAppntIndSchema.setName(StrTool.unicodeToGBK(request.getParameter("AppntName")));
  	tLCAppntIndSchema.setCustomerNo(request.getParameter("AppntCustomerNo"));
  	tLCAppntIndSchema.setIDType(request.getParameter("AppntIDType"));
  	tLCAppntIndSchema.setIDNo(request.getParameter("AppntIDNo"));
  	tLCAppntIndSchema.setNativePlace(StrTool.unicodeToGBK(request.getParameter("AppntNativePlace")));
  	tLCAppntIndSchema.setRgtAddress(StrTool.unicodeToGBK(request.getParameter("AppntRgtAddress")));
  	tLCAppntIndSchema.setPostalAddress(StrTool.unicodeToGBK(request.getParameter("AppntPostalAddress")));
  	tLCAppntIndSchema.setZipCode(request.getParameter("AppntZipCode"));
  	tLCAppntIndSchema.setPhone(request.getParameter("AppntPhone"));
  	tLCAppntIndSchema.setMobile(request.getParameter("AppntMobile"));
  	tLCAppntIndSchema.setEMail(request.getParameter("AppntEMail"));
  	tLCAppntIndSchema.setMobile(request.getParameter("AppntMobile"));
  	tLCAppntIndSchema.setGrpName(StrTool.unicodeToGBK(request.getParameter("AppntGrpName")));
  	tLCAppntIndSchema.setWorkType(StrTool.unicodeToGBK(request.getParameter("AppntWorkType")));
  	tLCAppntIndSchema.setPluralityType(StrTool.unicodeToGBK(request.getParameter("AppntPluralityType")));
  	tLCAppntIndSchema.setRelationToInsured(request.getParameter("AppntRelationToInsured"));	
		
	LCBnfSet tLCBnfSet=new LCBnfSet();
  	String tLCBnfGridNo[] = request.getParameterValues("LCBnfGridNo");  //得到序号列的所有值
     	String tLCBnfGrid1 [] = request.getParameterValues("LCBnfGrid1"); //得到第1列的所有值
	String tLCBnfGrid2 [] = request.getParameterValues("LCBnfGrid2"); //得到第2列的所有值
	String tLCBnfGrid3 [] = request.getParameterValues("LCBnfGrid3"); //得到第3列的所有值
	String tLCBnfGrid4 [] = request.getParameterValues("LCBnfGrid4"); //得到第4列的所有值
	String tLCBnfGrid5 [] = request.getParameterValues("LCBnfGrid5"); //得到第5列的所有值
	String tLCBnfGrid6 [] = request.getParameterValues("LCBnfGrid6"); //得到第6列的所有值
	String tLCBnfGrid7 [] = request.getParameterValues("LCBnfGrid7"); //得到第7列的所有值
	String tLCBnfGrid8 [] = request.getParameterValues("LCBnfGrid8"); //得到第8列的所有值	
	int  BnfCount ;  //得到接受到的记录数
	if(tLCBnfGridNo==null)
		BnfCount=0;
	else
		BnfCount=tLCBnfGridNo.length;	
	for(int index=0;index< BnfCount;index++)
	{
		//如果是空增加一行而没有数据，跳过
		if(tLCBnfGrid1[index].equals("")||tLCBnfGrid7[index].equals(""))
			continue;
		LCBnfSchema tLCBnfSchema=new LCBnfSchema();
		tLCBnfSchema.setPolNo(request.getParameter("PolNo"));
		tLCBnfSchema.setBnfType(tLCBnfGrid1[index]);
		tLCBnfSchema.setName(tLCBnfGrid2[index]);
		tLCBnfSchema.setIDType(tLCBnfGrid3[index]);
		tLCBnfSchema.setIDNo(tLCBnfGrid4[index]);
		tLCBnfSchema.setRelationToInsured(tLCBnfGrid5[index]);
		tLCBnfSchema.setBnfLot(tLCBnfGrid6[index]);
		tLCBnfSchema.setBnfGrade(tLCBnfGrid7[index]);
		tLCBnfSchema.setAddress(tLCBnfGrid8[index]);		
		tLCBnfSet.add(tLCBnfSchema);
	}

  	LCCustomerImpartSet tLCCustomerImpartSet=new LCCustomerImpartSet();
  	String tImpartGridNo[] = request.getParameterValues("ImpartGridNo");  //得到序号列的所有值
     	String tImpartGrid1 [] = request.getParameterValues("ImpartGrid1"); //得到第1列的所有值
	String tImpartGrid2 [] = request.getParameterValues("ImpartGrid2"); //得到第2列的所有值
	String tImpartGrid3 [] = request.getParameterValues("ImpartGrid3"); //得到第3列的所有值
	String tImpartGrid4 [] = request.getParameterValues("ImpartGrid4"); //得到第4列的所有值
	String tImpartGrid5 [] = request.getParameterValues("ImpartGrid5"); //得到第5列的所有值
	int  ImpartCount ;
	if(tImpartGridNo==null)
		ImpartCount=0;
	else
		ImpartCount = tImpartGridNo.length; //得到接受到的记录数
	for(int index=0;index< ImpartCount;index++)
	{
		//如果是空增加一行而没有数据，跳过
		if(tImpartGrid1[index].equals("")||tImpartGrid2[index].equals("")||tImpartGrid5[index].equals(""))
			continue;
		LCCustomerImpartSchema tLCCustomerImpartSchema=new LCCustomerImpartSchema();
		tLCCustomerImpartSchema.setPolNo(request.getParameter("PolNo"));
		tLCCustomerImpartSchema.setImpartVer(tImpartGrid1[index]);
		tLCCustomerImpartSchema.setImpartCode(tImpartGrid2[index]);
		tLCCustomerImpartSchema.setImpartContent(tImpartGrid4[index]);
		tLCCustomerImpartSchema.setCustomerNoType(tImpartGrid5[index]);
		if(tImpartGrid5[index].equals("I"))
			tLCCustomerImpartSchema.setCustomerNo(request.getParameter("CustomerNo"));
		else
			tLCCustomerImpartSchema.setCustomerNo(request.getParameter("AppntCustomerNo"));
			
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);	
	}
	
	LCEdorReasonSchema tLCEdorReasonSchema=new LCEdorReasonSchema();
	tLCEdorReasonSchema.setContNo(request.getParameter("ContNo"));	
	tLCEdorReasonSchema.setReason(StrTool.unicodeToGBK(request.getParameter("Reason")));
	*/
	// 准备传输数据 VData
	VData tVData = new VData();
	tVData.add(0,tLCContSchema );
	tVData.add(1,tLCInsuredSchema);
	tVData.add(2,tLCAppntSchema);
	//银行授权书-6.0系统中暂时没有记录.
	//tVData.add( tLCBankAuthSchema);
	tVData.add(3,tLCInsuredAddressSchema);
	tVData.add(4,tLCAppntAddressSchema);
	tVData.add(5,tLCBnfSet);
	tVData.add(6,tLCEdorReasonSchema);
	tVData.add(7,tG );

	// 数据传输
	PolModifyUI tPolModifyUI   = new PolModifyUI();	
	loggerDebug("PolModifySave","before PolModifyUI");
	if (tPolModifyUI.submitData(tVData,"PolModify") == false)
	{
		int n = tPolModifyUI.mErrors.getErrorCount();
		//for (int i = 0; i < n; i++)
		Content = " 保单修改失败，原因是: " + tPolModifyUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "Fail")
	{
	    tError = tPolModifyUI.mErrors;
		if (!tError.needDealError())
		{
			Content = " 保单修改成功! ";
		    	FlagStr = "Succ";
		}
		else                                                                           
		{
			Content = " 保单修改失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
