<html>
<%@page contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>

<%
	String strErrInfo = "";
	String strEdorNo = request.getParameter("EdorNo");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	String strType = request.getParameter("type");
	System.out.println("EdorNo: " + strEdorNo);
	System.out.println("prtType: " + strType);

	//Get the path of VTS file from LDSysVar table	
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();

	String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
	LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
	LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
	String strFilePath = tLDSysVarSchema.getV("SysVarValue");
	String strVFFileName = strFilePath + tG.Operator + "_"
			+ FileQueue.getFileName() + ".vts";

	//获取存放临时文件的路径
	String strRealPath = application.getRealPath("/")
			.replace('\\', '/');
	String strVFPathName = strRealPath + strVFFileName;

	CombineVts tcombineVts = null;
	InputStream ins = null;

	if (strEdorNo != null && !strEdorNo.equals("")) // 合并VTS模板文件与数据文件存入服务器磁盘中
	{
		// 建立数据库连接
		try {
			Connection conn = DBConnPool.getConnection();
			Statement stmt = null;
			ResultSet rs = null;

			if (conn == null) {
		strErrInfo = "连接数据库失败";
			} else {
		stmt = conn.createStatement(
				ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_UPDATABLE);
		if (strType != null && strType.equals("Bill")) {
			rs = stmt
			.executeQuery("SELECT * FROM LPEDORPRINT2 WHERE EdorNo = '"
					+ strEdorNo + "'");
		} else {
			rs = stmt
			.executeQuery("SELECT * FROM LPEDORPRINT WHERE EdorNo = '"
					+ strEdorNo + "'");
		}

		if (rs.next()) {
			//输出数据文件
			COracleBlob tCOracleBlob = new COracleBlob();
			Blob tBlob = null;

			String tSQL = " and EdorNo = '" + strEdorNo + "'";
			if (strType != null && strType.equals("Bill")) {
				System.out.println("==> 批单附属清单");
				tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT2",
				"edorinfo", tSQL, conn);
			} else {
				System.out.println("==> 个人批单");
				tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT",
				"edorinfo", tSQL, conn);
			}
			ins = tBlob.getBinaryStream();
			System.out.println("get stream object");

		} else {
			System.out.println("can't get stream object");
		}
		rs.close();
		stmt.close();
		conn.close();

		//合并VTS文件 
		String strTemplatePath = application
				.getRealPath("f1print/MStemplate/")
				+ "/";
		tcombineVts = new CombineVts(ins, strTemplatePath);

		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		System.out.println("==> Write VTS file to disk ");
		session.putValue("RealPath", strVFPathName);
		session.putValue("PrintNo", strEdorNo);
		System.out.println("strType = " + strType);
		if (strType.equals("Bill") || strType == "Bill") {
			session.putValue("Code", "EdorNameList");
		} else if (strType.equals("Endorsement")
				|| strType == "Endorsement") {
			session.putValue("Code", "endorsement");
		}
		System.out.println("put session value....");
		request.getRequestDispatcher(
				"../uw/GetF1PrintEndorse.jsp").forward(request,
				response);
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
