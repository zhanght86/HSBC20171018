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
  RnewNoticeF1PUI tRnewNoticeF1PUI = new RnewNoticeF1PUI();
  VData mResult;

//  tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
//
//  ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//  tcombineVts.output(dataStream);
//
//  //把dataStream存储到磁盘文件
//  //loggerDebug("RnewUWF1PSave","存储文件到"+strVFPathName);
//  AccessVtsFile.saveToFile(dataStream,strVFPathName);
//  loggerDebug("RnewUWF1PSave","==> Write VTS file to disk ");

  String tPath ;

  BookModelImpl newFile ;
  BookModelImpl targetFile  ;
  VtsFileCombine vfc ;

boolean getFile(VData tVData,String operate,String templateFile ) throws Exception
{

        if(!tRnewNoticeF1PUI.submitData(tVData,operate))
        {
          Content=tRnewNoticeF1PUI.mErrors.getFirstError().toString();
          return false;
        }
       
        
          mResult = tRnewNoticeF1PUI.getResult();
          XmlExport txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
            Content="没有得到要显示的数据文件";
                return false;
          }
          

  //templateFile = tPath + templateFile;
  
  loggerDebug("RnewUWF1PSave","Operate:"+operate+" before Combine:"+templateFile);
//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
  CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),tPath);

  ByteArrayOutputStream outStream = new ByteArrayOutputStream();

	loggerDebug("RnewUWF1PSave","before output...");
  tcombineVts.output(outStream);
	loggerDebug("RnewUWF1PSave","after output...");
	tcombineVts.destroy();
//	System.gc();

  byte[] bytes = outStream.toByteArray();
  loggerDebug("RnewUWF1PSave","byte.length="+bytes.length);

//  byte[] data = new byte[4098];
//  while((i=data.write(data)>-1)
//  {
//  	
//  
//  }
  InputStream inputStream = new ByteArrayInputStream(bytes);
 //AccessVtsFile.saveToFile(outStream,"c:\\1.vts");
// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
 outStream.close();
 
  try
  {  
  		loggerDebug("RnewUWF1PSave","before new newFile");
  		newFile = new BookModelImpl();
//      newFile.read(new FileInputStream("c:\\"+templateFile));
  		loggerDebug("RnewUWF1PSave","before read");
      newFile.read(inputStream);
  		loggerDebug("RnewUWF1PSave","after read");			
    
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
	  loggerDebug("RnewUWF1PSave","before new target...");
	  targetFile = new BookModelImpl();
	  loggerDebug("RnewUWF1PSave","after new target...");
	  targetFile.copyAll(newFile);
      loggerDebug("RnewUWF1PSave","init new file");
  }
  else
  {
	loggerDebug("RnewUWF1PSave","before combine");
	targetFile = vfc.dataCombine(newFile,targetFile);
//	 loggerDebug("RnewUWF1PSave","after combine:"+targetFile.getSelection());
  }

	inputStream.close();
	newFile.destroy();
  return true;
}	
          
          
void error() throws Exception
{
	loggerDebug("RnewUWF1PSave",Content);
	jspOut.println(Content);
}
%>
<%
		loggerDebug("RnewUWF1PSave","start");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
		
		newFile = new BookModelImpl();
		targetFile = null ;
		vfc = new VtsFileCombine();
		jspOut =out ;

		
	String PrtSeq=request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tPrtNo	 = request.getParameter("PrtNo");
	String tContNo	 = request.getParameter("ContNo");
	String tNoticeType = request.getParameter("NoticeType");
	tPath = application.getRealPath("f1print/MStemplate") + "/"; //add by liuqh 2008-11-21
	
  loggerDebug("RnewUWF1PSave","tMissionID="+tMissionID);
	loggerDebug("RnewUWF1PSave","tSubMissionID="+tSubMissionID);
	loggerDebug("RnewUWF1PSave","1111111111111111111");   
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("RnewUWF1PSave","222222222222222222");   
	loggerDebug("RnewUWF1PSave",PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
    tVData.addElement(tPath);
    
    XmlExport txmlExport = new XmlExport();
 loggerDebug("RnewUWF1PSave","33333333333333333333");   
//tongmeng 2007-11-12 modify
//增加核保通知书的打印
//打印内容:1 核保通知书(打印类)打印 2 核保通知书(非打印类)打印 3 业务员通知书
if(tNoticeType.equals("45"))
{
	  newFile = new BookModelImpl();
	  targetFile = null ;
	  vfc = new VtsFileCombine();
	  ExeSQL exeSql = new ExeSQL();
	  SSRS testSSRS = new SSRS();
	  testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
	  String tPath = testSSRS.GetText(1,1);
	  testSSRS = exeSql
		.execSQL(" select concat(concat(a.asktype,''),a.askcontentno),a.asktypename,a.askcontentno,a.askcontentname from" 
				+" lcquestionnaire a where proposalcontno = '"+tContNo+"'" 
				+" and serialno in (select serialno from Rnewissuepol where prtseq='" 
				+  PrtSeq +"')");
	  loggerDebug("RnewUWF1PSave","testSSRS.getMaxRow():"+testSSRS.getMaxRow());
		VtsFileCombine vtsfilecombine = new VtsFileCombine();
		int array = testSSRS.getMaxRow();
		String[] paths = new String[array + 1];
		if (testSSRS.MaxRow > 0) {
			for(int i=1;i<=testSSRS.MaxRow;i++)
			{
			  String type = testSSRS.GetText(i,1);
			  if(type.equals("H001"))        { if (!getFile(tVData,"PRINTdis","DisabilityData.vts"       )){error(); return ;} }
			  //else if(type.equals("H005"))   { if (!getFile(tVData,"PRINTdis","WaistData.vts"    )){error(); return ;} }
			  else if(type.equals("H009"))   { if (!getFile(tVData,"PRINTepi","EpilepsyData.vts"         )){error(); return ;} }
			  else if(type.equals("O001"))   { if (!getFile(tVData,"PRINTabr","AbroadData.vts"         )){error(); return ;} }
			  else if(type.equals("FO001"))   { if (!getFile(tVData,"PRINTaf1","Addfinance1Data.vts"         )){error(); return ;} }
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
			if (getFile(tVData, "PRINT", "aData.vts"))
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
			targetFile.write(strVFPathName,new WriteParams());

			try {
			newFile.destroy();
			targetFile.destroy() ;
			vfc = null;
			}
			catch(Exception ex)
			{
				
			}
//			end add//////////////////////////////////////////////////////
			session.putValue("PrintNo",PrtSeq );
			session.putValue("MissionID",tMissionID );
			session.putValue("SubMissionID",tSubMissionID );
			session.putValue("Code",tNoticeType );	//核保通知书类别
			session.putValue("PrtNo",tPrtNo );
			session.putValue("ContNo",tContNo );	
			session.putValue("RealPath",strVFPathName );
			FlagStr ="1";
			//request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
			}
}		

	
	if (operFlag==true&&FlagStr.equals(""))
	{
	session.putValue("PrintNo",PrtSeq );
	session.putValue("MissionID",tMissionID );
	session.putValue("SubMissionID",tSubMissionID );
	session.putValue("Code",tNoticeType );	//核保通知书类别
	session.putValue("PrtNo",tPrtNo );
	session.putValue("ContNo",tContNo );	
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("RnewUWF1PSave","put session value");
  //request.getRequestDispatcher("./GetF1Print.jsp").forward(request,response);
  request.getRequestDispatcher("./GetF1Print.jsp").forward(request,response);
	//response.sendRedirect("./GetF1Print.jsp");
	}else if(operFlag==true&&FlagStr.equals("1"))
	{
		loggerDebug("RnewUWF1PSave","put session value");
		request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
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
