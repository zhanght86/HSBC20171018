
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>
<%	         
	
	String FlagStr="";      //�������
	String Content = "";    //����̨��Ϣ
	String tAction = "";    //�������ͣ�delete update insert
	String tOperate = "";   //��������
	String mAction = request.getParameter("fmAction");
	String tTheCode = "";
	
	VData tData = new VData();
	//LDCodeMod tLDCodeMod = new LDCodeMod();
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	LDCodeModSchema tLDCodeModSchema = new LDCodeModSchema();
	TransferData mTransferData = new TransferData();

	String tSendObj=request.getParameter("BackObj"); //�õ����Ͷ��������Ϊ���������ĵ�һλ
	//tongmeng 2009-02-09 add
	//��������ɿͻ��Լ�¼��
	String tQuestCode = request.getParameter("QuestCode"); //���������ĺ���λ
	/*
	if(!"INSERT".equals(mAction)){
		String tQuestCode = request.getParameter("QuestCode"); //���������ĺ���λ
		tTheCode = tSendObj+tQuestCode;
	}else{
		tTheCode = tSendObj;
	}*/
	tTheCode = tQuestCode;
	mTransferData.setNameAndValue("TheCode",tTheCode);
	String tContent = request.getParameter("Content"); //���������
	String tRecordQuest = request.getParameter("RecordQuest"); //�Ƿ�������������
	
	tLDCodeModSchema.setCont(tContent);
	tLDCodeModSchema.setSendObj(tSendObj);
	//tLDCodeModSchema.setCode(tTheCode);
	tLDCodeModSchema.setRecordQuest(tRecordQuest);
	tLDCodeModSchema.setCodeType("Question");
	tLDCodeModSchema.setOperator(tGI.Operator);
	loggerDebug("QusetContentInputSave","�õ������������Ϊ��"+tTheCode);
	//tAction = request.getParameter("mAction");
	
	loggerDebug("QusetContentInputSave","�����ǣ�"+mAction);
	
  // ׼���������� VData
	tData.add( tGI );
	tData.add( mTransferData );
	tData.add( tLDCodeModSchema );
	//DealWithQuestUI tDealWithQuestUI = new DealWithQuestUI();
	String busiName="tbDealWithQuestUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData( tData, mAction,busiName ))
	{
		int n = tBusinessDelegate.getCErrors().getErrorCount();
		String strErr = "\\n";
		for(int i=0;i<n;i++){
			strErr += (i+1)+":"+tBusinessDelegate.getCErrors().getError(i).errorMessage+";\\n";
		}
		Content = " ����ʧ�ܣ�ԭ����: " +strErr;
		FlagStr = "Fail";
		loggerDebug("QusetContentInputSave",Content);
	}
	else
	{   
		Content = " �����ɹ�! ";
		FlagStr = "Succ";
		loggerDebug("QusetContentInputSave",Content);
		if(mAction.equals("INSERT")){
			VData tVData = new VData();
			TransferData tTransferData = new TransferData();
			tVData = tBusinessDelegate.getResult();
			tTransferData=(TransferData) tVData.getObjectByObjectName("TransferData", 0);
			String tCode="";
			tCode=(String)tTransferData.getValueByName("Code");
			String mQuestCode = tCode.substring(1,4);
			%>
			<script language="javascript">
			parent.fraInterface.fm.QuestCode.value=<%=mQuestCode%>;
			</script>
			<%
		}
	}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
