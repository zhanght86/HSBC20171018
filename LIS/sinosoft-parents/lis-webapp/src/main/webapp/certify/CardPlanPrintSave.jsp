
<%
	//�������ƣ�CardPlanPrintInit.jsp
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
	loggerDebug("CardPlanPrintSave","��ʼִ��Saveҳ��");
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
	String mOperateType="UPDATE";
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
