<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ������ִ�ӡ����Ա�ս�
//�����ܣ�
//�������ڣ�2002-12-12
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.easyscan.*"%>  
<%@page import="com.sinosoft.service.*" %>
<%
	  String Content = "";
	  String FlagStr = "";
		//�������
		CError cError = new CError( );
		CErrors mErrors = new CErrors();
		
	  int flag = -1;
		String action = request.getParameter("fmtransact");
	  String serverUrl = request.getParameter("Url");
	  loggerDebug("FinDayListSave",action);
		if (action.equals("download"))
		{
			flag = 0;
			EasyScanConfig tEasyScan = EasyScanConfig.getInstance();
		  String clientUrl = (String)session.getValue("ClientURL");
		  loggerDebug("FinDayListSave",clientUrl);
		  StrTool tTool = new StrTool();
		  int pos = tTool.getPos(serverUrl,"/",3) + 1;
		  String httpServer = serverUrl.substring(0,pos);
		  loggerDebug("FinDayListSave","serverUrl " + serverUrl);
		  
		  StringBuffer fileUrl = new StringBuffer();
		  fileUrl.append(httpServer);
		  if (tEasyScan.isForward(clientUrl,fileUrl) == true)
		  {
			  serverUrl = new String(fileUrl.append(serverUrl.substring(pos)));
		  	loggerDebug("FinDayListSave","Forward success ");
		  }
			loggerDebug("FinDayListSave","serverUrl------ " + serverUrl);
		}
		else
	  {
	  	flag = 1;
			GlobalInput tG1 = (GlobalInput)session.getValue("GI");
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("StartDate",request.getParameter("StartDate"));
			tTransferData.setNameAndValue("EndDate",request.getParameter("EndDate"));
			tTransferData.setNameAndValue("ComCode",request.getParameter("ManageCom"));
			
			String PrintType = request.getParameter("PrintType");
			loggerDebug("FinDayListSave","��ӡ������" + PrintType);
			
			VData tVData = new VData();
			tVData.addElement(tG1);
			tVData.addElement(tTransferData);
//			20110512 modified by Cuizhe ���뱨���Ҳ���BusinessDelegate�࣬tBusinessDelegateû��mErrors���ԣ���ΪgetCErrors
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			//FinDayListUI tFinDayListUI = new FinDayListUI();
			if(!tBusinessDelegate.submitData(tVData,PrintType,"FinDayListUI"))
			{
					FlagStr = "Fail";
					Content = "����ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
			}
		  else 
		  {
			  	FlagStr = "Success";
			    Content = "������ɣ� ";
		  }	
		}
		
		//VData mResult = tFinDayListUI.getResult();
		//XmlExport txmlExport = (XmlExport)mResult.getObjectByObjectName("XmlExport",0);
		//if (txmlExport==null)
		//{
		//	loggerDebug("FinDayListSave","null");
		//	return;
		//}
		//session.putValue("PrintStream", txmlExport.getInputStream());
		//loggerDebug("FinDayListSave","put session value");
		//response.sendRedirect("./GetF1Print.jsp");
%>
<html>
<script language="javascript">
	if (<%=flag%> == 1)
	{
		parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	}
	else{
		
	if ("<%=FlagStr%>" != "Fail")
	{
		parent.fraInterface.downAfterSubmit('<%=serverUrl%>',<%=flag%>);
	}
	}
</script>
</html>
