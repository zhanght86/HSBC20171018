
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
	loggerDebug("CardPlanApplySave","��ʼִ��Saveҳ��");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();
	LZCardPlanSchema mLZCardPlanSchema;
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanApplySave","������������ " + mOperateType);

	if (mOperateType.equals("INSERT||APPLY")) {
		String tPlanType = request.getParameter("PlanType1");
		loggerDebug("CardPlanApplySave","�ƻ������� " + tPlanType);

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

	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
	if (mOperateType.equals("INSERT||APPLY")) {
		mDescType = "����";
	}
	if (mOperateType.equals("UPDATE||APPLY")) {
		mDescType = "�޸�";
	}
	if (mOperateType.equals("DELETE||APPLY")) {
		mDescType = "ɾ��";
	}
	if (mOperateType.equals("QUERY||APPLY")) {
		mDescType = "��ѯ";
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
