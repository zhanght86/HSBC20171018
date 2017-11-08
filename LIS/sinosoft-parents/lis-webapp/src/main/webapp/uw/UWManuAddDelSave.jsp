<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：
//程序功能：
//创建日期：2005-10-22 11:10:36
//创 建 人: guanwei
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.service.*" %>
  
<%//定义三个变量，分别得到上一页面的值
  GlobalInput tGlobalInput = new GlobalInput();
  tGlobalInput = (GlobalInput)session.getValue("GI");
 // ./UWManuAddDelSave.jsp?DelPolNo="+tPolNo+"&DelDutyCode="+tDutyCode+"&DelPayPlanCode="+tPayPlanCode;
  String tPolNo=request.getParameter("DelPolNo"); //投保单号
  loggerDebug("UWManuAddDelSave","===tPolNo======"+tPolNo) ;
  String tDutyCode=request.getParameter("DelDutyCode"); //加费类型
   loggerDebug("UWManuAddDelSave","===tDutyCode======"+tDutyCode);
  String tPayPlanCode=request.getParameter("DelPayPlanCode"); //加费原因
 loggerDebug("UWManuAddDelSave","===tPayPlanType======"+tPayPlanCode);

	//将三个值变量的值装在TransferData中
	TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("PolNo",tPolNo);
  loggerDebug("UWManuAddDelSave","::::::::::::::polno::::::::::::"+tPolNo);
  tTransferData.setNameAndValue("DutyCode",tDutyCode);
   loggerDebug("UWManuAddDelSave","::::::::::::::tDutyCode::::::::::::"+tDutyCode);
  tTransferData.setNameAndValue("PayPlanCode",tPayPlanCode);
   loggerDebug("UWManuAddDelSave","::::::::::::::tPayPlanType::::::::::::"+tPayPlanCode);


	//将TransferData 装在VData
  VData tVData = new VData();
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
    tVData.addElement(tTransferData);
    tVData.addElement(tGlobalInput);


  //创建对象
  String Content = "";
  String FlagStr = "";
  loggerDebug("UWManuAddDelSave","CCCCCCCCCCCCCCCCCCCCCCCCCcc");
  //UWManuAddDelUI tUWManuAddDelUI = new UWManuAddDelUI();
  
  String busiName="cbcheckUWManuAddDelUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  if (!tBusinessDelegate.submitData(tVData, "",busiName)) {
    //VData rVData = tUWManuAddDelUI.mErrors;

    Content = " 处理失败，原因是:" +tBusinessDelegate.getCErrors().getFirstError().toString() ;
 	  FlagStr = "Fail";
  }
  else {
    Content = "^_^ 已成功删除该条记录 ^_^ ";
  	FlagStr = "Succ";


	loggerDebug("UWManuAddDelSave",Content + "\n" + FlagStr + "\n---GetReturnFromBankSave End---\n\n");
%>
<html>
<script language = "JavaScript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	//parent.fraInterface.initQuery();
</script>
</html>
<%  }%>
