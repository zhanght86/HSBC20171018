
<%
	//�������ƣ�CertifySendOutSave.jsp
	//�����ܣ�
	//�������ڣ�2002-09-23
	//������  ����ƽ
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
	String FlagStr = "Fail";
	String Content = "";
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// ��֤��Ϣ����
		String szSendOutCom = request.getParameter("SendOutCom");
		String szReceiveCom = request.getParameter("ReceiveCom");
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");
		String szAgentCom = request.getParameter("agentCom");
		loggerDebug("CertifySendOutSave","szSendOutCom= " + szSendOutCom);
		loggerDebug("CertifySendOutSave","szReceiveCom= " + szReceiveCom);
		loggerDebug("CertifySendOutSave","szAgentCom= " + szAgentCom);
		
		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList5");
		String szEndNo[] = request.getParameterValues("CertifyList6");
		String szSumCount[] = request.getParameterValues("CertifyList7");

		LZCardSet setLZCard = new LZCardSet();
		for (int nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardSchema schemaLZCard = new LZCardSchema();

			schemaLZCard.setCertifyCode(szCertifyCode[nIndex]);

			schemaLZCard.setStartNo(szStartNo[nIndex]);
			schemaLZCard.setEndNo(szEndNo[nIndex]);
			schemaLZCard.setSumCount(szSumCount[nIndex]);

			schemaLZCard.setSendOutCom(szSendOutCom);
			schemaLZCard.setReceiveCom(szReceiveCom);

			schemaLZCard.setHandler(szHandler);
			schemaLZCard.setHandleDate(szHandleDate);
			
			schemaLZCard.setAgentCom(szAgentCom);

			setLZCard.add(schemaLZCard);
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		// ���ò����ַ���
		String szOperator = "INSERT";

		// ���ݴ���
		/*CertSendOutUI tCertSendOutUI = new CertSendOutUI();
		if (!tCertSendOutUI.submitData(vData, szOperator)) {
			Content = " ����ʧ�ܣ�ԭ����: " + tCertSendOutUI.mErrors.getFirstError();
			FlagStr = "Fail";

			vData = tCertSendOutUI.getResult();
			setLZCard = (LZCardSet) vData.getObjectByObjectName("LZCardSet", 0);

			szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
			for (int nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
		LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);

		szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 1, '" + tLZCardSchema.getCertifyCode()
				+ "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 5, '" + tLZCardSchema.getStartNo() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 6, '" + tLZCardSchema.getEndNo() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 7, '" + tLZCardSchema.getSumCount() + "');\r\n";
			}
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
		       vData = tBusinessDelegate.getResult();
				setLZCard = (LZCardSet) vData.getObjectByObjectName("LZCardSet", 0);

				szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
				for (int nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
					LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);
		
					szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex) + ", 1, '" + tLZCardSchema.getCertifyCode()
							+ "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex) + ", 5, '" + tLZCardSchema.getStartNo() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex) + ", 6, '" + tLZCardSchema.getEndNo() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex) + ", 7, '" + tLZCardSchema.getSumCount() + "');\r\n";
				}
		  }
		  else
		  {
		     	Content = mDescType+"�ɹ�! ";
		      	FlagStr = "Succ";  
		  }
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = " ����ʧ�ܣ�ԭ����:" + ex.getMessage();
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
