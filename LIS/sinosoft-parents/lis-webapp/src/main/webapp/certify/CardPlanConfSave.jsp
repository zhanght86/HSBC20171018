
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
	loggerDebug("CardPlanConfSave","��ʼִ��Saveҳ��");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();
	LZCardPlanSchema mLZCardPlanSchema = new LZCardPlanSchema();
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanConfSave","������������ " + mOperateType);

	if (mOperateType.equals("UPDATE||CONF2")) { //�����ƻ��ύ:ֱ���ύ
		String tChk2[] = request
		.getParameterValues("InpCardPlanQueryGridChk");
		String tPlanID2[] = request
		.getParameterValues("CardPlanQueryGrid1");
		for (int i = 0; i < tChk2.length; i++) {
			if ("1".equals(tChk2[i])) {
		LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();
		tLZCardPlanSchema.setPlanID(tPlanID2[i]);
		loggerDebug("CardPlanConfSave","�����ƻ��ύ:" + i);
		loggerDebug("CardPlanConfSave","tPlanID=" + tPlanID2[i]);

		mLZCardPlanSet.add(tLZCardPlanSchema);
			}
		}
	} else if (mOperateType.equals("UPDATE||CONF")) { //�������棬�����������
		String tChk[] = request
		.getParameterValues("InpCardPlanQueryDetailGridChk");
		String tPlanID[] = request
		.getParameterValues("CardPlanQueryDetailGrid1");
		String tConCount[] = request
		.getParameterValues("CardPlanQueryDetailGrid7");
		String tRetOperator = request.getParameter("ConOperator");
		String tRetCom = request.getParameter("managecom");

		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();
		tLZCardPlanSchema.setPlanID(tPlanID[i]);
		tLZCardPlanSchema.setConCount(tConCount[i]);
		tLZCardPlanSchema.setConOperator(tRetOperator);
		tLZCardPlanSchema.setConCom(tRetCom);
		loggerDebug("CardPlanConfSave","��������:" + i);
		loggerDebug("CardPlanConfSave","tPlanID=" + tPlanID[i]);
		loggerDebug("CardPlanConfSave","tConCount=" + tConCount[i]);

		mLZCardPlanSet.add(tLZCardPlanSchema);
			}
		}
	} else if (mOperateType.equals("INSERT||PACK")) { //�ƻ������ύ�������ύ
		mLZCardPlanSchema.setAppCom(request.getParameter("managecom"));
		mLZCardPlanSchema.setAppOperator(request
		.getParameter("ConOperator"));
		mLZCardPlanSchema.setPlanType(request.getParameter("PlanType"));
		
		loggerDebug("CardPlanConfSave","�ƻ������ύ:");
		loggerDebug("CardPlanConfSave","managecom="
		+ request.getParameter("managecom"));
	}

	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
	if (mOperateType.equals("UPDATE||CONF2")) {
		mDescType = "�����ƻ��ύ";
	}
	if (mOperateType.equals("UPDATE||CONF")) {
		mDescType = "��������";
	}
	if (mOperateType.equals("INSERT||PACK")) {
		mDescType = "�ƻ������ύ";
	}

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(mOperateType);
		tVData.addElement(mLZCardPlanSet);
		tVData.addElement(mLZCardPlanSchema);

		//mCardPlanUI.submitData(tVData, mOperateType);
	/*} catch (Exception ex) {
		Content = mDescType + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = mCardPlanUI.mErrors;
		if (!tError.needDealError()) {
			Content = mDescType + " �ɹ�!";
			FlagStr = "Succ";
		} else {
			Content = mDescType + " ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
		}
	}*/
	String busiName="CardPlanUI";
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
