
<%
	//�������ƣ�PersonPayPlanSave.jsp
	//�����ܣ�
	//�������ڣ�2002-07-24 08:38:44
	//������  ��CrtHtml���򴴽�
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.get.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	//������Ϣ������У�鴦��
	//�������
	//�������
	CErrors tError = null;
	String tBmCert = "";

	String tRela = "";
	String FlagStr = "";
	String Content = "";
	String mSerialNo = "";
	String mCount = "";
	loggerDebug("PersonPayPlanSave","Start PersonPayPlan JSP Submit...");

	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����

	LCPolSchema tLCPolSchema = new LCPolSchema();
	tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
	tLCPolSchema.setContNo(request.getParameter("ContNo"));
	tLCPolSchema.setPolNo(request.getParameter("PolNo"));
	//tLCPolSchema.setAppntNo(request.getParameter("AppntNo"));
	tLCPolSchema.setInsuredNo(request.getParameter("InsuredNo"));

	LCGetSchema tLCGetSchema = new LCGetSchema();
	tLCGetSchema.setManageCom(request.getParameter("ManageCom"));
	tLCGetSchema.setContNo(request.getParameter("ContNo"));
	tLCGetSchema.setPolNo(request.getParameter("PolNo"));
	//tLCGetSchema.setAppntNo(request.getParameter("AppntNo"));
	tLCGetSchema.setInsuredNo(request.getParameter("InsuredNo"));

	TransferData aTransferData = new TransferData();
	//aTransferData.setNameAndValue("timeStart",request.getParameter("timeStart"));
	aTransferData.setNameAndValue("timeEnd", request
			.getParameter("timeEnd"));

	VData tVData = new VData();
	tVData.addElement(tGlobalInput);
	tVData.addElement(aTransferData);
	tVData.addElement(tLCPolSchema);
	tVData.addElement(tLCGetSchema);

	//PersonPayPlanUI tPersonPayPlanUI = new PersonPayPlanUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate(); 
	
	try {
		//tPersonPayPlanUI.submitData(tVData, "INSERT||PERSON");
		tBusinessDelegate.submitData(tVData,"INSERT||PERSON","PersonPayPlanUI");
	} catch (Exception ex) {
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
	}

	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	if (FlagStr == "") {
		//tError = tPersonPayPlanUI.mErrors;
		tError = tBusinessDelegate.getCErrors(); 
		if (tError.needDealError()) {
			Content = "�߸�ʧ�ܣ�"+tError.getLastError();
			FlagStr = "Fail";
		}else{			
			Content = " ����ɹ�";
			FlagStr = "Succ";
			//if (tPersonPayPlanUI.getResult() != null && tPersonPayPlanUI.getResult().size() > 0) {
			//	mSerialNo = (String) tPersonPayPlanUI.getResult().get(0);
			//	mCount = (String) tPersonPayPlanUI.getResult().get(1);
			//	
			//	// add by jiaqiangli 2009-04-09 ���Ӹ����Ѻõ���ʾ��Ϣ ��ȫ�������������Ĵ߸�
			//	if (Integer.parseInt(mCount) > 0 ) 
			//	{
			//		loggerDebug("PersonPayPlanSave","mSerialNo:" + tPersonPayPlanUI.getResult().get(0));
			//		loggerDebug("PersonPayPlanSave","mCount:" + tPersonPayPlanUI.getResult().get(1));
			//		Content=Content+"���ܹ�����"+ tPersonPayPlanUI.getResult().get(1)+"���߸���¼��";
			//	}
			
			if (tBusinessDelegate.getResult() != null && tBusinessDelegate.getResult().size() > 0) {
				mSerialNo = (String) tBusinessDelegate.getResult().get(0);
				mCount = (String) tBusinessDelegate.getResult().get(1);
				
				// add by jiaqiangli 2009-04-09 ���Ӹ����Ѻõ���ʾ��Ϣ ��ȫ�������������Ĵ߸�
				if (Integer.parseInt(mCount) > 0 ) 
				{
					loggerDebug("PersonPayPlanSave","mSerialNo:" + tBusinessDelegate.getResult().get(0));
					loggerDebug("PersonPayPlanSave","mCount:" + tBusinessDelegate.getResult().get(1));
					Content=Content+"���ܹ�����"+ tBusinessDelegate.getResult().get(1)+"���߸���¼��";
				}
				else {
					Content = "�߸�ʧ�ܣ�ԭ�������1��ȫ��������߸� 2�޿ɴ߸��������";
					FlagStr = "Fail";
				}
			}
		}		
	}   
%>    
      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=mSerialNo%>","<%=mCount%>");
</script>
</html>

