<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�bomSave.jsp
	//�����ܣ���ӣ��޸�BOM����
	//�������ڣ� 
	//������  �� 
	//���¼�¼��
	//
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.ibrms.*"%>
<%
	//������Ϣ������У�鴦��
	//�������
	LDMsgInfo_BOMSchema tLDMsgInfo_BOMSchema = new LDMsgInfo_BOMSchema();
	LDMsgInfoUI tLDMsgInfoUI = new LDMsgInfoUI();
	//�������
	CErrors tError = null;
	String tBmCert = "";
	//����Ҫִ�еĶ�������ӣ��޸�
	String transact = request.getParameter("hiddenAction");
	loggerDebug("LDMsgInfoInputSave","transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLDMsgInfo_BOMSchema.setLanguage(request.getParameter("MsgLan").trim());
	//    if(request.getParameter("cName").length()>0)
	tLDMsgInfo_BOMSchema.setKeyID(request.getParameter("hiddenKeyID").trim());
	tLDMsgInfo_BOMSchema.setMsg(request.getParameter("MsgDetail"));
	tLDMsgInfo_BOMSchema.setMsgType(request.getParameter("MsgType"));
	
		// ׼���������� VData
		VData tVData = new VData();
		tVData.addElement(tLDMsgInfo_BOMSchema);
		tVData.addElement(transact);
		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		
		if(!tLDMsgInfoUI.submitData(tVData, "MSGBOM"))
		{
			if (FlagStr == "") {
				tError = tLDMsgInfoUI.mErrors;
				if (!tError.needDealError()) {
					loggerDebug("LDMsgInfoInputSave","�ɹ�");
					Content = "����ɹ�";
					FlagStr = "Succ";
					
				} else {
					loggerDebug("LDMsgInfoInputSave","ʧ��");
					Content = transact + "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	loggerDebug("LDMsgInfoInputSave","FlagStr:"+FlagStr);
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
