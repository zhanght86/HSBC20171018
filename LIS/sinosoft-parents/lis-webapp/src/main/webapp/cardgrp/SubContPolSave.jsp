<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%	         
	
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码
	String tCustomerNoRet = "";

	VData tVData = new VData();
	LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //团体客户
	LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); //团体客户地址 
	LCGeneralSchema tLCGeneralSchema = new LCGeneralSchema();      //团体分单表
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

	tAction = request.getParameter( "fmAction" );

	//团体客户信息  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //单位名称
	loggerDebug("SubContPolSave","GrpName:" + request.getParameter("GrpName"));
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //单位性质
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //行业类别
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //总人数
	tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //注册资本
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //资产总额
	tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //净资产收益率
	tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //主营业务
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //法人
	tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //机构分布区域
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //机构分布区域
	tLDGrpSchema.setPhone(request.getParameter("Phone"));             //机构分布区域
	tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //机构分布区域
	tLDGrpSchema.setGetFlag(request.getParameter("GetFlag"));             //机构分布区域
	tLDGrpSchema.setBankCode(request.getParameter("BankCode"));             //机构分布区域
	tLDGrpSchema.setBankAccNo(request.getParameter("BankAccNo"));             //机构分布区域
	tLDGrpSchema.setRemark(request.getParameter("Remark"));             //机构分布区域
	//团体客户地址  LCGrpAddress	    
	tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //地址号码
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //单位地址
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //单位邮编
	//保险联系人一
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//保险联系人二
	//tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	//tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	//tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));
	//tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	//tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	//tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));
			
	tLCGeneralSchema.setGrpContNo(request.getParameter("GrpContNo"));
	tLCGeneralSchema.setPrtNo(request.getParameter("PrtNo"));
	tLCGeneralSchema.setExecuteCom(request.getParameter("ExecuteCom"));
	tLCGeneralSchema.setCustomerNo(request.getParameter("CustomerNo"));
	tLCGeneralSchema.setAddressNo(request.getParameter("AddressNo"));
	loggerDebug("SubContPolSave","end setSchema:");
	// 准备传输数据 VData
	tVData.add( tLDGrpSchema );
	tVData.add( tLCGrpAddressSchema );
	tVData.add( tLCGeneralSchema );
	tVData.add( tG );

	if( tAction.equals( "INSERT" )) tOperate = "INSERT";
	if( tAction.equals( "UPDATE" )) tOperate = "UPDATE";
	if( tAction.equals( "DELETE" )) tOperate = "DELETE";
	GroupSubContUI tGroupSubContUI = new GroupSubContUI();
	if( tGroupSubContUI.submitData( tVData, tOperate ) == false )
	{
		Content = " 保存失败，原因是: " + tGroupSubContUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " 保存成功! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tGroupSubContUI.getResult();
		TransferData tTransferData = (TransferData) tVData.getObjectByObjectName("TransferData", 0);
		if (tTransferData != null)
		{
			tCustomerNoRet = (String) tTransferData.getValueByName("CustomerNo");
		}

		// 显示
%>
<%		
	}
        loggerDebug("SubContPolSave","Content:"+Content);	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=tCustomerNoRet%>");
</script>
</html>
