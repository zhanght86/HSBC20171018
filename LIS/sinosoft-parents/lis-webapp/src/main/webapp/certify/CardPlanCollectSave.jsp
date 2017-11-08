
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
	loggerDebug("CardPlanCollectSave","【印刷计划汇总】开始执行Save页面");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPrintUI mCardPrintUI = new CardPrintUI();
	LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();
	LZCardPrintSchema mLZCardPrintSchema = null;

	String tPlanType = request.getParameter("PlanType");
	String tInputMan = request.getParameter("InputMan");

	if ("6".equals(tPlanType)) {
		String tChk[] = request
		.getParameterValues("InpCardPlanQueryGrid2Chk");
		String tCertifyCode[] = request
		.getParameterValues("CardPlanQueryGrid21");
		String tcertifyPrice[] = request
		.getParameterValues("CardPlanQueryGrid23");
		String tparentNum[] = request
		.getParameterValues("CardPlanQueryGrid25");
		String tsumCount[] = request
		.getParameterValues("CardPlanQueryGrid26");
		String tManageCom[] = request
		.getParameterValues("CardPlanQueryGrid28");
		String tStartNo[] = request
		.getParameterValues("CardPlanQueryGrid29");
		String tEndNo[] = request
		.getParameterValues("CardPlanQueryGrid210");
		loggerDebug("CardPlanCollectSave","tChk.length=" + tChk.length);
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		mLZCardPrintSchema = new LZCardPrintSchema();
		mLZCardPrintSchema.setPlanType(tPlanType);
		mLZCardPrintSchema.setInputMan(tInputMan);
		mLZCardPrintSchema.setCertifyCode(tCertifyCode[i]);
		mLZCardPrintSchema.setCertifyPrice(tcertifyPrice[i]);
		mLZCardPrintSchema.setParentNum(tparentNum[i]);
		mLZCardPrintSchema.setSumCount(tsumCount[i]);
		mLZCardPrintSchema.setManageCom(tManageCom[i]);
		mLZCardPrintSchema.setStartNo(tStartNo[i]);
		mLZCardPrintSchema.setEndNo(tEndNo[i]);

		loggerDebug("CardPlanCollectSave","tChk[i]:i=" + i);
		loggerDebug("CardPlanCollectSave","tPlanType=" + tPlanType);
		loggerDebug("CardPlanCollectSave","tInputMan=" + tInputMan);
		loggerDebug("CardPlanCollectSave","tCertifyCode=" + tCertifyCode[i]);
		loggerDebug("CardPlanCollectSave","tcertifyPrice=" + tcertifyPrice[i]);
		loggerDebug("CardPlanCollectSave","tparentNum=" + tparentNum[i]);
		loggerDebug("CardPlanCollectSave","tsumCount=" + tsumCount[i]);
		loggerDebug("CardPlanCollectSave","tManageCom=" + tManageCom[i]);
		loggerDebug("CardPlanCollectSave","tStartNo=" + tStartNo[i]);
		loggerDebug("CardPlanCollectSave","tEndNo=" + tEndNo[i]);

		mLZCardPrintSet.add(mLZCardPrintSchema);
			}
		}
	} else {
		String tChk[] = request
		.getParameterValues("InpCardPlanQueryGridChk");
		String tCertifyCode[] = request
		.getParameterValues("CardPlanQueryGrid1");
		String tcertifyPrice[] = request
		.getParameterValues("CardPlanQueryGrid3");
		String tparentNum[] = request
		.getParameterValues("CardPlanQueryGrid5");
		String tsumCount[] = request
		.getParameterValues("CardPlanQueryGrid6");
		loggerDebug("CardPlanCollectSave","tChk.length=" + tChk.length);
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		mLZCardPrintSchema = new LZCardPrintSchema();
		mLZCardPrintSchema.setPlanType(tPlanType);
		mLZCardPrintSchema.setInputMan(tInputMan);
		mLZCardPrintSchema.setCertifyCode(tCertifyCode[i]);
		mLZCardPrintSchema.setCertifyPrice(tcertifyPrice[i]);
		mLZCardPrintSchema.setParentNum(tparentNum[i]);
		mLZCardPrintSchema.setSumCount(tsumCount[i]);

		loggerDebug("CardPlanCollectSave","tChk[i]:i=" + i);
		loggerDebug("CardPlanCollectSave","tPlanType=" + tPlanType);
		loggerDebug("CardPlanCollectSave","tInputMan=" + tInputMan);
		loggerDebug("CardPlanCollectSave","tCertifyCode=" + tCertifyCode[i]);
		loggerDebug("CardPlanCollectSave","tcertifyPrice=" + tcertifyPrice[i]);
		loggerDebug("CardPlanCollectSave","tparentNum=" + tparentNum[i]);
		loggerDebug("CardPlanCollectSave","tsumCount=" + tsumCount[i]);

		mLZCardPrintSet.add(mLZCardPrintSchema);
			}
		}
	}

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(mLZCardPrintSet);

	/*	mCardPrintUI.submitData(tVData, "INSERT");
	} catch (Exception ex) {
		Content = "保存失败，原因是:" + ex.toString();
		FlagStr = "Fail";
	}

	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr == "") {
		tError = mCardPrintUI.mErrors;
		if (!tError.needDealError()) {
			Content = "保存成功!";
			FlagStr = "Succ";
		} else {
			Content = "保存失败，原因是:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/
	String busiName="CardPrintUI";
	String mOperateType="INSERT";
	String mDescType="保存";
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
