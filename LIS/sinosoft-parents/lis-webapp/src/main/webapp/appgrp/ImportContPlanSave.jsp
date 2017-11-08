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
<%@page import="com.sinosoft.lis.schema.LCContPlanDutyParamSchema"%>
<%@page import="com.sinosoft.lis.schema.LCContPlanSchema"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.vschema.LCContPlanDutyParamSet"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//接收信息，并作校验处理。
//输入参数

ImportContPlanBL tImportContPlanBL = new ImportContPlanBL();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "Fail";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("ImportContPlanSave","begin ...");

String tOperate=request.getParameter("mOperate");	//操作模式
String tGrpContNo = request.getParameter("GrpContNo");	//集体合同号码
String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");	//集体投保单号码
loggerDebug("ImportContPlanSave","wangxw@ tProposalGrpContNo"+tProposalGrpContNo);

  TransferData tTransferData = new TransferData(); 
  tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo")); 
  tTransferData.setNameAndValue("ProposalGrpContNo",request.getParameter("ProposalGrpContNo"));
  tTransferData.setNameAndValue("AskpriceNo",request.getParameter("AskpriceNo"));
  tTransferData.setNameAndValue("AskNo",request.getParameter("AskNo"));  
  //tTransferData.setNameAndValue("SupGrpNo",request.getParameter("SupGrpNo")); 
  //tTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo"));
  //tTransferData.setNameAndValue("AddressNo",request.getParameter("AddressNo"));


// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tTransferData);


try{
	loggerDebug("ImportContPlanSave","Import ContPlan data!!!");
	tImportContPlanBL.submitData(tVData,tOperate);
}
catch(Exception ex){
	Content = "导入失败，原因是:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tImportContPlanBL.mErrors;
	if (!tError.needDealError()){
		Content = " 导入成功! ";
		FlagStr = "Succ";
	}
	else{
		Content = " 导入失败，原因是:" + tError.getFirstError();
		FlagStr = "Fail";
	}
}
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
