<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�DirectDelRiskSave.jsp
//�����ܣ�ֱ������ɾ���ύ��Ϣ����
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����   
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%

	TransferData tTransferData = new TransferData(); 
	//ContInsuredUI tContInsuredUI   = new ContInsuredUI();
	String busiName="tbContInsuredUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	//�������
	String FlagStr = "";
	String Content = "";
	
	GlobalInput tGI = new GlobalInput(); 
	tGI=(GlobalInput)session.getValue("GI");   
	loggerDebug("DirectDelRiskSave","tGI"+tGI);
	if(tGI==null)
	{
		loggerDebug("DirectDelRiskSave","ҳ��ʧЧ,�����µ�½");   
		FlagStr = "Fail";        
		Content = "ҳ��ʧЧ,�����µ�½";  
	}
	else  
	{
		CErrors tError = null;
		String tBmCert = "";
		String fmAction=request.getParameter("fmAction");
	
		tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); 
		tTransferData.setNameAndValue("PolNo",request.getParameter("polno"));             
		try
		{
			// ׼���������� VData
			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tGI);
			loggerDebug("DirectDelRiskSave","�ύ����==="+fmAction);  
			loggerDebug("DirectDelRiskSave","InsuredNo=="+request.getParameter("InsuredNo"));
			loggerDebug("DirectDelRiskSave","polno=="+request.getParameter("polno")); 
			if ( tBusinessDelegate.submitData(tVData,fmAction,busiName))
			{
				Content ="����ɾ���ɹ���";
				FlagStr = "Succ";
			}
		}
		catch(Exception ex)
		{
		Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
		FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr=="")
		{
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError())
			{                          
			Content ="����ɾ���ɹ���";
			FlagStr = "Succ";
			}
			else                                                                           
			{
			Content = "ɾ��ʧ�ܣ�ԭ����:" + tError.getFirstError();
			FlagStr = "Fail";
			}
		}
		loggerDebug("DirectDelRiskSave","FlagStr:"+FlagStr+"Content:"+Content);
	
	} 
%>                                       
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit1("<%=FlagStr%>","<%=Content%>");
</script>
</html>

