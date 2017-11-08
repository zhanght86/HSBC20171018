<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name : EdorCLaimBackBillPrintSave.jsp
//Creator  ：liurx
//Date ：2005-08-08
%>
<!--用户校验类-->
  bh<%@include file="../common/jsp/UsrCheck.jsp"%>
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
  		//loggerDebug("EdorCLaimBackBillPrintSave","保全清单打印开始..........");
  		String Content = "";
  		CErrors tError = null;
  		String FlagStr = "Fail";
  		String strOperation = "";
        GlobalInput tG = new GlobalInput();
        tG = (GlobalInput)session.getValue("GI");
        VData tVData = new VData();
        
        String ManageCom = request.getParameter("ManageCom");
        String SaleChnl = request.getParameter("SaleChnl");
        String StartDate = request.getParameter("StartDay");
        String EndDate = request.getParameter("EndDay");
	      String mBillType=request.getParameter("NewBillType");
	      String mRiskCode=request.getParameter("RiskCode");	      
        strOperation = request.getParameter("fmtransact");

        loggerDebug("EdorCLaimBackBillPrintSave","startdate:"+StartDate);
        loggerDebug("EdorCLaimBackBillPrintSave","EndDate:"+EndDate);
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ManageCom",ManageCom);
        tTransferData.setNameAndValue("SaleChnl",SaleChnl);
        tTransferData.setNameAndValue("StartDate",StartDate);
        tTransferData.setNameAndValue("EndDate",EndDate);
        tTransferData.setNameAndValue("BillType", mBillType);
        tTransferData.setNameAndValue("RiskCode", mRiskCode);
        tTransferData.setNameAndValue("Type", "026");
        tVData.add(tG);
        tVData.add(tTransferData);
        VData mResult = new VData();
        CErrors mErrors = new CErrors();

        XmlExport txmlExport = new XmlExport();
        BqBillUI tBqBillUI = new BqBillUI();
        if(!tBqBillUI.submitData(tVData,strOperation))
        {
          loggerDebug("EdorCLaimBackBillPrintSave","保全清单打印失败！");
          FlagStr = "Fail";
          Content=tBqBillUI.mErrors.getFirstError().toString();
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

          mResult = tBqBillUI.getResult();
          txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
        	loggerDebug("EdorCLaimBackBillPrintSave","打印失败,原因是＝＝"+tError.getFirstError());
        	FlagStr = "Fail";
        	tError = tBqBillUI.mErrors;
       	    Content = "打印失败,原因是＝＝"+tError.getFirstError();
      	  }
      	  else
      	  {
        	loggerDebug("EdorCLaimBackBillPrintSave","保全清单即将合并模板");
    		//合并VTS文件
    		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
    		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

    		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
    		tcombineVts.output(dataStream);

    		//把dataStream存储到磁盘文件
    		//loggerDebug("EdorCLaimBackBillPrintSave","存储文件到"+strVFPathName);
    		AccessVtsFile.saveToFile(dataStream,strVFPathName);
    		//loggerDebug("EdorCLaimBackBillPrintSave","==> Write VTS file to disk ");

    		//loggerDebug("EdorCLaimBackBillPrintSave","===strVFFileName : "+strVFFileName);
    		session.putValue("RealPath", strVFPathName);
    		//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
    		request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
          }
        }
	if( !Content.equals("") ) {
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
