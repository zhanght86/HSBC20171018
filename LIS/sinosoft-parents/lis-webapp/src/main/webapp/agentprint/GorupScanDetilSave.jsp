<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.agentprint.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	GlobalInput tG = (GlobalInput) session.getValue("GI");

	VData tVData = new VData();


	String tManageCom = request.getParameter("ManageCom");
	String tPrtNo=request.getParameter("PrtNO");
    String tStartDay = request.getParameter("StartDay");
	String tEndDay = request.getParameter("EndDay");
    String ttjtype = request.getParameter("tjtype");

	
	LDSysVarDB tLDSysVarDB = new LDSysVarDB();
    tLDSysVarDB.setSysVar("XSEXCelTemplate");
	if (tLDSysVarDB.getInfo() == false) {
		loggerDebug("GorupScanDetilSave","LDSysVarȡ�ļ�·��XSEXCelTemplate����ʧ��");

	}
	String mFilePath = tLDSysVarDB.getSysVarValue();
	String TemplatePath = application.getRealPath(mFilePath) + "/";
	tVData.clear();
	tVData.addElement(tManageCom);
	tVData.addElement(tPrtNo);
	tVData.addElement(tStartDay);
	tVData.addElement(tEndDay);
	tVData.addElement(ttjtype);
    tVData.addElement(tG);
	tVData.addElement(TemplatePath);
    CError cError = new CError();
	CErrors tError = null;

	String strErrMsg = "";
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��	  
	String FlagStr = "";
	CErrors mErrors = new CErrors();
	/////////////////////By jieyh/////////

	  GorupScanDetilDeal tGorupScanDetilDeal = new GorupScanDetilDeal();
	/////////////////////By jieyh   /////////

	int tDelType = -1; //��������
	String Content = ""; //������
	String Flag = "";

	String action = request.getParameter("fmtransact");
	loggerDebug("GorupScanDetilSave","action = " + action);

	String myconfirm = request.getParameter("myconfirm");

	if (action.equals("download")) {
		String url = request.getParameter("Url");
		String filename = request.getParameter("FileName");
		File tempFile = new File(url + filename);
		loggerDebug("GorupScanDetilSave",url + filename);
		loggerDebug("GorupScanDetilSave","tempFile.exists" + tempFile.exists());
		if (!tempFile.exists()) {
			loggerDebug("GorupScanDetilSave","NO");
			Flag = "Fail";
			Content = " ��������Ҫ���ص��ļ����������������أ� ";
			tDelType = 1;
		} else {
			tDelType = 0;
		}
	} else {

		String url = request.getParameter("Url");
		String filename = request.getParameter("FileName");
		File tempFile = new File(url + filename);
		loggerDebug("GorupScanDetilSave","url+filename[" + (url + filename) + "]");

		loggerDebug("GorupScanDetilSave","exists[" + tempFile.exists() + "]");
		loggerDebug("GorupScanDetilSave","myconfirm[" + myconfirm + "]");
		//myconfirm ��һ�����ص�����
		if (tempFile.exists() && !myconfirm.equals("1")) {
			//��������JavaScript����ص�ConfirmSelect���ٴ��ύ
%>
<script language="javascript">
	   parent.fraInterface.ConfirmSelect();
		</script>
<%
	} else {
			//ɾ�����ļ� �ļ������ڻ��ļ���������Ҫ���¼���ʱ�ߴ˷�֧
			//comment by jiaqiangli 2007-07-06 У�鲻ͨ��֮��ɾ�����ļ�
			//tempFile.delete();
			tDelType = 1;
			if (!tGorupScanDetilDeal.getInputData(tVData)) {
				if (tGorupScanDetilDeal.mErrors.needDealError()) {
					strErrMsg = tGorupScanDetilDeal.mErrors
							.getFirstError();
				} else {
					strErrMsg = "tXJFundsUserDeal������û���ṩ��ϸ�ĳ�����Ϣ";
				}
				return;
			}
			if (!tGorupScanDetilDeal.prepareData()) {
				Flag = "Fail";
				Content = " ����ʧ�ܣ�ԭ����:"
						+ tGorupScanDetilDeal.mErrors.getFirstError();
			} else {
				Flag = "Success";
				Content = " ����ɹ� ";
			}
			loggerDebug("GorupScanDetilSave",Content + "\n" + Flag
					+ "\n---������� End---\n\n");
		}
	}
%>

<html>
<script language="javascript"> 
  <!--alert('<%=tDelType%>');-->
	if (<%=tDelType%> == 1) {
		 <!--alert('<%=Content%>');-->
		 <!--��document.all('myconfirm').value ������Ϊ��ʼ״̬ "0"	�𵽿�������	 --> 
		 parent.fraInterface.document.all('myconfirm').value = '0';
		 parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
	} else {

	  <!-- tDelType = 0; -->
	  <!-- ������治����������� -->
	  if (<%=tDelType%> != -1)
	  parent.fraInterface.downAfterSubmit(parent.fraInterface.fm.Url.value,parent.fraInterface.fm.FileName.value );
	 
	}
</script>
</html>
