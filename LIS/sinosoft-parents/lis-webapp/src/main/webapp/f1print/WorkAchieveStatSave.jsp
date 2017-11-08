<%@page contentType="text/html;charset=GBK" %>
<%
//程序名称：WorkAchieveStatSave.jsp
//程序功能：工作绩效统计清单
//创建日期：2005-11-29 17:20:22
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
  System.out.println("保全工作绩效统计打印开始..........");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  String strOperation = "";      
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  VData tVData = new VData();
  String ManageCom = request.getParameter("ManageCom");
  String StartDate = request.getParameter("StartDate");
  String EndDate = request.getParameter("EndDate");
  String UsrType = request.getParameter("UsrType");
  String EdorType = request.getParameter("EdorType");
  String UsrCode = request.getParameter("UsrCode");
  String EdorState = request.getParameter("EdorState");
  strOperation = request.getParameter("fmtransact");
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("ManageCom",ManageCom);
  tTransferData.setNameAndValue("StartDate",StartDate);
  tTransferData.setNameAndValue("EndDate",EndDate);
  tTransferData.setNameAndValue("UsrType",UsrType);
  tTransferData.setNameAndValue("EdorType",EdorType);
  tTransferData.setNameAndValue("UsrCode",UsrCode);
  tTransferData.setNameAndValue("EdorState",EdorState);
  tVData.add(tG);
  tVData.add(tTransferData);
  VData mResult = new VData();
  CErrors mErrors = new CErrors();
	    
  XmlExport txmlExport = new XmlExport();  
  EdorWorkAchievePrintUI tEdorWorkAchievePrintUI = new EdorWorkAchievePrintUI();
  if(!tEdorWorkAchievePrintUI.submitData(tVData,strOperation))
  {
          System.out.println("工作绩效统计清单打印失败！");
          FlagStr = "Fail";
          Content=tEdorWorkAchievePrintUI.mErrors.getFirstError().toString();                 
  } 
  else
  { 
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

      CombineVts tcombineVts = null;

	  mResult = tEdorWorkAchievePrintUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	    System.out.println("打印失败,原因是＝＝"+tError.getFirstError());
	    FlagStr = "Fail";
	    tError = tEdorWorkAchievePrintUI.mErrors;
        Content = "打印失败,原因是＝＝"+tError.getFirstError();  
	  }
      else
      {
           System.out.println("保全工作绩效统计清单即将合并模板");
           //合并VTS文件
	       String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
	       tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	       ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
           tcombineVts.output(dataStream);

           //把dataStream存储到磁盘文件
           //System.out.println("存储文件到"+strVFPathName);
           AccessVtsFile.saveToFile(dataStream,strVFPathName);
           System.out.println("==> Write VTS file to disk ");

           System.out.println("===strVFFileName : "+strVFFileName);
           session.putValue("RealPath", strVFPathName);
           //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
           request.getRequestDispatcher("GetF1PrintJ1.jsp").forward(request,response);
      }                                                       
   }
	if( !Content.equals("") ) {
		System.out.println("outputStream object has been open");
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