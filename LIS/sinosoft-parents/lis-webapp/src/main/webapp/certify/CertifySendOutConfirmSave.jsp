
<%
	//�������ƣ�CertifySendOutConfirmSave.jsp
	//�����ܣ�
	//�������ڣ�2002-09-23
	//������  ��mw
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
<SCRIPT src="./CQueryValueOperate.js"></SCRIPT>
<SCRIPT src="IndiDunFeeInput.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
	//�������
	String strTakeBackNo = "";
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		String szLimitFlag = "NO";
		String tChk[] = request.getParameterValues("InpCertifyListChk");
		String tApplyNo[] = request.getParameterValues("CertifyList1");
		String tCertifyCode[] = request
		.getParameterValues("CertifyList2");

		String tSendOutCom[] = request
		.getParameterValues("CertifyList4");
		String tReceiveCom[] = request
		.getParameterValues("CertifyList6");

		String tStartNo[] = request.getParameterValues("CertifyList8");
		String tEndNo[] = request.getParameterValues("CertifyList9");
		String tSumCount[] = request
		.getParameterValues("CertifyList10");

		String tHandler = request.getParameter("Handler");
		String tHandleDate = request.getParameter("HandleDate");

		loggerDebug("CertifySendOutConfirmSave","tChk.length=" + tChk.length);

		LZCardSet tLZCardSet = new LZCardSet();
		for (int i = 0; i < tChk.length; i++) {
			if ("1".equals(tChk[i])) {
		LZCardSchema tLZCardschema = new LZCardSchema();
		tLZCardschema.setApplyNo(tApplyNo[i]);

		tLZCardschema.setCertifyCode(tCertifyCode[i]);
		tLZCardschema.setStartNo(tStartNo[i]);
		tLZCardschema.setEndNo(tEndNo[i]);
		tLZCardschema.setSumCount(tSumCount[i]);

		tLZCardschema.setSendOutCom(tSendOutCom[i]);
		tLZCardschema.setReceiveCom(tReceiveCom[i]);

		tLZCardschema.setHandler(tHandler);
		tLZCardschema.setHandleDate(tHandleDate);
		tLZCardSet.add(tLZCardschema);
		loggerDebug("CertifySendOutConfirmSave","CertifySendOutConfirmSave.jsp�������ݣ�");
		loggerDebug("CertifySendOutConfirmSave","�����������=" + tApplyNo[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤����=" + tCertifyCode[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤���=" + tStartNo[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥ֹ֤��=" + tEndNo[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤����=" + tSumCount[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤������=" + tSendOutCom[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤������=" + tReceiveCom[i]);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤������=" + tHandler);
		loggerDebug("CertifySendOutConfirmSave","���쵥֤��������=" + tHandleDate);
			}
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(tLZCardSet);
		vData.addElement(szLimitFlag);

		// ���ò����ַ���
		String szOperator = "ADD";

		// ���ݴ���
		/*CertSendOutUI tCertSendOutUI = new CertSendOutUI();
		if (!tCertSendOutUI.submitData(vData, szOperator)) {
			Content = " ����ʧ�ܣ�ԭ����: "
			+ tCertSendOutUI.mErrors.getFirstError();
			FlagStr = "Fail";
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";
		}*/
		
		String busiName="CertSendOutUI";
		String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,szOperator,busiName))
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
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = FlagStr + " ����ʧ�ܣ�ԭ����:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
	</script>
<body>
</body>
</html>
