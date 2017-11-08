
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
	loggerDebug("CardPlanApplySave","开始执行Save页面");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();
	LZCardPlanSchema mLZCardPlanSchema;
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanApplySave","操作的类型是 " + mOperateType);

	if (mOperateType.equals("INSERT||APPLY")) {
		String tPlanType = request.getParameter("PlanType1");
		loggerDebug("CardPlanApplySave","计划类型是 " + tPlanType);

		String[] strNumber = request.getParameterValues("CardPlanGridNo");
		String[] tCertifyCode = request.getParameterValues("CardPlanGrid1");
		String[] tAppCount = request.getParameterValues("CardPlanGrid4");
		if (strNumber != null) {
			for (int i = 0; i < strNumber.length; i++) {
		mLZCardPlanSchema = new LZCardPlanSchema();
		mLZCardPlanSchema.setPlanType(tPlanType);
		mLZCardPlanSchema.setCertifyCode(tCertifyCode[i]);
		mLZCardPlanSchema.setAppCount(tAppCount[i]);
		mLZCardPlanSchema.setConCount(tAppCount[i]);

		mLZCardPlanSet.add(mLZCardPlanSchema);
			}
		}
	} else if (mOperateType.equals("UPDATE||APPLY")) {
		String tChk[] = request.getParameterValues("InpCardPlanQueryGridChk");
		String tPlanID[] = request.getParameterValues("CardPlanQueryGrid1");
		String tCertifyCode[] = request.getParameterValues("CardPlanQueryGrid2");
		String tPlanType[] = request.getParameterValues("CardPlanQueryGrid3");
		String tAppCount[] = request.getParameterValues("CardPlanQueryGrid5");

		for (int index = 0; index < tChk.length; index++) {
			if ("1".equals(tChk[index])) {
		loggerDebug("CardPlanApplySave","index=" + index);
		loggerDebug("CardPlanApplySave","PlanID=" + tPlanID[index]);
		mLZCardPlanSchema = new LZCardPlanSchema();
		mLZCardPlanSchema.setPlanID(tPlanID[index]);
		mLZCardPlanSchema.setCertifyCode(tCertifyCode[index]);
		mLZCardPlanSchema.setPlanType(tPlanType[index]);
		mLZCardPlanSchema.setAppCount(tAppCount[index]);
		mLZCardPlanSchema.setConCount(tAppCount[index]);
		mLZCardPlanSet.add(mLZCardPlanSchema);
			}
		}
	} else if (mOperateType.equals("DELETE||APPLY")) {
		String tChk[] = request.getParameterValues("InpCardPlanQueryGridChk");
		String tPlanID[] = request.getParameterValues("CardPlanQueryGrid1");

		for (int index = 0; index < tChk.length; index++) {
			if ("1".equals(tChk[index])) {
		loggerDebug("CardPlanApplySave","index=" + index);
		loggerDebug("CardPlanApplySave","PlanID=" + tPlanID[index]);
		mLZCardPlanSchema = new LZCardPlanSchema();
		mLZCardPlanSchema.setPlanID(tPlanID[index]);
		mLZCardPlanSet.add(mLZCardPlanSchema);
			}
		}
	}

	String mDescType = ""; //将操作标志的英文转换成汉字的形式
	if (mOperateType.equals("INSERT||APPLY")) {
		mDescType = "新增";
	}
	if (mOperateType.equals("UPDATE||APPLY")) {
		mDescType = "修改";
	}
	if (mOperateType.equals("DELETE||APPLY")) {
		mDescType = "删除";
	}
	if (mOperateType.equals("QUERY||APPLY")) {
		mDescType = "查询";
	}

	VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(mOperateType);
		tVData.addElement(mLZCardPlanSet);

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

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
