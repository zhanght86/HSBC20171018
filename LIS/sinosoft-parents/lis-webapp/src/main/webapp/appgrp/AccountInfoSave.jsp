<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpFeeSave.jsp
//程序功能：
//创建日期：2002-08-16 15:12:33
//创建人 ：CrtHtml程序创建
//更新记录： 更新人  更新日期   更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCGrpAddressSchema"%>
<%@page import="com.sinosoft.lis.schema.LDGrpSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//接收信息，并作校验处理。
//输入参数
LCAccountInfoUI tLCAccountInfoUI = new LCAccountInfoUI();

LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
LDGrpSchema tLDGrpSchema   = new LDGrpSchema();                //团体客户

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("AccountInfoSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String tGrpContNo = request.getParameter("GrpContNo");	//集体合同号码
loggerDebug("AccountInfoSave","wangxw@ tGrpContNo"+tGrpContNo);
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//集体投保单号码

//String tCValiDate=request.getParameter("CValiDate");
  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); 
  tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
  tTransferData.setNameAndValue("SupGrpNo",request.getParameter("SupGrpNo")); 
  tTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo"));
  tTransferData.setNameAndValue("AddressNo",request.getParameter("AddressNo"));

//子公司信息

	//团体客户信息  LDGrp
	tLDGrpSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLDGrpSchema.setGrpName(request.getParameter("GrpName"));             //单位名称
	tLDGrpSchema.setGrpNature(request.getParameter("GrpNature"));         //单位性质
	tLDGrpSchema.setBusinessType(request.getParameter("BusinessType"));   //行业类别
	tLDGrpSchema.setPeoples(request.getParameter("Peoples"));             //总人数
	tLDGrpSchema.setAsset(request.getParameter("Asset"));                 //资产总额	
	tLDGrpSchema.setCorporation(request.getParameter("Corporation"));     //法人	
	tLDGrpSchema.setFax(request.getParameter("Fax"));             //传真	
	//tLDGrpSchema.setOnWorkPeoples(request.getParameter("AppntOnWorkPeoples"));             //总人数
	//tLDGrpSchema.setOffWorkPeoples(request.getParameter("AppntOffWorkPeoples"));             //总人数
	//tLDGrpSchema.setOtherPeoples(request.getParameter("AppntOtherPeoples"));             //总人数	    	    	    
	//tLDGrpSchema.setRgtMoney(request.getParameter("RgtMoney"));           //注册资本
	//tLDGrpSchema.setNetProfitRate(request.getParameter("NetProfitRate")); //净资产收益率
	//tLDGrpSchema.setMainBussiness(request.getParameter("MainBussiness")); //主营业务
	//tLDGrpSchema.setComAera(request.getParameter("ComAera"));             //机构分布区域
	//tLDGrpSchema.setPhone(request.getParameter("Phone"));             //总机
	//tLDGrpSchema.setFoundDate(request.getParameter("FoundDate"));             //成立时间
	
	
	//团体客户地址  LCGrpAddress	    
	//tLCGrpAddressSchema.setCustomerNo(request.getParameter("GrpNo"));            //客户号码
	tLCGrpAddressSchema.setAddressNo(request.getParameter("GrpAddressNo"));      //地址号码
	tLCGrpAddressSchema.setGrpAddress(request.getParameter("GrpAddress"));       //单位地址
	loggerDebug("AccountInfoSave","*******************"+request.getParameter("GrpAddress"));
	tLCGrpAddressSchema.setGrpZipCode(request.getParameter("GrpZipCode"));       //单位邮编
	//保险联系人一
	tLCGrpAddressSchema.setLinkMan1(request.getParameter("LinkMan1"));
	tLCGrpAddressSchema.setDepartment1(request.getParameter("Department1"));
	tLCGrpAddressSchema.setPhone1(request.getParameter("Phone1"));
	tLCGrpAddressSchema.setE_Mail1(request.getParameter("E_Mail1"));
	//tLCGrpAddressSchema.setHeadShip1(request.getParameter("HeadShip1"));
	//tLCGrpAddressSchema.setFax1(request.getParameter("Fax1"));
	//保险联系人二
	tLCGrpAddressSchema.setLinkMan2(request.getParameter("LinkMan2"));
	tLCGrpAddressSchema.setDepartment2(request.getParameter("Department2"));
	tLCGrpAddressSchema.setPhone2(request.getParameter("Phone2"));
	tLCGrpAddressSchema.setE_Mail2(request.getParameter("E_Mail2"));
	//tLCGrpAddressSchema.setHeadShip2(request.getParameter("HeadShip2"));	
	//tLCGrpAddressSchema.setFax2(request.getParameter("Fax2"));



// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.add(tTransferData);
tVData.addElement(tLDGrpSchema);
tVData.addElement(tLCGrpAddressSchema);


try{
	loggerDebug("AccountInfoSave","this will deal the data!!!");
	tLCAccountInfoUI.submitData(tVData,tOperate);
	loggerDebug("AccountInfoSave","deal Accountinfo completely!!!");	
}
catch(Exception ex){
	Content = "保存失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tLCAccountInfoUI.mErrors;
	if (!tError.needDealError()){
		Content = " 保存成功! ";
		FlagStr = "Succ";
	}
	else{
		Content = " 保存失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}
	loggerDebug("AccountInfoSave","wangxw@ Content"+ Content);	
}
%>
 
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script> 
