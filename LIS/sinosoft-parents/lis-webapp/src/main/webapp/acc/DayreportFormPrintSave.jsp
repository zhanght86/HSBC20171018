<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%
	loggerDebug("DayreportFormPrintSave","--------------------start------------------");
  	CError cError = new CError( );
  	boolean operFlag=true;
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String strOperation = "";
	VData tVData = new VData();
	VData mResult = new VData();
	int x=1;
	LOAccUnitPriceSet tLOAccUnitPriceSet = new LOAccUnitPriceSet();
	CErrors mErrors = new CErrors();
  	String tBillType = request.getParameter("BillType");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getAttribute("GI");
	TransferData tTransferData = new TransferData();
  if(!tBillType.equals("tl_008"))
  {
	LOAccUnitPriceSchema tLOAccUnitPriceSchema=new LOAccUnitPriceSchema();
	tLOAccUnitPriceSchema.setInsuAccNo(request.getParameter("InsuAccNo"));
	tLOAccUnitPriceSchema.setStartDate(request.getParameter("StartDate"));
  	tVData.addElement(tLOAccUnitPriceSchema);
  	tVData.addElement(tG);
  }
  else
  {
	String tStartDate = request.getParameter("StartDate");
	String tEndDate = request.getParameter("EndDate");
	String tInsuAccNo = request.getParameter("InsuAccNo");

	tTransferData.setNameAndValue("StartDate",tStartDate);
	tTransferData.setNameAndValue("EndDate",tEndDate);
	tTransferData.setNameAndValue("InsuAccNo",tInsuAccNo);

  }
  	XmlExport txmlExport = new XmlExport();   
	//ExeSQL tExeSQL=new ExeSQL();
	String ClassBL = "";//tExeSQL.getOneValue("select prtbl from LOReportCode where code='"+tBillType+"'");
	String eSQL = "select prtbl from LOReportCode where code='"+tBillType+"'";
	TransferData iTransferData=new TransferData();
	iTransferData.setNameAndValue("SQL", eSQL);
	VData iVData = new VData();
	iVData.add(iTransferData);
    BusinessDelegate eBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(eBusinessDelegate.submitData(iVData, "getOneValue", "ExeSQLUI"))
    {
    	ClassBL=(String)eBusinessDelegate.getResult().getObjectByObjectName("String", 0);
    }
	
	if(ClassBL!=null&&!ClassBL.equals(""))
	{
		  try {
			  	tTransferData.setNameAndValue("ClassBL",ClassBL);
				tVData.add(tTransferData);
				tVData.addElement(tG);
			  	eBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			    if(!eBusinessDelegate.submitData(tVData, "PRINT", "DealInsuAccPrint"))
			    {
                	operFlag=false;
       				Content="打印失败"; 
			    } else {
		         //获取临时文件名
		         String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		         String strFilePath = "";//= tExeSQL.getOneValue(strSql);
		         iTransferData=new TransferData();
		         iTransferData.setNameAndValue("SQL", strSql);
		         iVData = new VData();
		         iVData.add(iTransferData);
		         BusinessDelegate xBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		         if(xBusinessDelegate.submitData(iVData, "getOneValue", "ExeSQLUI"))
		         {
		             strFilePath=(String)xBusinessDelegate.getResult().getObjectByObjectName("String", 0);
		         }
		         
		         String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
		         //获取存放临时文件的路径
		         String strRealPath = application.getRealPath("/").replace('\\','/');
		         String strVFPathName = strRealPath + strVFFileName;
		
		         CombineVts tcombineVts = null;		
				 mResult = eBusinessDelegate.getResult();			
				 txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
				 if(txmlExport==null)
				 {
				    operFlag=false;
				    mErrors = eBusinessDelegate.getCErrors();
			        Content = "打印失败,原因是＝＝"+mErrors.getFirstError();  
				 }
			     else
			     {
				     //合并VTS文件
				    String strTemplatePath = application.getRealPath("f1print/MStemplate/") + "/";
				    tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
			
				    ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
					tcombineVts.output(dataStream);
			
					//把dataStream存储到磁盘文件
					//loggerDebug("DayreportFormPrintSave","存储文件到"+strVFPathName);
					AccessVtsFile.saveToFile(dataStream,strVFPathName);
					loggerDebug("DayreportFormPrintSave","==> Write VTS file to disk ");
			
					loggerDebug("DayreportFormPrintSave","===strVFFileName : "+strVFFileName);
					session.putValue("RealPath", strVFPathName);
					//response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
					request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp").forward(request,response);
			     }
	             }
	                
	    }catch(Exception ex)
	    {
	       ex.printStackTrace();
	    }
	}
	if( !Content.equals("") ) {
  %>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
  <%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
  <script language="javascript">
	alert("<%=Content%>");
	top.opener.focus();
	top.close();
</script>
</html>
<%
	}
%>
