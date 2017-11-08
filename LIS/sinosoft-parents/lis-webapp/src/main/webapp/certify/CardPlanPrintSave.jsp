
<%
	//程序名称：CardPlanPrintInit.jsp
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
	loggerDebug("CardPlanPrintSave","开始执行Save页面");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPrintUI mCardPrintUI = new CardPrintUI();
	LZCardPrintSet tLZCardPrintSet = new LZCardPrintSet();

	String tChk[] = request
			.getParameterValues("InpCardPrintQueryGridChk");
	String tPrtNo[] = request.getParameterValues("CardPrintQueryGrid1");
	String tStartNo[] = request
			.getParameterValues("CardPrintQueryGrid6");
	String tEndNo[] = request.getParameterValues("CardPrintQueryGrid7");
	String tCertifyPrice[] = request
			.getParameterValues("CardPrintQueryGrid8");
	String tPrintery[] = request
			.getParameterValues("CardPrintQueryGrid9");
	String tPhone[] = request
			.getParameterValues("CardPrintQueryGrid10");
	String tLinkMan[] = request
			.getParameterValues("CardPrintQueryGrid11");
	String tMaxDate[] = request
			.getParameterValues("CardPrintQueryGrid12");

	for (int i = 0; i < tChk.length; i++) {
		if ("1".equals(tChk[i])) {
			LZCardPrintSchema mLZCardPrintSchema = new LZCardPrintSchema();
			mLZCardPrintSchema.setPrtNo(tPrtNo[i]);
			mLZCardPrintSchema.setStartNo(tStartNo[i]);
			mLZCardPrintSchema.setEndNo(tEndNo[i]);
			mLZCardPrintSchema.setCertifyPrice(tCertifyPrice[i]);
			mLZCardPrintSchema.setPrintery(tPrintery[i]);
			mLZCardPrintSchema.setPhone(tPhone[i]);
			mLZCardPrintSchema.setLinkMan(tLinkMan[i]);
			mLZCardPrintSchema.setMaxDate(tMaxDate[i]);
			loggerDebug("CardPlanPrintSave","tChk[i]=" + i);
			loggerDebug("CardPlanPrintSave","tPrtNo=" + tPrtNo[i]);
			loggerDebug("CardPlanPrintSave","tStartNo=" + tStartNo[i]);
			loggerDebug("CardPlanPrintSave","tEndNo=" + tEndNo[i]);
			loggerDebug("CardPlanPrintSave","tCertifyPrice=" + tCertifyPrice[i]);
			loggerDebug("CardPlanPrintSave","tPrintery=" + tPrintery[i]);
			loggerDebug("CardPlanPrintSave","tPhone=" + tPhone[i]);
			loggerDebug("CardPlanPrintSave","tLinkMan=" + tLinkMan[i]);
			loggerDebug("CardPlanPrintSave","tMaxDate=" + tMaxDate[i]);

			tLZCardPrintSet.add(mLZCardPrintSchema);
		}
	}

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(tLZCardPrintSet);

	/*	mCardPrintUI.submitData(tVData, "UPDATE");
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
	String mOperateType="UPDATE";
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
