
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

	if (tEdorNo != null && !tEdorNo.equals("")) // 合并VTS模板文件与数据文件存入服务器磁盘中
	{
		// 建立数据库连接
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

			//生成VTS文件 
			if (ins != null) {
		//以下一段代码是为了将printName节点加入到xml数据中而添加		
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
		} else if ("PrtNewGrpEndorsement.vts"
				.equals(templateName)) {
			control.addElement("PrintName").setText("团体保全申请书");
		} else if ("PrtNewEndorsement.vts".equals(templateName)) {
			control.addElement("PrintName").setText("个人保全批单");
		} else if ("PrtNewGrpEndorsement.vts"
				.equals(templateName)) {
			control.addElement("PrintName").setText("团体保全批单");
		}
		//输出inputstream
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

		//引入打印引擎xmlexportnew，由于已经从数据库中读取了字节流作为数据，因此不需要使用xmlexportnew.getInputStream了
		request.setAttribute("PrintStream", ins);
//		session中的值用于工作流的跳转
		session.putValue("PrintNo",tEdorNo);
     	session.putValue("Code","endorsement"); 
		request.getRequestDispatcher("../print/ShowPrint.jsp")
				.forward(request, response);
			} else {
		strErrInfo = "没有要查询到打印的数据";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	} else {
		strErrInfo = "没有输入保全号";
	}
%>
<body>
<%=strErrInfo%>
</body>
</html>
