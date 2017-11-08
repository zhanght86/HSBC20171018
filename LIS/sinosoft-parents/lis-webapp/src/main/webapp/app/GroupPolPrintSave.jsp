<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：GroupPolPrintSave.jsp
	//程序功能：
	//创建日期：2002-11-26
	//创建人  ：Kevin
	//修改人  ：朱向峰
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>

<%!InputStream ins = null;
	String PrintContent = "";
	String FlagStr = "";
	String Content = "";
	int tFail = 0;
	int tSucc = 0;
	boolean handleFunction(HttpSession session, HttpServletRequest request,
			String szTemplatePath) {
		String tOperate = "";

		//获得mutline中的数据信息
		int nIndex = 0;
		String tLCGrpContGrids[] = request.getParameterValues("GrpContGridNo");
		String tGrpContNo[] = request.getParameterValues("GrpContGrid1");
		String tPrintCount[] = request.getParameterValues("GrpContGrid6");
		String tChecks[] = request.getParameterValues("InpGrpContGridChk");

		//获得session中的人员信息
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		//操作对象及容器
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		//LCGrpContF1PUI tLCGrpContF1PUI = new LCGrpContF1PUI();
		VData vData = new VData();

		//循环打印选中的团单
		for (nIndex = 0; nIndex < tChecks.length; nIndex++) {
			LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
			//If this line isn't selected, continue，如果没有选中当前行，则继续
			if (!tChecks[nIndex].equals("1")) {
				continue;
			}
			//将数据放入合同保单集合

			tLCGrpContSchema = new LCGrpContSchema();
			tLCGrpContSchema.setGrpContNo(tGrpContNo[nIndex]);
			//判定打印模式
			loggerDebug("GroupPolPrintSave","打印模式" + tPrintCount[nIndex]);
			if (tPrintCount[nIndex].compareTo("0") == 0) {
				tOperate = "PRINT";
			} else {
				tOperate = "REPRINT";
			}
			tLCGrpContSet.add(tLCGrpContSchema);
		}
		//将数据集合放入容器中，准备传入后台处理
		vData = new VData();
		vData.add(tG);
		vData.addElement(tLCGrpContSet);
		vData.add(szTemplatePath);
		//vData.add(sOutXmlPath);
		//执行后台操作
		String busiName="f1printLCGrpContF1PUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		try {
			if (!tBusinessDelegate.submitData(vData, "PRINT",busiName)) {
				PrintContent = tBusinessDelegate.getCErrors().getFirstError();
				return false;
			} else {
				ins = (InputStream) (tBusinessDelegate.getResult().get(0));
				//PrintContent = tLCGrpContF1PUI.mContent;
				//tSucc += tBusinessDelegate.tSucc;
				//tFail += tBusinessDelegate.tFail;
				VData tVData=tBusinessDelegate.getResult();
				TransferData tTransferData=(TransferData)tVData.getObjectByObjectName("TransferData",0);
				PrintContent =(String)tTransferData.getValueByName("Content");
				tSucc =tSucc+(Integer)tTransferData.getValueByName("Succ");
				tFail =tFail+(Integer)tTransferData.getValueByName("Fail");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Content += ex.getMessage();
			return false;
		}
		return true;
	}%>

<%
	//add by yt 20040426，为了解决同时多个窗口打印会导致产生同一个文件名的问题。
	String tSN = "";
	tSN = PubFun1.CreateMaxNo("SNPolPrint", 10);
	if (tSN.equals("")) {
		FlagStr = "Fail";
		loggerDebug("GroupPolPrintSave","产生流水号失败，请您从新打印!");
	} else {
		try {
			//用于保单打印用的数据模板，主要用于团单下被保人清单
			String szTemplatePath = application
			.getRealPath("f1print/template/")
			+ "/"; //模板路径
			loggerDebug("GroupPolPrintSave","保单团单下被保人清单模板XML文件保存在应用服务器的路径是："
			+ szTemplatePath);
			if (handleFunction(session, request, szTemplatePath)
			&& tSucc > 0) {
		//String strTemplatePath = application.getRealPath("xerox/printdata") + "/";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("printdatanew");
		LDSysVarSet tLDSysVarSet = new LDSysVarSet();
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();
		tLDSysVarSet = tLDSysVarDB.query();
		if (tLDSysVarSet.size() > 0) {
			tLDSysVarSchema = tLDSysVarSet.get(1);
		} else {
			FlagStr = "Fail";
			loggerDebug("GroupPolPrintSave","无法获取保存XML文件的应用服务器的路径!");
		}
		//用于保存在应用服务器上某个路径之下        
		String strTemplatePath = tLDSysVarSchema
				.getSysVarValue().trim();
		//strTemplatePath = "c:/XML/"; //便于测试暂时放在C盘

		// It is important to use buffered streams to enhance performance
		BufferedInputStream bufIs = new BufferedInputStream(ins);
		String strTime = PubFun.getCurrentDate() + "_"
				+ PubFun.getCurrentTime();
		strTime = strTime.replace(':', '_').replace('-', '_');
		strTime = strTime + "_" + tSN.trim();
		//add by ssx 2008-04-24 建立分级目录
		String[] step = strTime.split("_");
		String year = step[0];
		String month = step[1];
		String day = step[2];
		strTemplatePath += year + "/" + month + "/" + day + "/";
		File file = new File(strTemplatePath);
		if (!file.exists()) {
			loggerDebug("GroupPolPrintSave","开始新建文件夹!");
			file.mkdirs();
		} else {
			loggerDebug("GroupPolPrintSave","目录已经存在: ");
		} //add end
		loggerDebug("GroupPolPrintSave","保单XML文件保存在应用服务器的路径是："
				+ strTemplatePath);
		FileOutputStream fos = new FileOutputStream(
				strTemplatePath + "LCGrpPolData" + strTime
				+ ".xml");
		BufferedOutputStream bufOs = new BufferedOutputStream(
				fos);
		int n = 0;

		while ((n = bufIs.read()) != -1) {
			bufOs.write(n);
		}
		bufOs.flush();
		bufOs.close();

		//将XML传输到指定的FTP的打印服务器
		LisFtpClient tLisFtpClient = new LisFtpClient();
		if (!tLisFtpClient.UpLoadFile(strTemplatePath
				+ "LCGrpPolData" + strTime + ".xml",
				"LCGrpPolData" + strTime + ".xml")) {
			FlagStr = "Fail";
			loggerDebug("GroupPolPrintSave","FTP传输文件失败，请检查相关服务器配置，原因可能是路径错误,文件目标磁盘空间不足等等！");
		} else {
			FlagStr = "Succ";
		}
			} else {
		FlagStr = "Fail";
			}
		} catch (Exception ex) {
			FlagStr = "Fail";
			ex.printStackTrace();
		}
	}
	PrintContent = "操作成功完成,本次打印保单共成功打印" + tSucc + "单,失败" + tFail
			+ "单!";
   tSucc=0;
   tFail=0;			
	String strTime1 = PubFun.getCurrentDate() + "_"
			+ PubFun.getCurrentTime();
	loggerDebug("GroupPolPrintSave","保单打印：" + PrintContent + strTime1);
%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=PrintContent%>");
</script>
</html>
