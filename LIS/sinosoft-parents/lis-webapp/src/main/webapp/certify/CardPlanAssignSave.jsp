
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
	loggerDebug("CardPlanAssignSave","��ʼִ��Saveҳ��");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");

	//CardPlanUI mCardPlanUI = new CardPlanUI();

	LZCardPlanSchema mLZCardPlanSchema = new LZCardPlanSchema();
	mLZCardPlanSchema.setAppCount(request.getParameter("sumBalance")); //��ʱ���ô��ֶ�������¼��������
	loggerDebug("CardPlanAssignSave","savejsp,����ǰ�������"+request.getParameter("sumBalance"));
	
	LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	String FlagStr = "";
	String Content = "";
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	loggerDebug("CardPlanAssignSave","������������ " + mOperateType);

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

	String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
	if (mOperateType.equals("UPDATE||ASSIGN")) {
		mDescType = "������";
	}

	VData tVData = new VData();
	//try {
		tVData.addElement(tG);
		tVData.addElement(mOperateType);
		tVData.addElement(mLZCardPlanSchema);
		tVData.addElement(mLZCardPlanSet);

	/*	mCardPlanUI.submitData(tVData, mOperateType);
	} catch (Exception ex) {
		Content = mDescType + "ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		tError = mCardPlanUI.mErrors;
		if (!tError.needDealError()) {
			Content = mDescType + "�ɹ�!";
			FlagStr = "Succ";
		} else {
			Content = mDescType + "ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
