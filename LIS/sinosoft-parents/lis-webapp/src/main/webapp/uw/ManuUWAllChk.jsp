<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�ManuUWAllChk.jsp
	//�����ܣ�����˱�
	//�������ڣ�2005-01-19 11:10:36
	//������  ��HYQ
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getValue("GI");
	if (tG == null) {
		loggerDebug("ManuUWAllChk","session has expired");
		return;
	}

	// Ͷ�����б�

	//String sApplyType = request.getParameter("ApplyType");
	String sMissionID = request.getParameter("MissionID");
	String sSubMissionID = request.getParameter("SubMissionID");
	String sActivityID = request.getParameter("ActivityID");
	loggerDebug("ManuUWAllChk","== sMissionID == " + sMissionID);
	loggerDebug("ManuUWAllChk","== sSubMissionID == " + sSubMissionID);
	loggerDebug("ManuUWAllChk","== sActivityID == " + sActivityID);
	String sCom = request.getParameter("Com");
	String sUWLevel = request.getParameter("Result");
	String sPrtNo = request.getParameter("PrtNo");
	String sType = request.getParameter("Type");
	loggerDebug("ManuUWAllChk","== sCom == " + sCom);
	loggerDebug("ManuUWAllChk","== sUWLevel == " + sUWLevel);
	loggerDebug("ManuUWAllChk","== sPrtNo == " + sPrtNo);
	loggerDebug("ManuUWAllChk","== sType == " + sType);

	TransferData mTransferData = new TransferData();
	//mTransferData.setNameAndValue("ApplyType", sApplyType);	
	mTransferData.setNameAndValue("MissionID", sMissionID);
	mTransferData.setNameAndValue("SubMissionID", sSubMissionID);
	mTransferData.setNameAndValue("ActivityID", sActivityID);
	mTransferData.setNameAndValue("Com", sCom);
	mTransferData.setNameAndValue("UWLevel", sUWLevel);
	mTransferData.setNameAndValue("PrtNo", sPrtNo);
	mTransferData.setNameAndValue("Type", sType);

	try {
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(mTransferData);
		tVData.add(tG);

		// ���ݴ���
		//UWApplyUI tUWApplyUI = new UWApplyUI();
		
		String busiName="cbcheckUWApplyUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData, "",busiName) == false) {
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++) {
				loggerDebug("ManuUWAllChk","Error: "
						+ tBusinessDelegate.getCErrors().getError(i).errorMessage);
				Content = " ����ʧ�ܣ�ԭ����: "
						+ tBusinessDelegate.getCErrors().getError(0).errorMessage;
			}
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail") {
			tError = tBusinessDelegate.getCErrors();
			loggerDebug("ManuUWAllChk","tError.getErrorCount:"
					+ tError.getErrorCount());
			if (!tError.needDealError()) {
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			} else {
				Content = " ����ʧ�ܣ�ԭ����:";
				int n = tError.getErrorCount();
				if (n > 0) {
					for (int i = 0; i < n; i++) {
						//tError = tErrors.getError(i);
						Content = Content.trim()
								+ i
								+ ". "
								+ tError.getError(i).errorMessage
										.trim() + ".";
					}
				}
				FlagStr = "Fail";
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		Content = Content.trim() + ".��ʾ���쳣��ֹ!";
	}
%>                      
<html>
<script language="javascript">
		
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
