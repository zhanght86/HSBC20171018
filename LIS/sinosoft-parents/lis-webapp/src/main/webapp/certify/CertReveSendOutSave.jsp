
<%
	//�������ƣ�CertReveSendOutSave.jsp
	//�����ܣ�
	//�������ڣ�2003-04-18
	//������  ��kevin
	//���¼�¼��  ������    ��������     ����ԭ��/����
	//
%>
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
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	//У�鴦��
	//���ݴ����
	try {
		// ��֤��Ϣ����
		String szSendOutCom = request.getParameter("SendOutCom");
		String szReceiveCom = request.getParameter("ReceiveCom");
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");

		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request
		.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList3");
		String szEndNo[] = request.getParameterValues("CertifyList4");
		String szSumCount[] = request
		.getParameterValues("CertifyList5");
		String szReason[] = request.getParameterValues("CertifyList6");

		int nIndex;
		LZCardSet setLZCard = new LZCardSet();

		if (szNo == null) {
			throw new Exception("û������Ҫ���յ���ʼ��֤�ź���ֹ��֤��");
		}

		for (nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardSchema schemaLZCard = new LZCardSchema();

			schemaLZCard.setCertifyCode(szCertifyCode[nIndex]);
			schemaLZCard.setStartNo(szStartNo[nIndex]);
			schemaLZCard.setEndNo(szEndNo[nIndex]);
			schemaLZCard.setSumCount(szSumCount[nIndex]);
			schemaLZCard.setReason(szReason[nIndex]);

			schemaLZCard.setSendOutCom(szSendOutCom);
			schemaLZCard.setReceiveCom(szReceiveCom);

			schemaLZCard.setHandler(szHandler);
			schemaLZCard.setHandleDate(szHandleDate);

			setLZCard.add(schemaLZCard);
		}

		// ׼���������� VData
		VData vData = new VData();

		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		Hashtable hashParams = new Hashtable();
		hashParams.put("CertifyClass",
		CertifyFunc.CERTIFY_CLASS_CERTIFY);
		vData.addElement(hashParams);

		// ���ݴ���
		/*CertReveSendOutUI tCertReveSendOutUI = new CertReveSendOutUI();
		if (!tCertReveSendOutUI.submitData(vData, "INSERT")) {
			Content = " ����ʧ�ܣ�ԭ����: "
			+ tCertReveSendOutUI.mErrors.getFirstError();
			FlagStr = "Fail";

			vData = tCertReveSendOutUI.getResult();
			setLZCard = (LZCardSet) vData.getObjectByObjectName(
			"LZCardSet", 0);

			szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
			for (nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
		LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);
		szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex)
				+ ", 1, '"
				+ tLZCardSchema.getCertifyCode() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex)
				+ ", 3, '"
				+ tLZCardSchema.getStartNo() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex)
				+ ", 4, '"
				+ tLZCardSchema.getEndNo() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex)
				+ ", 5, '"
				+ tLZCardSchema.getSumCount() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex)
				+ ", 6, '"
				+ tLZCardSchema.getReason() + "');\r\n";
			}
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";

			vData = tCertReveSendOutUI.getResult();
			String strTakeBackNo = (String) vData
			.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
			session.setAttribute("State", CertStatBL.PRT_STATE);
		}*/
		String busiName="CertReveSendOutUI";
		String mDescType="����";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(vData,"INSERT",busiName))
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
				setLZCard = (LZCardSet) vData.getObjectByObjectName(
				"LZCardSet", 0);

				szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
				for (nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
					LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);
					szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex)
							+ ", 1, '"
							+ tLZCardSchema.getCertifyCode() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex)
							+ ", 3, '"
							+ tLZCardSchema.getStartNo() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex)
							+ ", 4, '"
							+ tLZCardSchema.getEndNo() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex)
							+ ", 5, '"
							+ tLZCardSchema.getSumCount() + "');\r\n";
					szFailSet += "parent.fraInterface.CertifyList.setRowColData("
							+ String.valueOf(nIndex)
							+ ", 6, '"
							+ tLZCardSchema.getReason() + "');\r\n";
				}
		  }
		  else
		  {
		     	Content = mDescType+"�ɹ�! ";
		      	FlagStr = "Succ";
		      	
		      	vData = tBusinessDelegate.getResult();
				String strTakeBackNo = (String) vData
				.getObjectByObjectName("String", 0);
				session.setAttribute("TakeBackNo", strTakeBackNo);
				session.setAttribute("State", CertStatBL.PRT_STATE);
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
<body>
</body>
</html>
