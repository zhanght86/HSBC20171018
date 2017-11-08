<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>

<%!JspWriter jspOut;
	String Content;
	MeetF1PUI tMeetF1PUI = new MeetF1PUI();
	VData mResult;

	//  tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
	//
	//  ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	//  tcombineVts.output(dataStream);
	//
	//  //把dataStream存储到磁盘文件
	//  //loggerDebug("MeetF1BatchPSave","存储文件到"+strVFPathName);
	//  AccessVtsFile.saveToFile(dataStream,strVFPathName);
	//  loggerDebug("MeetF1BatchPSave","==> Write VTS file to disk ");

	String tPath;

	BookModelImpl newFile;
	BookModelImpl targetFile;
	VtsFileCombine vfc;

	boolean getFile(VData tVData, String operate, String templateFile)
			throws Exception {

		if (!tMeetF1PUI.submitData(tVData, operate)) {
			Content = tMeetF1PUI.mErrors.getFirstError().toString();
			return false;
		}

		mResult = tMeetF1PUI.getResult();
		XmlExport txmlExport = (XmlExport) mResult.getObjectByObjectName(
				"XmlExport", 0);
		if (txmlExport == null) {
			Content = "没有得到要显示的数据文件";
			return false;
		}

		//templateFile = tPath + templateFile;

		loggerDebug("MeetF1BatchPSave","Operate:" + operate + " before Combine:"
				+ templateFile);
		//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
		CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),
				tPath);

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		loggerDebug("MeetF1BatchPSave","before output...");
		tcombineVts.output(outStream);
		loggerDebug("MeetF1BatchPSave","after output...");
		tcombineVts.destroy();
//		System.gc();

		byte[] bytes = outStream.toByteArray();

		//  byte[] data = new byte[4098];
		//  while((i=data.write(data)>-1)
		//  {
		//  	
		//  
		//  }
		InputStream inputStream = new ByteArrayInputStream(bytes);
		AccessVtsFile.saveToFile(outStream, "c:\\1.vts");
		// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
		outStream.close();

		try {
			newFile = new BookModelImpl();
			//      newFile.read(new FileInputStream("c:\\"+templateFile));
			newFile.read(inputStream);

			//      newFile.setSelection(0,0,newFile.getLastRow(),newFile.getLastCol());
		} catch (Exception ex) {
			ex.printStackTrace();
			Content = "读取文件错误." + ex.getMessage();
			return false;
		}

		if (targetFile == null) {
			//      targetFile = newFile;  //同一个对象指针错误，切切
			targetFile = new BookModelImpl();
			targetFile.copyAll(newFile);
		} else {
			loggerDebug("MeetF1BatchPSave","before combine");
			targetFile = vfc.dataCombine(newFile, targetFile);
			//	 loggerDebug("MeetF1BatchPSave","after combine:"+targetFile.getSelection());
		}

		inputStream.close();
		newFile.destroy();
		return true;
	}

	void error() throws Exception {
		loggerDebug("MeetF1BatchPSave",Content);
		jspOut.println(Content);
	}%>

<%

	loggerDebug("MeetF1BatchPSave","--------------------start------------------");
	tPath = application.getRealPath("f1print/MStemplate") + "/";
	loggerDebug("MeetF1BatchPSave","标准路径===" + tPath);
	CError cError = new CError();
	boolean operFlag = true;
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	int chkCount = Integer.parseInt(request.getParameter("ChkCount"));
	String tPolGridChk[] = request.getParameterValues("PolGridChk");
	String tInpPolGridChk[] = request.getParameterValues("InpPolGridChk");

	String bPrtSeq[] = request.getParameterValues("PolGrid1");
	String bMissionID[] = request.getParameterValues("PolGrid6");
	String bSubMissionID[] = request.getParameterValues("PolGrid7");
	String bPrtNo[] = request.getParameterValues("PolGrid2");
	String bContNo[] = request.getParameterValues("PolGrid2");
	String bOldPrtSeq[] = request.getParameterValues("PolGrid8");
	String tCode = request.getParameter("NoticeType");
	if (tCode == null) {
		tCode = "04";
	}

	String[] tStrPrtSeq = new String[chkCount];
	String[] tStrPrtNo = new String[chkCount];
	String[] tStrContNo = new String[chkCount];
	String[] tStrMissionID = new String[chkCount];
	String[] tStrSubMissionID = new String[chkCount];
	String[] strVFPathNameList = new String[chkCount];

	for (int a = 0, j = 0; a < tInpPolGridChk.length; a++) {
		if (tInpPolGridChk[a].equals("0")) {
			continue;
		}
		
		newFile = new BookModelImpl();
		targetFile = null;
		vfc = new VtsFileCombine();

		jspOut = out;

		String PrtSeq = bPrtSeq[a];
		String OldPrtSeq = bOldPrtSeq[a];
		String tContNo = bContNo[a];

		tStrPrtSeq[j] = bPrtSeq[a];
		tStrPrtNo[j] = bPrtNo[a];
		tStrContNo[j] = bContNo[a];
		tStrMissionID[j] = bMissionID[a];
		tStrSubMissionID[j] = bSubMissionID[a];


		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(PrtSeq);
		tLOPRTManagerSchema.setCode(tCode);
		loggerDebug("MeetF1BatchPSave","tCode:" + tCode);
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput) session.getValue("GI");

		VData tVData = new VData();
		VData mResult = new VData();
		CErrors mErrors = new CErrors();
		tVData.addElement(tLOPRTManagerSchema);
		tVData.addElement(tG);
		tVData.addElement(tPath);
		MeetF1PUI tMeetF1PUI = new MeetF1PUI();

		ExeSQL exeSql = new ExeSQL();
		SSRS testSSRS = new SSRS();
		testSSRS = exeSql
				.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
		//String strTemplatePath = testSSRS.GetText(1,1); //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
		String strTemplatePath = tPath;
		if (OldPrtSeq != null && !OldPrtSeq.equals("")) {
			testSSRS = exeSql
					.execSQL("select askcode from LCRReportPrt where PrtSeq='"
							+ OldPrtSeq + "'");
			loggerDebug("MeetF1BatchPSave","使用旧的流水号查询===");
		} else {
			testSSRS = exeSql
					.execSQL("select askcode from LCRReportPrt where PrtSeq='"
							+ PrtSeq + "'");
			loggerDebug("MeetF1BatchPSave","使用新的流水号查询===");
		}
		String remark = "";

		loggerDebug("MeetF1BatchPSave","问卷的行数===" + testSSRS.MaxRow);
		for (int i = 1; i <= testSSRS.MaxRow; i++) {
			String type = testSSRS.GetText(i, 1);
			if (type.equals("87")) {
				if (!getFile(tVData, "PRINT1", "liveData.vts")) {
					error();
					return;
				}
			} else if (type.equals("88")) {
				if (!getFile(tVData, "PRINT2", "financeData.vts")) {
					error();
					return;
				}
			} else if (type.equals("89")) {
				if (!getFile(tVData, "PRINTa", "DXData.vts")) {
					error();
					return;
				}
			} else if (type.equals("90")) {
				if (!getFile(tVData, "PRINTb", "XCData.vts")) {
					error();
					return;
				}
			} else if (type.equals("91")) {
				if (!getFile(tVData, "PRINTc", "TNBData.vts")) {
					error();
					return;
				}
			} else if (type.equals("92")) {
				if (!getFile(tVData, "PRINTd", "JZXData.vts")) {
					error();
					return;
				}
			} else if (type.equals("93")) {
				if (!getFile(tVData, "PRINTe", "XTData.vts")) {
					error();
					return;
				}
			} else if (type.equals("94")) {
				if (!getFile(tVData, "PRINTf", "TTTYData.vts")) {
					error();
					return;
				}
			} else if (type.equals("95")) {
				if (!getFile(tVData, "PRINTg", "NXJKBCWJData.vts")) {
					error();
					return;
				}
			} else if (type.equals("96")) {
				if (!getFile(tVData, "PRINTh", "GYData.vts")) {
					error();
					return;
				}
			} else if (type.equals("97")) {
				if (!getFile(tVData, "PRINTi", "HXDData.vts")) {
					error();
					return;
				}
			} else if (type.equals("98")) {
				if (!getFile(tVData, "PRINTj", "XHXTData.vts")) {
					error();
					return;
				}
			} else if (type.equals("99")) {
				if (!getFile(tVData, "PRINTk", "YYEJKWJData.vts")) {
					error();
					return;
				}
			} else if (type.equals("100")) {
				if (!getFile(tVData, "PRINTl", "sportdcbData.vts")) {
					error();
					return;
				}
			} else if (type.equals("101")) {
				if (!getFile(tVData, "PRINTm", "CZWYWJData.vts")) {
					error();
					return;
				}
			} else if (type.equals("102")) {
				if (!getFile(tVData, "PRINTn", "BSWJData.vts")) {
					error();
					return;
				}
			} else if (type.equals("103")) {
				if (!getFile(tVData, "PRINTo", "JbwjGxaData.vts")) {
					error();
					return;
				}
			} else if (type.equals("104")) {
				if (!getFile(tVData, "PRINTp", "JszzzyzwjData.vts")) {
					error();
					return;
				}
			} else if (type.equals("105")) {
				if (!getFile(tVData, "PRINTq", "WcrydcwjData.vts")) {
					error();
					return;
				}
			} else if (type.equals("106")) {
				if (!getFile(tVData, "PRINTr", "WdrstbwjData.vts")) {
					error();
					return;
				}
			} else if (type.equals("107")) {
				if (!getFile(tVData, "PRINTs", "JbwjXhxkyData.vts")) {
					error();
					return;
				}
			} else if (type.equals("108")) {
				if (!getFile(tVData, "PRINTt", "YhyycyrywjData.vts")) {
					error();
					return;
				}
			}

		}
		if (getFile(tVData, "PRINT3", "announceData.vts")) {

			String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			LDSysVarSet tLDSysVarSet = tLDSysVarDB
					.executeQuery(strSql1);
			LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
			String strFilePath = tLDSysVarSchema.getV("SysVarValue");
		//	String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";
			String strVFFileName = strFilePath + j + ".vts";
			//获取存放临时文件的路径
			String strRealPath = application.getRealPath("/").replace(
					'\\', '/');
			String strVFPathName = strRealPath + strVFFileName;
			strVFPathNameList[j] = strVFPathName;
			targetFile.write(strVFPathName, new WriteParams());
			try {
				targetFile.destroy();
				vfc = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else {
			operFlag = false;
		}

		j++;
	}
	if (operFlag == true && FlagStr.equals("")) {
		//end add//////////////////////////////////////////////////////
		

		session.putValue("PrintNo", tStrPrtSeq);
		session.putValue("MissionID", tStrMissionID);
		session.putValue("SubMissionID", tStrSubMissionID);
		session.putValue("Code", tCode); //核保通知书类别
		session.putValue("ContNo", tStrContNo);
		session.putValue("PrtNo", tStrPrtNo);
		session.putValue("vfp", strVFPathNameList);
		request.getRequestDispatcher("./GetF1BatchPrintJ1.jsp")
				.forward(request, response);

	} else {
		FlagStr = "Fail";
%>
<html>
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">
      alert("<%=Content%>");
      top.close();

      //window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");

      </script>
</html>
<%
	}
%>

