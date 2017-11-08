
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
	//获取临时文件名
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

	//获取存放临时文件的路径
	String strRealPath = application.getRealPath("/")
			.replace('\\', '/');
	String vtsFullName = strRealPath + vtsFileName;

	String strTemplatePath = "";

	if (strEdorAcceptNo != null && !strEdorAcceptNo.equals("")) { // 合并VTS模板文件与数据文件存入服务器磁盘中
		// 建立数据库连接
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
		//多个批单(一个模板)合并
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
				//合并VTS文件 
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
				//把dataStream存储到磁盘文件
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
		//单个批单，单个模板
		CombineVts tcombineVts = null;
		InputStream ins = null;

		//合并VTS文件 
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
//			以下一段代码是为了将printName节点加入到xml数据中而添加		
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
				control.addElement("PrintName").setText("个人保全申请书");
			} else if ("PrtNewGrpEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("团体保全申请书");
			} else if ("PrtNewEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("个人保全批单");
			} else if ("PrtNewGrpEndorsement.vts".equals(templateName)) {
				control.addElement("PrintName").setText("团体保全批单");
			}
			//输出inputstream
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
			
			//引入打印引擎xmlexportnew，由于已经从数据库中读取了字节流作为数据，因此不需要使用xmlexportnew.getInputStream了
			request.setAttribute("PrintStream", ins);
			request.getRequestDispatcher("../print/ShowPrint.jsp")
					.forward(request, response);
				}
			}
		} else {
			strErrInfo = "没有要查询到打印的数据";
		}
			}
		}
	} else {
		strErrInfo = "没有输入保全号";
	}
%>
<body>
<%=strErrInfo%>
</body>
</html>
