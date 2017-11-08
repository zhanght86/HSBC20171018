<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
	VData tVData = new VData();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	
	LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
	LBPOContSchema tLBPOContSchema = new LBPOContSchema();
	TransferData tTransferData = new TransferData();
	//09-06-17 增加如果是银代或简易投保单则不在此处保存银行代码、银行账户名、银行账号
	String tPrtNo = request.getParameter("PrtNo");
	String tSubPrtNo = tPrtNo.substring(0,4);
	String tBankFlag = "0";
	if((!"8611".equals(tSubPrtNo))&&(!"8651".equals(tSubPrtNo))&&(!"8621".equals(tSubPrtNo))&&(!"8661".equals(tSubPrtNo))){
		tBankFlag = "1";
	}
	String tReleaseFlag = ""; //附加豁免险标记
	
	tLBPOContSchema.setContNo(request.getParameter("PrtNo"));
	tLBPOContSchema.setProposalContNo(request.getParameter("PrtNo"));
	tLBPOContSchema.setPrtNo(request.getParameter("PrtNo"));
	tLBPOContSchema.setGrpContNo("00000000000000000000");
	tLBPOContSchema.setInputNo(request.getParameter("InputNo"));
	tLBPOContSchema.setFillNo(request.getParameter("contFillNo"));
	
	tLBPOContSchema.setReleaseFlag(request.getParameter("ReleaseFlag"));
	tLBPOContSchema.setAmnt(request.getParameter("Amnt"));
	tLBPOContSchema.setPrem(request.getParameter("Prem"));
	tLBPOContSchema.setPassword(request.getParameter("Password"));
	tLBPOContSchema.setSumPrem(request.getParameter("SumPrem"));
	tLBPOContSchema.setPayIntv(request.getParameter("PayIntv"));
	tLBPOContSchema.setOutPayFlag(request.getParameter("OutPayFlag"));
	tLBPOContSchema.setPayMode(request.getParameter("PayMode"));
	tLBPOContSchema.setPayLocation(request.getParameter("PayLocation"));
	if(!"1".equals(tBankFlag)){
		tLBPOContSchema.setBankCode(request.getParameter("BankCode"));
		tLBPOContSchema.setAccName(request.getParameter("AccName"));
		tLBPOContSchema.setBankAccNo(request.getParameter("BankAccNo"));
	}
	tLBPOContSchema.setAutoPayFlag(request.getParameter("AutoPayFlag"));
	tLBPOContSchema.setRnewFlag(request.getParameter("RnewFlag"));
	tLBPOContSchema.setApproveCode(request.getParameter("ApproveCode"));
	tLBPOContSchema.setApproveTime(request.getParameter("ApproveTime"));
	tLBPOContSchema.setOperator(tG.Operator);
	
	String tSequenceNo = request.getParameter("SequenceNo3");
	String tFamilyType = request.getParameter("FamilyType");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tRiskSequence1 = request.getParameter("RiskSequence1");
	String tRiskSequence2 = request.getParameter("RiskSequence2");
	String tRiskSequence3 = request.getParameter("RiskSequence3");
	String tBonusGetMode1 = request.getParameter("BonusGetMode1");
	String tBonusGetMode2 = request.getParameter("BonusGetMode2");
	String tBonusGetMode3 = request.getParameter("BonusGetMode3");
	String tContType = request.getParameter("ContType");
	loggerDebug("DSGetSave","投保单类型为："+tContType);
	
	tLBPOPolSchema.setContNo(request.getParameter("PrtNo"));
	tLBPOPolSchema.setInputNo(request.getParameter("InputNo"));
	tLBPOPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));
	tLBPOPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));
	tLBPOPolSchema.setGetYear(request.getParameter("GetYear"));
	tLBPOPolSchema.setGetYearFlag(request.getParameter("GetYearFlag"));
	tLBPOPolSchema.setGetLimit(request.getParameter("GetLimit"));
	tLBPOPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
	
	tTransferData.setNameAndValue("SequenceNo",tSequenceNo);
	tTransferData.setNameAndValue("FamilyType",tFamilyType);
	tTransferData.setNameAndValue("InsuredNo",tInsuredNo);
	tTransferData.setNameAndValue("RiskSequence1",tRiskSequence1);
	tTransferData.setNameAndValue("RiskSequence2",tRiskSequence2);
	tTransferData.setNameAndValue("RiskSequence3",tRiskSequence3);
	tTransferData.setNameAndValue("BonusGetMode1",tBonusGetMode1);
	tTransferData.setNameAndValue("BonusGetMode2",tBonusGetMode2);
	tTransferData.setNameAndValue("BonusGetMode3",tBonusGetMode3);
	tTransferData.setNameAndValue("ContType",tContType);
	tTransferData.setNameAndValue("BankFlag",tBankFlag);
	
	
	tVData.addElement(tLBPOContSchema);
	tVData.addElement(tLBPOPolSchema);
	tVData.addElement(tTransferData);
	tVData.addElement(tG);
	//DSGetUI tDSGetUI = new DSGetUI();
	String busiName="tbDSGetUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )                       
	{                                                                               
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		loggerDebug("DSGetSave","Content:" + Content);
	}
	else
	{
		Content = " 操作成功! ";
		FlagStr = "Succ";

		tVData.clear();
		tVData = tBusinessDelegate.getResult();
		loggerDebug("DSGetSave","Content:" + Content);
%>
   <%} %> 	
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>

</html>
