<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//name : EdorUniverUnCountBillPrintSave.jsp
//Creator  ��liurx
//Date ��2005-08-08
%>
<!--�û�У����-->
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="java.io.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@page import="com.sinosoft.report.f1report.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
  		//loggerDebug("EdorUniverUnCountBillPrintSave","��ȫ�嵥��ӡ��ʼ..........");
  		String Content = "";
  		CErrors tError = null;
  		String FlagStr = "Fail";
  		String strOperation = "";
        GlobalInput tG = new GlobalInput();
        tG = (GlobalInput)session.getValue("GI");
        VData tVData = new VData();
        
        String ManageCom = request.getParameter("ManageCom");
        String CountDate = request.getParameter("CountDate");
        String sEdorType = request.getParameter("EdorType");
        strOperation = request.getParameter("fmtransact");

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ManageCom",ManageCom);
        tTransferData.setNameAndValue("EdorType", sEdorType);
        tTransferData.setNameAndValue("CountDate", CountDate);
        tTransferData.setNameAndValue("Type", "015");
        tVData.add(tG);
        tVData.add(tTransferData);
        VData mResult = new VData();
        CErrors mErrors = new CErrors();
        
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        XmlExport txmlExport = new XmlExport();
        //BqBillUI tBqBillUI = new BqBillUI();
        //if(!tBqBillUI.submitData(tVData,strOperation))
        if(!tBusinessDelegate.submitData(tVData, strOperation, "BqBillUI"))
        {
          loggerDebug("EdorUniverUnCountBillPrintSave","��ȫ�嵥��ӡʧ�ܣ�");
          FlagStr = "Fail";
          Content=tBusinessDelegate.getCErrors().getFirstError().toString();
        }
        else
        {
          //ExeSQL tExeSQL = new ExeSQL();
          BusinessDelegate tExeSQLBusinessDelegate=BusinessDelegate.getBusinessDelegate();
          //��ȡ��ʱ�ļ���
          String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
          TransferData t2TransferData = new TransferData();
          VData t2VData = new VData();
          t2TransferData.setNameAndValue("SQL",strSql);
          t2VData.add(t2TransferData);
          //String strFilePath = tExeSQL.getOneValue(strSql);
          String strFilePath = "";
          if(tExeSQLBusinessDelegate.submitData(t2VData,"getOneValue","ExeSQLUI")){
        	  strFilePath = (String)tExeSQLBusinessDelegate.getResult().getObject(0);
          }
        	  
          String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
          //��ȡ�����ʱ�ļ���·��
          //strSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
          //String strRealPath = tExeSQL.getOneValue(strSql);
          String strRealPath = application.getRealPath("/").replace('\\','/');
          String strVFPathName = strRealPath + strVFFileName;

          CombineVts tcombineVts = null;

          mResult = tBusinessDelegate.getResult();
          txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
          if(txmlExport==null)
          {
        	loggerDebug("EdorUniverUnCountBillPrintSave","��ӡʧ��,ԭ���ǣ���"+tError.getFirstError());
        	FlagStr = "Fail";
        	tError = tBusinessDelegate.getCErrors();
       	    Content = "��ӡʧ��,ԭ���ǣ���"+tError.getFirstError();
      	  }
      	  else
      	  {
        	loggerDebug("EdorUniverUnCountBillPrintSave","��ȫ�嵥�����ϲ�ģ��");
    		//�ϲ�VTS�ļ�
    		String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
    		tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

    		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
    		tcombineVts.output(dataStream);

    		//��dataStream�洢�������ļ�
    		//loggerDebug("EdorUniverUnCountBillPrintSave","�洢�ļ���"+strVFPathName);
    		AccessVtsFile.saveToFile(dataStream,strVFPathName);
    		//loggerDebug("EdorUniverUnCountBillPrintSave","==> Write VTS file to disk ");

    		//loggerDebug("EdorUniverUnCountBillPrintSave","===strVFFileName : "+strVFFileName);
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
