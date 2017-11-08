
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.f1j.ss.BookModelImpl"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>


<%
	String strEdorAcceptNo = request.getParameter("EdorAcceptNo");
	String strErrInfo = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	//Get the path of VTS file from LDSysVar table	
	//��ȡ��ʱ�ļ���
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

	//��ȡ�����ʱ�ļ���·��
	String strRealPath = application.getRealPath("/")
			.replace('\\', '/');
	String vtsFullName = strRealPath + vtsFileName;

	String strTemplatePath = "";

	if (strEdorAcceptNo != null && !strEdorAcceptNo.equals("")) { // �ϲ�VTSģ���ļ��������ļ����������������
		// �������ݿ�����
		SSRS aSSRS = new SSRS();
		String sql = "Select distinct EdorNo from LPGrpEdorItem where EdorAcceptNo = '"
		+ strEdorAcceptNo + "'";
		sTransferData = new TransferData();
		sTransferData.setNameAndValue("SQL", sql);
		sVData = new VData();
		sVData.add(sTransferData);
		tBusinessDelegate = BusinessDelegate.getBusinessDelegate();

		if (tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI")) {
			aSSRS = (SSRS) tBusinessDelegate.getResult()
			.getObjectByObjectName("SSRS", 0);
			if (aSSRS == null || aSSRS.getMaxRow() == 0) {
		sql = "select distinct edorno from lpedoritem where edoracceptno = '"
				+ strEdorAcceptNo + "'";
		sTransferData = new TransferData();
		sTransferData.setNameAndValue("SQL", sql);
		sVData = new VData();
		sVData.add(sTransferData);
		tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (tBusinessDelegate.submitData(sVData, "execSQL",
				"ExeSQLUI")) {
			aSSRS = (SSRS) tBusinessDelegate.getResult()
			.getObjectByObjectName("SSRS", 0);
		}
			}
			int a = aSSRS.getMaxRow();
			if (a > 1) {
		//�������(һ��ģ��)�ϲ�
		String[] strVFPathName = new String[a];
		Object o = null;
		String tTableName = "LPEdorPrint";
		String tColName = "EdorInfo";
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			String tEdorNo = aSSRS.GetText(j, 1);
			sql = "SELECT 1 FROM LPEDORPRINT WHERE EdorNo = '"
			+ tEdorNo + "'";
			String hasPrint = "";
			sTransferData = new TransferData();
			sTransferData.setNameAndValue("SQL", sql);
			sVData = new VData();
			sVData.add(sTransferData);
			tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
			if (tBusinessDelegate.submitData(sVData,
			"getOneValue", "ExeSQLUI")) {
				hasPrint = (String) tBusinessDelegate
				.getResult().getObjectByObjectName(
				"String", 0);
			}
			if ("1".equals(hasPrint)) {
				tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
				TransferData tTransferData = new TransferData();
				tTransferData
				.setNameAndValue("EdorNo", tEdorNo);
				tTransferData.setNameAndValue("TableName",
				tTableName);
				tTransferData.setNameAndValue("ColName",
				tColName);
				VData tVData = new VData();
				tVData.add(tTransferData);
				if (tBusinessDelegate.submitData(tVData, "",
				"BQPritUI")) {
			o = tBusinessDelegate.getResult()
					.getObjectByObjectName(
					"InputStream", 0);
			if (o != null) {
				CombineVts tcombineVts = null;
				//�ϲ�VTS�ļ� 
				strTemplatePath = application
				.getRealPath("f1print/MStemplate/")
				+ "/";
				loggerDebug("AppEndorsementF1PJ1",
				strTemplatePath);
				tcombineVts = new CombineVts(
				(InputStream) o,
				strTemplatePath);
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				tcombineVts.output(dataStream);
				//��dataStream�洢�������ļ�
				strVFPathName[j - 1] = String
				.valueOf(j - 1)
				+ "Endorsement.vts";
				AccessVtsFile.saveToFile(dataStream,
				strVFPathName[j - 1]);
			}
				}
			}
		}
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		BookModelImpl tb = vtsfilecombine
				.dataCombine(strVFPathName);
		try {
			vtsfilecombine.write(tb, vtsFullName);
		} catch (Exception ex) {
		}
		session.putValue("RealPath", vtsFullName);
		request.getRequestDispatcher("GetF1PrintJ1.jsp")
				.forward(request, response);
			} else {
		//��������������ģ��
		CombineVts tcombineVts = null;
		InputStream ins = null;

		//�ϲ�VTS�ļ� 
		String tEdorNo = aSSRS.GetText(1, 1);
		String tTableName = "LPEdorPrint";
		String tColName = "EdorInfo";
		sql = "SELECT 1 FROM LPEDORPRINT WHERE EdorNo = '"
				+ tEdorNo + "'";
		String hasPrint = "";
		sTransferData = new TransferData();
		sTransferData.setNameAndValue("SQL", sql);
		sVData = new VData();
		sVData.add(sTransferData);
		tBusinessDelegate = BusinessDelegate
				.getBusinessDelegate();
		if (tBusinessDelegate.submitData(sVData, "getOneValue",
				"ExeSQLUI")) {
			hasPrint = (String) tBusinessDelegate.getResult()
			.getObjectByObjectName("String", 0);
		}
		if ("1".equals(hasPrint)) {
			tBusinessDelegate = BusinessDelegate
			.getBusinessDelegate();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("EdorNo", tEdorNo);
			tTransferData.setNameAndValue("TableName",
			tTableName);
			tTransferData.setNameAndValue("ColName", tColName);
			VData tVData = new VData();
			tVData.add(tTransferData);
			if (tBusinessDelegate.submitData(tVData, "",
			"BQPritUI")) {
				BqPrintBean bpb = (BqPrintBean) tBusinessDelegate
				.getResult().getObjectByObjectName(
				"BqPrintBean", 0);

				if (bpb != null) {
			ins = new ByteArrayInputStream(bpb
					.getBytes());
//			����һ�δ�����Ϊ�˽�printName�ڵ���뵽xml�����ж����		
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
			} else if ("PrtNewGrpEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("���屣ȫ������");
			} else if ("PrtNewEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("���˱�ȫ����");
			} else if ("PrtNewGrpEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("���屣ȫ����");
			}
			//���inputstream
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputFormat format = OutputFormat.createCompactFormat();
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
			request.getRequestDispatcher("../print/ShowPrint.jsp")
					.forward(request, response);
				}
			}
		} else {
			strErrInfo = "û��Ҫ��ѯ����ӡ������";
		}
			}
		}
	} else {
		strErrInfo = "û�����뱣ȫ��";
	}
%>
<body>
<%=strErrInfo%>
</body>
</html>
