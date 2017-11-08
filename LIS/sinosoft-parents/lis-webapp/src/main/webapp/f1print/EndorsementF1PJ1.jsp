
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<%
	String tEdorNo = request.getParameter("EdorNo");
	String strErrInfo = "";
	GlobalInput tG = new GlobalInput();
	String tType = "Endorsement";
	tG = (GlobalInput) session.getValue("GI");
	TransferData sTransferData = new TransferData();
	VData sVData = new VData();
	sVData.add(tG);
	BusinessDelegate tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
	String vtsFileName = "";
	if (tBusinessDelegate.submitData(sVData, "", "BqFileNameUI")) {
		vtsFileName = (String) tBusinessDelegate.getResult()
		.getObjectByObjectName("String", 0);
	}
	String strRealPath = application.getRealPath("/")
			.replace('\\', '/');
	String vtsFullName = strRealPath + vtsFileName;

	String strTemplatePath = "";
	CombineVts tcombineVts = null;

	if (tEdorNo != null && !tEdorNo.equals("")) // �ϲ�VTSģ���ļ��������ļ����������������
	{
		// �������ݿ�����
		try {
			String tTableName = "";
			if (tType != null && tType.equals("CashValue")) {
		tTableName = "LPEDORPRINT3";
			} else {
		tTableName = "LPEDORPRINT";
			}
			String tColName = "EdorInfo";
			String sql = "SELECT 1 FROM " + tTableName
			+ " WHERE EdorNo = '" + tEdorNo + "'";
			sTransferData = new TransferData();
			sVData = new VData();
			sTransferData.setNameAndValue("SQL", sql);
			sVData.add(sTransferData);
			tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
			String hasPrint = "";

			if (tBusinessDelegate.submitData(sVData, "getOneValue",
			"ExeSQLUI")) {
		hasPrint = (String) tBusinessDelegate.getResult()
				.getObjectByObjectName("String", 0);
			}
			InputStream ins = null;
			if ("1".equals(hasPrint)) {
		tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorNo", tEdorNo);
		tTransferData.setNameAndValue("TableName", tTableName);
		tTransferData.setNameAndValue("ColName", tColName);
		VData tVData = new VData();
		tVData.add(tTransferData);
		if (tBusinessDelegate
				.submitData(tVData, "", "BQPritUI")) {
			BqPrintBean bpb = (BqPrintBean) tBusinessDelegate
			.getResult().getObjectByObjectName(
					"BqPrintBean", 0);
			if (bpb != null) {
				ins = new ByteArrayInputStream(bpb.getBytes());
			}
		}
			}

			//����VTS�ļ� 
			if (ins != null) {
		//����һ�δ�����Ϊ�˽�printName�ڵ���뵽xml�����ж����		
		SAXReader reader = new SAXReader();
		reader.setEncoding("GBK");
		Document myDocument = reader.read(ins);
		Element control = myDocument.getRootElement().element(
				"CONTROL");
		Element display  = control.element("DISPLAY");
		display.addElement("UserLanguage").setText("zh");
		display.addElement("OutputType").setText("0");
		String templateName = control.element("TEMPLATE")
				.getTextTrim();
		if ("PrtNewAppEndorsement.vts".equals(templateName)) {
			control.addElement("PrintName").setText("���˱�ȫ������");
		} else if ("PrtNewGrpEndorsement.vts"
				.equals(templateName)) {
			control.addElement("PrintName").setText("���屣ȫ������");
		} else if ("PrtNewEndorsement.vts".equals(templateName)) {
			control.addElement("PrintName").setText("���˱�ȫ����");
		} else if ("PrtNewGrpEndorsement.vts"
				.equals(templateName)) {
			control.addElement("PrintName").setText("���屣ȫ����");
		}
		//���inputstream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputFormat format = OutputFormat
				.createCompactFormat();
		format.setEncoding("GBK");
		format.setNewLineAfterDeclaration(false);
		XMLWriter outputter = new XMLWriter(format);
		outputter.setOutputStream(baos);
		outputter.write(myDocument);
		baos.close();
		outputter.close();
		ins = new ByteArrayInputStream(baos.toByteArray());

		//�����ӡ����xmlexportnew�������Ѿ������ݿ��ж�ȡ���ֽ�����Ϊ���ݣ���˲���Ҫʹ��xmlexportnew.getInputStream��
		request.setAttribute("PrintStream", ins);
//		session�е�ֵ���ڹ���������ת
		session.putValue("PrintNo",tEdorNo);
     	session.putValue("Code","endorsement"); 
		request.getRequestDispatcher("../print/ShowPrint.jsp")
				.forward(request, response);
			} else {
		strErrInfo = "û��Ҫ��ѯ����ӡ������";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} else {
		strErrInfo = "û�����뱣ȫ��";
	}
%>
<body>
<%=strErrInfo%>
</body>
</html>
