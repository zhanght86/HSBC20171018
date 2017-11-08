<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%@page import="com.f1j.ss.*"%>

<%!
	JspWriter jspOut;
  String Content;
  OperatorNoticeF1PUI tOperatorNoticeF1PUI = new OperatorNoticeF1PUI();
  VData mResult;

	String mSQL = "select * from ldsysvar where Sysvar='VTSFilePath'"; 
			LDSysVarDB tempLDSysVarDB = new LDSysVarDB();
			LDSysVarSet tempLDSysVarSet = tempLDSysVarDB.executeQuery(mSQL);
			LDSysVarSchema tempLDSysVarSchema = tempLDSysVarSet.get(1);
			String strFilePath = tempLDSysVarSchema.getV("SysVarValue");
			String mStrVFFileName = strFilePath + FileQueue.getFileName() + ".vts";

//			获取存放临时文件的路径
			String mStrRealPath = "";



  String tPath ;

  BookModelImpl newFile ;
  BookModelImpl targetFile  ;
  VtsFileCombine vfc ;

boolean getFile(VData tVData,String operate,String templateFile ) throws Exception
{

        if(!tOperatorNoticeF1PUI.submitData(tVData,operate))
        {
          Content=tOperatorNoticeF1PUI.mErrors.getFirstError().toString();
          return false;
        }
		String mStrVFPathName = mStrRealPath + mStrVFFileName;

        
          mResult = tOperatorNoticeF1PUI.getResult();
          XmlExport txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
            Content="没有得到要显示的数据文件";
                return false;
          }
          

  //templateFile = tPath + templateFile;
  
  loggerDebug("UWF1BatchPSave","Operate:"+operate+" before Combine:"+templateFile);
//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
  CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),tPath);

  ByteArrayOutputStream outStream = new ByteArrayOutputStream();

	loggerDebug("UWF1BatchPSave","before output...");
  tcombineVts.output(outStream);
	loggerDebug("UWF1BatchPSave","after output...");
	tcombineVts.destroy();
//	System.gc();

  byte[] bytes = outStream.toByteArray();

//  byte[] data = new byte[4098];
//  while((i=data.write(data)>-1)
//  {
//  	
//  
//  }
  InputStream inputStream = new ByteArrayInputStream(bytes);
// AccessVtsFile.saveToFile(outStream,mStrVFPathName);
// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
 outStream.close();
 
  try
  {  
  		loggerDebug("UWF1BatchPSave","before new newFile");
  		newFile = new BookModelImpl();
//      newFile.read(new FileInputStream("c:\\"+templateFile));
  		loggerDebug("UWF1BatchPSave","before read");
      newFile.read(inputStream);
  		loggerDebug("UWF1BatchPSave","after read");			
    
//      newFile.setSelection(0,0,newFile.getLastRow(),newFile.getLastCol());
   }catch (Exception ex)
  {
      ex.printStackTrace();
      Content = "读取文件错误."+ex.getMessage();
      return false;
  }
  
  if (targetFile == null)
  {
//      targetFile = newFile;  //同一个对象指针错误，切切
	  loggerDebug("UWF1BatchPSave","before new target...");
	  targetFile = new BookModelImpl();
	  loggerDebug("UWF1BatchPSave","after new target...");
	  targetFile.copyAll(newFile);
      loggerDebug("UWF1BatchPSave","init new file");
  }
  else
  {
	loggerDebug("UWF1BatchPSave","before combine");
	targetFile = vfc.dataCombine(newFile,targetFile);
	 loggerDebug("UWF1BatchPSave","after combine:"+targetFile.getSelection());
  }

	inputStream.close();
	newFile.destroy();
  return true;
}	
          
          
void error() throws Exception
{
	loggerDebug("UWF1BatchPSave",Content);
	jspOut.println(Content);
}
%>
<%
		loggerDebug("UWF1BatchPSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";


	int chkCount = Integer.parseInt(request.getParameter("ChkCount"));
	String tPolGridChk[] = request.getParameterValues("PolGridChk");
	String tInpPolGridChk[] = request.getParameterValues("InpPolGridChk");

	String bPrtSeq[] = request.getParameterValues("PolGrid1");
	String bOldPrtSeq[] = request.getParameterValues("PolGrid10");
	String bPrtNo[] = request.getParameterValues("PolGrid2");
	String bContNo[] = request.getParameterValues("PolGrid2");
	String bMissionID[] = request.getParameterValues("PolGrid8");
	String bSubMissionID[] = request.getParameterValues("PolGrid9");
	String tNoticeType = request.getParameterValues("PolGrid4")[0];


	String[] tStrPrtSeq = new String[chkCount];
	String[] tStrPrtNo = new String[chkCount];
	String[] tStrContNo = new String[chkCount];
	String[] tStrMissionID = new String[chkCount];
	String[] tStrSubMissionID = new String[chkCount];
	String[] strVFPathNameList = new String[chkCount];
	XmlExport[] txmlExportList = new XmlExport[chkCount];
	for (int a = 0, j = 0; a < tInpPolGridChk.length; a++) {

		if (tInpPolGridChk[a].equals("0")) {
			continue;
		}

			newFile = new BookModelImpl();
		targetFile = null ;
		vfc = new VtsFileCombine();
		jspOut =out ;

		
	String PrtSeq = bPrtSeq[a];
	String tMissionID = bMissionID[a];
	String tSubMissionID = bSubMissionID[a];
	String tPrtNo	 = bPrtNo[a];
	String tContNo	 = bContNo[a];
	
	tStrPrtSeq[j] = bPrtSeq[a];
	tStrPrtNo[j] = bPrtNo[a];
	tStrContNo[j] = bContNo[a];
	tStrMissionID[j] = bMissionID[a];
	tStrSubMissionID[j] = bSubMissionID[a];
	
	tPath = application.getRealPath("f1print/MStemplate") + "/"; //add by liuqh 2008-11-21
	mStrRealPath = application.getRealPath("/").replace('\\', '/');
	
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
    tVData.addElement(tPath);
    
    XmlExport txmlExport = new XmlExport();
    
  if(tNoticeType.equals("81")||tNoticeType.equals("84")||tNoticeType.equals("86"))
{UWPartCancelPrintUI tUWPartCancelPrintUI = new UWPartCancelPrintUI();
if(!tUWPartCancelPrintUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tUWPartCancelPrintUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tUWPartCancelPrintUI.getResult();
	loggerDebug("UWF1BatchPSave","mResult="+mResult);
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}
	
}
}
//tongmeng 2007-11-12 modify
//增加核保通知书的打印
//打印内容:1 核保通知书(打印类)打印 2 核保通知书(非打印类)打印 3 业务员通知书
if(tNoticeType.equals("TB89")||tNoticeType.equals("14")||tNoticeType.equals("TB90"))
{
	newFile = new BookModelImpl();
	  targetFile = null ;
	  vfc = new VtsFileCombine();
	  if(getFile(tVData, "PRINT", "aData.vts"))
	  {
	  
	  ExeSQL exeSql = new ExeSQL();
	  SSRS testSSRS = new SSRS();
	  testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
	  String tPath = testSSRS.GetText(1,1);
	  testSSRS = exeSql
		.execSQL(" select concat(concat(a.asktype,''),a.askcontentno),a.asktypename,a.askcontentno,a.askcontentname,"
				+" (select othersign from ldcode where substr(codetype,14,1)=a.asktype and code =a.askcontentno and codetype like 'Questionnaire%'),"
				+" (select codealias from ldcode where substr(codetype,14,1)=a.asktype and code =a.askcontentno and codetype like 'Questionnaire%') from" 
				+" lcquestionnaire a where proposalcontno = '"+tContNo+"'" 
				+" and serialno in (select serialno from lcissuepol where prtseq in " 
				+" (select oldprtseq from loprtmanager where otherno='"+tContNo+"' and prtseq='"+PrtSeq+"') ) order by a.asktype");
	  loggerDebug("UWF1BatchPSave","testSSRS.getMaxRow():"+testSSRS.getMaxRow());
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		int array = testSSRS.getMaxRow();
		String[] paths = new String[array + 1];
		if (testSSRS.MaxRow > 0) {
			for(int i=1;i<=testSSRS.MaxRow;i++)
			{
			  String type = testSSRS.GetText(i,1);
			  String tVTS =testSSRS.GetText(i,6);
			  String operate =testSSRS.GetText(i,5); //都一样，都是PRINTHQ
			  operate =operate+"-"+tVTS; //将问卷类型和模板名称拼成一个参数传入
			  if (!getFile(tVData,operate,tVTS )){error(); return ;} 
			  //if(type.equals("H001"))        { if (!getFile(tVData,"PRINTdis","DisabilityData.vts"       )){error(); return ;} }
			  //else if(type.equals("H005"))   { if (!getFile(tVData,"PRINTdis","WaistData.vts"    )){error(); return ;} }
			  //else if(type.equals("H009"))   { if (!getFile(tVData,"PRINTepi","EpilepsyData.vts"         )){error(); return ;} }
			  //else if(type.equals("O001"))   { if (!getFile(tVData,"PRINTabr","AbroadData.vts"         )){error(); return ;} } //出国人员问卷
			  //else if(type.equals("FO001"))   { if (!getFile(tVData,"PRINTaf1","Addfinance1Data.vts"         )){error(); return ;} } //财务情况补充问卷（甲）
			  //else if(type.equals("FO002"))   { if (!getFile(tVData,"PRINTd9","Addfinance2Data.vts"         )){error(); return ;} } //财务情况补充问卷（乙）
			  //else if(type.equals("O004"))   { if (!getFile(tVData,"PRINTd1","DangerOccupation.vts"         )){error(); return ;} } //危险职业问卷
			  //else if(type.equals("O002"))   { if (!getFile(tVData,"PRINTd2","Driver.vts"         )){error(); return ;} } //机动车驾驶执照持有者问卷
			  //else if(type.equals("O003"))   { if (!getFile(tVData,"PRINTd3","Racing.vts"         )){error(); return ;} } //赛车问卷
			  //else if(type.equals("O005"))   { if (!getFile(tVData,"PRINTd4","Jumping.vts"         )){error(); return ;} } //跳伞问卷
			  //else if(type.equals("O006"))   { if (!getFile(tVData,"PRINTd5"," Climbing.vts"         )){error(); return ;} } //爬山问卷
			  //else if(type.equals("O007"))   { if (!getFile(tVData,"PRINTd6"," DangerSports.vts"         )){error(); return ;} } //危险运动问卷
			  //else if(type.equals("O008"))   { if (!getFile(tVData,"PRINTd7"," OceanShipping.vts"         )){error(); return ;} } //海运问卷
			  //else if(type.equals("O010"))   { if (!getFile(tVData,"PRINTd8"," Aviation.vts"         )){error(); return ;} } //航空问卷
			  //else if(type.equals("H009"))   { if (!getFile(tVData,"PRINTa","EpilepsyData.vts"         )){error(); return ;} }
			  //if(type.equals("87"))        { if (!getFile(tVData,"PRINT1","liveData.vts"       )){error(); return ;} }
			  //else if(type.equals("88"))   { if (!getFile(tVData,"PRINT2","financeData.vts"    )){error(); return ;} }
			  //else if(type.equals("89"))   { if (!getFile(tVData,"PRINTa","DXData.vts"         )){error(); return ;} }
			  //else if(type.equals("90"))   { if (!getFile(tVData,"PRINTb","XCData.vts"         )){error(); return ;} }
			  //else if(type.equals("91"))   { if (!getFile(tVData,"PRINTc","TNBData.vts"        )){error(); return ;} }
			  //else if(type.equals("92"))   { if (!getFile(tVData,"PRINTd","JZXData.vts"        )){error(); return ;} }
			  //else if(type.equals("93"))   { if (!getFile(tVData,"PRINTe","XTData.vts"         )){error(); return ;} }
			  //else if(type.equals("94"))   { if (!getFile(tVData,"PRINTf","TTTYData.vts"       )){error(); return ;} }
			  //else if(type.equals("95"))   { if (!getFile(tVData,"PRINTg","NXJKBCWJData.vts"   )){error(); return ;} }
			  //else if(type.equals("96"))   { if (!getFile(tVData,"PRINTh","GYData.vts"         )){error(); return ;} }
			  //else if(type.equals("97"))   { if (!getFile(tVData,"PRINTi","HXDData.vts"        )){error(); return ;} }
			  //else if(type.equals("98"))   { if (!getFile(tVData,"PRINTj","XHXTData.vts"       )){error(); return ;} }
			  //else if(type.equals("99"))   { if (!getFile(tVData,"PRINTk","YYEJKWJData.vts"    )){error(); return ;} }
			  //else if(type.equals("100"))  { if (!getFile(tVData,"PRINTl","sportdcbData.vts"   )){error(); return ;} }
			  //else if(type.equals("101"))  { if (!getFile(tVData,"PRINTm","CZWYWJData.vts"     )){error(); return ;} }
			  //else if(type.equals("102"))  { if (!getFile(tVData,"PRINTn","BSWJData.vts"       )){error(); return ;} }
			  //else if(type.equals("103"))  { if (!getFile(tVData,"PRINTo","JbwjGxaData.vts"    )){error(); return ;} }
			  //else if(type.equals("104"))  { if (!getFile(tVData,"PRINTp","JszzzyzwjData.vts"  )){error(); return ;} }
			  //else if(type.equals("105"))  { if (!getFile(tVData,"PRINTq","WcrydcwjData.vts"   )){error(); return ;} }
			  //else if(type.equals("106"))  { if (!getFile(tVData,"PRINTr","WdrstbwjData.vts"   )){error(); return ;} }
			  //else if(type.equals("107"))  { if (!getFile(tVData,"PRINTs","JbwjXhxkyData.vts"  )){error(); return ;} }
			  //else if(type.equals("108"))  { if (!getFile(tVData,"PRINTt","YhyycyrywjData.vts" )){error(); return ;} }
			}
		}
	  }
			//if (getFile(tVData, "PRINT", "aData.vts"))
			{
			   
			String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
			LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
			String strFilePath = tLDSysVarSchema.getV("SysVarValue");
			String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";

//			获取存放临时文件的路径
			String strRealPath = application.getRealPath("/").replace('\\', '/');
			String strVFPathName = strRealPath + strVFFileName;
			

			try {
		//	newFile.destroy();
		    targetFile.write(strVFPathName,new WriteParams());
			targetFile.destroy() ;
			vfc = null;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
//			end add//////////////////////////////////////////////////////

			strVFPathNameList[j]=strVFPathName;
			FlagStr ="1";
			//request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
			}
}		
//tongmeng 2007-11-28 add
//增加拒保通知书打印
if(tNoticeType.equals("00"))
{
	 RANF1PUI_MS tRANF1PUI = new RANF1PUI_MS();
	if(!tRANF1PUI.submitData(tVData,"CONFIRM")) 
	{
		 operFlag=false;
       Content=tRANF1PUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tRANF1PUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }else{
			txmlExportList[j]=	txmlExport;
		}
	  }
}
//延期通知书
if(tNoticeType.equals("06"))
{
	 DANF1PUI_MS tDANF1PUI = new DANF1PUI_MS();
	if(!tDANF1PUI.submitData(tVData,"CONFIRM")) 
	{
		 operFlag=false;
       Content=tDANF1PUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tDANF1PUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }else{
			txmlExportList[j]=	txmlExport;
		}
	  }
}
//撤单通知书
if(tNoticeType.equals("09"))
{
	 AppCancelF1PUI_MS tAppCancelF1PUI = new AppCancelF1PUI_MS();
	if(!tAppCancelF1PUI.submitData(tVData,"CONFIRM")) 
	{
		 operFlag=false;
       Content=tAppCancelF1PUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tAppCancelF1PUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }else{
			txmlExportList[j]=	txmlExport;
		}
	  }
}
//首期交费通知书
if(tNoticeType.equals("07"))
{
	 FirstPayF1PUI_MS tFirstPayF1PUI = new FirstPayF1PUI_MS();
	if(!tFirstPayF1PUI.submitData(tVData,"CONFIRM")) 
	{
		 operFlag=false;
       Content=tFirstPayF1PUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tFirstPayF1PUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }else{
			txmlExportList[j]=	txmlExport;
		}
	  }
}
if(tNoticeType.equals("83"))
{
AddPremPrintUI tAddPremPrintUI = new AddPremPrintUI();
if(!tAddPremPrintUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tAddPremPrintUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tAddPremPrintUI.getResult();
	
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}

}
}


if(tNoticeType.equals("82"))
{
SpecPrintUI tSpecPrintUI = new SpecPrintUI();
if(!tSpecPrintUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tSpecPrintUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tSpecPrintUI.getResult();
	
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}

}
}


if(tNoticeType.equals("85"))
{
QuotaPrintUI tQuotaPrintUI = new QuotaPrintUI();
if(!tQuotaPrintUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tQuotaPrintUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tQuotaPrintUI.getResult();
	
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}

}
}


if(tNoticeType.equals("87"))
{
AddRefundmentUI tAddRefundmentUI = new AddRefundmentUI();
if(!tAddRefundmentUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tAddRefundmentUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tAddRefundmentUI.getResult();
	
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}

}
}


if(tNoticeType.equals("88"))
{
FBCGUI tFBCGUI = new FBCGUI();
if(!tFBCGUI.submitData(tVData,"PRINT"))
{
	operFlag=false;
	Content=tFBCGUI.mErrors.getFirstError().toString();
	
}
else
{
	mResult = tFBCGUI.getResult();
	
	txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	
	if(txmlExport==null)
	{
		operFlag=false;
		Content="没有得到要显示的数据文件";
	}else{
		txmlExportList[j]=	txmlExport;
	}

}
}
	j++;
}
	
	if (operFlag==true&&FlagStr.equals(""))
	{
	session.putValue("PrintNo",tStrPrtSeq );
	session.putValue("MissionID",tStrMissionID );
	session.putValue("SubMissionID",tStrSubMissionID );
	session.putValue("Code",tNoticeType );	//核保通知书类别
	session.putValue("PrtNo",tStrPrtNo );
	session.putValue("ContNo",tStrContNo );	
	session.putValue("xel", txmlExportList);
  //request.getRequestDispatcher("./GetF1Print.jsp").forward(request,response);
  request.getRequestDispatcher("./GetF1BatchPrint.jsp").forward(request,response);
	//response.sendRedirect("./GetF1Print.jsp");
	}else if(operFlag==true&&FlagStr.equals("1"))
	{
		session.putValue("PrintNo",tStrPrtSeq );
		session.putValue("MissionID",tStrMissionID );
		session.putValue("SubMissionID",tStrSubMissionID );
		session.putValue("Code",tNoticeType );	//核保通知书类别
		session.putValue("PrtNo",tStrPrtNo );
		session.putValue("ContNo",tStrContNo );	
		session.putValue("vfp", strVFPathNameList);
		//session.putValue("PrintStream", txmlExport.getInputStream());
		request.getRequestDispatcher("./GetF1BatchPrintJ1.jsp").forward(request,response);
	}
	else
	{
    	FlagStr = "Fail";

%>
<html>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">	
	alert("<%=Content%>");
	top.close();
	
</script>
</html>
<%
  }

%>
