
<%
	//�������ƣ�CertifyTakeBackSave.jsp
	//�����ܣ�
	//�������ڣ�2002-10-08
	//������  ��kevin
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
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	boolean bContinue = true;
	String szFailSet = "";

	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput) session.getValue("GI"));

	try {
		// ��֤��Ϣ����
		String szHandler = request.getParameter("Handler");
		String szHandleDate = request.getParameter("HandleDate");

		String szNo[] = request.getParameterValues("CertifyListNo");
		String szCertifyCode[] = request.getParameterValues("CertifyList1");
		String szStartNo[] = request.getParameterValues("CertifyList3");
		String szEndNo[] = request.getParameterValues("CertifyList4");

		if (szNo == null) {
			throw new Exception("û������Ҫ���յ���ʼ��֤�ź���ֹ��֤��");
		}

		int nIndex;
		LZCardSet setLZCard = new LZCardSet();
		for (nIndex = 0; nIndex < szNo.length; nIndex++) {
			LZCardSchema schemaLZCard = new LZCardSchema();

			schemaLZCard.setCertifyCode(szCertifyCode[nIndex]);

			schemaLZCard.setStartNo(szStartNo[nIndex]);
			schemaLZCard.setEndNo(szEndNo[nIndex]);

			schemaLZCard.setStateFlag("5"); //5���ֹ��������˹�������

			schemaLZCard.setHandler(szHandler);
			schemaLZCard.setHandleDate(szHandleDate);

			setLZCard.add(schemaLZCard);
		}

		// ׼���������� VData
		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		// ���ݴ���
		/*CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();
		if (!tCertTakeBackUI.submitData(vData, "INSERT")) {
			Content = " ����ʧ�ܣ�ԭ����: " + tCertTakeBackUI.mErrors.getFirstError();
			FlagStr = "Fail";

			vData = tCertTakeBackUI.getResult();
			setLZCard = (LZCardSet) vData.getObjectByObjectName("LZCardSet", 0);

			szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
			for (nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
		LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);
		szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 1, '" + tLZCardSchema.getCertifyCode()
				+ "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 3, '" + tLZCardSchema.getStartNo() + "');\r\n";
		szFailSet += "parent.fraInterface.CertifyList.setRowColData("
				+ String.valueOf(nIndex) + ", 4, '" + tLZCardSchema.getEndNo() + "');\r\n";
			}
		} else {
			Content = " ����ɹ� ";
			FlagStr = "Succ";
		}*/
		
		String busiName="CertTakeBackUI";
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
					setLZCard = (LZCardSet) vData.getObjectByObjectName("LZCardSet", 0);

					szFailSet = "parent.fraInterface.CertifyList.clearData();\r\n";
					for (nIndex = 0; nIndex < setLZCard.size(); nIndex++) {
						LZCardSchema tLZCardSchema = setLZCard.get(nIndex + 1);
						szFailSet += "parent.fraInterface.CertifyList.addOne();\r\n";
						szFailSet += "parent.fraInterface.CertifyList.setRowColData("
								+ String.valueOf(nIndex) + ", 1, '" + tLZCardSchema.getCertifyCode()
								+ "');\r\n";
						szFailSet += "parent.fraInterface.CertifyList.setRowColData("
								+ String.valueOf(nIndex) + ", 3, '" + tLZCardSchema.getStartNo() + "');\r\n";
						szFailSet += "parent.fraInterface.CertifyList.setRowColData("
								+ String.valueOf(nIndex) + ", 4, '" + tLZCardSchema.getEndNo() + "');\r\n";
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
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	</script>
<body>
</body>
</html>

