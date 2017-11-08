<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：EdorNoticePrintSave.jsp
//程序功能：保全通知书在线打印控制台
//创建日期：2005-08-02 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容 
%>
<!--用户校验类-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>
<%
  loggerDebug("BqCheckPrintSave","开始执行打印操作");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";

  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  String PrtSeq=request.getParameter("PrtSeq");	
  String EdorAcceptNo=request.getParameter("SelEdorAcceptNo");	
  String CheckNo = request.getParameter("CheckNo");
  String ChequeType = request.getParameter("ChequeType");
  
  loggerDebug("BqCheckPrintSave",PrtSeq+"___"+EdorAcceptNo+"___"+CheckNo+"___"+ChequeType);
  
  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
  tLOPRTManagerSchema.setPrtSeq(PrtSeq);

  ExeSQL texesql = new ExeSQL();
  String strsql = "select managecom from lpedorapp where edoracceptno = '"+EdorAcceptNo+"'";
  SSRS tssrs = texesql.execSQL(strsql);	   
  if(tssrs == null || tssrs.getMaxRow() < 1){
        FlagStr = "Fail";
        Content = "查询保全管理机构失败！";
  }else{ 
   String tManageCom = tssrs.GetText(1,1); 
   String templatepath = null;
   TransferData tTransferData = new TransferData();
   if(ChequeType.equals(PrintManagerBL.CODE_PEdorReceipt)){
      strsql = "select codealias,othersign from ldcode where codetype = 'bqinvoice' and trim(code) = substr('"+tManageCom+"',1,4)";
   }else if(ChequeType.equals(PrintManagerBL.CODE_GEdorInvoice)){
      strsql = "select codealias,othersign from ldcode where codetype = 'bqgrpinvoice' and trim(code) = substr('"+tManageCom+"',1,4)";   	
   }
   loggerDebug("BqCheckPrintSave",ChequeType+ "  "+ strsql);
   tssrs.Clear();
   tssrs = texesql.execSQL(strsql);
   if(tssrs != null && tssrs.getMaxRow() > 0){
      templatepath = tssrs.GetText(1,1);    
      tTransferData.setNameAndValue("OtherSign",tssrs.GetText(1,2)); 
   }
   tTransferData.setNameAndValue("ChequeType",ChequeType);  
   
   VData tVData = new VData();
   tVData.add(tG);
   tVData.add(tLOPRTManagerSchema); 
   tVData.add(tTransferData);
   loggerDebug("BqCheckPrintSave","______" + tLOPRTManagerSchema.encode());
   
   VData mResult = new VData(); 
   CErrors mErrors = new CErrors();   
   EdorCheckPrintUI tEdorCheckPrintUI = new EdorCheckPrintUI();
   if(!tEdorCheckPrintUI.submitData(tVData,"PRINT")){
      FlagStr = "Fail";
      Content=tEdorCheckPrintUI.mErrors.getFirstError().toString();                 
   }else{     
      //PubPrtConfigure tPubPrtConfigure = new PubPrtConfigure();
      //TransferData tTransferData = new TransferData();
      //tTransferData.setNameAndValue("ManageCom",tManageCom);
  	  //String templatepath = tPubPrtConfigure.getPrtPath(tTransferData,"BQ35");
      mResult = tEdorCheckPrintUI.getResult();
      ExeSQL tExeSQL = new ExeSQL();
      //获取临时文件名
      String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
      String strFilePath = tExeSQL.getOneValue(strSql);
      String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
      //获取存放临时文件的路径
      //strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
      //String strRealPath = tExeSQL.getOneValue(strSql);
      String strRealPath = application.getRealPath("/").replace('\\','/');
      String strVFPathName = strRealPath + strVFFileName;
      loggerDebug("BqCheckPrintSave",strVFPathName);
      CombineVts tcombineVts = null;

      XmlExport txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport == null){
	  	 loggerDebug("BqCheckPrintSave","txmlExport==null");
	     FlagStr = "Fail";
	     tError = tEdorCheckPrintUI.mErrors;
         Content = "打印失败,原因是＝＝"+tError.getFirstError();  
	  }else{
	     //合并VTS文件
	     loggerDebug("BqCheckPrintSave","templatepath:"+templatepath);
	     if(templatepath == null || templatepath.trim().equals("")){
	         templatepath = "f1print/NCLtemplate/";
	     }
         String strTemplatePath = application.getRealPath(templatepath) + "/";
	     tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	     ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	     tcombineVts.output(dataStream);

	     //把dataStream存储到磁盘文件
	     loggerDebug("BqCheckPrintSave","存储文件到"+strVFPathName);
	     AccessVtsFile.saveToFile(dataStream,strVFPathName);
	     loggerDebug("BqCheckPrintSave","==> Write VTS file to disk ");

	     loggerDebug("BqCheckPrintSave","===strVFFileName : "+strVFFileName);
	     session.putValue("RealPath", strVFPathName);
	     
	     String QuerySQL = "";
         if(ChequeType.equals(PrintManagerBL.CODE_PEdorReceipt)){	     
	         QuerySQL = "select agentcode from lccont where contno in (select contno from lpedoritem where edoracceptno = '"
	                     + EdorAcceptNo + "' limit 1)";
	         //session.putValue("Code","BQCHECK");             
	     }else if(ChequeType.equals(PrintManagerBL.CODE_GEdorInvoice)){
	         QuerySQL = "select agentcode from lcgrpcont where grpcontno in (select grpcontno from lpgrpedoritem where edoracceptno = '"
	                     + EdorAcceptNo + "' limit 1)";	
	         //session.putValue("Code","BQCHECK");                  	
	     }                
	     String ssAgentCode = texesql.getOneValue(QuerySQL);
	     
	     session.putValue("PrintNo",PrtSeq);  
	     session.putValue("Code","BQCHECK"); 
	     session.putValue("ssAgentCode", "D"+ssAgentCode);
		 session.putValue("ssManageCom", "A"+tG.ComCode);
		 session.putValue("ChequNo",CheckNo);   
		 session.putValue("certifycode",request.getParameter("CertifyCode"));             
	     //session.putValue("PrintStream", txmlExport.getInputStream());
	     loggerDebug("BqCheckPrintSave","put session value");
         //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
	     //response.sendRedirect("../uw/BqGetF1Print.jsp");
	     request.getRequestDispatcher("../uw/BqGetF1Print.jsp").forward(request,response);
      }
    }
   }
	if( !Content.equals("") ) {
		loggerDebug("BqCheckPrintSave","outputStream object has been open");
%>
<html>
<script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
	}
%>
