
<%
	//�������ƣ���֤���,CertifyGetInSave.jsp
	//�����ܣ���֤ӡˢ�󡢵�֤���ŵ��¼�����ʱ����Ҫ���ջ�������֤������
	//�������ڣ�2009-01-04
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
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	LZCardSet setLZCard = new LZCardSet();
	LZCardPrintSet setLZCardPrint = new LZCardPrintSet();

	try {
		// ��֤��Ϣ����
		String szHandler = request.getParameter("Operator");
		String szHandleDate = request.getParameter("OperateDate");
		
		// ���ò����ַ�����INSERTΪȷ����⣬CANCELΪ�ܾ����
		String szOperator = request.getParameter("operateFlag");

		String tChk[] = request.getParameterValues("InpCertifyListGridChk");
		String szCertifyCode[] = request.getParameterValues("CertifyListGrid1");
		String szSendOutCom[] = request.getParameterValues("CertifyListGrid3");
		String szReceiveCom[] = request.getParameterValues("CertifyListGrid4");
		String szStartNo[] = request.getParameterValues("CertifyListGrid5");
		String szEndNo[] = request.getParameterValues("CertifyListGrid6");
		String szSumCount[] = request.getParameterValues("CertifyListGrid7");
		String szSourceFlag[] = request.getParameterValues("CertifyListGrid8");
		String szPrtNo[] = request.getParameterValues("CertifyListGrid9");
		String szReason[] = request.getParameterValues("CertifyListGrid10");

		loggerDebug("CertifyGetInSave","tChk.length=" + tChk.length);
		for (int i = 0; i < tChk.length; i++) {
			//loggerDebug("CertifyGetInSave",i);
			if ("1".equals(tChk[i])) {
		LZCardSchema schemaLZCard = new LZCardSchema();
		schemaLZCard.setCertifyCode(szCertifyCode[i]);
		schemaLZCard.setSendOutCom(szSendOutCom[i]);
		schemaLZCard.setReceiveCom(szReceiveCom[i]);
		schemaLZCard.setStartNo(szStartNo[i]);
		schemaLZCard.setEndNo(szEndNo[i]);
		schemaLZCard.setSumCount(szSumCount[i]);
		if ("CANCEL".equals(szOperator)) {
			schemaLZCard.setReason(szReason[i]);
		}
		schemaLZCard.setHandler(szHandler);
		schemaLZCard.setHandleDate(szHandleDate);
		setLZCard.add(schemaLZCard);
		loggerDebug("CertifyGetInSave","szCertifyCode=" + szCertifyCode[i]);
		loggerDebug("CertifyGetInSave","szSendOutCom=" + szSendOutCom[i]);
		loggerDebug("CertifyGetInSave","szReceiveCom=" + szReceiveCom[i]);
		loggerDebug("CertifyGetInSave","szStartNo=" + szStartNo[i]);
		loggerDebug("CertifyGetInSave","szEndNo=" + szEndNo[i]);
		loggerDebug("CertifyGetInSave","szSumCount=" + szSumCount[i]);
		loggerDebug("CertifyGetInSave","szHandler=" + szHandler);
		loggerDebug("CertifyGetInSave","szHandleDate=" + szHandleDate);

		//�˴�setLZCardPrint��setLZCard��Schema��������һ�£����Ϊ�������PrtNo��ʵ�ǿյ�
		LZCardPrintSchema schemaLZCardPrint = new LZCardPrintSchema();
		schemaLZCardPrint.setPrtNo(szPrtNo[i]);
		setLZCardPrint.add(schemaLZCardPrint);
			}
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);
		vData.addElement(setLZCardPrint);

		// ���ݴ���
		/*CertGetInUI tCertGetInUI = new CertGetInUI();
		if (!tCertGetInUI.submitData(vData, szOperator)) {
			Content = "����ʧ�ܣ�ԭ���ǣ�" + tCertGetInUI.mErrors.getFirstError();
			FlagStr = "Fail";

			vData = tCertGetInUI.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";

			vData = tCertGetInUI.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
		}*/
		String busiName="CertGetInUI";
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
		  vData = tBusinessDelegate.getResult();
			strTakeBackNo = (String) vData.getObjectByObjectName("String", 0);
			session.setAttribute("TakeBackNo", strTakeBackNo);
	} catch (Exception ex) {
		ex.printStackTrace();
		Content = "����ʧ�ܣ�ԭ����:" + ex.getMessage();
		FlagStr = "Fail";
	}
%>

<html>
<script language="javascript">
  	<%= szFailSet %>
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>", "<%= strTakeBackNo %>");
	</script>
<body>
</body>
</html>
