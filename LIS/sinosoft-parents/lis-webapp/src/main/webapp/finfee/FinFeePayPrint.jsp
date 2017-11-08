<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="com.sinosoft.service.*" %>


<%
	boolean operFlag = true;
	GlobalInput tG = new GlobalInput();
	String strVFPathName="";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String strOperation = "";
    
  TransferData tTransferData = new TransferData();  
	loggerDebug("FinFeePayPrint","ddddddddddd====="+request.getParameter("ActuGetNo1"));
	String ActuGetNo = request.getParameter("ActuGetNo1");
	LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
  tTransferData.setNameAndValue("ActuGetNo",ActuGetNo);
  loggerDebug("FinFeePayPrint","传往后台的数据==========="+ActuGetNo);
  tG = (GlobalInput)session.getValue("GI");

	strOperation = request.getParameter("fmtransact");
  
  VData tVData = new VData();
	VData mResult = new VData();

  tVData.addElement(tG);
  tVData.addElement(tLOPRTManagerSet);
  tVData.addElement(tTransferData);
  //GetChequeBL tGetChequeBL = new GetChequeBL();
  
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  loggerDebug("FinFeePayPrint","-----------------------我在创建xml之前");
 	XmlExport txmlExport = new XmlExport();
 	loggerDebug("FinFeePayPrint","------------------------------我在之后");

	if(!tBusinessDelegate.submitData(tVData,"print","GetChequeBL"))
	{
				loggerDebug("FinFeePayPrint","=======================================1");
       	FlagStr="Fail";
       	Content=tBusinessDelegate.getCErrors().getFirstError().toString();
  }
  else
  {
  			loggerDebug("FinFeePayPrint","=======================================2");
  			FlagStr = "Succ";
				mResult = tBusinessDelegate.getResult();
	  		txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  		loggerDebug("FinFeePayPrint","adsf我的天空");

	  		if(txmlExport==null)
	  		{
	  					FlagStr="Fail";
	  					loggerDebug("FinFeePayPrint","=======================================3ssss");
	  					Content="没有得到要显示的数据文件";
	  		}
	  		else
	  		{
	  					ExeSQL tExeSQL = new ExeSQL();
	  					loggerDebug("FinFeePayPrint","=======================================3");

	  					//获取临时文件名
							//String strSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
							//String strFilePath = tExeSQL.getOneValue(strSql);

							//String strVFFileName = strFilePath + tG.Operator + "_" + FileQueue.getFileName()+".vts";
					    	TransferData sTransferData=new TransferData();
					        VData sVData = new VData();
					        sVData.add(tG);
					        BusinessDelegate sBusinessDelegate=BusinessDelegate.getBusinessDelegate();
					        String vtsFileName = "";
					      	if(sBusinessDelegate.submitData(sVData, "", "BqFileNameUI")){
					      		strVFFileName = (String)sBusinessDelegate.getResult().getObjectByObjectName("String", 0);
					      	}

							//获取存放临时文件的路径
							//String strRealPath = application.getRealPath("/").replace('\\','/');
							String strRealPath = request.getSession().getServletContext().getResource("/").replace('\\','/');
  						strVFPathName = strRealPath + strVFFileName;
	   					CombineVts tcombineVts = null;

	   					//合并VTS文件
		 					//String strTemplatePath = application.getRealPath("f1print/template/") + "/";
	   						String strTemplatePath = request.getSession().getServletContext().getResource("/")+"f1print/template/";
		 					tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);
		 					loggerDebug("FinFeePayPrint",strTemplatePath);
     					
		 					ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		 					tcombineVts.output(dataStream);

		 					//把dataStream存储到磁盘文件
		 					loggerDebug("FinFeePayPrint","存储文件到"+strVFPathName);
		 					AccessVtsFile.saveToFile(dataStream,strVFPathName);
		 					loggerDebug("FinFeePayPrint","==> Write VTS file to disk ");
	  					loggerDebug("FinFeePayPrint","===strVFFileName : "+strVFFileName);
	  					session.putValue("RealPath", strVFPathName);

		 					 //本来打算采用get方式来传递文件路径
		 					 //response.sendRedirect("../f1print/GetF1PrintJ1.jsp");
		 					 request.getRequestDispatcher("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName).forward(request,response);
							 loggerDebug("FinFeePayPrint","Flag=="+FlagStr);
							 loggerDebug("FinFeePayPrint","done and finished");

							 FlagStr = "succee";
							 Content = "";
				}
	}

		if( !Content.equals("")) {
				loggerDebug("FinFeePayPrint","outputStream object has been open");

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
