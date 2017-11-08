
<%
	//程序名称：LLReportInput.jsp
	//程序功能：
	//创建日期：2002-07-19 16:49:22
	//创建人  ：CrtHtml程序创建
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	loggerDebug("CardPlanReturnSave","开始执行Save页面");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();
	LZCardPlanSchema mLZCardPlanSchema = new LZCardPlanSchema();
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanReturnSave","操作的类型是 " + mOperateType);

	if (mOperateType.equals("UPDATE||RETURN")) {
		String tChk[] = request
		.getParameterValues("InpCardPlanDetailGridChk");
		String tPlanID[] = request
		.getParameterValues("CardPlanDetailGrid1");
		String tRetCount[] = request
		.getParameterValues("CardPlanDetailGrid9");

		String tRetOperator = request.getParameter("RetOperator");
		String tRetCom = request.getParameter("managecom");

		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();
		tLZCardPlanSchema.setPlanID(tPlanID[i]);
		tLZCardPlanSchema.setRetCount(tRetCount[i]);
		tLZCardPlanSchema.setRetOperator(tRetOperator);
		tLZCardPlanSchema.setRetCom(tRetCom);
		mLZCardPlanSet.add(tLZCardPlanSchema);
			}
		}
	}

	String mDescType = ""; //将操作标志的英文转换成汉字的形式
	if (mOperateType.equals("UPDATE||RETURN")) {
		mDescType = "批复";
	}

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(mOperateType);
		tVData.addElement(mLZCardPlanSet);
		tVData.addElement(mLZCardPlanSchema);

		//mCardPlanUI.submitData(tVData, mOperateType);
	String busiName="CardPlanUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,mOperateType,busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"失败";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"成功! ";
	      	FlagStr = "Succ";  
	  }	
	/*} catch (Exception ex) {
		Content = mDescType + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = mCardPlanUI.mErrors;
		if (!tError.needDealError()) {
			Content = mDescType + " 成功!";
			FlagStr = "Succ";
		} else {
			Content = mDescType + " 失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
