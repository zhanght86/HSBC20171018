
<%
	//�������ƣ�LLReportInput.jsp
	//�����ܣ�
	//�������ڣ�2002-07-19 16:49:22
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	loggerDebug("CardPlanCollectSave","��ӡˢ�ƻ����ܡ���ʼִ��Saveҳ��");
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
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = mCardPrintUI.mErrors;
		if (!tError.needDealError()) {
			Content = "����ɹ�!";
			FlagStr = "Succ";
		} else {
			Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/
	String busiName="CardPrintUI";
	String mOperateType="INSERT";
	String mDescType="����";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	  if(!tBusinessDelegate.submitData(tVData,mOperateType,busiName))
	  {    
	       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	       { 
				Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
				FlagStr = "Fail";
		   }
		   else
		   {
				Content = mDescType+"ʧ��";
				FlagStr = "Fail";				
		   }
	  }
	  else
	  {
	     	Content = mDescType+"�ɹ�! ";
	      	FlagStr = "Succ";  
	  }
%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
