
<%
	//程序名称：LLClaimNoRgtBillSave.jsp
	//程序功能：个险理赔不予立案清单
	//创建日期：2009-04-14
	//创建人  ：mw
	//更新记录：
%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>

<%
	//准备通用参数
	CError cError = new CError();
	String FlagStr = "FAIL";
	String Content = "";

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		loggerDebug("LLClaimDelayInterestBillSave","未获取到登录信息!");
		return;
	}

	//准备数据容器信息
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("MngCom", request.getParameter("MngCom")); //统计机构
	tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate")); //起始日期
	tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate")); //结束日期

	// 准备传输数据 VData
	VData tVData = new VData();
	VData tResult = new VData();
	XmlExport txmlExport = new XmlExport();

	tVData.add(tG);
	tVData.add(tTransferData);
	LLClaimBillUI tLLClaimBillUI = new LLClaimBillUI();
	if (tLLClaimBillUI.submitData(tVData, "DelayInterestBill") == false) {
		int n = tLLClaimBillUI.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			Content = Content + "原因是: " + tLLClaimBillUI.mErrors.getError(i).errorMessage;
			FlagStr = "FAIL";
		}
	} else {
		tResult = tLLClaimBillUI.getResult();
		txmlExport = (XmlExport) tResult.getObjectByObjectName("XmlExport", 0);
		FlagStr = "Succ";
		if (txmlExport == null) {
			FlagStr = "FAIL";
			Content = "没有得到要显示的数据文件";
			loggerDebug("LLClaimDelayInterestBillSave","没有得到要显示的数据文件");
		}
	}

	if (FlagStr.equals("Succ")) {
		ExeSQL tExeSQL = new ExeSQL();
		//获取临时文件名
		String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		String strFilePath = tExeSQL.getOneValue(strSql);
		String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName() + ".vts";
		loggerDebug("LLClaimDelayInterestBillSave","strVFFileName=" + strVFFileName);

		//获取存放临时文件的路径
		String strRealPath = application.getRealPath("/").replace('\\', '/');
		loggerDebug("LLClaimDelayInterestBillSave","strRealPath=" + strRealPath);

		String strVFPathName = strRealPath + strVFFileName;

		CombineVts tcombineVts = null;
		//合并VTS文件
		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
		tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		tcombineVts.output(dataStream);

		//把dataStream存储到磁盘文件
		loggerDebug("LLClaimDelayInterestBillSave","存储文件到" + strVFPathName);
		AccessVtsFile.saveToFile(dataStream, strVFPathName);
		session.putValue("RealPath", strVFPathName);

		request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request, response);
	} else {
%>
<html>
<script language="javascript">  
    alert("<%=Content%>");
    top.close();  
</script>
</html>
<%
}
%>
