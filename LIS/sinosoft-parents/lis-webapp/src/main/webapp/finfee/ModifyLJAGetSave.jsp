<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ModifyLJAGetSave.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.finfee.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  loggerDebug("ModifyLJAGetSave","\n\n---ModifyLJAGetSave Start---");

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  //zy 2009-04-09 16:45 优化付费方式变更
  String mPaymode = request.getParameter("PayMode");
  String mBankCode="";
  String mBankAccNo="";
  String mAccName="";
  String mIDtype="";
  String mIDno="";
  if("2".equals(mPaymode))
  {
  	mBankCode=request.getParameter("BankCode2");
  	mBankAccNo=request.getParameter("ChequeNo2");
  
  }
  if("3".equals(mPaymode))
  {
    mBankCode=request.getParameter("BankCode3");
  	mBankAccNo=request.getParameter("ChequeNo3");
  }
  if("4".equals(mPaymode))
  {
    mBankCode=request.getParameter("BankCode4");
  	mBankAccNo=request.getParameter("BankAccNo4");
  	mAccName=request.getParameter("AccName4");
  	mIDtype=request.getParameter("IDType");
  	mIDno=request.getParameter("IDNo");
  }
  loggerDebug("ModifyLJAGetSave","mBankAccNo---" +mBankAccNo);
  LJAGetSchema tLJAGetSchema = new LJAGetSchema();
  tLJAGetSchema.setActuGetNo(request.getParameter("ActuGetNo2"));
  tLJAGetSchema.setPayMode(request.getParameter("PayMode"));
  if(!("".equals(mBankCode) || mBankCode==null))
  {
  	tLJAGetSchema.setBankCode(mBankCode);
  }
  if(!("".equals(mBankAccNo) || mBankAccNo==null))
  {
  	tLJAGetSchema.setBankAccNo(mBankAccNo);
  }
  if(!("".equals(mAccName) || mAccName==null))
  {
  	tLJAGetSchema.setAccName(mAccName);
  }
  if(!("".equals(mIDtype) || mIDtype==null))
  {
  	tLJAGetSchema.setDrawerType(mIDtype);
  }
    if(!("".equals(mIDno) || mIDno==null))
  {
  	tLJAGetSchema.setDrawerID(mIDno);
  }
  LJAGetSet inLJAGetSet = new LJAGetSet();
  inLJAGetSet.add(tLJAGetSchema);

  VData inVData = new VData();
  inVData.add(inLJAGetSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //ModifyLJAGetUI ModifyLJAGetUI1 = new ModifyLJAGetUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  if (!tBusinessDelegate.submitData(inVData, "INSERT","ModifyLJAGetUI")) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("ModifyLJAGetSave",Content + "\n" + FlagStr + "\n---ModifyLJAGetSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	//parent.fraInterface.easyQueryClick();
</script>
</html>
