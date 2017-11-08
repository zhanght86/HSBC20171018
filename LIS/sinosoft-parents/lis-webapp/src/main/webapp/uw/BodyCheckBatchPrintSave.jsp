<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
		loggerDebug("BodyCheckBatchPrintSave","--------------------start------------------");
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";

	int chkCount = Integer.parseInt(request.getParameter("ChkCount"));
	String tPolGridChk[] = request.getParameterValues("PolGridChk");
	String tInpPolGridChk[] = request.getParameterValues("InpPolGridChk");

	String bPrtSeq[] = request.getParameterValues("PolGrid1");
	String bPrtNo[] = request.getParameterValues("PolGrid2");
	String bContNo[] = request.getParameterValues("PolGrid2");
	String bMissionID[] = request.getParameterValues("PolGrid6");
	String bSubMissionID[] = request.getParameterValues("PolGrid7");


	String[] tStrPrtSeq = new String[chkCount];
	String[] tStrPrtNo = new String[chkCount];
	String[] tStrContNo = new String[chkCount];
	String[] tStrMissionID = new String[chkCount];
	String[] tStrSubMissionID = new String[chkCount];
	XmlExport[] txmlExportList = new XmlExport[chkCount];
	for (int a = 0, j = 0; a < tInpPolGridChk.length; a++) {

		if (tInpPolGridChk[a].equals("0")) {
			loggerDebug("BodyCheckBatchPrintSave","该行未被选中");
			continue;
		}
		
	String PrtSeq = bPrtSeq[a];
	String tMissionID = bMissionID[a];
	String tSubMissionID = bSubMissionID[a];
	String tPrtNo	 = bPrtNo[a];
	String tContNo	 = bContNo[a];
	
	tStrPrtSeq[j] = bPrtSeq[a];
	tStrPrtNo[j] = bPrtNo[a];
	tStrContNo[j] = bContNo[a];
	tStrMissionID[j] = bMissionID[a];
	tStrSubMissionID[j] = bSubMissionID[a];


	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	loggerDebug("BodyCheckBatchPrintSave",PrtSeq);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
       
    BodyCheckPrintUI tBodyCheckPrintUI = new BodyCheckPrintUI();
	XmlExport txmlExport = new XmlExport();    
    if(!tBodyCheckPrintUI.submitData(tVData,"PRINT"))
    {
       operFlag=false;
       Content=tBodyCheckPrintUI.mErrors.getFirstError().toString();                 
    }
    else
    {    
	  mResult = tBodyCheckPrintUI.getResult();			
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="没有得到要显示的数据文件";	  
	  }else{
	  	txmlExportList[j]=txmlExport;	
	  }
	}
		j++;
	}
	
	if (operFlag==true)
	{

	session.putValue("PrintNo",tStrPrtSeq );
	session.putValue("MissionID",tStrMissionID );
	session.putValue("SubMissionID",tStrSubMissionID );
	session.putValue("Code","03" );	//核保通知书类别
	session.putValue("ContNo",tStrContNo );
	session.putValue("PrtNo",tStrPrtNo );	
	session.putValue("xel", txmlExportList);
	
	request.getRequestDispatcher("./GetF1BatchPrint.jsp").forward(request,response);
	//response.sendRedirect("GetF1Print.jsp");
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
