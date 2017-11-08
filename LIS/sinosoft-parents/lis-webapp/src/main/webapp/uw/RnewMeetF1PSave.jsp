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

<%!
	JspWriter jspOut;
  String Content;
  //RnewMeetF1PUI tRnewMeetF1PUI = new RnewMeetF1PUI();
  VData mResult;
  String tPath ;

  BookModelImpl newFile ;
  BookModelImpl targetFile  ;
  VtsFileCombine vfc ;

boolean getFile(VData tVData,String operate,String templateFile ) throws Exception
{
		String busiName="RnewMeetF1PUI";
    	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    	if(!tBusinessDelegate.submitData(tVData,operate,busiName)){
    		Content=tBusinessDelegate.getCErrors().getFirstError().toString();
    		return false;
    	}
       // if(!tRnewMeetF1PUI.submitData(tVData,operate))
        //{
         // Content=tRnewMeetF1PUI.mErrors.getFirstError().toString();
         // return false;
      //  }
       
        
          mResult = tBusinessDelegate.getResult();
          XmlExport txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
            Content="没有得到要显示的数据文件";
                return false;
          }
          

  //templateFile = tPath + templateFile;
  
  loggerDebug("RnewMeetF1PSave","Operate:"+operate+" before Combine:"+templateFile);
//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
  CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),tPath);

  ByteArrayOutputStream outStream = new ByteArrayOutputStream();

	loggerDebug("RnewMeetF1PSave","before output...");
  tcombineVts.output(outStream);
	loggerDebug("RnewMeetF1PSave","after output...");
	tcombineVts.destroy();
//	System.gc();

  byte[] bytes = outStream.toByteArray();
  loggerDebug("RnewMeetF1PSave","byte.length="+bytes.length);


  InputStream inputStream = new ByteArrayInputStream(bytes);
 //AccessVtsFile.saveToFile(outStream,"c:\\1.vts");
// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
 outStream.close();
 
  try
  {  
  		loggerDebug("RnewMeetF1PSave","before new newFile");
  		newFile = new BookModelImpl();
//      newFile.read(new FileInputStream("c:\\"+templateFile));
  		loggerDebug("RnewMeetF1PSave","before read");
      newFile.read(inputStream);
  		loggerDebug("RnewMeetF1PSave","after read");			
    
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
	  loggerDebug("RnewMeetF1PSave","before new target...");
	  targetFile = new BookModelImpl();
	  loggerDebug("RnewMeetF1PSave","after new target...");
	  targetFile.copyAll(newFile);
      loggerDebug("RnewMeetF1PSave","init new file");
  }
  else
  {
	loggerDebug("RnewMeetF1PSave","before combine");
	targetFile = vfc.dataCombine(newFile,targetFile);
//	 loggerDebug("RnewMeetF1PSave","after combine:"+targetFile.getSelection());
  }

	inputStream.close();
	newFile.destroy();
  return true;
}	
          
          
void error() throws Exception
{
	loggerDebug("RnewMeetF1PSave",Content);
	jspOut.println(Content);
}
%>

<%
  newFile = new BookModelImpl();
  targetFile = null ;
  vfc = new VtsFileCombine();

jspOut =out ;

loggerDebug("RnewMeetF1PSave","--------------------start------------------");
tPath = application.getRealPath("f1print/MStemplate") + "/";
loggerDebug("RnewMeetF1PSave","标准路径==="+tPath);
CError cError = new CError( );
boolean operFlag=true;
String tRela  = "";
String FlagStr = "";
String Content = "";
String strOperation = "";
String PrtSeq=request.getParameter("PrtSeq");
String OldPrtSeq = request.getParameter("OldPrtSeq");
String tMissionID = request.getParameter("MissionID");
String tSubMissionID = request.getParameter("SubMissionID");
String tContNo	 = request.getParameter("ContNo");
loggerDebug("RnewMeetF1PSave","合同号==="+tContNo);
loggerDebug("RnewMeetF1PSave","OldPrtSeq=="+OldPrtSeq);

String tCode = request.getParameter("NoticeType");
if(tCode==null)
{
  tCode="44";
}
LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
tLOPRTManagerSchema.setPrtSeq(PrtSeq);
tLOPRTManagerSchema.setCode(tCode);
loggerDebug("RnewMeetF1PSave",PrtSeq);
loggerDebug("RnewMeetF1PSave","tCode:"+tCode);
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

VData tVData = new VData();
VData mResult = new VData();
CErrors mErrors = new CErrors();
tVData.addElement(tLOPRTManagerSchema);
tVData.addElement(tG);
tVData.addElement(tPath);
BQMeetF1PUI tBQMeetF1PUI = new BQMeetF1PUI();


ExeSQL exeSql = new ExeSQL();
SSRS testSSRS = new SSRS();
testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
//String strTemplatePath = testSSRS.GetText(1,1); //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
String strTemplatePath=tPath;
if(OldPrtSeq!=null&&!OldPrtSeq.equals("")){
  testSSRS = exeSql.execSQL("select askcode from LCRReportPrt where PrtSeq='"+OldPrtSeq+"'");
  loggerDebug("RnewMeetF1PSave","使用旧的流水号查询===");
}
else
{
  testSSRS = exeSql.execSQL("select askcode from LCRReportPrt where PrtSeq='"+PrtSeq+"'");
  loggerDebug("RnewMeetF1PSave","使用新的流水号查询===");
}
String remark="";

loggerDebug("RnewMeetF1PSave","问卷的行数==="+testSSRS.MaxRow);
for(int i=1;i<=testSSRS.MaxRow;i++)
{
  String type = testSSRS.GetText(i,1);
  if(type.equals("87"))        { if (!getFile(tVData,"PRINT1","liveData.vts"       )){error(); return ;} }
  else if(type.equals("88"))   { if (!getFile(tVData,"PRINT2","financeData.vts"    )){error(); return ;} }
  else if(type.equals("89"))   { if (!getFile(tVData,"PRINTa","DXData.vts"         )){error(); return ;} }
  else if(type.equals("90"))   { if (!getFile(tVData,"PRINTb","XCData.vts"         )){error(); return ;} }
  else if(type.equals("91"))   { if (!getFile(tVData,"PRINTc","TNBData.vts"        )){error(); return ;} }
  else if(type.equals("92"))   { if (!getFile(tVData,"PRINTd","JZXData.vts"        )){error(); return ;} }
  else if(type.equals("93"))   { if (!getFile(tVData,"PRINTe","XTData.vts"         )){error(); return ;} }
  else if(type.equals("94"))   { if (!getFile(tVData,"PRINTf","TTTYData.vts"       )){error(); return ;} }
  else if(type.equals("95"))   { if (!getFile(tVData,"PRINTg","NXJKBCWJData.vts"   )){error(); return ;} }
  else if(type.equals("96"))   { if (!getFile(tVData,"PRINTh","GYData.vts"         )){error(); return ;} }
  else if(type.equals("97"))   { if (!getFile(tVData,"PRINTi","HXDData.vts"        )){error(); return ;} }
  else if(type.equals("98"))   { if (!getFile(tVData,"PRINTj","XHXTData.vts"       )){error(); return ;} }
  else if(type.equals("99"))   { if (!getFile(tVData,"PRINTk","YYEJKWJData.vts"    )){error(); return ;} }
  else if(type.equals("100"))  { if (!getFile(tVData,"PRINTl","sportdcbData.vts"   )){error(); return ;} }
  else if(type.equals("101"))  { if (!getFile(tVData,"PRINTm","CZWYWJData.vts"     )){error(); return ;} }
  else if(type.equals("102"))  { if (!getFile(tVData,"PRINTn","BSWJData.vts"       )){error(); return ;} }
  else if(type.equals("103"))  { if (!getFile(tVData,"PRINTo","JbwjGxaData.vts"    )){error(); return ;} }
  else if(type.equals("104"))  { if (!getFile(tVData,"PRINTp","JszzzyzwjData.vts"  )){error(); return ;} }
  else if(type.equals("105"))  { if (!getFile(tVData,"PRINTq","WcrydcwjData.vts"   )){error(); return ;} }
  else if(type.equals("106"))  { if (!getFile(tVData,"PRINTr","WdrstbwjData.vts"   )){error(); return ;} }
  else if(type.equals("107"))  { if (!getFile(tVData,"PRINTs","JbwjXhxkyData.vts"  )){error(); return ;} }
  else if(type.equals("108"))  { if (!getFile(tVData,"PRINTt","YhyycyrywjData.vts" )){error(); return ;} }


}
if (getFile(tVData, "PRINT3", "announceData.vts")){
	   
	String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
	LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
	LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
	String strFilePath = tLDSysVarSchema.getV("SysVarValue");
	String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";
	
	//获取存放临时文件的路径
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
	
	loggerDebug("RnewMeetF1PSave","tCode=="+tCode);
	//end add//////////////////////////////////////////////////////
	session.putValue("PrintNo", PrtSeq);
	session.putValue("MissionID", tMissionID);
	session.putValue("SubMissionID", tSubMissionID);
	session.putValue("Code", tCode); //核保通知书类别
	session.putValue("ContNo", tContNo);
	
	session.putValue("PrtNo", tContNo);
	//session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("RnewMeetF1PSave","put session value,ContNo:" + tContNo);
	loggerDebug("RnewMeetF1PSave","put session value==" + tMissionID);
	loggerDebug("RnewMeetF1PSave","put session value,SubMissionID:" + tSubMissionID);
	loggerDebug("RnewMeetF1PSave","put session value,PrtSeq=="+PrtSeq);
	session.putValue("RealPath",strVFPathName );
	request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
	//response.sendRedirect("./GetF1PrintJ1.jsp");
	//	response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
	//	  response.sendRedirect("../f1print/GetF1Print.jsp?RealPath="+strVFPathName);
	
	
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

      //window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");

      </script>
      </html>
      <%
      }

      %>

