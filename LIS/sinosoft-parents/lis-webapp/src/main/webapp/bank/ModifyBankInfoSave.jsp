<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：ModifyBankInfoSave.jsp
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
  <%@page import="com.sinosoft.lis.bank.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%
  loggerDebug("ModifyBankInfoSave","\n\n---ModifyBankInfoSave Start---");
  

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  String BankCode = request.getParameter("BankCode");
  String AccName = request.getParameter("AccName");
  String AccNo = request.getParameter("AccNo");
  String TempfeeNo =request.getParameter("TempfeeNo1");
  //String PrtNo2 = request.getParameter("PrtNo2");
  loggerDebug("ModifyBankInfoSave","BankCode:============================" + BankCode);
  loggerDebug("ModifyBankInfoSave","AccName:=============================" +AccName );
  loggerDebug("ModifyBankInfoSave","AccNo:===============================" +AccNo );
  loggerDebug("ModifyBankInfoSave","================="+TempfeeNo);
 TransferData mTransferData = new TransferData();
	// mTransferData.setNameAndValue("PrtNo2",PrtNo2);
	mTransferData.setNameAndValue("TempfeeNo",TempfeeNo);
	 mTransferData.setNameAndValue("BankCode",BankCode);
	 mTransferData.setNameAndValue("AccName",AccName);
	 mTransferData.setNameAndValue("AccNo",AccNo);
	 /*String Feetype = request.getParameter("FeeType");
   mTransferData.setNameAndValue("FeeType",Feetype);
	 if("1".equals(Feetype))
	 {
	 	  mTransferData.setNameAndValue("GetNoticeNo",request.getParameter("GetNoticeNo2"));
	    mTransferData.setNameAndValue("PayMode",request.getParameter("PayMode"));
	  }*/
  VData inVData = new VData();
  inVData.add(mTransferData);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  //ModifyBankInfoUI ModifyBankInfoUI1 = new ModifyBankInfoUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();


  if (!tBusinessDelegate.submitData(inVData, "INSERT","ModifyBankInfoUI")) {
    VData rVData = tBusinessDelegate.getResult();
    Content = " 处理失败，原因是:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " 处理成功! ";
  	FlagStr = "Succ";
  }

	loggerDebug("ModifyBankInfoSave",Content + "\n" + FlagStr + "\n---ModifyBankInfoSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
</script>
</html>
