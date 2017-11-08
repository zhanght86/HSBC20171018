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
  RnewRefusePUI tRnewRefusePUI = new RnewRefusePUI();
  XmlExport txmlExport = new XmlExport();
  VData mResult;
  String tPath ;

  BookModelImpl newFile ;
  BookModelImpl targetFile  ;
  VtsFileCombine vfc ;

boolean getFile(VData tVData,String operate,String templateFile ) throws Exception
{

        if(!tRnewRefusePUI.submitData(tVData,operate))
        {
          Content=tRnewRefusePUI.mErrors.getFirstError().toString();
          return false;
        }
       
        
          mResult = tRnewRefusePUI.getResult();
          txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
            Content="没有得到要显示的数据文件";
                return false;
          }
          

  //templateFile = tPath + templateFile;
  
  loggerDebug("RnewRefuseSave","Operate:"+operate+" before Combine:"+templateFile);
//  txmlExport.outputDocumentToFile("c:\\",templateFile+".xml");
  CombineVts tcombineVts = new CombineVts(txmlExport.getInputStream(),tPath);

  ByteArrayOutputStream outStream = new ByteArrayOutputStream();

	loggerDebug("RnewRefuseSave","before output...");
  tcombineVts.output(outStream);
	loggerDebug("RnewRefuseSave","after output...");
	tcombineVts.destroy();
//	System.gc();

  byte[] bytes = outStream.toByteArray();
  loggerDebug("RnewRefuseSave","byte.length="+bytes.length);


  InputStream inputStream = new ByteArrayInputStream(bytes);
 AccessVtsFile.saveToFile(outStream,"c:\\1.vts");
// AccessVtsFile.saveToFile(outStream,"c:\\"+templateFile);
 outStream.close();
 
  try
  {  
  		loggerDebug("RnewRefuseSave","before new newFile");
  		newFile = new BookModelImpl();
//      newFile.read(new FileInputStream("c:\\"+templateFile));
  		loggerDebug("RnewRefuseSave","before read");
      newFile.read(inputStream);
  		loggerDebug("RnewRefuseSave","after read");			
    
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
	  loggerDebug("RnewRefuseSave","before new target...");
	  targetFile = new BookModelImpl();
	  loggerDebug("RnewRefuseSave","after new target...");
	  targetFile.copyAll(newFile);
      loggerDebug("RnewRefuseSave","init new file");
  }
  else
  {
	loggerDebug("RnewRefuseSave","before combine");
	targetFile = vfc.dataCombine(newFile,targetFile);
//	 loggerDebug("RnewRefuseSave","after combine:"+targetFile.getSelection());
  }

	inputStream.close();
	newFile.destroy();
  return true;
}	
          
          
void error() throws Exception
{
	loggerDebug("RnewRefuseSave",Content);
	jspOut.println(Content);
}
%>

<%
  newFile = new BookModelImpl();
  targetFile = null ;
  vfc = new VtsFileCombine();

jspOut =out ;

loggerDebug("RnewRefuseSave","--------------------start------------------");
tPath = application.getRealPath("f1print/MStemplate") + "/";
loggerDebug("RnewRefuseSave","标准路径==="+tPath);
CError cError = new CError( );
boolean operFlag=true;
String tRela  = "";
String FlagStr = "";
String Content = "";
String strOperation = "";
String PrtSeq=request.getParameter("PrtSeq");
String OldPrtSeq = request.getParameter("OldPrtSeq");
String tContNo	 = request.getParameter("ContNo");
loggerDebug("RnewRefuseSave","合同号==="+tContNo);
loggerDebug("RnewRefuseSave","OldPrtSeq=="+OldPrtSeq);

String tCode = request.getParameter("NoticeType");
if(tCode==null)
{
  tCode="RE00";
}
LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
tLOPRTManagerSchema.setPrtSeq(PrtSeq);
tLOPRTManagerSchema.setCode(tCode);
loggerDebug("RnewRefuseSave",PrtSeq);
loggerDebug("RnewRefuseSave","tCode:"+tCode);
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");

VData tVData = new VData();
VData mResult = new VData();
CErrors mErrors = new CErrors();
tVData.addElement(tLOPRTManagerSchema);
tVData.addElement(tG);
tVData.addElement(tPath);

if (getFile(tVData, "CONFIRM", "")){
	   
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
	
	loggerDebug("RnewRefuseSave","tCode=="+tCode);
	//end add//////////////////////////////////////////////////////
	
	session.putValue("PrintNo", PrtSeq);
	session.putValue("Code", tCode); //核保通知书类别
	session.putValue("ContNo", tContNo);
	session.putValue("MissionID", "XBJB");
	session.putValue("SubMissionID", "");
	session.putValue("PrtNo",tContNo );
	//session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("RnewRefuseSave","put session value,ContNo:" + tContNo);
	loggerDebug("RnewRefuseSave","put session value,PrtSeq=="+PrtSeq);
	session.putValue("RealPath",strVFPathName );
	request.getRequestDispatcher("./GetF1PrintJ1.jsp").forward(request,response);
  /*
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("RnewRefuseSave","put session value");
	response.sendRedirect("../f1print/GetF1Print.jsp");
	*/
	loggerDebug("RnewRefuseSave","1111111111");
	
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

