
<%
	//程序名称：PersonPayPlanSave.jsp
	//程序功能：
	//创建日期：2002-07-24 08:38:44
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.get.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//接收信息，并作校验处理。
	//输入参数
	//输出参数
	CErrors tError = null;
	String tBmCert = "";

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String mSerialNo = "";
	String mCount = "";
	loggerDebug("PersonPayPlanSave","Start PersonPayPlan JSP Submit...");

	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI"); //添加页面控件的初始化。

	LCPolSchema tLCPolSchema = new LCPolSchema();
	tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
	tLCPolSchema.setContNo(request.getParameter("ContNo"));
	tLCPolSchema.setPolNo(request.getParameter("PolNo"));
	//tLCPolSchema.setAppntNo(request.getParameter("AppntNo"));
	tLCPolSchema.setInsuredNo(request.getParameter("InsuredNo"));

	LCGetSchema tLCGetSchema = new LCGetSchema();
	tLCGetSchema.setManageCom(request.getParameter("ManageCom"));
	tLCGetSchema.setContNo(request.getParameter("ContNo"));
	tLCGetSchema.setPolNo(request.getParameter("PolNo"));
	//tLCGetSchema.setAppntNo(request.getParameter("AppntNo"));
	tLCGetSchema.setInsuredNo(request.getParameter("InsuredNo"));

	TransferData aTransferData = new TransferData();
	//aTransferData.setNameAndValue("timeStart",request.getParameter("timeStart"));
	aTransferData.setNameAndValue("timeEnd", request
			.getParameter("timeEnd"));

	VData tVData = new VData();
	tVData.addElement(tGlobalInput);
	tVData.addElement(aTransferData);
	tVData.addElement(tLCPolSchema);
	tVData.addElement(tLCGetSchema);

	//PersonPayPlanUI tPersonPayPlanUI = new PersonPayPlanUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate(); 
	
	try {
		//tPersonPayPlanUI.submitData(tVData, "INSERT||PERSON");
		tBusinessDelegate.submitData(tVData,"INSERT||PERSON","PersonPayPlanUI");
	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		//tError = tPersonPayPlanUI.mErrors;
		tError = tBusinessDelegate.getCErrors(); 
		if (tError.needDealError()) {
			Content = "催付失败："+tError.getLastError();
			FlagStr = "Fail";
		}else{			
			Content = " 保存成功";
			FlagStr = "Succ";
			//if (tPersonPayPlanUI.getResult() != null && tPersonPayPlanUI.getResult().size() > 0) {
			//	mSerialNo = (String) tPersonPayPlanUI.getResult().get(0);
			//	mCount = (String) tPersonPayPlanUI.getResult().get(1);
			//	
			//	// add by jiaqiangli 2009-04-09 增加更加友好的提示信息 保全挂起不允许生存金的催付
			//	if (Integer.parseInt(mCount) > 0 ) 
			//	{
			//		loggerDebug("PersonPayPlanSave","mSerialNo:" + tPersonPayPlanUI.getResult().get(0));
			//		loggerDebug("PersonPayPlanSave","mCount:" + tPersonPayPlanUI.getResult().get(1));
			//		Content=Content+"，总共产生"+ tPersonPayPlanUI.getResult().get(1)+"条催付记录！";
			//	}
			
			if (tBusinessDelegate.getResult() != null && tBusinessDelegate.getResult().size() > 0) {
				mSerialNo = (String) tBusinessDelegate.getResult().get(0);
				mCount = (String) tBusinessDelegate.getResult().get(1);
				
				// add by jiaqiangli 2009-04-09 增加更加友好的提示信息 保全挂起不允许生存金的催付
				if (Integer.parseInt(mCount) > 0 ) 
				{
					loggerDebug("PersonPayPlanSave","mSerialNo:" + tBusinessDelegate.getResult().get(0));
					loggerDebug("PersonPayPlanSave","mCount:" + tBusinessDelegate.getResult().get(1));
					Content=Content+"，总共产生"+ tBusinessDelegate.getResult().get(1)+"条催付记录！";
				}
				else {
					Content = "催付失败：原因可能是1保全挂起不允许催付 2无可催付的生存金！";
					FlagStr = "Fail";
				}
			}
		}		
	}   
%>    
      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=mSerialNo%>","<%=mCount%>");
</script>
</html>

