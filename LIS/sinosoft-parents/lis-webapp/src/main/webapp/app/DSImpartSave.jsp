<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
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
	
	LBPOContSchema tLBPOContSchema = new LBPOContSchema();
	LBPOCustomerImpartSet tLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
	TransferData tTransferData = new TransferData();     
	
	tOperate=request.getParameter("fmAction");
	loggerDebug("DSImpartSave","动作是："+tOperate);
	String InputNo=request.getParameter("InputNo");
	String tempInputTime="";
	tempInputTime=request.getParameter("InputTime");
	loggerDebug("DSImpartSave","tempInputTime"+tempInputTime);
	if(tempInputTime.equals("0")||tempInputTime.equals("")||tempInputTime==null){
		tempInputTime="0";
	}
	int tInputTime = 0;
	tInputTime = Integer.parseInt(tempInputTime);
	tInputTime = tInputTime+1;
	InputNo = String.valueOf(tInputTime);
	loggerDebug("DSImpartSave","InputNo: "+InputNo);
	//09-06-17 增加如果是银代或简易投保单则在此处保存银行代码、银行账户名、银行账号
	String tPrtNo = request.getParameter("PrtNo");
	String tSubPrtNo = tPrtNo.substring(0,4);
	String tBankFlag = "0";
	if((!"8611".equals(tSubPrtNo))&&(!"8651".equals(tSubPrtNo))&&(!"8621".equals(tSubPrtNo))&&(!"8661".equals(tSubPrtNo))){
		tBankFlag = "1";
	}
	//String[]的起名方式统一为与数据库中的字段对应
	//tongmeng 2009-04-02 modify
	//对于个人和中介投保单.界面的告知是先被保人再投保人.
	String tContNo = request.getParameter("PrtNo");
	String tImpartNum[] = request.getParameterValues("ImpartGridNo");
	String tImpartCode[] = request.getParameterValues("ImpartGrid1");
	String tImpartVer[] = request.getParameterValues("ImpartGrid2");
	String tImpartContent[] = request.getParameterValues("ImpartGrid3");
	String tImpartParamModle[] = request.getParameterValues("ImpartGrid4");
	String tPrtFlag[] = null;
	String tInsured1[] = null;
	if(tContNo!=null&&(tContNo.substring(0,4).equals("8611")||tContNo.substring(0,4).equals("8621")))
	{
		tPrtFlag = request.getParameterValues("ImpartGrid6"); //用该字段记录是否为投保人
		tInsured1 = request.getParameterValues("ImpartGrid5");
	}
	else
	{
		tPrtFlag = request.getParameterValues("ImpartGrid5"); //用该字段记录是否为投保人
		tInsured1 = request.getParameterValues("ImpartGrid6");
	}
	
	//String tPrtFlag[] = request.getParameterValues("ImpartGrid5"); //用该字段记录是否为投保人
	//String tInsured1[] = request.getParameterValues("ImpartGrid6");
	String tInsured2[] = request.getParameterValues("ImpartGrid7");
	String tInsured3[] = request.getParameterValues("ImpartGrid8");
	String tImpartFillNo[] = request.getParameterValues("ImpartGrid9");
	//客户告知信息
	
	
	int ImpartCount=0;
	ImpartCount = tImpartNum.length;
	loggerDebug("DSImpartSave",ImpartCount);
	for(int i=0;i<ImpartCount;i++){
		loggerDebug("DSImpartSave",i);
		LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
		tLBPOCustomerImpartSchema.setContNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setGrpContNo("00000000000000000000");
		tLBPOCustomerImpartSchema.setInputNo(InputNo);
		tLBPOCustomerImpartSchema.setProposalContNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setPrtNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setImpartCode(tImpartCode[i]);
		tLBPOCustomerImpartSchema.setImpartVer(tImpartVer[i]);
		tLBPOCustomerImpartSchema.setImpartContent(tImpartContent[i]);
		tLBPOCustomerImpartSchema.setImpartParamModle(tImpartParamModle[i]);
		tLBPOCustomerImpartSchema.setCustomerNo("000000");
		tLBPOCustomerImpartSchema.setCustomerNoType("0");
		tLBPOCustomerImpartSchema.setPrtFlag(tPrtFlag[i]);
		tLBPOCustomerImpartSchema.setInsured1(tInsured1[i]);
		tLBPOCustomerImpartSchema.setInsured2(tInsured2[i]);
		tLBPOCustomerImpartSchema.setinsured3(tInsured3[i]);
		tLBPOCustomerImpartSchema.setPatchNo("0");
		tLBPOCustomerImpartSchema.setFillNo(tImpartFillNo[i]);
		
		tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
	}
	
	tLBPOContSchema.setContNo(request.getParameter("PrtNo"));
	tLBPOContSchema.setInputNo(InputNo);
	if("1".equals(tBankFlag)){
		tLBPOContSchema.setAccName(request.getParameter("AccName"));
		tLBPOContSchema.setBankCode(request.getParameter("BankCode"));
		tLBPOContSchema.setBankAccNo(request.getParameter("BankAccNo"));
	}
	tLBPOContSchema.setAppSignName(request.getParameter("AppSignName"));
	tLBPOContSchema.setInsSignName2(request.getParameter("InsSignName2"));
	tLBPOContSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
	tLBPOContSchema.setManageCom(request.getParameter("ManageCom"));
	tLBPOContSchema.setAgentCode(request.getParameter("AgentCode"));
	tLBPOContSchema.setHandler(request.getParameter("Handler"));
	tLBPOContSchema.setSaleChnl(request.getParameter("SaleChnl"));
	tLBPOContSchema.setAgentComName(request.getParameter("AgentComName"));
	tLBPOContSchema.setAgentCom(request.getParameter("AgentCom"));
	tLBPOContSchema.setSignAgentName(request.getParameter("SignAgentName"));
	tLBPOContSchema.setAgentSignDate(request.getParameter("AgentSignDate"));
	tLBPOContSchema.setFirstTrialOperator(request.getParameter("FirstTrialOperator"));
	tLBPOContSchema.setFirstTrialDate(request.getParameter("FirstTrialDate"));
	tLBPOContSchema.setUWOperator(request.getParameter("UWOperator"));
	tLBPOContSchema.setUWDate(request.getParameter("UWDate"));
	tLBPOContSchema.setFillNo(request.getParameter("contFillNo"));
	tLBPOContSchema.setImpartRemark(request.getParameter("ImpartRemark"));
	tLBPOContSchema.setLang(request.getParameter("Lang")); //简易投保单时用该字段记录客户经理
	tLBPOContSchema.setCurrency(request.getParameter("Currency")); //简易投保单时用该字段记录客户经理工号
	
	//业务员告知信息，与客户告知信息统一放入LBPOCustomerImpartSchema中统一提交String[]名称统一与数据库字段名相同
	LBPOCustomerImpartSet ttLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
	String tAgentImpartNum[] = request.getParameterValues("AgentImpartGridNo");
	String ttImpartCode[] = request.getParameterValues("AgentImpartGrid1");
	String ttImpartVer[] = request.getParameterValues("AgentImpartGrid2");
	String ttImpartContent[] = request.getParameterValues("AgentImpartGrid3");
	String ttImpartParamModle[] = request.getParameterValues("AgentImpartGrid4");
	String ttPrtFlag[] = request.getParameterValues("AgentImpartGrid5"); //用该字段记录是否为投保人
	String ttInsured1[] = request.getParameterValues("AgentImpartGrid6");
	String ttInsured2[] = request.getParameterValues("AgentImpartGrid7");
	String ttInsured3[] = request.getParameterValues("AgentImpartGrid8");
	String ttImpartFillNo[] = request.getParameterValues("AgentImpartGrid9");
	int AgentImpartCount=0;
	if(tAgentImpartNum!=null){
	   AgentImpartCount = tAgentImpartNum.length;
	}else{
		AgentImpartCount=0;
	}
	loggerDebug("DSImpartSave",AgentImpartCount);
	for(int j=0;j<AgentImpartCount;j++){
		loggerDebug("DSImpartSave","ttImpartFillNo[j]:"+ttImpartFillNo[j]);
		LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
		tLBPOCustomerImpartSchema.setContNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setGrpContNo("00000000000000000000");
		tLBPOCustomerImpartSchema.setInputNo(InputNo);
		tLBPOCustomerImpartSchema.setProposalContNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setPrtNo(request.getParameter("PrtNo"));
		tLBPOCustomerImpartSchema.setImpartCode(ttImpartCode[j]);
		tLBPOCustomerImpartSchema.setImpartVer(ttImpartVer[j]);
		tLBPOCustomerImpartSchema.setImpartContent(ttImpartContent[j]);
		tLBPOCustomerImpartSchema.setImpartParamModle(ttImpartParamModle[j]);
		tLBPOCustomerImpartSchema.setCustomerNo("000000");
		tLBPOCustomerImpartSchema.setCustomerNoType("A");
		tLBPOCustomerImpartSchema.setPrtFlag(ttPrtFlag[j]);
		tLBPOCustomerImpartSchema.setInsured1(ttInsured1[j]);
		tLBPOCustomerImpartSchema.setInsured2(ttInsured2[j]);
		tLBPOCustomerImpartSchema.setinsured3(ttInsured3[j]);
		tLBPOCustomerImpartSchema.setPatchNo("0");
		tLBPOCustomerImpartSchema.setFillNo(ttImpartFillNo[j]);
		ttLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
	}
	VData ttVData = new VData();
	ttVData.addElement(ttLBPOCustomerImpartSet);
	//被保人投保年龄
	String tInsuredAppAge = request.getParameter("InsuredAppAge");
	tTransferData.setNameAndValue("InsuredAppAge",tInsuredAppAge);
	tTransferData.setNameAndValue("BankFlag",tBankFlag);
	
	tVData.addElement(tLBPOCustomerImpartSet);
	tVData.addElement(tLBPOContSchema);
	tVData.addElement(tTransferData);
	tVData.addElement(ttVData);
	tVData.addElement(tG);
	
	//DSImpartUI tDSImpartUI = new DSImpartUI();
	String busiName="tbDSImpartUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if( tBusinessDelegate.submitData( tVData, tOperate,busiName ) == false )                       
	{                                                                               
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
		loggerDebug("DSImpartSave","Content:" + Content);
	}
	else
	{
		Content = " 操作成功! ";
		FlagStr = "Succ";
		loggerDebug("DSImpartSave","Content:" + Content);
		tVData.clear();
		tVData = tBusinessDelegate.getResult();
} %> 	
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit4("<%=FlagStr%>","<%=Content%>");
</script>

</html>
