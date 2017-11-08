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
  //EdorAskPrintUI tEdorAskPrintUI = new EdorAskPrintUI();
  String busiName="EdorAskPrintUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  VData mResult;

//  tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
//
//  ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//  tcombineVts.output(dataStream);
//
//  //��dataStream�洢�������ļ�
//  //loggerDebug("EdorAskSave","�洢�ļ���"+strVFPathName);
//  AccessVtsFile.saveToFile(dataStream,strVFPathName);
//  loggerDebug("EdorAskSave","==> Write VTS file to disk ");

  String tPath ;

  BookModelImpl newFile ;
  BookModelImpl targetFile  ;
  VtsFileCombine vfc ;

boolean getFile(VData tVData,String operate,String templateFile ) throws Exception
{

        if(!tBusinessDelegate.submitData(tVData,operate,busiName))
        {
          Content=tBusinessDelegate.getCErrors().getFirstError().toString();
          return false;
        }
       
        
          mResult = tBusinessDelegate.getResult();
          XmlExport txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
            Content="û�еõ�Ҫ��ʾ�������ļ�";
                return false;
          }
          

  //templateFile = tPath + templateFile;
  
  loggerDebug("EdorAskSave","Operate:"+operate+" before Combine:"+templateFile);
//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
  CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),tPath);

  ByteArrayOutputStream outStream = new ByteArrayOutputStream();

	loggerDebug("EdorAskSave","before output...");
  tcombineVts.output(outStream);
	loggerDebug("EdorAskSave","after output...");
	tcombineVts.destroy();
//	System.gc();

  byte[] bytes = outStream.toByteArray();
  loggerDebug("EdorAskSave","byte.length="+bytes.length);

//  byte[] data = new byte[4098];
//  while((i=data.write(data)>-1)
//  {
//  	
//  
//  }
  InputStream inputStream = new ByteArrayInputStream(bytes);
 AccessVtsFile.saveToFile(outStream,"c:\\1.vts");
// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
 outStream.close();
 
  try
  {  
  		loggerDebug("EdorAskSave","before new newFile");
  		newFile = new BookModelImpl();
//      newFile.read(new FileInputStream("c:\\"+templateFile));
  		loggerDebug("EdorAskSave","before read");
      newFile.read(inputStream);
  		loggerDebug("EdorAskSave","after read");			
    
//      newFile.setSelection(0,0,newFile.getLastRow(),newFile.getLastCol());
   }catch (Exception ex)
  {
      ex.printStackTrace();
      Content = "��ȡ�ļ�����."+ex.getMessage();
      return false;
  }
  
  if (targetFile == null)
  {
//      targetFile = newFile;  //ͬһ������ָ���������
	  loggerDebug("EdorAskSave","before new target...");
	  targetFile = new BookModelImpl();
	  loggerDebug("EdorAskSave","after new target...");
	  targetFile.copyAll(newFile);
      loggerDebug("EdorAskSave","init new file");
  }
  else
  {
	loggerDebug("EdorAskSave","before combine");
	targetFile = vfc.dataCombine(newFile,targetFile);
//	 loggerDebug("EdorAskSave","after combine:"+targetFile.getSelection());
  }

	inputStream.close();
	newFile.destroy();
  return true;
}	
          
          
void error() throws Exception
{
	loggerDebug("EdorAskSave",Content);
	jspOut.println(Content);
}
%>

<%
  newFile = new BookModelImpl();
  targetFile = null ;
  vfc = new VtsFileCombine();

jspOut =out ;

loggerDebug("EdorAskSave","--------------------start------------------");
tPath = application.getRealPath("f1print/NCLtemplate") + "/";
loggerDebug("EdorAskSave","��׼·��==="+tPath);
CError cError = new CError( );
boolean operFlag=true;
String tRela  = "";
String FlagStr = "";
String Content = "";
String strOperation = "";
String PrtSeq=request.getParameter("PrtSeq");
String tMissionID = request.getParameter("MissionID");
String tSubMissionID = request.getParameter("SubMissionID");
String tActivityID = request.getParameter("ActivityID");
String tContNo	 = request.getParameter("ContNo");
loggerDebug("EdorAskSave","��ͬ��==="+tContNo);

String tCode = request.getParameter("NoticeType");
if(tCode==null)
{
  tCode="04";
}
LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
tLOPRTManagerSchema.setPrtSeq(PrtSeq);
tLOPRTManagerSchema.setCode(tCode);
loggerDebug("EdorAskSave",PrtSeq);
loggerDebug("EdorAskSave","tCode:"+tCode);
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

VData tVData = new VData();
VData mResult = new VData();
CErrors mErrors = new CErrors();
tVData.addElement(tLOPRTManagerSchema);
tVData.addElement(tG);
tVData.addElement(tPath);
EdorAskPrintUI tEdorAskPrintUI = new EdorAskPrintUI();


ExeSQL exeSql = new ExeSQL();
SSRS testSSRS = new SSRS();
testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
//String strTemplatePath = testSSRS.GetText(1,1); //���ݿ��е�·��-ģ��·��D:/lis/ui/f1print/NCLtemplate/
String strTemplatePath=tPath;
testSSRS = exeSql.execSQL("select askcode from LCRReportPrt where PrtSeq='"+PrtSeq+"'");
String remark="";

loggerDebug("EdorAskSave","�ʾ������==="+testSSRS.MaxRow);
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
if (getFile(tVData, "PRINT3", "announceData.vts"))
{
   
String strSql1 = "select * from ldsysvar where Sysvar='VTSFilePath'";
LDSysVarDB tLDSysVarDB = new LDSysVarDB();
LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(strSql1);
LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
String strFilePath = tLDSysVarSchema.getV("SysVarValue");
String strVFFileName = strFilePath + FileQueue.getFileName() + ".vts";

//��ȡ�����ʱ�ļ���·��
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


//end add//////////////////////////////////////////////////////
session.putValue("PrintNo", PrtSeq);
session.putValue("MissionID", tMissionID);
session.putValue("SubMissionID", tSubMissionID);
session.putValue("ActivityID", tActivityID);
session.putValue("Code", tCode); //�˱�֪ͨ�����
session.putValue("ContNo", tContNo);

session.putValue("PrtNo", tContNo);
//session.putValue("PrintStream", txmlExport.getInputStream());
loggerDebug("EdorAskSave","put session value,ContNo:" + tContNo);
loggerDebug("EdorAskSave","put session value==" + tMissionID);
loggerDebug("EdorAskSave","put session value,SubMissionID:" + tSubMissionID);
loggerDebug("EdorAskSave","put session value,PrtSeq=="+PrtSeq);
session.putValue("RealPath",strVFPathName );
loggerDebug("EdorAskSave","before sendredirect..............");
//	response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
//	  response.sendRedirect("../f1print/GetF1Print.jsp?RealPath="+strVFPathName);
//response.sendRedirect("./GetF1PrintJ1.jsp");
//request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
request.getRequestDispatcher("../uw/GetF1PrintJ1.jsp").forward(request,response);



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

