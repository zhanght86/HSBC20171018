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
	LDMsgInfo_MsgSchema tLDMsgInfo_MsgSchema = new LDMsgInfo_MsgSchema();
	LDMsgInfoUI tLDMsgInfoUI = new LDMsgInfoUI();
	//�������
	CErrors tError = null;
	String tBmCert = "";
	//����Ҫִ�еĶ�������ӣ��޸�
	String transact = request.getParameter("hiddenAction");
	loggerDebug("LDMsgResultSave","transact:"+transact);
	
	String tRela = "";
	String FlagStr = "";
	String Content = "";
	//    if(request.getParameter("eName").length()>0)
	tLDMsgInfo_MsgSchema.setLanguage(request.getParameter("MsgLan").trim());
	//    if(request.getParameter("cName").length()>0)
	tLDMsgInfo_MsgSchema.setKeyID(request.getParameter("hiddenKeyID").trim());
	tLDMsgInfo_MsgSchema.setMsg(request.getParameter("MsgDetail"));
	
	
		// ׼���������� VData
		VData tVData = new VData();
		tVData.addElement(tLDMsgInfo_MsgSchema);
		tVData.addElement(transact);
		//ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
		
		if(!tLDMsgInfoUI.submitData(tVData, "MSG"))
		{
			if (FlagStr == "") {
				tError = tLDMsgInfoUI.mErrors;
				if (!tError.needDealError()) {
					loggerDebug("LDMsgResultSave","�ɹ�");
					Content = "����ɹ�";
					FlagStr = "Succ";
					
				} else {
					loggerDebug("LDMsgResultSave","ʧ��");
					Content = transact + "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
					FlagStr = "Fail";
				}
			}	
		}			
	loggerDebug("LDMsgResultSave","FlagStr:"+FlagStr);
%>

<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
