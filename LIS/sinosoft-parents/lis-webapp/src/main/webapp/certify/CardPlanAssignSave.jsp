
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
	loggerDebug("CardPlanAssignSave","开始执行Save页面");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();

	LZCardPlanSchema mLZCardPlanSchema = new LZCardPlanSchema();
	mLZCardPlanSchema.setAppCount(request.getParameter("sumBalance")); //暂时借用此字段用来记录分配后结余
	loggerDebug("CardPlanAssignSave","savejsp,分配前库存量："+request.getParameter("sumBalance"));
	
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanAssignSave","操作的类型是 " + mOperateType);

	if (mOperateType.equals("UPDATE||ASSIGN")) {
		String tChk[] = request
		.getParameterValues("InpCardPlanQueryGridChk");
		String tPlanID[] = request
		.getParameterValues("CardPlanQueryGrid1");
		String tAssignCount[] = request
		.getParameterValues("CardPlanQueryGrid5");

		String tRetOperator = request.getParameter("RetOperator");
		String tRetCom = request.getParameter("managecom");

		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();
		tLZCardPlanSchema.setPlanID(tPlanID[i]);
		tLZCardPlanSchema.setAssignCount(tAssignCount[i]);

		mLZCardPlanSet.add(tLZCardPlanSchema);
			}
		}
	}

	String mDescType = ""; //将操作标志的英文转换成汉字的形式
	if (mOperateType.equals("UPDATE||ASSIGN")) {
		mDescType = "库存分配";
	}

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(mOperateType);
		tVData.addElement(mLZCardPlanSchema);
		tVData.addElement(mLZCardPlanSet);

	/*	mCardPlanUI.submitData(tVData, mOperateType);
	} catch (Exception ex) {
		Content = mDescType + "失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = mCardPlanUI.mErrors;
		if (!tError.needDealError()) {
			Content = mDescType + "成功!";
			FlagStr = "Succ";
		} else {
			Content = mDescType + "失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/
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
	
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
